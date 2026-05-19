package es.caib.sistrahelp.frontend.controller;

import es.caib.sistra2.commons.plugins.email.api.EmailPluginException;
import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.DatosResumen;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.ResultadoEventoCM;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.api.service.MensajeEmailService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;
import org.apache.commons.digester.plugins.PluginException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

@ManagedBean
@ViewScoped
public class DialogEnviarMail extends DialogControllerBase {

	@Inject
	private HelpDeskService hService;

	@Inject
	private ConfiguracionService confService;

	@Inject
	private SessionBean sb;

	@Inject
	private MensajeEmailService mensajeEmailService;

	private String fechaDesde;
	private String fechaHasta;
	private String horaDesde;

	private String portapapeles;
	private String listaEmails;
	private String errorCopiar;
	private int tramIni;

	private int tramFin;

	private int errTot;

	private int errPlat;

	private int pagIni;

	private int pagFin;

	private int regIni;

	private int regFin;

	private int formIni;

	private int formFin;

	private int firmaIni;

	private int firmaFinOk;

	private List<ErroresPorTramiteCM> listaErrores;

	private List<EventoCM> listaTramErrores;

	private List<ErroresPorTramiteCM> listaInacabados;

	private FiltroAuditoriaTramitacion filtros;

	private List<EventoAuditoriaTramitacion> listaDatos;

	private List<EventoAuditoriaTramitacion> listaDatosEventosPlataforma;

	private List<EventoCM> listaErrPlat;

	private List<HistorialAlerta> listaAlertas;

	private FiltroAuditoriaTramitacion filtrosInacabados;

