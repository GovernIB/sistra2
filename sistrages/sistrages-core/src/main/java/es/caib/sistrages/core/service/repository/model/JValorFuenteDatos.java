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

import es.caib.sistrages.core.api.model.FuenteDatosValor;

/**
 * JValorFuenteDatos
 */
@Entity
@Table(name = "STG_VALCFU")
public class JValorFuenteDatos implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_VALCFU_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_VALCFU_SEQ", sequenceName = "STG_VALCFU_SEQ")
	@Column(name = "VCF_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VCF_CODCFU", nullable = false)
	private JCampoFuenteDatos campoFuenteDatos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VCF_CODFIF", nullable = false)
	private JFilasFuenteDatos filaFuenteDatos;

	@Column(name = "VCF_VALOR", length = 4000)
	private String valor;

	public JValorFuenteDatos() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JCampoFuenteDatos getCampoFuenteDatos() {
		return this.campoFuenteDatos;
	}

	public void setCampoFuenteDatos(final JCampoFuenteDatos campoFuenteDatos) {
		this.campoFuenteDatos = campoFuenteDatos;
	}

	public JFilasFuenteDatos getFilaFuenteDatos() {
		return this.filaFuenteDatos;
	}

	public void setFilaFuenteDatos(final JFilasFuenteDatos filaFuenteDatos) {
		this.filaFuenteDatos = filaFuenteDatos;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(final String vcfValor) {
		this.valor = vcfValor;
	}

	public FuenteDatosValor toModel() {
		final FuenteDatosValor fuenteDatosValor = new FuenteDatosValor();
		fuenteDatosValor.setCodigo(this.getCodigo());
		fuenteDatosValor.setValor(this.getValor());
		fuenteDatosValor.setIdCampo(this.getCampoFuenteDatos().getIdCampo());
		fuenteDatosValor.setOrdenCampo(this.getCampoFuenteDatos().getOrden());
		fuenteDatosValor.setCampo(this.getCampoFuenteDatos().toModel());
		return fuenteDatosValor;
	}

	public void fromModel(final FuenteDatosValor fila) {
		if (fila != null) {
			this.setCodigo(fila.getCodigo());
			this.setValor(fila.getValor());

			if (fila.getCampo() != null) {
				final JCampoFuenteDatos campo = new JCampoFuenteDatos();
				campo.fromModel(fila.getCampo());
				this.setCampoFuenteDatos(campo);
			}
		}
	}

}
