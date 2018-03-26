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

/**
 * JFilasFuenteDatos
 */
@Entity
@Table(name = "STG_FILFUE")
public class JFilasFuenteDatos implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FILFUE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FILFUE_SEQ", sequenceName = "STG_FILFUE_SEQ")
	@Column(name = "FIF_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIF_CODFUE", nullable = false)
	private JFuenteDatos fuenteDatos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "filaFuenteDatos")
	private Set<JValorFuenteDatos> valoresFuenteDatos = new HashSet<JValorFuenteDatos>(0);

	public JFilasFuenteDatos() {
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

	public Set<JValorFuenteDatos> getValoresFuenteDatos() {
		return this.valoresFuenteDatos;
	}

	public void setValoresFuenteDatos(final Set<JValorFuenteDatos> valoresFuenteDatos) {
		this.valoresFuenteDatos = valoresFuenteDatos;
	}

}
