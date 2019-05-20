package es.caib.sistramit.core.service.component.script.plugins;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistramit.core.api.model.comun.types.TypeValidacion;
import es.caib.sistramit.core.service.model.script.PlgValidacionInt;

/**
 * Plugin que permite establecer un mensaje de aviso al ejecutar el script.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgValidacion implements PlgValidacionInt {

	/**
	 * Indica si existe aviso.
	 */
	private boolean existeAviso;

	/**
	 * Indica tipo aviso: INFO / WARNING / ERROR.
	 */
	private TypeValidacion tipoAviso;

	/**
	 * Código mensaje aviso. Para establecer aviso por codigo mensaje.
	 */
	private String codigoMensajeAviso;

	/**
	 * Parámetros a substuir en el mensaje que hace referencia el código de error.
	 * ({0} {1} ...).
	 */
	private final List<String> parametrosMensajeAviso = new ArrayList<String>();

	/**
	 * Mensaje aviso particularizado. Para establecer error directamente con el
	 * texto.
	 */
	private String textoMensajeAviso;

	/**
	 * Campo formulario asociado a la validación.
	 */
	private String campo;

	/**
	 * Constructor.
	 */
	public PlgValidacion() {
		super();
	}

	/**
	 * Constructor (validación asociada a un elemento).
	 */
	public PlgValidacion(final String pIdElemento) {
		super();
		this.campo = pIdElemento;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Indica si hay error.
	 *
	 * @return Indica si hay error.
	 */
	public boolean isExisteAviso() {
		return existeAviso;
	}

	@Override
	public void setExisteAviso(final boolean pExisteaAviso, final String pTipo) throws ScriptException {
		existeAviso = pExisteaAviso;
		tipoAviso = TypeValidacion.fromString(StringUtils.lowerCase(pTipo));
		if (tipoAviso == null) {
			throw new ScriptException("Tipo de aviso no valido: " + pTipo);
		}
	}

	/**
	 * Obtiene codigo de error.
	 *
	 * @return Obtiene codigo de error.
	 */
	public String getCodigoMensajeAviso() {
		return codigoMensajeAviso;
	}

	@Override
	public void addParametroMensajeAviso(final String parametro) {
		parametrosMensajeAviso.add(parametro);
	}

	@Override
	public void setCodigoMensajeAviso(final String pCodigoMensajeError) {
		codigoMensajeAviso = pCodigoMensajeError;
	}

	/**
	 * Obtiene texto de error.
	 *
	 * @return Obtiene texto de error.
	 */
	public String getTextoMensajeAviso() {
		return textoMensajeAviso;
	}

	@Override
	public void setTextoMensajeAviso(final String pTextoMensajeError) {
		textoMensajeAviso = pTextoMensajeError;
	}

	/**
	 * Obtiene parametros de error.
	 *
	 * @return Obtiene parametros de error.
	 */
	public List<String> getParametrosMensajeAviso() {
		return parametrosMensajeAviso;
	}

	/**
	 * Método de acceso a tipoAviso.
	 *
	 * @return tipoAviso
	 */
	public TypeValidacion getTipoAviso() {
		return tipoAviso;
	}

	/**
	 * Método de acceso a campo.
	 *
	 * @return idElemento
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * Método para establecer idElemento.
	 *
	 * @param idElemento
	 *            idElemento a establecer
	 */
	public void setCampo(final String idElemento) {
		this.campo = idElemento;
	}

	@Override
	public void setExisteAviso(final boolean pExisteAviso, final String pTipo, final String pCampo)
			throws ScriptException {
		this.setExisteAviso(pExisteAviso, pTipo);
		this.setCampo(pCampo);
	}

}
