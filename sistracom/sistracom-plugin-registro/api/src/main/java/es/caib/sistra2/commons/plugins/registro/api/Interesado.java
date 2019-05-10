package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;

import es.caib.sistra2.commons.plugins.registro.api.types.TypeCanal;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumentoIdentificacion;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeInteresado;

/**
 * Datos del interasado
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Interesado implements Serializable {

	/** tipo de actuacion del interesado (representante/representado) */
	private TypeInteresado actuaComo;

	/** tipo documento de identificacion */
	private TypeDocumentoIdentificacion tipoDocumento;

	/** tipo de documento de identificación del interesado */
	private String docIdentificacion;

	/** razon social del interesado */
	private String razonSocial;

	/** nombre del interesado */
	private String nombre;

	/** primer apellido del interesado */
	private String apellido1;

	/** segundo apellido del interesado */
	private String apellido2;

	/** pais del interesado */
	private Long pais;

	/** provincia del interesado */
	private Long provincia;

	/** municipio del interesado */
	private Long municipio;

	/** direccion postal del interesado */
	private String direccion;

	/** codigo postal del interesado */
	private String codigoPostal;

	/** email del interesado */
	private String email;

	/** telefono del interesado */
	private String telefono;

	/** direccion electronica del interesado (DEH) */
	private String direccionElectronica;

	/** canal preferente de notificacion */
	private TypeCanal canal;

	/** Observaciones del interesado */
	private String observaciones;

	public TypeInteresado getActuaComo() {
		return actuaComo;
	}

	public void setActuaComo(final TypeInteresado actuaComo) {
		this.actuaComo = actuaComo;
	}

	public TypeDocumentoIdentificacion getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(final TypeDocumentoIdentificacion tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocIdentificacion() {
		return docIdentificacion;
	}

	public void setDocIdentificacion(final String docIdentificacion) {
		this.docIdentificacion = docIdentificacion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(final String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(final String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(final String apellido2) {
		this.apellido2 = apellido2;
	}

	public Long getPais() {
		return pais;
	}

	public void setPais(final Long pais) {
		this.pais = pais;
	}

	public Long getProvincia() {
		return provincia;
	}

	public void setProvincia(final Long provincia) {
		this.provincia = provincia;
	}

	public Long getMunicipio() {
		return municipio;
	}

	public void setMunicipio(final Long municipio) {
		this.municipio = municipio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(final String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	public String getDireccionElectronica() {
		return direccionElectronica;
	}

	public void setDireccionElectronica(final String direccionElectronica) {
		this.direccionElectronica = direccionElectronica;
	}

	public TypeCanal getCanal() {
		return canal;
	}

	public void setCanal(final TypeCanal canal) {
		this.canal = canal;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(final String observaciones) {
		this.observaciones = observaciones;
	}

}
