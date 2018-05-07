package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ComponenteFormularioImagen;

/**
 * JImagenFormulario
 */
@Entity
@Table(name = "STG_FORIMG")
public class JImagenFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FIM_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIM_CODFIC", nullable = false)
	private JFichero fichero;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "FIM_CODIGO")
	private JElementoFormulario elementoFormulario;

	public JImagenFormulario() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFichero getFichero() {
		return this.fichero;
	}

	public void setFichero(final JFichero fichero) {
		this.fichero = fichero;
	}

	public JElementoFormulario getElementoFormulario() {
		return this.elementoFormulario;
	}

	public void setElementoFormulario(final JElementoFormulario elementoFormulario) {
		this.elementoFormulario = elementoFormulario;
	}

	public ComponenteFormularioImagen toModel() {
		ComponenteFormularioImagen imagen = null;

		if (elementoFormulario != null) {
			imagen = (ComponenteFormularioImagen) elementoFormulario.toModel(ComponenteFormularioImagen.class);
			if (imagen != null && fichero != null) {
				imagen.setFichero(fichero.toModel());
			}

		}

		return imagen;
	}
}
