package es.caib.sistramit.core.service.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistrages.rest.api.interna.RArea;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistramit.core.api.exception.ErrorFormularioSoporteException;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.model.flujo.DatosFormularioSoporte;
import es.caib.sistramit.core.service.model.flujo.DatosSesionTramitacion;

/**
 * Utilidades formulario soporte.
 *
 * @author Indra
 *
 */
public final class UtilsFormularioSoporte {

	/** Plantilla HTML. */
	private static String plantillaHtml;

	/**
	 * Obtiene asunto email.
	 *
	 * @param literales
	 *                             literales
	 * @param datosFormSoporte
	 *                             datos soporte
	 * @param entidad
	 *                             entidad
	 * @param sesion
	 *                             datos sesion tramite
	 * @return asunto
	 */
	public static String obtenerAsuntoFormularioSoporte(final Literales literales,
			final DatosFormularioSoporte datosFormSoporte, final RConfiguracionEntidad entidad,
			final DatosSesionTramitacion sesion) {
		final String idioma = sesion.getDatosTramite().getIdioma();
		final String asunto = literales.getLiteral(Literales.SOPORTE_INCIDENCIAS, "asunto", idioma) + " - "
				+ datosFormSoporte.getProblemaTipoDesc() + " - " + sesion.getDatosTramite().getIdSesionTramitacion();
		return asunto;
	}

	/**
	 * Obtiene destinatarios.
	 *
	 * @param opc
	 *                    opción
	 * @param entidad
	 * @param area
	 * @return
	 */
	public static List<String> obtenerDestinatariosFormularioSoporte(final DatosFormularioSoporte datosFormSoporte,
			final RConfiguracionEntidad entidad, final String area, final DatosSesionTramitacion datosSesion) {

		final ROpcionFormularioSoporte opc = obtenerOpcionFormularioSoporte(datosFormSoporte.getProblemaTipo(),
				entidad);

		final List<String> destinatarios = new ArrayList<>();

		// Destinatarios responsables
		if (opc.getDestinatario().contains("R")) {
			// Responsable incidencias o email contacto genérico
			String emailDest = datosSesion.getDatosTramite().getDefinicionTramiteCP().getEmailSoporte();
			if (StringUtils.isBlank(emailDest)) {
				emailDest = entidad.getEmail();
			}
			if (StringUtils.isBlank(emailDest)) {
				throw new ErrorFormularioSoporteException(
						"Tràmit no té indicat email de suport ni a nivell de tràmit ni d'entidad");
			}
			if (!ValidacionesTipo.getInstance().esEmail(emailDest)) {
				throw new ErrorFormularioSoporteException("Email no és vàlid: " + emailDest);
			}
			destinatarios.add(emailDest);
		}

		// Destinatarios especificos
		if (opc.getDestinatario().contains("E")) {
			// Lista destinatarios
			destinatarios.addAll(obtenerDestinatariosFromListaString(opc.getListaEmails()));
		}

		// Destinatarios area
		if (opc.getDestinatario().contains("A")) {
			for (final RArea a : entidad.getArea()) {
				if (a.getId().equals(area)) {
					destinatarios.addAll(obtenerDestinatariosFromListaString(a.getEmails()));
					break;
				}
			}
		}

		// Si no hay ningun destinatario generamos error
		if (destinatarios.isEmpty()) {
			throw new ErrorFormularioSoporteException("Tipus destinatari no suportat: " + opc.getDestinatario());
		}

		return destinatarios;

	}

	/**
	 * Obtiene emails de string.
	 *
	 * @param listaEmails
	 *                        emails separados por ;
	 * @return Lista emails
	 */
	protected static List<String> obtenerDestinatariosFromListaString(final String listaEmails) {
		final List<String> destinatariosE = new ArrayList<>();
		if (StringUtils.isNotBlank(listaEmails)) {
			final String[] listaDest = listaEmails.split(";");
			for (final String dest : listaDest) {
				if (!ValidacionesTipo.getInstance().esEmail(dest)) {
					throw new ErrorFormularioSoporteException("Email no és vàlid: " + dest);
				}
				destinatariosE.add(dest);
			}
		}
		return destinatariosE;
	}

	/**
	 * Obtiene opcion formulario soporte.
	 *
	 * @param problemaTipo
	 *                         codigo opcion
	 * @param entidad
	 *                         datos entidad
	 * @return opcion
	 */
	public static ROpcionFormularioSoporte obtenerOpcionFormularioSoporte(final String problemaTipo,
			final RConfiguracionEntidad entidad) {
		ROpcionFormularioSoporte opc = null;
		if (entidad.getAyudaFormulario() != null) {
			for (final ROpcionFormularioSoporte o : entidad.getAyudaFormulario()) {
				if (o.getCodigo().toString().equals(problemaTipo)) {
					opc = o;
					break;
				}
			}
		}
		if (opc == null) {
			throw new ErrorFormularioSoporteException("No existeix opció amb codi: " + problemaTipo);
		}
		return opc;
	}

