package es.caib.sistrages.frontend.model;

public class ResultadoError {

	private int codigo;
	private String mensaje;

	public ResultadoError() {
		super();
	}

	public ResultadoError(final int codigo, final String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(final int codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}
}
