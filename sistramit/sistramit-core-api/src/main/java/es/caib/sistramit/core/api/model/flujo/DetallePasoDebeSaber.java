package es.caib.sistramit.core.api.model.flujo;

import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle del paso "Debe saber".
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasoDebeSaber extends DetallePaso {

	/**
	 * Instrucciones inicio (HTML).
	 */
	private String instrucciones;

	/** Info LOPD (HTML). */
	// TODO LOPD PENDIENTE DE QUITAR
	private String infoLOPD;

	/** Info LOPD resumen (HTML). */
	// TODO LOPD PENDIENTE DE QUITAR
	private String infoResumenLOPD;

	/** Requisitos técnicos (HTML). */
	private String requisitosTecnicos;

	/** Fecha fin plazo. */
	private String finPlazo;

	/**
	 * Lista de pasos con su descripcion.
	 */
	private List<DescripcionPaso> pasos;

	/**
	 * Constructor.
	 */
	public DetallePasoDebeSaber() {
		super();
		this.setTipo(TypePaso.DEBESABER);
	}

	/**
	 * Método de acceso a descripcionPasos.
	 *
	 * @return descripcionPasos
	 */
	public List<DescripcionPaso> getPasos() {
		return pasos;
	}

	/**
	 * Método para establecer descripcionPasos.
	 *
	 * @param pPasos
	 *                   descripcionPasos a establecer
	 */
	public void setPasos(final List<DescripcionPaso> pPasos) {
		pasos = pPasos;
	}

	@Override
	public String print() {
		final int capacity = ConstantesNumero.N2 * ConstantesNumero.N1024;
		final StringBuffer strb = new StringBuffer(capacity);

		strb.append("\nDETALLE PASO DEBE SABER\n");
		strb.append("=====================\n");

		strb.append("Id paso:" + getId() + "\n");
		strb.append("Completado:" + getCompletado() + "\n");
		strb.append("Solo lectura:" + getSoloLectura() + "\n");
		strb.append("Tipo:" + getTipo() + "\n");
		strb.append("Lista pasos:\n");
		for (final DescripcionPaso desc : getPasos()) {
			strb.append(" Id:" + desc.getId() + "\n");
			strb.append(" Tipo:" + desc.getTipo() + "\n");
		}

		return strb.toString();
	}

	/**
	 * Método de acceso a instrucciones.
	 *
	 * @return instrucciones
	 */
	public String getInstrucciones() {
		return instrucciones;
	}

	/**
	 * Método para establecer instrucciones.
	 *
	 * @param instrucciones
	 *                          instrucciones a establecer
	 */
	public void setInstrucciones(final String instrucciones) {
		this.instrucciones = instrucciones;
	}

	/**
	 * Método de acceso a infoLOPD.
	 *
	 * @return infoLOPD
	 */
	public String getInfoLOPD() {
		return infoLOPD;
	}

	/**
	 * Método para establecer infoLOPD.
	 *
	 * @param infoLOPD
	 *                     infoLOPD a establecer
	 */
	public void setInfoLOPD(final String infoLOPD) {
		this.infoLOPD = infoLOPD;
	}

	/**
	 * Método de acceso a infoResumenLOPD.
	 *
	 * @return infoResumenLOPD
	 */
	public String getInfoResumenLOPD() {
		return infoResumenLOPD;
	}

	/**
	 * Método para establecer infoResumenLOPD.
	 *
	 * @param infoResumenLOPD
	 *                            infoResumenLOPD a establecer
	 */
	public void setInfoResumenLOPD(final String infoResumenLOPD) {
		this.infoResumenLOPD = infoResumenLOPD;
	}

	/**
	 * Método de acceso a requisitosTecnicos.
	 *
	 * @return requisitosTecnicos
	 */
	public String getRequisitosTecnicos() {
		return requisitosTecnicos;
	}

	/**
	 * Método para establecer requisitosTecnicos.
	 *
	 * @param requisitosTecnicos
	 *                               requisitosTecnicos a establecer
	 */
	public void setRequisitosTecnicos(final String requisitosTecnicos) {
		this.requisitosTecnicos = requisitosTecnicos;
	}

	/**
	 * Método de acceso a finPlazo.
	 *
	 * @return finPlazo
	 */
	public String getFinPlazo() {
		return finPlazo;
	}

	/**
	 * Método para establecer finPlazo.
	 *
	 * @param finPlazo
	 *                     finPlazo a establecer
	 */
	public void setFinPlazo(final String finPlazo) {
		this.finPlazo = finPlazo;
	}

}
