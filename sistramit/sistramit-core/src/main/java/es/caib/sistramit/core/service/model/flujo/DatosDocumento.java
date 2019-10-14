package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;

/**
 * Datos de un documento de un paso accesibles desde los demás pasos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class DatosDocumento implements Serializable {

	/**
	 * Id paso.
	 */
	private String idPaso;

	/**
	 * Id documento.
	 */
	private String id;
	/**
	 * Tipo documento.
	 */
	private TypeDocumento tipo;
	/**
	 * Presentacion.
	 */
	private TypePresentacion presentacion;
	/**
	 * Título.
	 */
	private String titulo;
	/**
	 * Referencia fichero.
	 */
	private ReferenciaFichero fichero;
	/**
	 * Firmar.
	 */
	private TypeSiNo firmar = TypeSiNo.NO;
	/**
	 * Indica lista de firmantes del documento.
	 */
	private List<Persona> firmantes = new ArrayList<>();

	/**
	 * Tipo documento ENI.
	 */
	private String tipoENI = "TD99";

	/**
	 * Método de acceso a id DatosDocumento.
	 *
	 * @return id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Método para establecer id DatosDocumento.
	 *
	 * @param pId
	 *                id a establecer
	 */
	public final void setId(final String pId) {
		id = pId;
	}

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public final String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param pTitulo
	 *                    titulo a establecer
	 */
	public final void setTitulo(final String pTitulo) {
		titulo = pTitulo;
	}

	/**
	 * Método de acceso a datosFichero.
	 *
	 * @return datosFichero
	 */
	public final ReferenciaFichero getFichero() {
		return fichero;
	}

	/**
	 * Método para establecer datosFichero.
	 *
	 * @param pDatosFichero
	 *                          datosFichero a establecer
	 */
	public final void setFichero(final ReferenciaFichero pDatosFichero) {
		fichero = pDatosFichero;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public final TypeDocumento getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param pTipo
	 *                  tipo a establecer
	 */
	protected final void setTipo(final TypeDocumento pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método de acceso a presentacion.
	 *
	 * @return presentacion
	 */
	public TypePresentacion getPresentacion() {
		return presentacion;
	}

	/**
	 * Método para establecer presentacion.
	 *
	 * @param presentacion
	 *                         presentacion a establecer
	 */
	public void setPresentacion(final TypePresentacion presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * Método de acceso a idPaso.
	 *
	 * @return idPaso
	 */
	public String getIdPaso() {
		return idPaso;
	}

	/**
	 * Método para establecer idPaso.
	 *
	 * @param idPaso
	 *                   idPaso a establecer
	 */
	public void setIdPaso(final String idPaso) {
		this.idPaso = idPaso;
	}

	/**
	 * Método para establecer firmar.
	 *
	 * @param firmar
	 *                   firmar a establecer
	 */
	public void setFirmar(final TypeSiNo firmar) {
		this.firmar = firmar;
	}

	/**
	 * Método de acceso a firmantes.
	 *
	 * @return firmantes
	 */
	public List<Persona> getFirmantes() {
		return firmantes;
	}

	/**
	 * Método para establecer firmantes.
	 *
	 * @param firmantes
	 *                      firmantes a establecer
	 */
	public void setFirmantes(final List<Persona> firmantes) {
		this.firmantes = firmantes;
	}

	/**
	 * Método de acceso a firmar.
	 *
	 * @return firmar
	 */
	public TypeSiNo getFirmar() {
		return firmar;
	}

	/**
	 * Método de acceso a tipoENI.
	 * 
	 * @return tipoENI
	 */
	public String getTipoENI() {
		return tipoENI;
	}

	/**
	 * Método para establecer tipoENI.
	 * 
	 * @param tipoENI
	 *                    tipoENI a establecer
	 */
	public void setTipoENI(final String tipoENI) {
		this.tipoENI = tipoENI;
	}

}
