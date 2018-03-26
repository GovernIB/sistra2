package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JFormularioSoporte
 */
@Entity
@Table(name = "STG_FORSOP")
public class JFormularioSoporte implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORSOP_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORSOP_SEQ", sequenceName = "STG_FORSOP_SEQ")
	@Column(name = "FSO_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FSO_CODENT", nullable = false)
	private JEntidad entidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FSO_TIPINC", nullable = false)
	private JLiteral literalTipoIncidencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FSO_DSCTIP", nullable = false)
	private JLiteral descripcionTipoIncidencia;

	@Column(name = "FSO_TIPDST", nullable = false, length = 1)
	private String tipoDestinatario;

	@Column(name = "FSO_LSTEMA", length = 4000)
	private String listaEmails;

	public JFormularioSoporte() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JEntidad getEntidad() {
		return this.entidad;
	}

	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
	}

	public JLiteral getLiteralTipoIncidencia() {
		return this.literalTipoIncidencia;
	}

	public void setLiteralTipoIncidencia(final JLiteral literalTipoIncidencia) {
		this.literalTipoIncidencia = literalTipoIncidencia;
	}

	public JLiteral getDescripcionTipoIncidencia() {
		return this.descripcionTipoIncidencia;
	}

	public void setDescripcionTipoIncidencia(final JLiteral descripcionTipoIncidencia) {
		this.descripcionTipoIncidencia = descripcionTipoIncidencia;
	}

	public String getTipoDestinatario() {
		return this.tipoDestinatario;
	}

	public void setTipoDestinatario(final String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public String getListaEmails() {
		return this.listaEmails;
	}

	public void setListaEmails(final String listaEmails) {
		this.listaEmails = listaEmails;
	}

}
