package es.caib.sistrahelp.core.service;

import es.caib.sistrahelp.core.api.model.DatosResumen;
import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrahelp.core.api.service.MensajeEmailService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import java.util.function.Function;

@Service
public class MensajeEmailServiceImpl implements MensajeEmailService {

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(MensajeEmailServiceImpl.class);

    @Override
    public String mensajeResumenDiario(String idioma, String nombre, String logo, byte[] imageBytes, DatosResumen datosResumen, TypeRoleAcceso rolUsuario) {
        Integer umbralNormalAtencion = datosResumen.getUmbralNormalAtencion();
        Integer umbralAtencionRevisar = datosResumen.getUmbralAtencionRevisar();

        DecimalFormat df = new DecimalFormat("0.00");

        Function<Pair<Integer, Integer>, String> formatDouble = d -> {
            String porcentaje;
            int ini = d.getLeft();
            int fin = d.getRight();
            if (ini != 0) {
                porcentaje = new DecimalFormat("0.00").format((100 - ((Double.valueOf(fin) * 100) / Double.valueOf(ini))));
            } else {
                if (fin == 0) {
                    porcentaje = "0,00";
                } else {
                    porcentaje = "100,00";
                }
            }
            return porcentaje;
        };

        String formPor = formatDouble.apply(datosResumen.getFormIniFin());
        String firmaPor = formatDouble.apply(datosResumen.getFirmaIniFinOk());
        String pagosPor = formatDouble.apply(datosResumen.getPagIniFin());
        String registrosPor = formatDouble.apply(datosResumen.getRegIniFin());
        String tramPor = formatDouble.apply(datosResumen.getTramIniFin());

        LOGGER.debug("DialogEnviarMAIL INI");
        LOGGER.debug("DialogEnviarMAIL P1 " + imageBytes);

        Locale localeUsuario = new Locale(idioma);

        String msg = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"" +  idioma +"\" lang=\"" + idioma + "\">" + "   <head>"
                + "      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" + "      <title>"
                + nombre + "</title>" + "      <!-- css -->" + "   </head>" + "   <body>"
                + "      <!-- contenidor --> 	"
                + "      <div id=\"contenidor\" style=\"width: 100%;font: normal 80% 'TrebuchetMS', 'Trebuchet MS', Arial, Helvetica, sans-serif;color: #000;background-color: #fff;\">"
                + "         <!-- logo illes balears --> 		"
                + "         <div id=\"cap\" style=\"font-size: 1.2em;font-weight: bold;text-align: center;margin-bottom: 1em;\">";
					/*if(logo != null && !logo.isEmpty()) {
						msg += "            <img  src=\"" + logo + "\" alt=\"logo\" width=\"100\" height=\"100\"/>";
					}*/

        if (imageBytes != null && imageBytes.length > 0) {
            //msg += "			   <h3> LOGO </h3>";
            String encoded = Base64.getEncoder().encodeToString(imageBytes);
            LOGGER.debug("Codificiado : " + encoded);
            msg += "            <img  src=\"data:image/jpeg;base64," + encoded + "\" alt=\"logo\" width=\"100\" height=\"100\" style=\"width: 100px;height:100px;\"/>";
        } else if( logo != null && !logo.isEmpty()) {
            msg += "            <img  src=\"" + logo + "\" alt=\"logo\" width=\"100\" height=\"100\"/>";
        }

        msg += "            <h1>" + nombre.toUpperCase() + "</h1>" + "         </div>"
                + "         <!-- /logo illes balears -->  		<!-- continguts -->"
                + "         <div id=\"continguts\" style=\"padding: 1em;border: 1em solid #f2f2f2;\">"
                + "            <!-- titol --> 			"
                + "            <h1 style=\"font-size: 1.4em;margin-top: 0;margin-bottom: 1em;\">"
                + "               " + messageSource.getMessage("resumen.diario.mail.rango", Arrays.asList(datosResumen.getFechaDesde(), datosResumen.getFechaHasta()).toArray(), new Locale(idioma));
//                + "               Aquest missatge ha estat generat pel sistema d'alertes de SISTRAHELP y ofereix un resum dels events recogits al Quadre de comandament de SISTRAHELP, des del "
//                + "               " + datosResumen.getFechaDesde();
//        if (StringUtils.isNotBlank(datosResumen.getFechaHasta())) {
//            msg += " fins al " + datosResumen.getFechaHasta();
//        }
        msg += " 			" + "            </h1>" + "            </div><div style=\"height:20px;\"></div>"
                + "            <table id=\"form:tablaCuadroMando\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "               <tbody>" + "                  <tr>"
                + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                           <tbody>" + "                              <tr>"
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.titulo", null, new Locale(idioma)) + "</span></td>"
                + "                              </tr>" + "                           </tbody>"
                + "                        </table>" + "                     </td>" + "                  </tr>"
                + "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                           <tbody>" + "                              <tr>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
                + "                                 <td style=\"background-color: lightgrey; border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.header.iniciados", null, new Locale(idioma))+ "</span></td>"
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.header.finalizados", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.header.porcentaje_no_finalizados", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
                + "                              </tr>" + "                              <tr>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.linea.rellenar_formulario", null, new Locale(idioma)) +"</span></td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getFormIniFin().getLeft() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getFormIniFin().getRight() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + formPor + "%</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + nivelGravedad(formPor, umbralNormalAtencion, umbralAtencionRevisar) + "</td>" + "                              </tr>"
                + "                              <tr>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.linea.firmar", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getFirmaIniFinOk().getLeft() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getFirmaIniFinOk().getRight() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + firmaPor + "%</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + nivelGravedad(firmaPor, umbralNormalAtencion, umbralAtencionRevisar) + "</td>" + "                              </tr>"
                + "                              <tr>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.linea.pagar", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getPagIniFin().getLeft() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getPagIniFin().getRight() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + pagosPor + "%</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + nivelGravedad(pagosPor, umbralNormalAtencion, umbralAtencionRevisar) + "</td>" + "                              </tr>"
                + "                              <tr>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.linea.registrar", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getRegIniFin().getLeft() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getRegIniFin().getRight() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + registrosPor + "%</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + nivelGravedad(registrosPor, umbralNormalAtencion, umbralAtencionRevisar) + "</td>" + "                              </tr>"
                + "                           </tbody>" + "                        </table>"
                + "                     </td>" + "                  </tr>" + "                  <tr>"
                + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                           <tbody>" + "                              <tr>"
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.tramites.titulo",null, new Locale(idioma)) + "</span></td>"
                + "                              </tr>" + "                           </tbody>"
                + "                        </table>" + "                     </td>" + "                  </tr>"
                + "                  <tr>"
                + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                           <tbody>" + "                              <tr>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.tramites.header.iniciados", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.tramites.header.finalizados", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.tramites.header.porcentaje_no_finalizados", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\"></td>"
                + "                              </tr>" + "                              <tr>"
                + "                                 <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey;\">"
                + messageSource.getMessage("resumen.diario.mail.acciones.tramites.linea.tramites", null, new Locale(idioma)) + "</span></td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getTramIniFin().getLeft() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getTramIniFin().getRight() + "</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + tramPor + "%</td>"
                + "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + nivelGravedad(tramPor, umbralNormalAtencion, umbralAtencionRevisar) + "</td>" + "                              </tr>"
                + "                           </tbody>" + "                        </table>"
                + "                     </td>" + "                  </tr>" + "                  <tr>"
                + "                     <td style=\"font-weight: bold;\">"
                + "                        <div style=\"height:20px;\"></div>" + "                     </td>"
                + "                  </tr>" + "                  <tr>"
                + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                           <tbody>" + "                              <tr>";
        if(rolUsuario.equals(TypeRoleAcceso.SUPERVISOR_ENTIDAD)) {
            msg += "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey; font-size: 1.2em !important;\">"
            + messageSource.getMessage("resumen.diario.mail.errores.titulo.plataforma", null, localeUsuario)  + messageSource.getMessage("resumen.diario.mail.errores.titulo.plataforma", null, localeUsuario) + "</span></td>";
        }else {
            msg += "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;background-color: lightgrey; font-size: 1.2em !important;\">"
                    + messageSource.getMessage("resumen.diario.mail.errores.titulo.tramitacion", null, localeUsuario) + "</span></td>";
        }
        msg += "                                 <td style=\"background-color: RGB(255,255,255);border: 1px solid #c5c5c5;font-weight: bold;\">"
                + datosResumen.getErrTot() + "</td>" + "                              </tr>" + "                           </tbody>"
                + "                        </table>" + "                     </td>" + "                  </tr>"
                + "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                           <tbody>" + "                              <tr>"
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\"><u>"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_tramite", null, localeUsuario) + "</u></span></td>"
                + "                              </tr>" + "                           </tbody>"
                + "                        </table>" + "                     </td>" + "                  </tr>"
                + "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
                + "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                          <tbody>" + "                             <tr>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_tramite.tramite", null, localeUsuario) + "</span></td>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_tramite.version", null, localeUsuario) +"</span></td>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_tramite.sesiones_finalizadas", null, localeUsuario) +"</span></td>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_tramite.sesiones_no_finalizadas", null, localeUsuario) +"</span></td>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_tramite.porcentaje_sesiones_no_finalizadas", null, localeUsuario) +"</span></td>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_tramite.suma_errores", null, localeUsuario) +"</span></td>"
                + "                             </tr>";
        if (CollectionUtils.isNotEmpty( datosResumen.getListaErrores() )) {
            for (ErroresPorTramiteCM lerr : datosResumen.getListaErrores()) {
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
                        + df.format(lerr.getPorcentage()) + "%</td>"
                        + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
                        + lerr.getNumeroErrores() + "</td>" + "                             </tr>";
            }
        } else {
            msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
                    + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">" +
                    " " + messageSource.getMessage("resumen.diario.mail.errores.no_registro", null, new Locale(idioma)) + "</td>"
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
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\"><u>"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_error" , null, new Locale(idioma)) + "</u></span></td>"
                + "                              </tr>" + "                           </tbody>"
                + "                        </table>" + "                     </td>" + "                  </tr>"
                + "                  <tr>" + "                    <td style=\"font-weight: bold;\">"
                + "                       <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                          <tbody>" + "                             <tr>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_error.error" , null, new Locale(idioma)) + "</span></td>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_error.suma" , null, new Locale(idioma)) + "</span></td>"
                + "                                <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_error.porcentaje" , null, new Locale(idioma)) + "</span></td>"
                + "                             </tr>";
        if (CollectionUtils.isNotEmpty( datosResumen.getListaTramErrores() )) {
            for (EventoCM ltrerr : datosResumen.getListaTramErrores()) {
                msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
                        + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
                        + ltrerr.getTipoEvento() + "</td>"
                        + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
                        + ltrerr.getConcurrencias() + "</td>"
                        + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
                        + df.format(ltrerr.getPorc()) + "%</td>        </tr>";
            }
        } else {
            msg += "                             <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
                    + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> "
                    + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_error" , null, new Locale(idioma)) + "</td>"
                    + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
                    + "                                <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
                    + "                             </tr>";
        }
        msg += "                          </tbody>" + "                       </table>"
                + "                    </td>" + "                 </tr>" + "                  "
                + "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                           <tbody>" + "                              <tr>"
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold;\"><u>" +
                messageSource.getMessage("resumen.diario.mail.errores.plataforma" , null, new Locale(idioma)) + "</u></span></td>"
                + "                              </tr>" + "                           </tbody>"
                + "                        </table>" + "                     </td>" + "                  </tr>"
                + "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                            <tbody>" + "                               <tr>"
                + "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.plataforma.error" , null, new Locale(idioma)) +"</span></td>"
                + "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.plataforma.suma" , null, new Locale(idioma)) +"</span></td>"
                + "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.plataforma.porcentaje" , null, new Locale(idioma)) +"</span></td>"
                + "                               </tr>";
        if ( CollectionUtils.isNotEmpty( datosResumen.getListaErrPlat() )) {
            for (EventoCM lerrp : datosResumen.getListaErrPlat()) {
                msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
                        + "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
                        + lerrp.getTipoEvento() + "</td>"
                        + "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
                        + lerrp.getConcurrencias() + "</td>"
                        + "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\">"
                        + df.format(lerrp.getPorc()) + "%</td>  " + "                               </tr>";
            }
        } else {
            msg += "                               <tr data-ri=\"0\" role=\"row\" aria-selected=\"false\">"
                    + "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> "
                    + messageSource.getMessage("resumen.diario.mail.errores.tramitacion.por_error" , null, new Locale(idioma)) + "</td>"
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
                + "                                 <td style=\"background-color: lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span style=\"font-weight: bold; font-size: 1.2em !important;\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramite.no_finalizados" , null, new Locale(idioma)) + "</span></td>"
                + "                              </tr>" + "                           </tbody>"
                + "                        </table>" + "                     </td>" + "                  </tr>"
                + "                  <tr>" + "                     <td style=\"font-weight: bold;\">"
                + "                        <table border=\"1\" cellpadding=\"5\" cellspacing=\"1\" width=\"100%\">"
                + "                            <tbody>" + "                               <tr>"
                + "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramite.no_finalizados.tramite" , null, new Locale(idioma)) + "</span></td>"
                + "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramite.no_finalizados.version" , null, new Locale(idioma)) + "</span></td>"
                + "                                  <td style=\"background-color:  lightgrey;border: 1px solid #c5c5c5;font-weight: bold;\"><span class=\"ui-column-title\">"
                + messageSource.getMessage("resumen.diario.mail.errores.tramite.no_finalizados.sesiones_no_finalizadas" , null, new Locale(idioma)) + "</span></td>"
                + "                               </tr>";
        if (CollectionUtils.isNotEmpty( datosResumen.getListaInacabados() )) {
            for (ErroresPorTramiteCM lerri : datosResumen.getListaInacabados()) {
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
                    + "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"> " + messageSource.getMessage("resumen.diario.mail.errores.no_registro", null, new Locale(idioma)) + "</td>"
                    + "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>"
                    + "                                  <td role=\"gridcell\" style=\"word-wrap: break-word;font-weight: bold;text-align: left;border: 1px solid #c5c5c5;\"></td>  "
                    + "                               </tr>";
        }
        msg += "                            </tbody>" + "                         </table>"
                + "                     </td>" + "                  </tr>" + "                  <tr>"
                + "                     <td style=\"font-weight: bold;\">"
                + "                       <div style=\"height:20px;\"></div>" + "                    </td>"
                + "                 </tr>"
                + "         </div>"
                + "         <p style=\"margin: 1.5em 0;padding: 1em;font-size: 0.9em;font-style: italic;\">" + messageSource.getMessage("resumen.diario.mail.nota.importante", null, new Locale(idioma)) + "</p>"
                + "      </div>" + "   </body>" + "</html>";

        return msg;
    }


    private String nivelGravedad(String porcentageStr, Integer umbralNormalAtencion, Integer umbralAtencionRevisar) {
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
}
