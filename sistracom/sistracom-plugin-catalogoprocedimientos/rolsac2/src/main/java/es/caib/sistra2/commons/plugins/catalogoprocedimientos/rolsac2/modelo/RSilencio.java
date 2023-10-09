package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

import java.util.Calendar;

/**
 * Serveis.
 *
 * @author Indra
 *
 */
public class RSilencio {

	/** codigo **/
	private long codigo;

	private String identificador;

	private String descripcion;

	private String descripcion2;

	private Calendar fechaBorrar;

	/**
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the descripcion2
	 */
	public String getDescripcion2() {
		return descripcion2;
	}

	/**
	 * @param descripcion2 the descripcion2 to set
	 */
	public void setDescripcion2(String descripcion2) {
		this.descripcion2 = descripcion2;
	}

	/**
	 * @return the fechaBorrar
	 */
	public Calendar getFechaBorrar() {
		return fechaBorrar;
	}

	/**
	 * @param fechaBorrar the fechaBorrar to set
	 */
	public void setFechaBorrar(Calendar fechaBorrar) {
		this.fechaBorrar = fechaBorrar;
	}
}
