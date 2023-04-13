package es.caib.sistrahelp.core.api.model;

import java.util.Date;

import es.caib.sistrahelp.core.api.model.types.TypeEvento;

/**
 * Dominio.
 *
 * @author Indra
 *
 */

public class HistorialAlerta extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	private Long codigo;

	private Alerta alerta;

	private String evento;

	private Date fecha;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public HistorialAlerta() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public final Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public final void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigoAviso
	 */
	public final Alerta getAlerta() {
		return alerta;
	}

	/**
	 * @param codigoAviso the codigoAviso to set
	 */
	public final void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	/**
	 * @return the fecha
	 */
	public final Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public final void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the evento
	 */
	public final String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public final void setEvento(String evento) {
		this.evento = evento;
	}
}
