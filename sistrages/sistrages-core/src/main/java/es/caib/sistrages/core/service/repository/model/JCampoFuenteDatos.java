package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * JCampoFuenteDatos
 */
@Entity
@Table(name = "STG_CAMFUE", uniqueConstraints = @UniqueConstraint(columnNames = { "CFU_CODFUE", "CFU_IDENT" }))
public class JCampoFuenteDatos implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_CAMFUE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_CAMFUE_SEQ", sequenceName = "STG_CAMFUE_SEQ")
	@Column(name = "CFU_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CFU_CODFUE", nullable = false)
	private JFuenteDatos fuenteDatos;

	@Column(name = "CFU_IDENT", nullable = false, length = 20)
	private String idCampo;

	@Column(name = "CFU_ESPK", nullable = false, length = 1)
	private String clavePrimaria;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campoFuenteDatos")
	private Set<JValorFuenteDatos> valoresFuenteDatos = new HashSet<JValorFuenteDatos>(0);

	public JCampoFuenteDatos() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFuenteDatos getFuenteDatos() {
		return this.fuenteDatos;
	}

	public void setFuenteDatos(final JFuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public String getIdCampo() {
		return this.idCampo;
	}

	public void setIdCampo(final String idCampo) {
		this.idCampo = idCampo;
	}

	public String getClavePrimaria() {
		return this.clavePrimaria;
	}

	public void setClavePrimaria(final String clavePrimaria) {
		this.clavePrimaria = clavePrimaria;
	}

	public Set<JValorFuenteDatos> getValoresFuenteDatos() {
		return this.valoresFuenteDatos;
	}

	public void setValoresFuenteDatos(final Set<JValorFuenteDatos> valoresFuenteDatos) {
		this.valoresFuenteDatos = valoresFuenteDatos;
	}

}
