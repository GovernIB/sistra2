package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Datos del asiento registral
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class AsientoRegistral implements Serializable {

    /** Datos origen del asiento */
    private DatosOrigen datosOrigen;

    /** Datos destino del asiento */
    private DatosDestino datosDestino;

    private List<Interesado> interesados = new ArrayList ();

	private List<DocumentoAsiento> documentosRegistro = new ArrayList ();

	private DatosAsunto datosAsunto;

	public DatosOrigen getDatosOrigen() {
		return datosOrigen;
	}

	public void setDatosOrigen(DatosOrigen datosOrigen) {
		this.datosOrigen = datosOrigen;
	}

	public DatosDestino getDatosDestino() {
		return datosDestino;
	}

	public void setDatosDestino(DatosDestino datosDestino) {
		this.datosDestino = datosDestino;
	}


	public List<Interesado> getInteresados() {
		return interesados;
	}

	public void setInteresados(List<Interesado> interesados) {
		this.interesados = interesados;
	}

	public List<DocumentoAsiento> getDocumentosRegistro() {
		return documentosRegistro;
	}

	public void setDocumentosRegistro(List<DocumentoAsiento> documentosRegistro) {
		this.documentosRegistro = documentosRegistro;
	}

	public DatosAsunto getDatosAsunto() {
		return datosAsunto;
	}

	public void setDatosAsunto(DatosAsunto datosAsunto) {
		this.datosAsunto = datosAsunto;
	}

}
