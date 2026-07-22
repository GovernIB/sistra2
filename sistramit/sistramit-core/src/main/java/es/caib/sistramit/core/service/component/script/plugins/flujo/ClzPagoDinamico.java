package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.api.model.flujo.Persona;

import javax.script.ScriptException;
import java.io.Serializable;

/**
 * Información de un pago definido de forma dinámica.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzPagoDinamico implements Serializable {

	/** Identificador pago dinamico. */
	private String identificador;

	/** Descripción pago dinamico. */
	private String descripcion;

	/** Obligatorio. */
	private boolean obligatorio;

	/** Pasarela a usar. */
	private String pasarela;

	/** Organismo (depende pasarela). */
	private String organismo;

	/** Contribuyente. (si vacio será el que rellena el trámite) */
	private Persona contribuyente;

	/** Modelo de pago.	 */
	private String modelo;

	/** Concepto del pago. */
	private String concepto;

	/** Tasa. */
	private String tasa;

	/** Importe (en cents).	 */
	private int importe;

	/** Multiplicador (cuando el pago implique multiplicar n veces el importe). */
	private int multiplicador = 1;

	/** Simular pago. */
	private boolean simularPago;

	/**
	 * Indica si se filtran los métodos de pago (lista separada por ; ). Si no se
	 * establece, se mostarán los activos por defecto.
	 */
	private String metodosPago;


	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getPasarela() {
		return pasarela;
	}

	public void setPasarela(String pasarela) {
		this.pasarela = pasarela;
	}

	public void setDetallePago(final String modelo, final String concepto, final String tasa, final int importe) {
		this.modelo = modelo;
		this.concepto = concepto;
		this.tasa = tasa;
		this.importe = importe;
	}

	public void setDetallePago(final String modelo, final String concepto, final String tasa, final int unidades, final int importeUnidad) {
		this.modelo = modelo;
		this.concepto = concepto;
		this.tasa = tasa;
		this.importe = importeUnidad * unidades;
		this.multiplicador = unidades;
	}

	public void setContribuyente(String nif, String nombre) {
		this.contribuyente = new Persona(nif, nombre);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

	public String getOrganismo() {
		return organismo;
	}

	public Persona getContribuyente() {
		return contribuyente;
	}

	public String getModelo() {
		return modelo;
	}

	public String getConcepto() {
		return concepto;
	}

	public String getTasa() {
		return tasa;
	}

	public int getImporte() {
		return importe;
	}

	public String getMetodosPago() {
		return metodosPago;
	}

	public void setMetodosPago(String metodosPago) {
		this.metodosPago = metodosPago;
	}

	public boolean isSimularPago() {
		return simularPago;
	}

	public void setSimularPago(boolean simularPago) {
		this.simularPago = simularPago;
	}

	public int getMultiplicador() {
		return multiplicador;
	}
}
