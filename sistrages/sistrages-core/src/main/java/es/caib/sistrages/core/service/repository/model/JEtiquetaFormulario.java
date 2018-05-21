package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.types.TypeEtiqueta;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * JEtiquetaFormulario
 */
@Entity
@Table(name = "STG_FORETQ")
public class JEtiquetaFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ETI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "ETI_CODIGO")
	private JElementoFormulario elementoFormulario;

	@Column(name = "ETI_TIPETI", nullable = false, length = 1)
	private String tipo;

	public JEtiquetaFormulario() {
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

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public ComponenteFormularioEtiqueta toModel() {
		ComponenteFormularioEtiqueta etiqueta = null;

		if (elementoFormulario != null) {
			etiqueta = (ComponenteFormularioEtiqueta) elementoFormulario.toModel(ComponenteFormularioEtiqueta.class);
			if (etiqueta != null) {
				etiqueta.setTipoEtiqueta(TypeEtiqueta.fromString(tipo));
			}
		}

		return etiqueta;
	}

	public static JEtiquetaFormulario createDefault(final int pOrden, final JLineaFormulario pJLinea) {
		final JEtiquetaFormulario jModel = new JEtiquetaFormulario();
		jModel.setTipo(TypeEtiqueta.INFO.toString());
		jModel.setElementoFormulario(JElementoFormulario.createDefault(TypeObjetoFormulario.ETIQUETA, pOrden, pJLinea));
		return jModel;
	}

}
