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

import es.caib.sistrages.core.api.model.ParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeParametroDominio;

/**
 * JParametroDominioCampoIndexado
 */
@Entity
@Table(name = "STG_PRDCIN")
public class JParametroDominioCampoIndexado implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PRDCIN_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PRDCIN_SEQ", sequenceName = "STG_PRDCIN_SEQ")
	@Column(name = "CIP_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CIP_CODCIN", nullable = false)
	private JCampoFormularioIndexado campoFormularioIndexado;

	@Column(name = "CIP_TIPO", nullable = false, length = 1)
	private String tipo;

	@Column(name = "CIP_VALOR", nullable = false, length = 1000)
	private String valor;

	@Column(name = "CIP_PARAM", length = 1000)
	private String parametro;

	public JParametroDominioCampoIndexado() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JCampoFormularioIndexado getCampoFormularioIndexado() {
		return this.campoFormularioIndexado;
	}

	public void setCampoFormularioIndexado(final JCampoFormularioIndexado campoFormularioIndexado) {
		this.campoFormularioIndexado = campoFormularioIndexado;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String cipTipo) {
		this.tipo = cipTipo;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(final String cipValor) {
		this.valor = cipValor;
	}

	public String getParametro() {
		return this.parametro;
	}

	public void setParametro(final String parametro) {
		this.parametro = parametro;
	}

	public ParametroDominio toModel() {
		final ParametroDominio parametroDominio = new ParametroDominio();
		parametroDominio.setId(codigo);
		parametroDominio.setTipo(TypeParametroDominio.fromString(tipo));
		parametroDominio.setValor(valor);
		parametroDominio.setParametro(parametro);
		return parametroDominio;
	}

	public static JParametroDominioCampoIndexado fromModel(final ParametroDominio model) {
		JParametroDominioCampoIndexado jModel = null;
		if (model != null) {
			jModel = new JParametroDominioCampoIndexado();
			jModel.setCodigo(model.getId());
			jModel.setTipo(model.getTipo().toString());
			jModel.setValor(model.getValor());
			jModel.setParametro(model.getParametro());
		}
		return jModel;
	}
}
