package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.exception.CampoFormularioNoExisteException;
import es.caib.sistramit.core.api.exception.ErrorCampoNoModificableException;
import es.caib.sistramit.core.api.exception.TipoValorCampoFormularioException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.AccionFormularioPersonalizada;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;

/**
 *
 * Campos de una página del formulario que se almacena en sesión: configuración
 * y valores.
 *
 * Para establecer los valores de los campos se deberá utilizar la función
 * setValorCampo o setValoresCampos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PaginaData implements Serializable {

	/**
	 * Id formulario al que pertenece la pagina.
	 */
	private final String idFormulario;

	/**
	 * Mostrar titulo.
	 */
	private TypeSiNo mostrarTitulo;

	/**
	 * Titulo formulario.
	 */
	private String titulo;

	/**
	 * Identificador página.
	 */
	private final String identificador;

	/**
	 * Configuracion de los campos.
	 */
	private List<ConfiguracionCampo> configuracion = new ArrayList<>();

	/**
	 * Valores de los campos.
	 */
	private final List<ValorCampo> valores = new ArrayList<>();

	/**
	 * Valores iniciales de los campos.
	 */
	private final List<ValorCampo> valoresIniciales = new ArrayList<>();

	/**
	 * Acciones personalizadas del formulario.
	 */
	private List<AccionFormularioPersonalizada> accionesPersonalizadas = new ArrayList<>();

	/**
	 * Dependencias de los campos de la página.
	 */
	private List<DependenciaCampo> dependencias;

	/**
	 * Constructor.
	 *
	 * @param pIdFormulario
	 *                          id formulario
	 * @param pIdPagina
	 *                          id pagina
	 */
	public PaginaData(final String pIdFormulario, final String pIdPagina) {
		this.idFormulario = pIdFormulario;
		this.identificador = pIdPagina;
	}

	/**
	 * Método de acceso a idFormulario.
	 *
	 * @return idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * Método de acceso a configuracion.
	 *
	 * @return configuracion
	 */
	public List<ConfiguracionCampo> getConfiguracion() {
		return configuracion;
	}

	/**
	 * Método para establecer configuracion.
	 *
	 * @param pConfiguracion
	 *                           configuracion a establecer
	 */
	public void setConfiguracion(final List<ConfiguracionCampo> pConfiguracion) {
		configuracion = pConfiguracion;
	}

	/**
	 * Método de acceso a valores.
	 *
	 * @return valores
	 */
	public List<ValorCampo> getValores() {
		return valores;
	}

	/**
	 * Método de acceso a acciones.
	 *
	 * @return acciones
	 */
	public List<AccionFormularioPersonalizada> getAccionesPersonalizadas() {
		return accionesPersonalizadas;
	}

	/**
	 * Método para establecer acciones.
	 *
	 * @param pAcciones
	 *                      acciones a establecer
	 */
	public void setAccionesPersonalizadas(final List<AccionFormularioPersonalizada> pAcciones) {
		accionesPersonalizadas = pAcciones;
	}

	/**
	 * Obtiene el valor de un campo.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @return Valor campo
	 */
	public ValorCampo getValorCampo(final String idCampo) {
		return getValorCampoLista(idCampo, valores);
	}

	/**
	 * Obtiene el valor inicial de un campo.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @return Valor campo
	 */
	public ValorCampo getValorInicialCampo(final String idCampo) {
		return getValorCampoLista(idCampo, valoresIniciales);
	}

	/**
	 * Método para actualizar valores modificados.
	 *
	 * @param pValores
	 *                     valores a establecer
	 */
	public void actualizarValoresPagina(final List<ValorCampo> pValores) {
		for (final ValorCampo vc : pValores) {
			// Se actualizan valores excepto si es un captcha (nunca se actualiza valor)
			if (this.getConfiguracionCampo(vc.getId()).getTipo() != TypeCampo.CAPTCHA) {
				actualizarValorCampo(vc);
			}
		}
	}

	/**
	 * Inicializa un campo.
	 *
	 * @param pConfCampo
	 *                        Configuracion campo
	 * @param pValorCampo
	 *                        valor campo
	 */
	public void inicializaCampo(final ConfiguracionCampo pConfCampo, final ValorCampo pValorCampo) {
		this.configuracion.add(pConfCampo);
		this.valores.add(pValorCampo);
		this.valoresIniciales.add(pValorCampo.duplicar());
	}

	/**
	 * Establece valor campo.
	 *
	 * @param pvcNew
	 *                   Valor campo
	 */
	public void actualizarValorCampo(final ValorCampo pvcNew) {

		// Obtenemos valor actual campo
		final ValorCampo vcOld = getValorCampoLista(pvcNew.getId(), valores);

		// Comprobamos si el campo existe
		if (vcOld == null) {
			throw new CampoFormularioNoExisteException(this.getIdFormulario(), pvcNew.getId());
		}

		// Verificamos si es del tipo correcto
		if (vcOld.getTipo() != pvcNew.getTipo()) {
			throw new TipoValorCampoFormularioException(pvcNew.getId());
		}

		if (this.getConfiguracionCampo(vcOld.getId()).getModificable() == TypeSiNo.NO) {
			final ValorCampo vcInicial = getValorInicialCampo(pvcNew.getId());
			if (!pvcNew.esValorIgual(vcInicial)) {
				throw new ErrorCampoNoModificableException("El camp " + vcOld.getId() + " no és modificable");
			}
		}

		// Reemplazamos valor
		vcOld.reemplazaValor(pvcNew);

	}

	/**
	 * Reinicializa valor de un campo (cambia valor inicial).
	 *
	 * @param pValorCampo
	 *                        valor campo
	 */
	public void reinicializarValorCampo(final ValorCampo pValorCampo) {
		final ValorCampo vc = getValorCampo(pValorCampo.getId());
		if (vc != null) {
			this.valores.remove(vc);
		}
		final ValorCampo vcInicial = getValorCampoLista(pValorCampo.getId(), valoresIniciales);
		if (vcInicial != null) {
			this.valoresIniciales.remove(vcInicial);
		}

		this.valores.add(pValorCampo);
		this.valoresIniciales.add(pValorCampo.duplicar());
	}

	/**
	 * Obtiene la configuración de un campo.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @return Configuración campo
	 */
	public ConfiguracionCampo getConfiguracionCampo(final String idCampo) {
		ConfiguracionCampo res = null;
		for (final ConfiguracionCampo cc : configuracion) {
			if (cc.getId().equals(idCampo)) {
				res = cc;
				break;
			}
		}
		return res;
	}

	/**
	 * Obtiene valor campo de una lista de valores.
	 *
	 * @param pIdCampo
	 *                          Id campo
	 * @param pListaValores
	 *                          Lista de valores
	 * @return Valor campo
	 */
	private ValorCampo getValorCampoLista(final String pIdCampo, final List<ValorCampo> pListaValores) {
		ValorCampo res = null;
		for (final ValorCampo vc : pListaValores) {
			if (vc.getId().equals(pIdCampo)) {
				res = vc;
				break;
			}
		}
		return res;
	}

	/**
	 * Método de acceso a valoresIniciales.
	 *
	 * @return valoresIniciales
	 */
	public List<ValorCampo> getValoresIniciales() {
		return valoresIniciales;
	}

	/**
	 * Busca accion personalizada en lista de acciones.
	 *
	 * @param codigoAccion
	 *                         Código de acción
	 * @return Acción personalizada
	 */
	public AccionFormularioPersonalizada buscarAccion(final String codigoAccion) {
		AccionFormularioPersonalizada res = null;
		if (accionesPersonalizadas != null) {
			for (final AccionFormularioPersonalizada accion : accionesPersonalizadas) {
				if (accion.getValor().equals(codigoAccion)) {
					res = accion;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param titulo
	 *                   titulo a establecer
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Método de acceso a mostrarTitulo.
	 *
	 * @return mostrarTitulo
	 */
	public TypeSiNo getMostrarTitulo() {
		return mostrarTitulo;
	}

	/**
	 * Método para establecer mostrarTitulo.
	 *
	 * @param mostrarTitulo
	 *                          mostrarTitulo a establecer
	 */
	public void setMostrarTitulo(final TypeSiNo mostrarTitulo) {
		this.mostrarTitulo = mostrarTitulo;
	}

	/**
	 * Método de acceso a identificador.
	 *
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método de acceso a dependencias.
	 *
	 * @return dependencias
	 */
	public List<DependenciaCampo> getDependencias() {
		return dependencias;
	}

	/**
	 * Método para establecer dependencias.
	 *
	 * @param dependencias
	 *                         dependencias a establecer
	 */
	public void setDependencias(final List<DependenciaCampo> dependencias) {
		this.dependencias = dependencias;
	}

}
