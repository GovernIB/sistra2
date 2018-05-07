package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeParametroDominio;

@SuppressWarnings("serial")
public class ParametroDominio extends ModelApi {

	private Long id;

	private TypeParametroDominio tipo;

	private String valor;

	private String parametro;

	public ParametroDominio() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public TypeParametroDominio getTipo() {
		return tipo;
	}

	public void setTipo(final TypeParametroDominio tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(final String valor) {
		this.valor = valor;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(final String parametro) {
		this.parametro = parametro;
	}
}
