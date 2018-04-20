package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JSeccionFormulario
 */
@Entity
@Table(name = "STG_FORSEC")
public class JSeccionFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FSE_CODIGO", length = 19)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "FSE_CODIGO")
	private JElementoFormulario elementoFormulario;

	@Column(name = "FSE_LETRA", length = 2)
	private String letra;

	public JSeccionFormulario() {
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

	public String getLetra() {
		return this.letra;
	}

	public void setLetra(final String letra) {
		this.letra = letra;
	}

}
