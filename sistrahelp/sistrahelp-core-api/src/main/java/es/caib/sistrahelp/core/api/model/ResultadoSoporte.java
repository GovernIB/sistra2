package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 * La clase ResultadoEventoAuditoria.
 */
public final class ResultadoSoporte extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de EventoAuditoriaTramitacion.
	 */
	public ResultadoSoporte() {
		super();
	}

	/**
	 * id sesion tramitacion.
	 */
	private Long numElementos;

	private List<Soporte> listaFormularios;

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

	public List<Soporte> getListaFormularios() {
		return listaFormularios;
	}

	public void setListaFormularios(final List<Soporte> listaFormularios) {
		this.listaFormularios = listaFormularios;
	}

}
