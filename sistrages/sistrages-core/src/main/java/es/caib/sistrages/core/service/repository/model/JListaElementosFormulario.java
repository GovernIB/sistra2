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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEL_CODPAF", nullable = false)
	private JPaginaFormulario paginaFormulario;

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

	public JPaginaFormulario getPaginaFormulario() {
		return this.paginaFormulario;
	}

	public void setPaginaFormulario(final JPaginaFormulario paginaFormulario) {
		this.paginaFormulario = paginaFormulario;
	}

}
