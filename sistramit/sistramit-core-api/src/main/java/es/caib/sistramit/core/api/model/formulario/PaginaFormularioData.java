package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.exception.CampoFormularioNoExisteException;
import es.caib.sistramit.core.api.exception.ErrorCampoNoModificableException;
import es.caib.sistramit.core.api.exception.TipoValorCampoFormularioException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

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
public final class PaginaFormularioData implements Serializable {

	/**
	 * Id formulario al que pertenece la pagina.
	 */
	private String idFormulario;

	/**
	 * Mostrar titulo.
	 */
	private TypeSiNo mostrarTitulo;

	/**
	 * Titulo formulario.
	 */
	private String titulo;

	/**
	 * Indice página en la definición de formulario.
	 */
	private int indiceDef;

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
	 * Recursos estáticos del formulario. Se establecen las urls de estos recursos
	 * estáticos para establecerse en el html del formulario.
	 */
	private RecursosFormulario recursos;

	/**
	 * Constructor.
	 *
	 * @param pIdFormulario
	 *            id formulario
	 */
	public PaginaFormularioData(final String pIdFormulario, final int pIndiceDef) {
		this.idFormulario = pIdFormulario;
		this.indiceDef = pIndiceDef;
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
	 * Método para establecer idFormulario.
	 *
	 * @param pIdFormulario
	 *            idFormulario a establecer
	 */
	public void setIdFormulario(final String pIdFormulario) {
		idFormulario = pIdFormulario;
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
	 *            configuracion a establecer
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
	 *            acciones a establecer
	 */
	public void setAccionesPersonalizadas(final List<AccionFormularioPersonalizada> pAcciones) {
		accionesPersonalizadas = pAcciones;
	}

	/**
	 * Obtiene el valor de un campo.
	 *
	 * @param idCampo
	 *            Id campo
	 * @return Valor campo
	 */
	public ValorCampo getValorCampo(final String idCampo) {
		return getValorCampoLista(idCampo, valores);
	}

	/**
	 * Método de acceso a recursos.
	 *
	 * @return el recursos
	 */
	public RecursosFormulario getRecursos() {
		return recursos;
	}

	/**
	 * Método para settear el campo recursos.
	 *
	 * @param pRecursos
	 *            el recursos a settear
	 */
	public void setRecursos(final RecursosFormulario pRecursos) {
		recursos = pRecursos;
	}

	/**
	 * Método para actualizar valores modificados.
	 *
	 * @param pValores
	 *            valores a establecer
	 */
	public void actualizarValoresPagina(final List<ValorCampo> pValores) {
		for (final ValorCampo vc : pValores) {
			actualizarValorCampo(vc);
		}
	}

	/**
	 * Inicializa un campo.
	 *
	 * @param pConfCampo
	 *            Configuracion campo
	 * @param pValorCampo
	 *            valor campo
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
	 *            Valor campo
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
			final ValorCampo vcInicial = getValorCampoLista(pvcNew.getId(), valoresIniciales);
			if (!pvcNew.esValorIgual(vcInicial)) {
				throw new ErrorCampoNoModificableException("El campo " + vcOld.getId() + " no es modificable");
			}
		}

		// Reemplazamos valor
		vcOld.reemplazaValor(pvcNew);

	}

	/**
	 * Obtiene la configuración de un campo.
	 *
	 * @param idCampo
	 *            Id campo
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
	 *            Id campo
	 * @param pListaValores
	 *            Lista de valores
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
	 * Método de acceso a indiceDef.
	 *
	 * @return indiceDef
	 */
	public int getIndiceDef() {
		return indiceDef;
	}

	/**
	 * Método para establecer indiceDef.
	 *
	 * @param indiceDef
	 *            indiceDef a establecer
	 */
	public void setIndiceDef(int indiceDef) {
		this.indiceDef = indiceDef;
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
	 *            Código de acción
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
	 *            titulo a establecer
	 */
	public void setTitulo(String titulo) {
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
	 *            mostrarTitulo a establecer
	 */
	public void setMostrarTitulo(TypeSiNo mostrarTitulo) {
		this.mostrarTitulo = mostrarTitulo;
	}

}
