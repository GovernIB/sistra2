package es.caib.sistrages.core.service.repository.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ComponenteFormularioListaElementos;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JListaElementosFormulario
 */
@Entity
@Table(name = "STG_FORLEL")
public class JListaElementosFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LEL_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "LEL_CODIGO")
	private JElementoFormulario elementoFormulario;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "LEL_CODIGO")
	private JCampoFormulario campoFormulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEL_CODFOR", nullable = false)
	private JFormulario  formularioAsociado;

	@Column(name = "LEL_MAXELE", nullable = false, precision = 3, scale = 0)
	private int numeroMaximoElementos;

	/** Constructor. **/
	public JListaElementosFormulario() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JElementoFormulario getElementoFormulario() {
		return this.elementoFormulario;
	}

	public void setElementoFormulario(final JElementoFormulario elementoFormulario) {
		this.elementoFormulario = elementoFormulario;
	}

	/**
	 * @return the formularioAsociado
	 */
	public JFormulario getFormularioAsociado() {
		return formularioAsociado;
	}

	/**
	 * @return the campoFormulario
	 */
	public JCampoFormulario getCampoFormulario() {
		return campoFormulario;
	}

	/**
	 * @param campoFormulario the campoFormulario to set
	 */
	public void setCampoFormulario(JCampoFormulario campoFormulario) {
		this.campoFormulario = campoFormulario;
	}

	/**
	 * @param formularioAsociado the formularioAsociado to set
	 */
	public void setFormularioAsociado(JFormulario formularioAsociado) {
		this.formularioAsociado = formularioAsociado;
	}



	/**
	 * @return the numeroMaximoElementos
	 */
	public int getNumeroMaximoElementos() {
		return numeroMaximoElementos;
	}

	/**
	 * @param numeroMaximoElementos the numeroMaximoElementos to set
	 */
	public void setNumeroMaximoElementos(int numeroMaximoElementos) {
		this.numeroMaximoElementos = numeroMaximoElementos;
	}

	public static JListaElementosFormulario clonar(final JListaElementosFormulario listaElementosFormulario,
			final JLineaFormulario jlinea, JPaginaFormulario jpagina, final JElementoFormulario jelem, boolean cambioArea, final Map<Long, JFormulario> mapLE) {
		JListaElementosFormulario jlista = null;
		if (listaElementosFormulario != null) {
			jlista = new JListaElementosFormulario();
			jlista.setElementoFormulario(jelem);
			if (mapLE != null && listaElementosFormulario.getFormularioAsociado() != null && mapLE.get(listaElementosFormulario.getFormularioAsociado().getCodigo()) != null) {
				jlista.setFormularioAsociado(mapLE.get(listaElementosFormulario.getFormularioAsociado().getCodigo()) );
			}
			jlista.setNumeroMaximoElementos(listaElementosFormulario.getNumeroMaximoElementos());
		}
		return jlista;
	}

	public static JListaElementosFormulario createDefault(final int pOrden, final JLineaFormulario pJLinea, final boolean isTipoSeccion, final String identificadorSeccion) {
		final JListaElementosFormulario jModel = new JListaElementosFormulario();
		jModel.setCampoFormulario(JCampoFormulario.createDefault(TypeObjetoFormulario.LISTA_ELEMENTOS, pOrden, pJLinea, isTipoSeccion, identificadorSeccion));
		jModel.setNumeroMaximoElementos(10);
		return jModel;
	}


	public ComponenteFormularioListaElementos toModel() {
		ComponenteFormularioListaElementos campoLE = null;

		if (elementoFormulario != null) {
			if (elementoFormulario.getCampoFormulario() != null) {
				campoLE = (ComponenteFormularioListaElementos) elementoFormulario.getCampoFormulario().toModel(ComponenteFormularioListaElementos.class);
			} else {
				campoLE = (ComponenteFormularioListaElementos) elementoFormulario.getListaElementosFormulario().getCampoFormulario().toModel(ComponenteFormularioListaElementos.class);
			}
			if (campoLE != null) {
				campoLE.setTexto(campoLE.getTexto());
			}
			campoLE.setNumeroMaximoElementos(numeroMaximoElementos);
			if (elementoFormulario.getListaElementosFormulario().getFormularioAsociado() != null) {
				campoLE.setIdFormulario(elementoFormulario.getListaElementosFormulario().getFormularioAsociado().getCodigo());
			}
		}

		return campoLE;
	}

}
