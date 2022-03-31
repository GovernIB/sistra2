package es.caib.sistramit.core.service.component.script.plugins.flujo;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistramit.core.service.component.script.ScriptUtils;
import es.caib.sistramit.core.service.model.script.flujo.ResPersonaInt;

/**
 * Establece datos persona.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResPersona implements ResPersonaInt {

	/**
	 * Indica si es nulo.
	 */
	private boolean nulo = true;

	/**
	 * Indica si se ha establecido datos contacto.
	 */
	private boolean contactoNulo = true;

	/**
	 * Nif persona.
	 */
	private String nif;

	/**
	 * Nombre persona.
	 */
	private String nombre;

	/**
	 * Apellido 1 persona.
	 */
	private String apellido1;

	/**
	 * Apellido 2 persona.
	 */
	private String apellido2;

	/** Pais del interesado (codificación SICRES) */
	private Long pais;

	/** provincia del interesado (codificación SICRES) */
	private Long provincia;

	/** municipio del interesado (codificación SICRES) */
	private Long municipio;

	/** direccion postal del interesado */
	private String direccion;

	/** codigo postal del interesado */
	private String codigoPostal;

	/** email del interesado */
	private String email;

	/** telefono del interesado */
	private String telefono;

	@Override
	public String getPluginId() {
		return ID;
	}

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif persona
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método de acceso a nombre.
	 *
	 * @return nombre persona
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método de acceso a apellido1.
	 *
	 * @return apellido1 persona
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Método de acceso a apellido2.
	 *
	 * @return apellido2 persona
	 */
	public String getApellido2() {
		return apellido2;
	}

	@Override
	public void setNulo(final boolean pNulo) {
		this.nulo = pNulo;
	}

	/**
	 * Método de acceso a nulo.
	 *
	 * @return nulo
	 */
	public boolean isNulo() {
		return nulo;
	}

	/**
	 * Método de acceso a pais.
	 *
	 * @return pais
	 */
	public Long getPais() {
		return pais;
	}

	/**
	 * Método para establecer pais.
	 *
	 * @param pais
	 *                 pais a establecer
	 */
	public void setPais(final Long pais) {
		this.pais = pais;
	}

	/**
	 * Método de acceso a provincia.
	 *
	 * @return provincia
	 */
	public Long getProvincia() {
		return provincia;
	}

	/**
	 * Método para establecer provincia.
	 *
	 * @param provincia
	 *                      provincia a establecer
	 */
	public void setProvincia(final Long provincia) {
		this.provincia = provincia;
	}

	/**
	 * Método de acceso a municipio.
	 *
	 * @return municipio
	 */
	public Long getMunicipio() {
		return municipio;
	}

	/**
	 * Método para establecer municipio.
	 *
	 * @param municipio
	 *                      municipio a establecer
	 */
	public void setMunicipio(final Long municipio) {
		this.municipio = municipio;
	}

	/**
	 * Método de acceso a direccion.
	 *
	 * @return direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Método para establecer direccion.
	 *
	 * @param direccion
	 *                      direccion a establecer
	 */
	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Método de acceso a codigoPostal.
	 *
	 * @return codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Método para establecer codigoPostal.
	 *
	 * @param codigoPostal
	 *                         codigoPostal a establecer
	 */
	public void setCodigoPostal(final String codigoPostal) {
		this.codigoPostal = codigoPostal;
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
	 * Método de acceso a telefono.
	 *
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Método para establecer telefono.
	 *
	 * @param telefono
	 *                     telefono a establecer
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param nif
	 *                nif a establecer
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * Método para establecer nombre.
	 *
	 * @param nombre
	 *                   nombre a establecer
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método para establecer apellido1.
	 *
	 * @param apellido1
	 *                      apellido1 a establecer
	 */
	public void setApellido1(final String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * Método para establecer apellido2.
	 *
	 * @param apellido2
	 *                      apellido2 a establecer
	 */
	public void setApellido2(final String apellido2) {
		this.apellido2 = apellido2;
	}

	@Override
	public void setDatosPersona(final String pNif, final String pNombre, final String pApellido1,
			final String pApellido2) throws ScriptException {

		if (StringUtils.isBlank(pNif)) {
			throw new ScriptException("Es obligatori establir el nif");
		}

		if (StringUtils.isBlank(pNombre)) {
			throw new ScriptException("Es obligatori establir el nom");
		}

		nif = NifUtils.normalizarNif(pNif);

		ScriptUtils.validarDatosPersona(nif, pNombre, pApellido1, pApellido2);

		nombre = pNombre;

		if (!StringUtils.isBlank(pApellido1)) {
			apellido1 = pApellido1;
		}
		if (!StringUtils.isBlank(pApellido2)) {
			apellido2 = pApellido2;
		}

		nulo = false;
	}

	@Override
	public void setDatosContacto(final String pais, final String provincia, final String municipio,
			final String direccion, final String codigoPostal, final String email, final String telefono)
			throws ScriptException {
		if (mayorCero(pais)) {
			this.setPais(Long.parseLong(pais));
		}
		if (mayorCero(provincia)) {
			this.setProvincia(Long.parseLong(provincia));
		}
		if (mayorCero(municipio)) {
			this.setMunicipio(Long.parseLong(municipio));
		}
		if (StringUtils.isNotBlank(direccion)) {
			this.setDireccion(direccion);
		}
		if (StringUtils.isNotBlank(codigoPostal)) {
			this.setCodigoPostal(codigoPostal);
		}
		if (StringUtils.isNotBlank(email)) {
			this.setEmail(email);
		}
		if (StringUtils.isNotBlank(telefono)) {
			this.setTelefono(telefono);
		}
		contactoNulo = false;
	}

	/**
	 * Verifica si numero es mayor que 0.
	 *
	 * @param numero
	 * @return
	 */
	private boolean mayorCero(final String numero) throws ScriptException {
		boolean res = false;
		if (StringUtils.isNotBlank(numero)) {
			try {
				res = (Long.parseLong(numero) > 0);
			} catch (final NumberFormatException nfe) {
				throw new ScriptException("No és nombre: " + numero);
			}
		}
		return res;
	}

	/**
	 * Método de acceso a contactoNulo.
	 *
	 * @return contactoNulo
	 */
	public boolean isContactoNulo() {
		return contactoNulo;
	}

	/**
	 * Método para establecer contactoNulo.
	 *
	 * @param contactoNulo
	 *                         contactoNulo a establecer
	 */
	public void setContactoNulo(final boolean contactoNulo) {
		this.contactoNulo = contactoNulo;
	}

}
