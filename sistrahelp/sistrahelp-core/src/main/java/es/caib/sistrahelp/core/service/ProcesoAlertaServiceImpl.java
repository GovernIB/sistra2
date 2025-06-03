package es.caib.sistrahelp.core.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.digester.plugins.PluginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.plugins.email.api.EmailPluginException;
import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistrahelp.core.api.exception.CargaConfiguracionException;
import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.ResultadoEventoAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoEventoCM;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ProcesoAlertaService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.component.SistragesApiComponent;
import es.caib.sistrahelp.core.service.component.SistramitApiComponent;
import es.caib.sistrahelp.core.service.repository.dao.AlertaDao;
import es.caib.sistrahelp.core.service.repository.dao.HistorialAlertaDao;

/**
 * La clase DominioServiceImpl.
 */

@Service
@Transactional
public class ProcesoAlertaServiceImpl implements ProcesoAlertaService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(ProcesoAlertaServiceImpl.class);

	/** Alertas DAO. */
	@Autowired
	private AlertaDao alertaDao;

	/** HistorialAlertaDao DAO. */
	@Autowired
	private HistorialAlertaDao historialAlertaDao;

	@Autowired
	private SistragesApiComponent sistragesApiComponent;

	@Autowired
	private SistramitApiComponent sistramitApiComponent;

	private int tramIni;

	private int tramFin;

	private int errTot;

	private int pagIni;

	private int pagFin;

	private int regIni;

	private int regFin;

	private int formIni;

	private int formFin;

	private int firmaIni;

	private int firmaFinOk;

	private Alerta alert;

	private List<ErroresPorTramiteCM> listaErrores;

	private List<EventoCM> listaTramErrores;

	private List<ErroresPorTramiteCM> listaInacabados;

	private FiltroAuditoriaTramitacion filtros;

	private List<EventoAuditoriaTramitacion> listaDatos;

	private List<EventoAuditoriaTramitacion> listaDatosEventosPlataforma;

	private List<EventoCM> listaErrPlat;

	private List<HistorialAlerta> listaAlertas;

	private FiltroAuditoriaTramitacion filtrosInacabados;

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales = recuperarConfiguracionProperties();
	private Integer umbralNormalAtencionProperties;
	private Integer umbralAtencionRevisarProperties;

	ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine interprete = manager.getEngineByName("js");

	@Override
    @NegocioInterceptor
    public boolean procesarAlertas(Alerta al) {
		boolean evaluacion = false;
		String expEv = evaluarAlerta(al);
		if(expEv != null) {
			if (!expEv.equals("RESUMEN_DIARIO") && !expEv.equals("RESUM_DIARI")) {
				enviarEmail(al,expEv);
				anadirHistorial(al,expEv);
				evaluacion = true;
			} else {
				enviarResumenDiario(al);
				evaluacion = true;
			}
		}
		alertaDao.updateUltimaVerificacion(al.getCodigo());
		return evaluacion;
    }

	private String evaluarAlerta(Alerta al) {
		if (!al.getNombre().equals("RESUMEN_DIARIO") && !al.getNombre().equals("RESUM_DIARI")) {
			String grupoAnterior = "1";

			log.debug("ALERTAS STH: Inicia el hilo para evaluar la alerta: " + al.getNombre());
			List<String> eventos = al.getEventos();
			String[] partes = null;
			FiltroAuditoriaTramitacion faut = new FiltroAuditoriaTramitacion(al.getListaAreas(), false, false);
			if(al.getTipo().equals("T")) {
				faut.setIdTramite(al.getTramite());
			}else if(al.getTipo().equals("V")) {
				faut.setIdTramite(al.getTramite());
				faut.setVersionTramite(al.getVersion());
			}
		    String condicion = "";
		    String expresionCorreoHistorial = "";

			for (int i=0; i<eventos.size(); i++) {
				partes = eventos.get(i).split(":");
			    boolean cambioGrupo = false;
				String grupo = partes[1];
				if (i > 0) {
					if (!grupoAnterior.equals(grupo)) {
						grupoAnterior = grupo;
						cambioGrupo = true;
					}
				}
				String and_or = "";
				String not = "";
				String and_orStr = "";
				String notStr = "";
				faut.setEvento(TypeEvento.fromString(partes[4]));
				Long eventoLong = countAuditoriaEvento(faut);
				String evento = String.valueOf(eventoLong);
				String eventoStr = TypeEvento.fromString(partes[4]).name();
				String operador = partes[5];
				String operadorStr = partes[5];
				String concurrencia = partes[6];

				if (!partes[2].equals("null")) {
					if (partes[2].equals("AND")) {
						and_or = "&&";
						and_orStr = "AND";
					} else {
						and_or = "||";
						and_orStr = "OR";
					}
				}

				if (partes[3].equals("false")) {
					not = "";
					notStr = "";
				} else {
					not = "!";
					notStr = " NOT";
				}

				if (partes[5].equals("=")) {
					operador = "==";
					operadorStr = "=";
				}

				if (i == 0) {
					condicion += "(" + not + evento + operador + concurrencia;
					expresionCorreoHistorial += "(" + notStr + " " + eventoStr + " [" + evento + "] " + operadorStr + " " + concurrencia;
					if (eventos.size() == 1) {
						condicion += " )";
						expresionCorreoHistorial += " )";
					}
				} else {
					if (cambioGrupo) {
						condicion += ")" + and_or + "(" + not + evento + operador + concurrencia;
						expresionCorreoHistorial += " )" + " " + and_orStr + " " + "(" + notStr + " " + eventoStr + " [" + evento + "] " + operadorStr + " " + concurrencia;
					} else {
						condicion += and_or + not + evento + operador + concurrencia;
						expresionCorreoHistorial += " " + and_orStr + " " + notStr + " " + eventoStr + " [" + evento + "] " + operadorStr + " " + concurrencia;
					}
					if (i == eventos.size() - 1) {
						condicion += ")";
						expresionCorreoHistorial += " )";
					}
				}
			}
			Boolean evaluacion = null;
			try {
				evaluacion = (Boolean)interprete.eval(condicion);
			} catch (ScriptException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.debug("ALERTAS STH: La condición " + condicion + " de la alerta " + al.getNombre() + " se evalua como " + evaluacion);

			return evaluacion ? expresionCorreoHistorial : null;
		} else {
			return "RESUMEN_DIARIO";
		}
	}

	private void enviarEmail(Alerta al, String expresionCorreoHistorial) {

		try {
			String nombre = "";
			String logo = "";
		    byte[] imageBytes = null;
			if (al.getListaAreas() != null && !al.getListaAreas().isEmpty()
					&& al.getListaAreas().get(0) != null) {
				//Entidad entidad = sistragesApiComponent.obtenerDatosEntidad(alert.getListaAreas().get(0));
				logo = sistramitApiComponent.urlLogoEntidad(al.getIdEntidad());
				try {
					imageBytes = sistragesApiComponent.urlLogoEntidad(al.getIdEntidad());
				} catch (Exception e) {
					log.error("ALERTAS STH: Revisar porque puede que la id entidad esté mal (tiene que ser dir3, no id): " + al.getIdEntidad());
					log.error("ALERTAS STH: Error obteniendo la imagen" , e);
				}
				Entidad entidad = sistragesApiComponent.obtenerDatosEntidad(al.getIdEntidad());
				if (entidad.getNombre() != null) {
					nombre = entidad.getNombre().getTraduccion("ca");
				}
			}
			final IEmailPlugin plgEmail = (IEmailPlugin) sistragesApiComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
			String entorno="";
			final String pathProperties = System.getProperty("es.caib.sistrahelp.properties.path");
			try (FileInputStream fis = new FileInputStream(pathProperties);) {
				final Properties props = new Properties();
				props.load(fis);
				entorno = props.getProperty("entorno").toUpperCase();
			} catch (final IOException e) {
				log.error("ALERTAS STH: Error obteniendo entorno" , e);
			}
			String msg =
			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ca\" lang=\"ca\">\r\n" + "\r\n"
					+ "<head>\r\n" + "\r\n"
					+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n"
					+ "	<title>" + nombre + "</title>\r\n" + "\r\n" + "	<!-- css -->\r\n"
					+ "	<style type=\"text/css\">\r\n"
					+ "		#contenidor { width:90%; font:normal 80% 'TrebuchetMS', 'Trebuchet MS', Arial, Helvetica, sans-serif; color:#000; margin:1em auto; background-color:#fff; }\r\n"
					+ "		#cap { font-size:1.2em; font-weight:bold; text-align:center; margin-bottom:1em; }\r\n"
					+ "		#continguts { padding:1em 2em; border:1em solid #f2f2f2; }\r\n"
					+ "		#continguts h1 { font-size:1.4em; margin-top:0; margin-bottom:1em; }\r\n"
					+ "		#continguts table { margin-bottom:1.5em; border:0; empty-cells:hide; border-collapse:collapse; }\r\n"
					+ "		#continguts table th { float:left; width:10em; font-size:1.1em; font-variant:small-caps; font-weight:normal; text-align:right; padding-right:.8em; }\r\n"
					+ "		#continguts table td { font-weight:bold; padding-bottom:.5em; }\r\n"
					+ "		#continguts h2 { font-size:1.1em; margin:.8em 0; }\r\n"
					+ "		#continguts p { margin:.8em 0; }\r\n"
					+ "		#contenidor p.peu { margin:1.5em 0; padding:1em; border:1px solid #ccc; }\r\n"
					+ "		#contenidor div.accedir { padding:1em; background-color:#f7f7f7; }\r\n"
					+ "		#contenidor a.accedirCertificado { display:block; font-size:1.5em; text-align:center; padding:1em; background-color:#f7f7f7; }\r\n"
					+ "		#contenidor a.accedirClave { display:block; font-size:1.5em; text-align:center; padding:1em; background-color:#f7f7f7; }\r\n"
					+ "		#contenidor p.auto { margin:1.5em 0; padding:1em;  font-size:0.9em; font-style: italic;}\r\n"
					+ "	</style>\r\n" + "	<!-- /css -->\r\n" + "\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
					+ "\r\n" + "	<!-- contenidor -->\r\n" + "	<div id=\"contenidor\">\r\n" + "\r\n"
					+ "		<!-- logo illes balears -->\r\n" + "		<div id=\"cap\">\r\n";
					if (imageBytes != null && imageBytes.length > 0) {
						String encoded = Base64.getEncoder().encodeToString(imageBytes);
						msg += "            <img  src=\"data:image/jpeg;base64," + encoded + "\" alt=\"logo\" width=\"100\" height=\"100\" style=\"width: 100px;height:100px;\"/>";
					} else if( logo != null && !logo.isEmpty()) {
						msg += "            <img  src=\"" + logo + "\" alt=\"logo\" width=\"100\" height=\"100\"/>";
					}
//					+ "			<h3> LOGO </h3>\r\n"
					msg += "			<h1>" + nombre.toUpperCase() + "</h1>\r\n" + "		</div>\r\n"
					+ "		<!-- /logo illes balears -->\r\n" + "\r\n" + "		<!-- continguts -->\r\n"
					+ "	  <div id=\"continguts\">\r\n" + "\r\n" + "			<!-- titol -->\r\n"
					+ "			<h1>\r\n"
					+ "				Aquest missatge ha estat generat pel sistema d'alertes de SISTRAHELP"
					+ "</h1>" + "<h2>L&#39;expressi&#243;: \"" + expresionCorreoHistorial + "\" de l&#39;Alerta: \""
					+ al.getNombre() + "\" (configurada per l&#39;entitat: "+al.getListaAreas().get(0).split("\\.")[0];
						if(al.getTipo().equals("A")) {
							msg += ", &#224;rea: " + al.getListaAreas().get(0).split("\\.")[1];
						}else if(al.getTipo().equals("T")) {
							msg += ", &#224;rea: " + al.getListaAreas().get(0).split("\\.")[1] + " i tr&#224;mit: "+ al.getTramite();
						}else if(al.getTipo().equals("V")) {
							msg += ", &#224;rea: " + al.getListaAreas().get(0).split("\\.")[1] + ", tr&#224;mit: "+ al.getTramite() + " i versi&#243;: "+ al.getVersion();
						}
					msg += ") s&#39;ha avaluat com a certa."
					+ " En aquesta expressió apareixen entre claudàtors el nombre de vegades que s'ha produït l'esdeveniment corresponent durant el dia d'avui."// + countAuditoriaEvento(faut) + " vegades." + "			</h2>\r\n" + "\r\n"
					+ "		<!-- /continguts -->\r\n" + "\r\n" + "\r\n" + "</div>"
					+ "	<p class=\"auto\">MOLT IMPORTANT: Aquest correu s&#39;ha generat de forma autom&#224;tica. Si us plau no s&#39;ha de respondre a aquest correu.</p>\r\n"
					+ "\r\n" + "	</div>\r\n" + "	<!-- /contenidor -->\r\n" + "\r\n" + "</body>\r\n"
					+ "</html>";
			//plgEmail.envioEmail(alertaDao.getByCodigo(alCod).getEmail(), "SISTRAHELP: AVÍS - " + alertaDao.getByCodigo(alCod).getNombre() + " - "
			//		+ entorno, msg, null, DatatypeConverter.printBase64Binary(imageBytes));
				plgEmail.envioEmail(al.getEmail(), "SISTRAHELP: AVÍS - " + al.getNombre() + " - "
						+ entorno, msg, null);
		} catch (EmailPluginException e) {
			log.error("ALERTAS STH: Error en el plugin de email " , e);
		} catch (PluginException e) {
			log.error("ALERTAS STH: Error de plugin " , e);
		} catch (Exception e) {
			log.error("ALERTAS STH: Error general enviando el email " , e);
		}
	}

	private void anadirHistorial(Alerta al, String expresionCorreoHistorial) {
		HistorialAlerta hA = new HistorialAlerta();
		hA.setAlerta(al);
		hA.setEvento(expresionCorreoHistorial);
		hA.setFecha(new Date());
		historialAlertaDao.add(hA);
	}

	private Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		Long resultado = null;
		FiltroAuditoriaTramitacion filtroAuditoriaTramitacion = null;

		filtroAuditoriaTramitacion = new FiltroAuditoriaTramitacion(pFiltroBusqueda);
		filtroAuditoriaTramitacion.setSoloContar(true);

		final ResultadoEventoAuditoria resultadoEventoAuditoria = sistramitApiComponent
				.obtenerAuditoriaEvento(filtroAuditoriaTramitacion, null);
		if (resultadoEventoAuditoria != null) {
			resultado = resultadoEventoAuditoria.getNumElementos();
		}

		return resultado;
	}

	private void enviarResumenDiario(Alerta alert) {

		umbralNormalAtencionProperties = Integer.valueOf(propiedadesLocales.getProperty(TypePropiedadConfiguracion.UMBRAL_NORMAL_ATENCION.toString()));
		umbralAtencionRevisarProperties = Integer.valueOf(propiedadesLocales.getProperty(TypePropiedadConfiguracion.UMBRAL_ATENCION_REVISAR.toString()));

		log.debug("ALERTAS STH: Entra en ProcesoAlertaResumenDiarioServiceImpl.enviarResumenDiario()");
		String nombre = "";
		String logo = "";
		byte[] imageBytes = null;
		if (alert.getListaAreas() != null && !alert.getListaAreas().isEmpty() && alert.getListaAreas().get(0) != null) {
			//Entidad entidad = sistragesApiComponent.obtenerDatosEntidad(alert.getListaAreas().get(0));
			logo = sistramitApiComponent.urlLogoEntidad(alert.getIdEntidad());
			try {
				imageBytes = sistragesApiComponent.urlLogoEntidad(alert.getIdEntidad());
			} catch (Exception e) {
				log.error("ALERTAS STH: Revisar porque puede que la id entidad esté mal (tiene que ser dir3, no id): " + alert.getIdEntidad());
				log.error("ALERTAS STH: Error obteniendo la imagen" , e);
			}
			Entidad entidad = sistragesApiComponent.obtenerDatosEntidad(alert.getIdEntidad());
			if (entidad.getNombre() != null) {
				nombre = entidad.getNombre().getTraduccion("ca");
			}
		}
		log.debug("ALERTAS STH: Entra en TimerReinicioDiario.run. Día de los datos a mostrar: " + getYesterday().toString());
		if (alert != null) {
			buscar(alert);
			String formPor;
			if (formIni != 0) {
				formPor = formatDouble((100 - ((Double.valueOf(formFin) * 100) / Double.valueOf(formIni))));
			} else {
				if (formFin == 0) {
					formPor = "0,00";
				} else {
					formPor = "100,00";
				}
			}
			String firmaPor;
			if (firmaIni != 0) {
				firmaPor = formatDouble((100 - ((Double.valueOf(firmaFinOk) * 100) / Double.valueOf(firmaIni))));
			} else {
				if (firmaFinOk == 0) {
					firmaPor = "0,00";
				} else {
					firmaPor = "100,00";
				}
			}
			String pagosPor;
			if (pagIni != 0) {
				pagosPor = formatDouble((100 - ((Double.valueOf(pagFin) * 100) / Double.valueOf(pagIni))));
			} else {
				if (pagFin == 0) {
					pagosPor = "0,00";
				} else {
					pagosPor = "100,00";
				}
			}
			String registrosPor;
			if (regIni != 0) {
				registrosPor = formatDouble((100 - ((Double.valueOf(regFin) * 100) / Double.valueOf(regIni))));
			} else {
				if (regFin == 0) {
					registrosPor = "0,00";
				} else {
					registrosPor = "100,00";
				}
			}
			String tramPor;
			if (tramIni != 0) {
				tramPor = formatDouble(100 - ((Double.valueOf(tramFin) * 100) / Double.valueOf(tramIni)));
			} else {
				if (tramFin == 0) {
					tramPor = "0,00";
				} else {
					tramPor = "100,00";
				}
			}

			String msg = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ca\" lang=\"ca\">" + "   <head>"
					+ "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + "      <title>"
					+ nombre + "</title>" + "      <!-- css -->" + "   </head>" + "   <body>"
					+ "      <!-- contenidor --> 	"
					+ "      <div id=\"contenidor\" style=\"width: 100%;font: normal 80% 'TrebuchetMS', 'Trebuchet MS', Arial, Helvetica, sans-serif;color: #000;background-color: #fff;\">"
					+ "         <!-- logo illes balears --> 		"
					+ "         <div id=\"cap\" style=\"font-size: 1.2em;font-weight: bold;text-align: center;margin-bottom: 1em;\">";

			if (imageBytes != null && imageBytes.length > 0) {
				String encoded = Base64.getEncoder().encodeToString(imageBytes);
				msg += "            <img  src=\"data:image/jpeg;base64," + encoded + "\" alt=\"logo\" width=\"100\" height=\"100\" style=\"width: 100px;height:100px;\"/>";
			} else if( logo != null && !logo.isEmpty()) {
				msg += "            <img  src=\"" + logo + "\" alt=\"logo\" width=\"100\" height=\"100\"/>";
			}

	//				+ "			   <h3> LOGO </h3>"
					msg += "            <h1>" + nombre.toUpperCase() + "</h1>" + "         </div>"
					+ "         <!-- /logo illes balears -->  		<!-- continguts -->"
					+ "         <div id=\"continguts\" style=\"padding: 1em;border: 1em solid #f2f2f2;\">"
					+ "            <!-- titol --> 			"
					+ "            <h1 style=\"font-size: 1.4em;margin-top: 0;margin-bottom: 1em;\">"
					+ "               Aquest missatge ha estat generat pel sistema d'alertes de SISTRAHELP y ofereix un resum dels events recogits al Quadre de comandament de SISTRAHELP, pel "
					+ "               " + parseFecha(getYesterday()) + ": 			" + "            </h1>"
					+ "            </div><div style=\"height:20px;\"></div>"
					+ "            <table id=\"form:tablaCuadroMando\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
					+ "               <tbody>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">ACCIONS</span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
					+ "                                 <td style=\"background-color: lightgrey; border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Iniciats</span></td>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Finalitzats</span></td>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Percen&shy;tatge d&#39;accions<wbr> no<wbr> finali&shy;zades</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
					+ "                              </tr>" + "                              <tr>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Emplenar formulari</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ formIni + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ formFin + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ formPor + "%</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ nivelGravedad(formPor) + "</td>" + "                              </tr>"
					+ "                              <tr>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Firmar</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ firmaIni + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ firmaFinOk + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ firmaPor + "%</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ nivelGravedad(firmaPor) + "</td>" + "                              </tr>"
					+ "                              <tr>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Pagar</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ pagIni + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ pagFin + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ pagosPor + "%</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ nivelGravedad(pagosPor) + "</td>" + "                              </tr>"
					+ "                              <tr>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Registrar</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ regIni + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ regFin + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ registrosPor + "%</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ nivelGravedad(registrosPor) + "</td>" + "                              </tr>"
					+ "                           </tbody>" + "                        </table>"
					+ "                     </td>" + "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\">Tr&#224;mits</span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Iniciades</span></td>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Finalitzades</span></td>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Percen&shy;tatge de tr&#224;mits<wbr> no<wbr> finali&shy;tzats</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
					+ "                              </tr>" + "                              <tr>"
					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Tr&#224;mits</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ tramIni + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ tramFin + "</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ tramPor + "%</td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ nivelGravedad(tramPor) + "</td>" + "                              </tr>"
					+ "                           </tbody>" + "                        </table>"
					+ "                     </td>" + "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <div style=\"height:20px;\"></div>" + "                     </td>"
					+ "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey; font-size: 1.2em !important;\">NOMBRE TOTAL D&#39;ERRORS (TRAMITACI&#211; + PLATAFORMA)</span></td>"
					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
					+ errTot + "</td>" + "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-top: .5em;\"><span style=\"font-weight: bold; \"><u>Errors de tramitaci&#243; (Errors per Tr&#224;mit):</u></span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
					+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                          <tbody>" + "                             <tr>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Tr&#224;mit</span></td>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Versi&#243;</span></td>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Sessions finalitzades</span></td>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Sessions<wbr> no<wbr> finalitzades</span></td>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Percen&shy;tatge de sessions<wbr> no<wbr> finali&shy;tzades</span></td>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Suma d&#39;errors</span></td>"
					+ "                             </tr>";
			if (listaErrores != null && !listaErrores.isEmpty()) {
				for (ErroresPorTramiteCM lerr : listaErrores) {
					String [] idTramite = lerr.getIdTramite().split("\\.");
					msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ idTramite[0]+".<wbr>"+ idTramite[1]+".<wbr>"+ idTramite[2] + "</td>"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerr.getVersion() + "</td>"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerr.getSesionesFinalizadas() + "</td>"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerr.getSesionesInacabadas() + "</td>"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ formatDouble(lerr.getPorcentage()) + "%</td>"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerr.getNumeroErrores() + "</td>" + "                             </tr>";
				}
			} else {
				msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al dia d'ahir</td>"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                             </tr>";
			}
			msg += "                          </tbody>" + "                       </table>"
					+ "                    </td>" + "                 </tr>" + "                  ";
			msg += " <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-top: .5em;\"><span style=\"font-weight: bold; \"><u>Errors de tramitaci&#243; (Tr&#224;mits per Error):</u></span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
					+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                          <tbody>" + "                             <tr>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Error</span></td>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Suma d&#39;errors</span></td>"
					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Percen&shy;tatge d&#39;errors</span></td>"
					+ "                             </tr>";
			if (listaTramErrores != null && !listaTramErrores.isEmpty()) {
				for (EventoCM ltrerr : listaTramErrores) {
					msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ ltrerr.getTipoEvento() + "</td>"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ ltrerr.getConcurrencias() + "</td>"
							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ formatDouble(ltrerr.getPorc()) + "%</td>        </tr>";
				}
			} else {
				msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al dia d'ahir</td>"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                             </tr>";
			}
			msg += "                          </tbody>" + "                       </table>"
					+ "                    </td>" + "                 </tr>" + "                  "
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\"><u>Errors de Plataforma</u></span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                            <tbody>" + "                               <tr>"
					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Error</span></td>"
					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Suma d&#39;errors</span></td>"
					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Percen&shy;tatge d&#39;errors</span></td>"
					+ "                               </tr>";
			if (listaErrPlat != null && !listaErrPlat.isEmpty()) {
				for (EventoCM lerrp : listaErrPlat) {
					msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerrp.getTipoEvento() + "</td>"
							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerrp.getConcurrencias() + "</td>"
							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ formatDouble(lerrp.getPorc()) + "%</td>  " + "                               </tr>";
				}
			} else {
				msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al dia d'ahir</td>"
						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>  "
						+ "                               </tr>";
			}
			msg += "                            </tbody>" + "                         </table>"
					+ "                     </td>" + "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <div style=\"height:20px;\"></div>" + "                     </td>"
					+ "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">TR&#192;MITS<wbr> NO<wbr> FINALI&shy;TZATS<wbr> SENSE<wbr> ERRORS</span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
					+ "                            <tbody>" + "                               <tr>"
					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Tr&#224;mit</span></td>"
					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Versi&#243;</span></td>"
					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Sessions<wbr> no<wbr> finali&shy;tzades</span></td>"
					+ "                               </tr>";
			if (listaInacabados != null && !listaInacabados.isEmpty()) {
				for (ErroresPorTramiteCM lerri : listaInacabados) {
					msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerri.getIdTramite() + "</td>"
							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerri.getVersion() + "</td>"
							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
							+ lerri.getSesionesInacabadas() + "</td>  " + "                               </tr>";
				}
			} else {
				msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al dia d'ahir</td>"
						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>  "
						+ "                               </tr>";
			}
			msg += "                            </tbody>" + "                         </table>"
					+ "                     </td>" + "                  </tr>" + "			<tr>"
					+ "                    <td style=\"font-weight: bold;\">"
					+ "                       <div style=\"height:20px;\"></div>" + "                    </td>"
					+ "                 </tr>"
	//				+ "					<tr>"
	//				+ "                    <td style=\"font-weight: bold;\">"
	//				+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
	//				+ "                          <tbody>" + "                             <tr>"
	//				+ "                                <td style=\"padding-left: 10px;background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\">Historial d&#39;alertes</span></td>"
	//				+ "                             </tr>" + "                          </tbody>"
	//				+ "                       </table>" + "                    </td>" + "                 </tr>"
	//				+ "                 <tr>"
	//				+ "                    <td style=\"font-weight: bold;\">"
	//				+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
	//				+ "                           <tbody>" + "                              <tr>"
	//				+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Alerta</span></td>"
	//				+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Data</span></td>"
	//				+ "                              </tr>";
	//		if (listaAlertas != null && !listaAlertas.isEmpty()) {
	//			for (HistorialAlerta lerra : listaAlertas) {
	//				msg += "                              <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
	//						+ "                                 <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
	//						+ lerra.getAlerta().getNombre() + " - " + lerra.getEvento() + "</td>"
	//						+ "                                 <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
	//						+ convertirFecha(lerra.getFecha()) + "</td>" + "                              </tr>";
	//			}
	//		} else {
	//			msg += "                              <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
	//					+ "                                 <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al dia d'ahir</td>"
	//					+ "                                 <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
	//					+ "                              </tr>";
	//		}
	//		msg += "                           </tbody>" + "                        </table>"
	//				+ "                    </td>" + "                 </tr>" + "               </tbody>"
	//				+ "            </table>" + "            "
					+ "         </div>"
					+ "         <p style=\"margin: 1.5em 0;padding: 1em;font-size: 0.9em;font-style: italic;\">MOLT IMPORTANT: Aquest correu s&#39;ha generat de forma autom&#224;tica. Si us plau no s&#39;ha de respondre a aquest correu.</p>"
					+ "      </div>" + "   </body>" + "</html>";
			log.debug("ALERTAS STH: Resumen diario enviado");
			//enviarEmail(msg, DatatypeConverter.printBase64Binary(imageBytes));
			enviarEmail(alert, msg, null);
			purgarHistorial(historialAlertaDao.getAllByFiltro(null, null));
			purgarAlertas();
	//		TimerHilosAlertas tAl = new TimerHilosAlertas();
	//		tAl.run(aService, hService, confService, historialService);
		} else {
			log.error("ALERTAS STH: L'alerta RESUMEN_DIARIO no és creada.");
		}
	}

	private Date getYesterday() {
		//return Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

		Date date = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

	private Date getNow() {
		Date date = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		//Date date = new Date();
		return date;
	}

	private void purgarAlertas() {
		List<Alerta> alertasEliminar = alertaDao.listarAlertaActivo(null, false);
		for (Alerta a : alertasEliminar) {
			alertaDao.remove(a.getCodigo());
		}
	}

	private String nivelGravedad(String porcentageStr) {
		String texto = "";
		String porcentageParsed = porcentageStr.replace(",", ".");
		Double porcentage = Double.parseDouble(porcentageParsed);
		if (porcentage > 0) {
			if (porcentage >= umbralAtencionRevisarProperties) {
				texto = "Revisar";
			} else if (porcentage < umbralAtencionRevisarProperties && porcentage >= umbralNormalAtencionProperties) {
				texto = "Atenció";
			} else if (porcentage < umbralNormalAtencionProperties) {
				texto = "Normal";
			}
		} else {
			texto = "Normal";
		}
		return texto;
	}

	public String formatDouble(Double dbl) {
		return new DecimalFormat("0.00").format(dbl);
	}

	/**
	 * Buscar.
	 */
	private void buscar(Alerta alert) {
		tramIni = 0;
		tramFin = 0;
		pagIni = 0;
		pagFin = 0;
		regIni = 0;
		regFin = 0;
		errTot = 0;
		formIni = 0;
		formFin = 0;
		firmaIni = 0;
		firmaFinOk = 0;
		listaErrores = new ArrayList<ErroresPorTramiteCM>();
		listaTramErrores = new ArrayList<EventoCM>();
		filtros = new FiltroAuditoriaTramitacion(alert.getListaAreas(), false, false);
		filtrosInacabados = new FiltroAuditoriaTramitacion(alert.getListaAreas(), false, false);

		filtros.setFechaDesde(getYesterday());
		filtrosInacabados.setFechaDesde(getYesterday());
		filtros.setFechaHasta(getNow());
		filtrosInacabados.setFechaHasta(getNow());

		filtros.setIdTramite(null);
		filtros.setVersionTramite(null);
		filtros.setErrorPlataforma(true);
		filtrosInacabados.setIdTramite(null);
		filtrosInacabados.setVersionTramite(null);
		filtrosInacabados.setErrorPlataforma(false);
		ResultadoEventoCM resEventoCM = sistramitApiComponent.obtenerCountEventoCM(filtros);

		if (resEventoCM != null && resEventoCM.getListaEventosCM() != null
				&& !resEventoCM.getListaEventosCM().isEmpty()) {
			ponerEventos(resEventoCM.getListaEventosCM());
		}

		filtros.setSoloContar(false);
		listaErrores = sistramitApiComponent.obtenerErroresPorTramiteCM(filtros, null).getListaErroresCM();
		listaTramErrores = sistramitApiComponent.obtenerTramitesPorErrorCM(filtros, null).getListaEventosCM();

		// Filtra

		filtrosInacabados.setSoloContar(false);
		filtros.setErrorPlataforma(false);
		listaInacabados = sistramitApiComponent.obtenerErroresPorTramiteCM(filtros, null).getListaErroresCM();

		filtros.setSoloContar(false);
		listaErrPlat = sistramitApiComponent.obtenerErroresPlataformaCM(filtros, null).getListaEventosCM();

		listaAlertas = historialAlertaDao.getAllByFiltro(getYesterday(), getNow());
	}

	private String txtHacer(EventoAuditoriaTramitacion terr, String tipo) {
		if (tipo.equals("n")) {
			if (terr.getNombre() != null && terr.getApellido1() != null && terr.getApellido2() != null) {
				return terr.getNombre().concat(" ").concat(terr.getApellido1()).concat(" ").concat(terr.getApellido2());
			} else if (terr.getNombre() != null && terr.getApellido1() != null) {
				return terr.getNombre().concat(" ").concat(terr.getApellido1());
			} else if (terr.getNombre() != null) {
				return terr.getNombre();
			} else {
				return "";
			}
		} else {
			if (terr.getNif() != null) {
				return terr.getNif();
			} else {
				return "";
			}
		}

	}

	private String convertirFecha(Date fecha) {
		return new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(fecha);
	}

	private void ponerEventos(List<EventoCM> listaEventos) {
		for (EventoCM ev : listaEventos) {
			switch (TypeEvento.fromString(ev.getTipoEvento())) {
			case REGISTRAR_TRAMITE_INICIO:
				regIni = ev.getConcurrencias().intValue();
				break;
			case REGISTRAR_TRAMITE:
				regFin = ev.getConcurrencias().intValue();
				break;
			case FORMULARIO_INICIO:
				formIni = ev.getConcurrencias().intValue();
				break;
			case FORMULARIO_FIN:
				formFin = ev.getConcurrencias().intValue();
				break;
			case FIRMA_INICIO:
				firmaIni = ev.getConcurrencias().intValue();
				break;
			case FIRMA_FIN_OK:
				firmaFinOk = ev.getConcurrencias().intValue();
				break;
			case PAGO_ELECTRONICO_INICIO:
				pagIni = ev.getConcurrencias().intValue();
				break;
			case PAGO_ELECTRONICO_VERIFICADO:
				pagFin += ev.getConcurrencias();
				break;
			/*case PAGO_PRESENCIAL:
				pagFin += ev.getConcurrencias();
				break;*/
			case INICIAR_TRAMITE:
				tramIni = ev.getConcurrencias().intValue();
				break;
			case FIN_TRAMITE:
				tramFin = ev.getConcurrencias().intValue();
				break;
			case ERROR:
				errTot = ev.getConcurrencias().intValue();
				break;
			default:
				break;
			}
		}
	}

	private void purgarHistorial(List<HistorialAlerta> lHal) {
		if (lHal != null && !lHal.isEmpty()) {
			for (HistorialAlerta h : lHal) {
				historialAlertaDao.remove(h.getCodigo());
			}
		}
	}

	private void enviarEmail(Alerta alert, String msg, String img64) {
		try {
			final IEmailPlugin plgEmail = (IEmailPlugin) sistragesApiComponent.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
			log.debug("ALERTAS STH: ENTRA EN EL email TIMERREINICIODIARIO");
			log.debug("ALERTAS STH: MSG:" + msg);
			log.debug("ALERTAS STH: EMAILS:" + alert.getEmail());
			log.debug("ALERTAS STH: Entorno:" + getEntorno());
			boolean mailEnviado = plgEmail.envioEmail(alert.getEmail(), "SISTRAHELP: Resum Diari - " + getEntorno(),
					msg, null);
			log.debug("ALERTAS STH: SALE EN EL email TIMERREINICIODIARIO");

		} catch (EmailPluginException e) {
			log.error("ALERTAS STH: Error enviando el email TimerReinicioDiario" , e);
		} catch (PluginException e) {
			log.error("ALERTAS STH: Error en el plugin el email TimerReinicioDiario" , e);
		}catch (Exception e) {
			log.error("ALERTAS STH: Error general TimerReinicioDiario" , e);
		}
	}

	private String getEntorno() {
		final String pathProperties = System.getProperty("es.caib.sistrahelp.properties.path");
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props.getProperty("entorno").toUpperCase();
		} catch (Exception e) {
			log.error("ALERTAS STH: Error obteniendo entorno" , e);
		}
		return "";
	}

	private String parseFecha(Date fecha) {
		Locale spanishLocale = new Locale("ca", "ES");
		LocalDateTime fechaL = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		String dateInSpanish = fechaL.format(DateTimeFormatter.ofPattern("EEEE, dd  MMMM 'de' yyyy", spanishLocale));
		return dateInSpanish;
	}

	private String parseFechaHistorial(Date fecha) {
		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sourceFormat.format(fecha);
	}

	private Properties recuperarConfiguracionProperties() {
		final String pathProperties = System.getProperty(TypePropiedadConfiguracion.PATH_PROPERTIES.toString());
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}

	}

	/**
	 * @return the tramIni
	 */
	public final int getTramIni() {
		return tramIni;
	}

	/**
	 * @param tramIni the tramIni to set
	 */
	public final void setTramIni(int tramIni) {
		this.tramIni = tramIni;
	}

	/**
	 * @return the tramFin
	 */
	public final int getTramFin() {
		return tramFin;
	}

	/**
	 * @param tramFin the tramFin to set
	 */
	public final void setTramFin(int tramFin) {
		this.tramFin = tramFin;
	}

	/**
	 * @return the errTot
	 */
	public final int getErrTot() {
		return errTot;
	}

	/**
	 * @param errTot the errTot to set
	 */
	public final void setErrTot(int errTot) {
		this.errTot = errTot;
	}

	/**
	 * @return the pagIni
	 */
	public final int getPagIni() {
		return pagIni;
	}

	/**
	 * @param pagIni the pagIni to set
	 */
	public final void setPagIni(int pagIni) {
		this.pagIni = pagIni;
	}

	/**
	 * @return the pagFin
	 */
	public final int getPagFin() {
		return pagFin;
	}

	/**
	 * @param pagFin the pagFin to set
	 */
	public final void setPagFin(int pagFin) {
		this.pagFin = pagFin;
	}

	/**
	 * @return the regIni
	 */
	public final int getRegIni() {
		return regIni;
	}

	/**
	 * @param regIni the regIni to set
	 */
	public final void setRegIni(int regIni) {
		this.regIni = regIni;
	}

	/**
	 * @return the regFin
	 */
	public final int getRegFin() {
		return regFin;
	}

	/**
	 * @param regFin the regFin to set
	 */
	public final void setRegFin(int regFin) {
		this.regFin = regFin;
	}

	/**
	 * @return the listaErrores
	 */
	public final List<ErroresPorTramiteCM> getListaErrores() {
		return listaErrores;
	}

	/**
	 * @param listaErrores the listaErrores to set
	 */
	public final void setListaErrores(List<ErroresPorTramiteCM> listaErrores) {
		this.listaErrores = listaErrores;
	}

	/**
	 * @return the filtros
	 */
	public final FiltroAuditoriaTramitacion getFiltros() {
		return filtros;
	}

	/**
	 * @param filtros the filtros to set
	 */
	public final void setFiltros(FiltroAuditoriaTramitacion filtros) {
		this.filtros = filtros;
	}

	/**
	 * @return the listaDatos
	 */
	public final List<EventoAuditoriaTramitacion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public final void setListaDatos(List<EventoAuditoriaTramitacion> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the listaDatosEventosPlataforma
	 */
	public final List<EventoAuditoriaTramitacion> getListaDatosEventosPlataforma() {
		return listaDatosEventosPlataforma;
	}

	/**
	 * @param listaDatosEventosPlataforma the listaDatosEventosPlataforma to set
	 */
	public final void setListaDatosEventosPlataforma(List<EventoAuditoriaTramitacion> listaDatosEventosPlataforma) {
		this.listaDatosEventosPlataforma = listaDatosEventosPlataforma;
	}

	/**
	 * @return the formIni
	 */
	public final int getFormIni() {
		return formIni;
	}

	/**
	 * @param formIni the formIni to set
	 */
	public final void setFormIni(int formIni) {
		this.formIni = formIni;
	}

	/**
	 * @return the formFin
	 */
	public final int getFormFin() {
		return formFin;
	}

	/**
	 * @param formFin the formFin to set
	 */
	public final void setFormFin(int formFin) {
		this.formFin = formFin;
	}

	/**
	 * @return the firmaIni
	 */
	public final int getFirmaIni() {
		return firmaIni;
	}

	/**
	 * @param firmaIni the firmaIni to set
	 */
	public final void setFirmaIni(int firmaIni) {
		this.firmaIni = firmaIni;
	}

	public int getFirmaFinOk() {
		return firmaFinOk;
	}

	public void setFirmaFinOk(int firmaFinOk) {
		this.firmaFinOk = firmaFinOk;
	}
}