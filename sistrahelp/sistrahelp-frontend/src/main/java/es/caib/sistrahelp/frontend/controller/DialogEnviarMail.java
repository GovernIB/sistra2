package es.caib.sistrahelp.frontend.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.digester.plugins.PluginException;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.caib.sistra2.commons.plugins.email.api.EmailPluginException;
import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.FicheroAuditoria;
import es.caib.sistrahelp.core.api.model.FicheroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoEventoCM;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.procesos.TimerHilosAlertas;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogEnviarMail extends DialogControllerBase {

	@Inject
	private HelpDeskService hService;

	@Inject
	private ConfiguracionService confService;

	@Inject
	private SessionBean sb;

	private String fechaDesde;
	private String fechaHasta;
	private String horaDesde;

	private String portapapeles;
	private String listaEmails;
	private String errorCopiar;
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

	private int firmaFin;

	private List<ErroresPorTramiteCM> listaErrores;

	private List<EventoCM> listaTramErrores;

	private List<ErroresPorTramiteCM> listaInacabados;

	private FiltroAuditoriaTramitacion filtros;

	private List<EventoAuditoriaTramitacion> listaDatos;

	private List<EventoAuditoriaTramitacion> listaDatosEventosPlataforma;

	private List<EventoCM> listaErrPlat;

	private List<HistorialAlerta> listaAlertas;

	private FiltroAuditoriaTramitacion filtrosInacabados;

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
			//String logo = "";
//		    byte[] imageBytes = new byte[0];
			Entidad entidad = sb.getEntidad();
			//logo = hService.urlLogoEntidad(entidad.getCodigoDIR3());
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
				nombre = entidad.getNombre().getTraduccion("ca");
			}

			buscar();
			String formPor;
			if (pagIni != 0) {
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
				firmaPor = formatDouble((100 - ((Double.valueOf(firmaFin) * 100) / Double.valueOf(firmaIni))));
			} else {
				if (firmaFin == 0) {
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
				tramPor = "0";
			}

			String msg = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ca\" lang=\"ca\">" + "   <head>"
					+ "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + "      <title>"
					+ nombre + "</title>" + "      <!-- css -->" + "   </head>" + "   <body>"
					+ "      <!-- contenidor --> 	"
					+ "      <div id=\"contenidor\" style=\"width: 90%;font: normal 80% 'TrebuchetMS', 'Trebuchet MS', Arial, Helvetica, sans-serif;color: #000;margin: 1em auto;background-color: #fff;\">"
					+ "         <!-- logo illes balears --> 		"
					+ "         <div id=\"cap\" style=\"font-size: 1.2em;font-weight: bold;text-align: center;margin-bottom: 1em;\">"
//					+ "            <img  src=\"cid:logo\" alt=\"logo\" width=\"100\" height=\"100\"/>"
					+ "			   <h3> LOGO </h3>"
					+ "            <h1>" + nombre.toUpperCase() + "</h1>" + "         </div>"
					+ "         <!-- /logo illes balears -->  		<!-- continguts -->"
					+ "         <div id=\"continguts\" style=\"padding: 1em 2em;border: 1em solid #f2f2f2;\">"
					+ "            <!-- titol --> 			"
					+ "            <h1 style=\"font-size: 1.4em;margin-top: 0;margin-bottom: 1em;\">"
					+ "               Aquest missatge ha estat generat pel sistema d'alertes de SISTRAHELP y ofereix un resum dels events recogits al Quadre de comandament de SISTRAHELP, pel "
					+ "               " + fechaDesde;
			if (fechaHasta != null && !fechaHasta.isEmpty()) {
				msg += " fins al " + fechaHasta;
			}
			msg += " : 			" + "            </h1>" + "            <div style=\"height:20px;\"></div>"
					+ "            <table id=\"form:tablaCuadroMando\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100% !important;border-collapse: collapse !important;display: flex;padding: 0px !important;justify-content: center;margin-top: 10px;margin-bottom: 1.5em;border: 0;empty-cells: hide;\">"
					+ "               <tbody>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"wpx16 white borde pad grey\" style=\"padding-right: 10px;background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;width: 16px !important;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">Accions</span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"w20 white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: lightgrey; border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Iniciats</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Finalitzats</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Percentatge d&#39;accions no finalizades</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"></td>"
					+ "                              </tr>" + "                              <tr>"
					+ "                                 <td class=\"w20 white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Emplenar formulari</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ formIni + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ formFin + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ formPor + "%</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ nivelGravedad(formPor) + "</td>" + "                              </tr>"
					+ "                              <tr>"
					+ "                                 <td class=\"w20 white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Firmar</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ firmaIni + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ firmaFin + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ firmaPor + "%</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ nivelGravedad(firmaPor) + "</td>" + "                              </tr>"
					+ "                              <tr>"
					+ "                                 <td class=\"w20 white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Pagar</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ pagIni + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ pagFin + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ pagosPor + "%</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ nivelGravedad(pagosPor) + "</td>" + "                              </tr>"
					+ "                              <tr>"
					+ "                                 <td class=\"w20 white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Registrar</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ regIni + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ regIni + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ registrosPor + "%</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ nivelGravedad(registrosPor) + "</td>" + "                              </tr>"
					+ "                           </tbody>" + "                        </table>"
					+ "                     </td>" + "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"wpx16 white borde pad grey\" style=\"padding-right: 10px;background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;width: 16px !important;\"><span style=\"font-weight: bold;\">Tr&#224;mits</span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>"
					+ "                     <td style=\"font-weight: bold;padding-bottom: .5em;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"w20 white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Iniciades</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Finalitzades</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Percentatge de tr&#224;mits no finalitzats</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"></td>"
					+ "                              </tr>" + "                              <tr>"
					+ "                                 <td class=\"w20 white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey;\">Tr&#224;mits</span></td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ tramIni + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ tramFin + "</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ tramPor + "%</td>"
					+ "                                 <td class=\" white borde\" style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ nivelGravedad(100 + tramPor) + "</td>" + "                              </tr>"
					+ "                           </tbody>" + "                        </table>"
					+ "                     </td>" + "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;padding-bottom: .5em;\">"
					+ "                        <div style=\"height:20px;\"></div>" + "                     </td>"
					+ "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"grey borde \" style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"grey\" style=\"font-weight: bold;background-color: lightgrey; font-size: 1.2em !important;\">Nombre total d&#39;errors</span></td>"
					+ "                                 <td class=\"wpx100 white borde pad\" style=\"padding-right: 10px;padding-left: 10px;background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\">"
					+ errTot + "</td>" + "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"grey borde \" style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;padding-top: .5em;\"><span style=\"font-weight: bold; padding-left: 5px;\"><u>Errors de tramitaci&#243; (Errors per Tr&#224;mit):</u></span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
					+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                          <tbody>" + "                             <tr>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Tr&#224;mit</span></td>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Versi&#243;</span></td>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Sessions Ok</span></td>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Sessions no finalitzades</span></td>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Percentatge de sessions no finalitzades</span></td>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Freq&#252;&#232;ncia d&#39;errors</span></td>"
					+ "                             </tr>";
			if (listaErrores != null && !listaErrores.isEmpty()) {
				for (ErroresPorTramiteCM lerr : listaErrores) {
					msg += "                             <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 1000px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerr.getIdTramite() + "</td>"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 100px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerr.getVersion() + "</td>"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerr.getSesionesFinalizadas() + "</td>"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 500px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerr.getSesionesInacabadas() + "</td>"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 500px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ formatDouble(lerr.getPorcentage()) + "%</td>"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 500px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerr.getNumeroErrores() + "</td>" + "                             </tr>";
				}
			} else {
				msg += "                             <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 1000px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"> No hi ha registres al dia de avui</td>"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 100px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 500px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 500px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 500px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                             </tr>";
			}
			msg += "                          </tbody>" + "                       </table>"
					+ "                    </td>" + "                 </tr>" + "                  ";
			msg += " <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"grey borde \" style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;padding-top: .5em;\"><span style=\"font-weight: bold; padding-left: 5px;\"><u>Errors de tramitaci&#243; (Tr&#224;mits per Error):</u></span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
					+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                          <tbody>" + "                             <tr>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Error</span></td>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Freq&#252;&#232;ncia</span></td>"
					+ "                                <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Percentatge d&#39;error</span></td>"
					+ "                             </tr>";
			if (listaTramErrores != null && !listaTramErrores.isEmpty()) {
				for (EventoCM ltrerr : listaTramErrores) {
					msg += "                             <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 2000px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ ltrerr.getTipoEvento() + "</td>"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 400px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ ltrerr.getConcurrencias() + "</td>"
							+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 400px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ formatDouble(ltrerr.getPorc()) + "%</td>        </tr>";
				}
			} else {
				msg += "                             <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 2000px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"> No hi ha registres al dia de avui</td>"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 400px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                                <td role=\"gridcell\" class=\"borde\" style=\"width: 400px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                             </tr>";
			}
			msg += "                          </tbody>" + "                       </table>"
					+ "                    </td>" + "                 </tr>" + "                  "
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"wpx16 white borde pad grey\" style=\"padding-right: 10px;background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;width: 16px !important;\"><span style=\"font-weight: bold;\"><u>Errors de Plataforma</u></span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                            <tbody>" + "                               <tr>"
					+ "                                  <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Error</span></td>"
					+ "                                  <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Freq&#252;&#232;ncia</span></td>"
					+ "                                  <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Percentatge d&#39;errors</span></td>"
					+ "                               </tr>";
			if (listaErrPlat != null && !listaErrPlat.isEmpty()) {
				for (EventoCM lerrp : listaErrPlat) {
					msg += "                               <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
							+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerrp.getTipoEvento() + "</td>"
							+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerrp.getConcurrencias() + "</td>"
							+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ formatDouble(lerrp.getPorc()) + "%</td>  " + "                               </tr>";
				}
			} else {
				msg += "                               <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
						+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"> No hi ha registres al dia de avui</td>"
						+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>  "
						+ "                               </tr>";
			}
			msg += "                            </tbody>" + "                         </table>"
					+ "                     </td>" + "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;padding-bottom: .5em;\">"
					+ "                        <div style=\"height:20px;\"></div>" + "                     </td>"
					+ "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                           <tbody>" + "                              <tr>"
					+ "                                 <td class=\"wpx16 white borde pad grey\" style=\"padding-right: 10px;background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;width: 16px !important;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">Tr&#224;mits no finalitzats sense errors</span></td>"
					+ "                              </tr>" + "                           </tbody>"
					+ "                        </table>" + "                     </td>" + "                  </tr>"
					+ "                  <tr>" + "                     <td style=\"font-weight: bold;padding-bottom: .5em;\">"
					+ "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
					+ "                            <tbody>" + "                               <tr>"
					+ "                                  <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Tr&#224;mit</span></td>"
					+ "                                  <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Versi&#243;</span></td>"
					+ "                                  <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Sessions no finalitzades</span></td>"
					+ "                               </tr>";
			if (listaInacabados != null && !listaInacabados.isEmpty()) {
				for (ErroresPorTramiteCM lerri : listaInacabados) {
					msg += "                               <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
							+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerri.getIdTramite() + "</td>"
							+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerri.getVersion() + "</td>"
							+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
							+ lerri.getSesionesInacabadas() + "</td>  " + "                               </tr>";
				}
			} else {
				msg += "                               <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
						+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"> No hi ha registres al dia de avui</td>"
						+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
						+ "                                  <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>  "
						+ "                               </tr>";
			}
			msg += "                            </tbody>" + "                         </table>"
					+ "                     </td>" + "                  </tr>" + "                  <tr>"
					+ "                     <td style=\"font-weight: bold;padding-bottom: .5em;\">"
					+ "                       <div style=\"height:20px;\"></div>" + "                    </td>"
					+ "                 </tr>"
//					+ "                 <tr>"
//						+ "                    <td style=\"font-weight: bold;\">"
//						+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
//						+ "                          <tbody>" + "                             <tr>"
//						+ "                                <td class=\"wpx16 white borde pad grey\" style=\"padding-right: 10px;padding-left: 10px;background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;width: 16px !important;\"><span style=\"font-weight: bold;\">Historial d&#39;alertes</span></td>"
//						+ "                             </tr>" + "                          </tbody>"
//						+ "                       </table>" + "                    </td>" + "                 </tr>"
//						+ "                 <tr>"
//						+ "                    <td style=\"font-weight: bold;padding-bottom: .5em;\">"
//						+ "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" style=\"width: 75%;margin: auto;border-collapse: collapse !important;border: 0;empty-cells: hide;\">"
//						+ "                           <tbody>" + "                              <tr>"
//						+ "                                 <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Alerta</span></td>"
//						+ "                                 <td class=\" white borde\" style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;padding-bottom: .5em;\"><span class=\"ui-column-title\">Data</span></td>"
//						+ "                              </tr>";
//				if (listaAlertas != null && !listaAlertas.isEmpty()) {
//					for (HistorialAlerta lerra : listaAlertas) {
//						msg += "                              <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
//								+ "                                 <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
//								+ lerra.getAlerta().getNombre() + " - " + lerra.getEvento() + "</td>"
//								+ "                                 <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\">"
//								+ convertirFecha(lerra.getFecha()) + "</td>" + "                              </tr>";
//					}
//				} else {
//					msg += "                              <tr data-ri=\"0\" class=\"ui-widget-content ui-datatable-even ui-datatable-selectable\" role=\"row\" aria-selected=\"false\">"
//							+ "                                 <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"> No hi ha registres al dia de avui</td>"
//							+ "                                 <td role=\"gridcell\" class=\"borde\" style=\"width: 200px !important;word-wrap: break-word;font-weight: bold;text-align: left;padding-left: 5px;border: 1px solid #c5c5c5;padding-bottom: .5em;\"></td>"
//							+ "                              </tr>";
//				}
//				msg += "                           </tbody>" + "                        </table>"
//						+ "                    </td>" + "                 </tr>" + "               </tbody>"
//						+ "            </table>" + "            "
					+ "         </div>"
					+ "         <p class=\"auto\" style=\"margin: 1.5em 0;padding: 1em;font-size: 0.9em;font-style: italic;\">MOLT IMPORTANT: Aquest correu s&#39;ha generat de forma autom&#224;tica. Si us plau no s&#39;ha de respondre a aquest correu.</p>"
					+ "      </div>" + "   </body>" + "</html>";
			System.out.println("Informe enviado");
			//enviarEmail(msg,DatatypeConverter.printBase64Binary(imageBytes));
			enviarEmail(msg,null);

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
		formIni = 0;
		formFin = 0;
		firmaIni = 0;
		firmaFin = 0;
		listaErrores = new ArrayList<ErroresPorTramiteCM>();
		listaTramErrores = new ArrayList<EventoCM>();
		filtros = new FiltroAuditoriaTramitacion(listarIdArea(sb.getListaAreasEntidad()), false, false);
		filtrosInacabados = new FiltroAuditoriaTramitacion(listarIdArea(sb.getListaAreasEntidad()), false, false);

		try {
			filtros.setFechaDesde(new SimpleDateFormat("dd/MM/yyyy").parse(fechaDesde));
			filtros.setFechaHasta(new SimpleDateFormat("dd/MM/yyyy").parse(fechaHasta));
			filtrosInacabados.setFechaDesde(new SimpleDateFormat("dd/MM/yyyy").parse(fechaDesde));
			filtrosInacabados.setFechaHasta(new SimpleDateFormat("dd/MM/yyyy").parse(fechaHasta));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		filtros.setIdTramite(null);
		filtros.setVersionTramite(null);
		filtros.setErrorPlataforma(true);
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

	}

	private String formatDouble(Double dbl) {
		return new DecimalFormat("0.00").format(dbl);
	}

	private void enviarEmail(String msg, String imgBase64) {
		try {
			final IEmailPlugin plgEmail = (IEmailPlugin) confService.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
//			plgEmail.envioEmail(Arrays.asList(listaEmails.split(Constantes.LISTAS_SEPARADOR)),
//					"SISTRAHELP: Informe Quadre Comandament - " + UtilJSF.getEntorno().toUpperCase(), msg, null, imgBase64);
			plgEmail.envioEmail(Arrays.asList(listaEmails.split(Constantes.LISTAS_SEPARADOR)),
					"SISTRAHELP: Informe Quadre Comandament - " + UtilJSF.getEntorno().toUpperCase(), msg, null);
		} catch (EmailPluginException e) {
			e.printStackTrace();
		} catch (PluginException e) {
			e.printStackTrace();
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
			case FIRMA_FIN:
				firmaFin = ev.getConcurrencias().intValue();
				break;
			case PAGO_ELECTRONICO_INICIO:
				pagIni = ev.getConcurrencias().intValue();
				break;
			case PAGO_ELECTRONICO_VERIFICADO:
				pagFin += ev.getConcurrencias();
				break;
			case PAGO_PRESENCIAL:
				pagFin += ev.getConcurrencias();
				break;
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
		Double porcentage = 100 - Double.parseDouble(porcentageParsed);
		if (porcentage <= 15) {
			texto = "Revisar";
		} else if (porcentage > 15 && porcentage <= 50) {
			texto = "Atenció";
		} else if (porcentage > 50) {
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
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
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
	public void copiadoErr() {
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

}
