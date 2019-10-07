package es.caib.sistramit.core.service.model.integracion;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;

/**
 * Datos usario retornados por Clave.
 *
 * @author Indra
 *
 */
public class DatosAutenticacionUsuario {

	/** Nif. */
	private String nif;

	/** Nombre. */
	private String nombre;

	/** Apellido 1. */
	private String apellido1;

	/** Apellido 2. */
	private String apellido2;

	/** Email. */
	private String email;

	/** Qaa. */
	private TypeQAA qaa;

	/** Tipo autenticacion. */
	private TypeAutenticacion autenticacion;

	/** Metodo autenticacion. */
	private TypeMetodoAutenticacion metodoAutenticacion;

	/** Representante. */
	private DatosAutenticacionRepresentante representante;

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param pNif
	 *                 nif a establecer
	 */
	public void setNif(final String pNif) {
		nif = pNif;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param pNombre
	 *                    nombre a establecer
	 */
	public void setNombre(final String pNombre) {
		nombre = pNombre;
	}

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método para establecer apellido1.
	 *
	 * @param pApellido1
	 *                       apellido1 a establecer
	 */
	public void setApellido1(final String pApellido1) {
		apellido1 = pApellido1;
	}

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Método para establecer apellido2.
	 *
	 * @param pApellido2
	 *                       apellido2 a establecer
	 */
	public void setApellido2(final String pApellido2) {
		apellido2 = pApellido2;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param pMetodoAutenticacion
	 *                                 metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final TypeMetodoAutenticacion pMetodoAutenticacion) {
		metodoAutenticacion = pMetodoAutenticacion;
	}

	/**
	 * Método de acceso a email.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método para establecer email.
	 *
	 * @param email
	 *                  email a establecer
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public TypeAutenticacion getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param autenticacion
	 *                          autenticacion a establecer
	 */
	public void setAutenticacion(final TypeAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a representante.
	 *
	 * @return representante
	 */
	public DatosAutenticacionRepresentante getRepresentante() {
		return representante;
	}

	/**
	 * Método para establecer representante.
	 *
	 * @param representante
	 *                          representante a establecer
	 */
	public void setRepresentante(final DatosAutenticacionRepresentante representante) {
		this.representante = representante;
	}

	/**
	 * Método de acceso a qaa.
	 * 
	 * @return qaa
	 */
	public TypeQAA getQaa() {
		return qaa;
	}

	/**
	 * Método para establecer qaa.
	 * 
	 * @param qaa
	 *                qaa a establecer
	 */
	public void setQaa(final TypeQAA qaa) {
		this.qaa = qaa;
	}

}
