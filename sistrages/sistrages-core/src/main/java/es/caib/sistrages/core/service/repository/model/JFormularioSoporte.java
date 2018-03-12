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
public class JFormularioSoporte implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORSOP_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORSOP_SEQ", sequenceName = "STG_FORSOP_SEQ")
	@Column(name = "FSO_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FSO_CODENT", nullable = false)
	private JEntidades entidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FSO_TIPINC", nullable = false)
	private JLiterales literalTipoIncidencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FSO_DSCTIP", nullable = false)
	private JLiterales descripcionTipoIncidencia;

	@Column(name = "FSO_TIPDST", nullable = false, length = 1)
	private String tipoDestinatario;

	@Column(name = "FSO_LSTEMA", length = 4000)
	private String listaEmails;

	public JFormularioSoporte() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long fsoCodigo) {
		this.codigo = fsoCodigo;
	}

	public JEntidades getEntidad() {
		return this.entidad;
	}

	public void setEntidad(final JEntidades entidad) {
		this.entidad = entidad;
	}

	public JLiterales getLiteralTipoIncidencia() {
		return this.literalTipoIncidencia;
	}

	public void setLiteralTipoIncidencia(final JLiterales literalTipoIncidencia) {
		this.literalTipoIncidencia = literalTipoIncidencia;
	}

	public JLiterales getDescripcionTipoIncidencia() {
		return this.descripcionTipoIncidencia;
	}

	public void setDescripcionTipoIncidencia(final JLiterales descripcionTipoIncidencia) {
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