	private String umbralNormalAtencionPropertiesString;
	private String umbralAtencionRevisarPropertiesString;
	private String umbralNormalAtencionUsuarioString;
	private String umbralAtencionRevisarUsuarioString;
	private String umbralNormalAtencionString;
	private String umbralAtencionRevisarString;
	private Integer umbralNormalAtencion;
	private Integer umbralAtencionRevisar;
	private Integer umbralNormalAtencionProperties;
	private Integer umbralAtencionRevisarProperties;

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogEnviarMail.class);

	/**
	 * Inicialización.
	 */
	public void init() {
		if(fechaDesde==null && horaDesde!=null && !horaDesde.isEmpty()) {
			fechaDesde = horaDesde;
		}

		if(fechaHasta == null) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			fechaHasta = dateFormat.format(new Date());
		}

		if (umbralNormalAtencionString != null || umbralAtencionRevisarString != null) {
			umbralNormalAtencion = Integer.parseInt(umbralNormalAtencionString);
			umbralAtencionRevisar = Integer.parseInt(umbralAtencionRevisarString);
		} else {
			umbralNormalAtencion = umbralNormalAtencionProperties = Integer.parseInt(umbralNormalAtencionPropertiesString);
			umbralAtencionRevisar = umbralAtencionRevisarProperties = Integer.parseInt(umbralAtencionRevisarPropertiesString);
		}

	}

	/**
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public void aceptar() {
		if (listaEmails != null && !listaEmails.isEmpty()) {

			final String[] listaExtensiones = listaEmails.split(Constantes.LISTAS_SEPARADOR);
			for (final String cadena : listaExtensiones) {
				if (!cadena.matches(
						"^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$")
						&& cadena != null && !cadena.isEmpty()) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.email.formato"));
					return;
				}
			}

			String nombre = "";
			String logo = "";
//		    byte[] imageBytes = new byte[0];
			Entidad entidad = sb.getEntidad();
			logo = hService.urlLogoEntidad(entidad.getCodigoDIR3());
			LOGGER.debug("DialogEnviarMAIL INI");
			byte[] imageBytes = hService.contenidoLogoEntidad(entidad.getCodigoDIR3());
			LOGGER.debug("DialogEnviarMAIL P1 " + imageBytes);
			 //String urltext = logo;
//			 try {
//			    URL url = new URL(urltext);
//			    BufferedInputStream bis = new BufferedInputStream(url.openStream());
//			    for(byte[] ba = new byte[bis.available()];
//			        bis.read(ba) != -1;) {
//			        byte[] baTmp = new byte[imageBytes.length + ba.length];
//			        System.arraycopy(imageBytes, 0, baTmp, 0, imageBytes.length);
//			        System.arraycopy(ba, 0, baTmp, imageBytes.length, ba.length);
//			        imageBytes = baTmp;
//			    }
//			 }catch(MalformedURLException e) {
//					e.printStackTrace();
//			 }catch(IOException e) {
//					e.printStackTrace();
//			 }


			if (entidad.getNombre() != null) {
				nombre = entidad.getNombre().getTraduccion(UtilJSF.getIdioma().toString());
			}

			buscar();
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

			String msg = null;

//			String msg = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ca\" lang=\"ca\">" + "   <head>"
//					+ "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + "      <title>"
//					+ nombre + "</title>" + "      <!-- css -->" + "   </head>" + "   <body>"
//					+ "      <!-- contenidor --> 	"
//					+ "      <div id=\"contenidor\" style=\"width: 100%;font: normal 80% 'TrebuchetMS', 'Trebuchet MS', Arial, Helvetica, sans-serif;color: #000;background-color: #fff;\">"
//					+ "         <!-- logo illes balears --> 		"
//					+ "         <div id=\"cap\" style=\"font-size: 1.2em;font-weight: bold;text-align: center;margin-bottom: 1em;\">";
//					/*if(logo != null && !logo.isEmpty()) {
//						msg += "            <img  src=\"" + logo + "\" alt=\"logo\" width=\"100\" height=\"100\"/>";
//					}*/
//
//			if (imageBytes != null && imageBytes.length > 0) {
//				//msg += "			   <h3> LOGO </h3>";
//				String encoded = Base64.getEncoder().encodeToString(imageBytes);
//				LOGGER.debug("Codificiado : " + encoded);
//				msg += "            <img  src=\"data:image/jpeg;base64," + encoded + "\" alt=\"logo\" width=\"100\" height=\"100\" style=\"width: 100px;height:100px;\"/>";
//			} else if( logo != null && !logo.isEmpty()) {
//				msg += "            <img  src=\"" + logo + "\" alt=\"logo\" width=\"100\" height=\"100\"/>";
//			}
//			msg += "            <h1>" + nombre.toUpperCase() + "</h1>" + "         </div>"
//					+ "         <!-- /logo illes balears -->  		<!-- continguts -->"
//					+ "         <div id=\"continguts\" style=\"padding: 1em;border: 1em solid #f2f2f2;\">"
//					+ "            <!-- titol --> 			"
//					+ "            <h1 style=\"font-size: 1.4em;margin-top: 0;margin-bottom: 1em;\">"
//					+ "               Aquest missatge ha estat generat pel sistema d'alertes de SISTRAHELP y ofereix un resum dels events recogits al Quadre de comandament de SISTRAHELP, des del "
//					+ "               " + fechaDesde;
//			if (fechaHasta != null && !fechaHasta.isEmpty()) {
//				msg += " fins al " + fechaHasta;
//			}
//			msg += ": 			" + "            </h1>" + "            </div><div style=\"height:20px;\"></div>"
//					+ "            <table id=\"form:tablaCuadroMando\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
//					+ "               <tbody>" + "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">ACCIONS</span></td>"
//					+ "                              </tr>" + "                           </tbody>"
//					+ "                        </table>" + "                     </td>" + "                  </tr>"
//					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
//					+ "                                 <td style=\"background-color: lightgrey; border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Iniciats</span></td>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Finalitzats</span></td>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Percen&shy;tatge d&#39;accions<wbr> no<wbr> finali&shy;zades</span></td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
//					+ "                              </tr>" + "                              <tr>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Emplenar formulari</span></td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ formIni + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ formFin + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ formPor + "%</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ nivelGravedad(formPor) + "</td>" + "                              </tr>"
//					+ "                              <tr>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Firmar</span></td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ firmaIni + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ firmaFinOk + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ firmaPor + "%</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ nivelGravedad(firmaPor) + "</td>" + "                              </tr>"
//					+ "                              <tr>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Pagar</span></td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ pagIni + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ pagFin + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ pagosPor + "%</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ nivelGravedad(pagosPor) + "</td>" + "                              </tr>"
//					+ "                              <tr>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Registrar</span></td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ regIni + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ regFin + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ registrosPor + "%</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ nivelGravedad(registrosPor) + "</td>" + "                              </tr>"
//					+ "                           </tbody>" + "                        </table>"
//					+ "                     </td>" + "                  </tr>" + "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\">Tr&#224;mits</span></td>"
//					+ "                              </tr>" + "                           </tbody>"
//					+ "                        </table>" + "                     </td>" + "                  </tr>"
//					+ "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Iniciades</span></td>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Finalitzades</span></td>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Percen&shy;tatge de tr&#224;mits<wbr> no<wbr> finali&shy;tzats</span></td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
//					+ "                              </tr>" + "                              <tr>"
//					+ "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">Tr&#224;mits</span></td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ tramIni + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ tramFin + "</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ tramPor + "%</td>"
//					+ "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ nivelGravedad(tramPor) + "</td>" + "                              </tr>"
//					+ "                           </tbody>" + "                        </table>"
//					+ "                     </td>" + "                  </tr>" + "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                        <div style=\"height:20px;\"></div>" + "                     </td>"
//					+ "                  </tr>" + "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>";
//				if(UtilJSF.getSessionBean().getActiveRole().equals(TypeRoleAcceso.SUPERVISOR_ENTIDAD)) {
//					msg += "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey; font-size: 1.2em !important;\">NOMBRE TOTAL D&#39;ERRORS (TRAMITACI&#211; + PLATAFORMA)</span></td>";
//				}else {
//					msg += "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey; font-size: 1.2em !important;\">NOMBRE TOTAL D&#39;ERRORS (TRAMITACI&#211;)</span></td>";
//				}
//				msg += "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
//					+ errTot + "</td>" + "                              </tr>" + "                           </tbody>"
//					+ "                        </table>" + "                     </td>" + "                  </tr>"
//					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\"><u>Errors de tramitaci&#243; (Errors per Tr&#224;mit):</u></span></td>"
//					+ "                              </tr>" + "                           </tbody>"
//					+ "                        </table>" + "                     </td>" + "                  </tr>"
//					+ "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
//					+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                          <tbody>" + "                             <tr>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Tr&#224;mit</span></td>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Versi&#243;</span></td>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Sessions finalitzades</span></td>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Sessions<wbr> no<wbr> finalitzades</span></td>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Percen&shy;tatge de sessions<wbr> no<wbr> finali&shy;tzades</span></td>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Suma d&#39;errors</span></td>"
//					+ "                             </tr>";
//			if (listaErrores != null && !listaErrores.isEmpty()) {
//				for (ErroresPorTramiteCM lerr : listaErrores) {
//					String [] idTramite = lerr.getIdTramite().split("\\.");
//					msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ idTramite[0]+".<wbr>"+ idTramite[1]+".<wbr>"+ idTramite[2] + "</td>"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerr.getVersion() + "</td>"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerr.getSesionesFinalizadas() + "</td>"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerr.getSesionesInacabadas() + "</td>"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ formatDouble(lerr.getPorcentage()) + "%</td>"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerr.getNumeroErrores() + "</td>" + "                             </tr>";
//				}
//			} else {
//				msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al període de temps</td>"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                             </tr>";
//			}
//			msg += "                          </tbody>" + "                       </table>"
//					+ "                    </td>" + "                 </tr>" + "                  ";
//			msg += " <tr>" + "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\"><u>Errors de tramitaci&#243; (Tr&#224;mits per Error):</u></span></td>"
//					+ "                              </tr>" + "                           </tbody>"
//					+ "                        </table>" + "                     </td>" + "                  </tr>"
//					+ "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
//					+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                          <tbody>" + "                             <tr>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Error</span></td>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Suma d&#39;errors</span></td>"
//					+ "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Percen&shy;tatge d&#39;errors</span></td>"
//					+ "                             </tr>";
//			if (listaTramErrores != null && !listaTramErrores.isEmpty()) {
//				for (EventoCM ltrerr : listaTramErrores) {
//					msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ ltrerr.getTipoEvento() + "</td>"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ ltrerr.getConcurrencias() + "</td>"
//							+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ formatDouble(ltrerr.getPorc()) + "%</td>        </tr>";
//				}
//			} else {
//				msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al període de temps</td>"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                             </tr>";
//			}
//			msg += "                          </tbody>" + "                       </table>"
//					+ "                    </td>" + "                 </tr>" + "                  "
//					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\"><u>Errors de Plataforma</u></span></td>"
//					+ "                              </tr>" + "                           </tbody>"
//					+ "                        </table>" + "                     </td>" + "                  </tr>"
//					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                            <tbody>" + "                               <tr>"
//					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Error</span></td>"
//					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Suma d&#39;errors</span></td>"
//					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Percen&shy;tatge d&#39;errors</span></td>"
//					+ "                               </tr>";
//			if (listaErrPlat != null && !listaErrPlat.isEmpty()) {
//				for (EventoCM lerrp : listaErrPlat) {
//					msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerrp.getTipoEvento() + "</td>"
//							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerrp.getConcurrencias() + "</td>"
//							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ formatDouble(lerrp.getPorc()) + "%</td>  " + "                               </tr>";
//				}
//			} else {
//				msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al període de temps</td>"
//						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>  "
//						+ "                               </tr>";
//			}
//			msg += "                            </tbody>" + "                         </table>"
//					+ "                     </td>" + "                  </tr>" + "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                        <div style=\"height:20px;\"></div>" + "                     </td>"
//					+ "                  </tr>" + "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                           <tbody>" + "                              <tr>"
//					+ "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">TR&#192;MITS<wbr> NO<wbr> FINALI&shy;TZATS<wbr> SENSE<wbr> ERRORS</span></td>"
//					+ "                              </tr>" + "                           </tbody>"
//					+ "                        </table>" + "                     </td>" + "                  </tr>"
//					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
//					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
//					+ "                            <tbody>" + "                               <tr>"
//					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Tr&#224;mit</span></td>"
//					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Versi&#243;</span></td>"
//					+ "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">Sessions<wbr> no<wbr> finali&shy;tzades</span></td>"
//					+ "                               </tr>";
//			if (listaInacabados != null && !listaInacabados.isEmpty()) {
//				for (ErroresPorTramiteCM lerri : listaInacabados) {
//					msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerri.getIdTramite() + "</td>"
//							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerri.getVersion() + "</td>"
//							+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
//							+ lerri.getSesionesInacabadas() + "</td>  " + "                               </tr>";
//				}
//			} else {
//				msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
//						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> No hi ha registres al període de temps</td>"
//						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
//						+ "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>  "
//						+ "                               </tr>";
//			}
//			msg += "                            </tbody>" + "                         </table>"
//					+ "                     </td>" + "                  </tr>" + "                  <tr>"
//					+ "                     <td style=\"font-weight: bold;\">"
//					+ "                       <div style=\"height:20px;\"></div>" + "                    </td>"
//					+ "                 </tr>"
//					+ "         </div>"
//					+ "         <p style=\"margin: 1.5em 0;padding: 1em;font-size: 0.9em;font-style: italic;\">MOLT IMPORTANT: Aquest correu s&#39;ha generat de forma autom&#224;tica. Si us plau no s&#39;ha de respondre a aquest correu.</p>"
//					+ "      </div>" + "   </body>" + "</html>";


			DatosResumen datosResumen = new DatosResumen();

			datosResumen.setFechaDesde(fechaDesde);
			datosResumen.setFechaHasta(fechaHasta);
			datosResumen.setListaErrores(listaErrores);
			datosResumen.setListaTramErrores(listaTramErrores);
			datosResumen.setListaErrPlat(listaErrPlat);
			datosResumen.setListaInacabados(listaInacabados);
			datosResumen.setFormIniFin(Pair.of(formIni, formFin));
			datosResumen.setFirmaIniFinOk(Pair.of(firmaIni, firmaFinOk));

			datosResumen.setPagIniFin(Pair.of(pagIni, pagFin));
			datosResumen.setRegIniFin(Pair.of(regIni, regFin));
			datosResumen.setTramIniFin(Pair.of(tramIni, tramFin));

			datosResumen.setErrTot(errTot);
			datosResumen.setErrPlat(errPlat);

			datosResumen.setUmbralNormalAtencion(umbralNormalAtencion);
			datosResumen.setUmbralAtencionRevisar(umbralAtencionRevisar);

			String idioma = UtilJSF.getIdioma().toString();
			msg = mensajeEmailService.mensajeResumenDiario(idioma, nombre, logo, imageBytes, datosResumen,
					UtilJSF.getSessionBean().getActiveRole());


			LOGGER.debug("Informe enviado");
			//enviarEmail(msg,DatatypeConverter.printBase64Binary(imageBytes));
			LOGGER.debug("DialogEnviarMAIL P2 ");
			LOGGER.debug(msg);
			LOGGER.debug("------- BYTES: ------");
			LOGGER.debug(DatatypeConverter.printBase64Binary(imageBytes));
			enviarEmail(msg, null); //DatatypeConverter.printBase64Binary(imageBytes));
			LOGGER.debug("DialogEnviarMAIL P3");
			// Retornamos resultado
			final DialogResult result = new DialogResult();
			result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
			UtilJSF.closeDialog(result);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.email.vacio"));
			return;
		}
	}

	/**
	 * Buscar.
	 */
	private void buscar() {
		tramIni = 0;
		tramFin = 0;
		pagIni = 0;
		pagFin = 0;
		regIni = 0;
		regFin = 0;
		errTot = 0;
		errPlat = 0;
		formIni = 0;
		formFin = 0;
		firmaIni = 0;
		firmaFinOk = 0;
		listaErrores = new ArrayList<ErroresPorTramiteCM>();
		listaTramErrores = new ArrayList<EventoCM>();
		filtros = new FiltroAuditoriaTramitacion(listarIdArea(sb.getListaAreasEntidad()), false, false);
		filtrosInacabados = new FiltroAuditoriaTramitacion(listarIdArea(sb.getListaAreasEntidad()), false, false);

		try {
			filtros.setFechaDesde(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaDesde));
			filtros.setFechaHasta(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaHasta));
			filtrosInacabados.setFechaDesde(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaDesde));
			filtrosInacabados.setFechaHasta(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaHasta));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		filtros.setIdTramite(null);
		filtros.setVersionTramite(null);
		filtros.setErrorPlataforma(true);
		filtros.setRolAcceso(UtilJSF.getSessionBean().getActiveRole().toString());
		filtrosInacabados.setIdTramite(null);
		filtrosInacabados.setVersionTramite(null);
		filtrosInacabados.setErrorPlataforma(false);
		ResultadoEventoCM resEventoCM = hService.obtenerCountEventoCM(filtros);

		if (resEventoCM != null && resEventoCM.getListaEventosCM() != null
				&& !resEventoCM.getListaEventosCM().isEmpty()) {
			ponerEventos(resEventoCM.getListaEventosCM());
		}

		filtros.setSoloContar(false);
		listaErrores = hService.obtenerErroresPorTramiteCM(filtros, null).getListaErroresCM();
		listaTramErrores = hService.obtenerTramitesPorErrorCM(filtros, null).getListaEventosCM();

		// Filtra

		filtrosInacabados.setSoloContar(false);
		filtros.setErrorPlataforma(false);
		listaInacabados = hService.obtenerErroresPorTramiteCM(filtros, null).getListaErroresCM();

		filtros.setSoloContar(false);
		listaErrPlat = hService.obtenerErroresPlataformaCM(filtros, null).getListaEventosCM();

		errPlat = hService.sumarErroresPlataformaCM(filtros).intValue();

	}

	private String formatDouble(Double dbl) {
		return new DecimalFormat("0.00").format(dbl);
	}

	private void enviarEmail(String msg, String imgBase64) {
		try {
			final IEmailPlugin plgEmail = (IEmailPlugin) confService.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
			if (imgBase64 == null) {
				plgEmail.envioEmail(Arrays.asList(listaEmails.split(Constantes.LISTAS_SEPARADOR)),
						"SISTRAHELP: Informe Quadre Comandament - " + getEntorno().toUpperCase(), msg, null);
			} else {
				plgEmail.envioEmail(Arrays.asList(listaEmails.split(Constantes.LISTAS_SEPARADOR)),
					"SISTRAHELP: Informe Quadre Comandament - " + getEntorno().toUpperCase(), msg, null, imgBase64);
			}
		} catch (EmailPluginException e) {
			LOGGER.error("Error de excepción enviando el email " , e);
		} catch (PluginException e) {
			LOGGER.error("Error de excepción plugin el email " , e);
		}
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

	private String nivelGravedad(String porcentageStr) {
		String texto = "";
		String porcentageParsed = porcentageStr.replace(",", ".");
		Double porcentage = Double.parseDouble(porcentageParsed);
		if (porcentage > 0) {
			if (porcentage >= umbralAtencionRevisar) {
				texto = "Revisar";
			} else if (porcentage < umbralAtencionRevisar && porcentage >= umbralNormalAtencion) {
				texto = "Atenció";
			} else if (porcentage < umbralNormalAtencion) {
				texto = "Normal";
			}
		} else {
			texto = "Normal";
		}
		return texto;
	}

	private List<String> listarIdArea(List<Area> listaAreas) {
		final List<String> listaIdAreas = new ArrayList<>();
		for (final Area area : listaAreas) {
			listaIdAreas.add(area.getIdentificador());
		}

		return listaIdAreas;
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("dialogoEnviarMail");
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr(AjaxBehaviorEvent event) {

		if (StringUtils.isEmpty(portapapeles)) {
			copiadoErr(event);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	private String getEntorno() {
		final String pathProperties = System.getProperty("es.caib.sistrahelp.properties.path");
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props.getProperty("entorno").toUpperCase();
		} catch (Exception e) {
			LOGGER.error("ALERTAS STH: Error obteniendo entorno" , e);
		}
		return "";
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr(AjaxBehaviorEvent event) {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewAuditoriaTramites.copiar"));
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	public String getListaEmails() {
		return listaEmails;
	}

	public void setListaEmails(String listaEmails) {
		this.listaEmails = listaEmails;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getHoraDesde() {
		return horaDesde;
	}

	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}

	/**
	 * @return the umbralNormalAtencion
	 */
	public Integer getUmbralNormalAtencion() {
		return umbralNormalAtencion;
	}

	/**
	 * @param umbralNormalAtencion the umbralNormalAtencion to set
	 */
	public void setUmbralNormalAtencion(Integer umbralNormalAtencion) {
		this.umbralNormalAtencion = umbralNormalAtencion;
	}

	/**
	 * @return the umbralAtencionRevisar
	 */
	public Integer getUmbralAtencionRevisar() {
		return umbralAtencionRevisar;
	}

	/**
	 * @param umbralAtencionRevisar the umbralAtencionRevisar to set
	 */
	public void setUmbralAtencionRevisar(Integer umbralAtencionRevisar) {
		this.umbralAtencionRevisar = umbralAtencionRevisar;
	}

	/**
	 * @return the umbralNormalAtencionUsuarioString
	 */
	public String getUmbralNormalAtencionUsuarioString() {
		return umbralNormalAtencionUsuarioString;
	}

	/**
	 * @param umbralNormalAtencionUsuarioString the umbralNormalAtencionUsuarioString to set
	 */
	public void setUmbralNormalAtencionUsuarioString(String umbralNormalAtencionUsuarioString) {
		this.umbralNormalAtencionUsuarioString = umbralNormalAtencionUsuarioString;
	}

	/**
	 * @return the umbralAtencionRevisarUsuarioString
	 */
	public String getUmbralAtencionRevisarUsuarioString() {
		return umbralAtencionRevisarUsuarioString;
	}

	/**
	 * @param umbralAtencionRevisarUsuarioString the umbralAtencionRevisarUsuarioString to set
	 */
	public void setUmbralAtencionRevisarUsuarioString(String umbralAtencionRevisarUsuarioString) {
		this.umbralAtencionRevisarUsuarioString = umbralAtencionRevisarUsuarioString;
	}

	/**
	 * @return the umbralNormalAtencionString
	 */
	public String getUmbralNormalAtencionString() {
		return umbralNormalAtencionString;
	}

	/**
	 * @param umbralNormalAtencionString the umbralNormalAtencionString to set
	 */
	public void setUmbralNormalAtencionString(String umbralNormalAtencionString) {
		this.umbralNormalAtencionString = umbralNormalAtencionString;
	}

	/**
	 * @return the umbralAtencionRevisarString
	 */
	public String getUmbralAtencionRevisarString() {
		return umbralAtencionRevisarString;
	}

	/**
	 * @param umbralAtencionRevisarString the umbralAtencionRevisarString to set
	 */
	public void setUmbralAtencionRevisarString(String umbralAtencionRevisarString) {
		this.umbralAtencionRevisarString = umbralAtencionRevisarString;
	}


	/**
	 * @return the umbralNormalAtencionPropertiesString
	 */
	public String getUmbralNormalAtencionPropertiesString() {
		return umbralNormalAtencionPropertiesString;
	}

	/**
	 * @param umbralNormalAtencionPropertiesString the umbralNormalAtencionPropertiesString to set
	 */
	public void setUmbralNormalAtencionPropertiesString(String umbralNormalAtencionPropertiesString) {
		this.umbralNormalAtencionPropertiesString = umbralNormalAtencionPropertiesString;
	}

	/**
	 * @return the umbralAtencionRevisarPropertiesString
	 */
	public String getUmbralAtencionRevisarPropertiesString() {
		return umbralAtencionRevisarPropertiesString;
	}

	/**
	 * @param umbralAtencionRevisarPropertiesString the umbralAtencionRevisarPropertiesString to set
	 */
	public void setUmbralAtencionRevisarPropertiesString(String umbralAtencionRevisarPropertiesString) {
		this.umbralAtencionRevisarPropertiesString = umbralAtencionRevisarPropertiesString;
	}

	/**
	 * @return the umbralNormalAtencionProperties
	 */
	public Integer getUmbralNormalAtencionProperties() {
		return umbralNormalAtencionProperties;
	}

	/**
	 * @param umbralNormalAtencionProperties the umbralNormalAtencionProperties to set
	 */
	public void setUmbralNormalAtencionProperties(Integer umbralNormalAtencionProperties) {
		this.umbralNormalAtencionProperties = umbralNormalAtencionProperties;
	}

	/**
	 * @return the umbralAtencionRevisarProperties
	 */
	public Integer getUmbralAtencionRevisarProperties() {
		return umbralAtencionRevisarProperties;
	}

	/**
	 * @param umbralAtencionRevisarProperties the umbralAtencionRevisarProperties to set
	 */
	public void setUmbralAtencionRevisarProperties(Integer umbralAtencionRevisarProperties) {
		this.umbralAtencionRevisarProperties = umbralAtencionRevisarProperties;
	}

}