	/**
	 * Construye mensaje formulario soporte.
	 *
	 * @param literales
	 *                             literales
	 * @param datosFormSoporte
	 *                             datos form soporte
	 * @param entidad
	 *                             entidad
	 * @param datosSesion
	 *                             datos sesión
	 * @return mensaje (html)
	 */
	public static String construyeMensajeSoporteIncidencias(final Literales literales,
			final DatosFormularioSoporte datosFormSoporte, final RConfiguracionEntidad entidad,
			final DatosSesionTramitacion datosSesion, final String urlSth) {

		final String idioma = datosSesion.getDatosTramite().getIdioma();

		String listaCampos = "";
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "tramiteDesc"),
				datosSesion.getDatosTramite().getTituloTramite());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "tramiteId"),
				datosSesion.getDatosTramite().getIdTramite());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "procedimientoId"),
				datosSesion.getDatosTramite().getDefinicionTramiteCP().getProcedimiento().getIdProcedimientoSIA());

		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "fechaCreacion"),
				formateaFecha(datosSesion.getDatosTramite().getFechaInicio()));
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "idPersistencia"),
				datosSesion.getDatosTramite().getIdSesionTramitacion());
		final String qaa = datosSesion.getDatosTramite()
				.getMetodoAutenticacionInicio() != TypeMetodoAutenticacion.ANONIMO
						? " - QAA: " + (getLiteral(literales, idioma,
								"autenticacion.qaa."
										+ datosSesion.getDatosTramite().getUsuarioAutenticado().getQaa().toString()))
						: "";
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "autenticacion"),
				getLiteral(literales, idioma,
						"autenticacion." + datosSesion.getDatosTramite().getMetodoAutenticacionInicio().toString())
						+ qaa);

		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "nif"), datosFormSoporte.getNif());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "nombre"), datosFormSoporte.getNombre());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "telefono"), datosFormSoporte.getTelefono());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "email"), datosFormSoporte.getEmail());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "horarioContacto"),
				datosFormSoporte.getHorarioContacto());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "problemaTipo"),
				datosFormSoporte.getProblemaTipoDesc());
		listaCampos += addParameterMensaje(getLiteral(literales, idioma, "problemaDesc"),
				datosFormSoporte.getProblemaDesc());

		String urlIdSesionMensaje = literales.getLiteral(Literales.SOPORTE_INCIDENCIAS, "urlIdSesion",
				new String[] { urlSth + "/viewAuditoriaTramites.xhtml?idSesion=" + datosSesion.getDatosTramite().getIdSesionTramitacion() }, idioma);

		String mensaje = getPlantilla();
		mensaje = StringUtils.replace(mensaje, "[#TITULO#]",
				StringEscapeUtils.escapeHtml4(getLiteral(literales, idioma, "titulo")));
		mensaje = StringUtils.replace(mensaje, "[#CONTENIDO#]", listaCampos);
		mensaje = StringUtils.replace(mensaje, "[#TEXTO.URL.IDSESION#]", urlIdSesionMensaje);

		return mensaje;
	}

	/**
	 * Obtiene literal.
	 *
	 * @param literales
	 *                      literales
	 * @param idioma
	 *                      idioma
	 * @param campo
	 *                      campo
	 * @return literal
	 */
	public static String getLiteral(final Literales literales, final String idioma, final String campo) {
		return literales.getLiteral(Literales.SOPORTE_INCIDENCIAS, campo, idioma);
	}

	/**
	 * Monta parametro mensaje.
	 *
	 * @param paramDesc
	 *                       descripción
	 * @param paramValue
	 *                       valor
	 * @return parametro
	 */
	private static String addParameterMensaje(final String paramDesc, final String paramValue) {
		String res = "";
		if (StringUtils.isNotBlank(paramValue)) {
			res = "\n<tr><th>" + StringEscapeUtils.escapeHtml4(paramDesc) + "</th><td>"
					+ StringEscapeUtils.escapeHtml4(paramValue) + "</td></tr>";
		}
		return res;
	}

	/**
	 * Obtiene plantilla correo.
	 *
	 * @return plantilla correo
	 */
	private static String getPlantilla() {
		try {
			if (plantillaHtml == null) {
				final InputStream isHtml = UtilsFormularioSoporte.class.getClassLoader()
						.getResourceAsStream("mailIncidencia.html");
				final byte[] content = IOUtils.toByteArray(isHtml);
				plantillaHtml = new String(content, "UTF-8");
			}
			return plantillaHtml;
		} catch (final Exception ex) {
			throw new ErrorFormularioSoporteException("No s'ha pogut carregar la plantilla per enviament avis", ex);
		}
	}

	/**
	 * Formatea fecha.
	 *
	 * @param fecha
	 *                  fecha
	 * @return string
	 */
	private static String formateaFecha(final Date fecha) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(fecha);
	}
}
