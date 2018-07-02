package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypePresentacion;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;

/**
 *
 * Documento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Documento extends ModelApi {

	/** Id. */
	private Long codigo;

	/** Código. */
	private String identificador;

	/** Descripción. */
	private Literal descripcion;

	/** Tipo. */
	private TypeFormulario tipo;

	/** Obligatorio: - Si (S) - Opcional (N) - Dependiente (D). */
	private TypeFormularioObligatoriedad obligatoriedad;

	/** Orden. **/
	private int orden;

	/** En caso de ser dependiente establece obligatoriedad */
	private Script scriptObligatoriedad;

	/** Texto de ayuda online para realizar el anexado */
	private Literal ayudaTexto;

	/** Fichero binario con la plantilla del documento */
	private Fichero ayudaFichero;

	/** Plantilla es una URL **/
	private String ayudaURL;

	/** Tipo presentación: E (electrónica) / P (Presencial) */
	private TypePresentacion tipoPresentacion;

	/** Número instancia (por defecto 1, para multiinstancia > 1) */
	private int numeroInstancia;

	/** Lista extensiones permitidas separadas por coma */
	private String extensiones;

	/** Tamaño máximo (segun unidad tamaño) */
	private int tamanyoMaximo;

	/** Unidad de tamaño del anexo (KB o MB) */
	private TypeTamanyo tipoTamanyo;

	/** Indica si hay que convertir a PDF el anexo */
	private boolean debeConvertirPDF;

	/** Indica si se debe firmar digitalmente */
	private boolean debeFirmarDigitalmente;

	/**
	 * Permite indicar quién debe firmar el anexo (permite indicar varios
	 * firmantes). Si se habilita el pase a bandeja de firma se podrá especificar si
	 * el anexo debe ser anexado por uno de los firmantes.
	 */
	private Script scriptFirmarDigitalmente;

	/** Indica si se debe anexar firmado */
	private boolean debeAnexarFirmado;

	/**
	 * Permite establecer una validación sobre el documento anexado. En este script
	 * estará disponible un plugin que permita acceder a datos de formularios PDF.
	 */
	private Script scriptValidacion;

	/** Compulsar (presencial) */
	private boolean debeCompulsar;

	/** Fotocopia (presencial) */
	private boolean debeFotocopiar;

	/**
	 * Crea una nueva instancia de Documento.
	 */
	public Documento() {
		super();
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public String getIdString() {
		if (codigo == null) {
			return null;
		} else {
			return codigo.toString();
		}
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setIdString(final String idString) {
		if (codigo == null) {
			codigo = null;
		} else {
			codigo = Long.valueOf(idString);
		}
	}

	/**
	 * Obtiene el valor de identificador.
	 *
	 * @return el valor de identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Establece el valor de identificador.
	 *
	 * @param identificador
	 *            el nuevo valor de identificador
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de tipo.
	 *
	 * @return el valor de tipo
	 */
	public TypeFormulario getTipo() {
		return tipo;
	}

	/**
	 * Establece el valor de tipo.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo
	 */
	public void setTipo(final TypeFormulario tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el valor de obligatoriedad.
	 *
	 * @return el valor de obligatoriedad
	 */
	public TypeFormularioObligatoriedad getObligatoriedad() {
		return obligatoriedad;
	}

	/**
	 * Establece el valor de obligatoriedad.
	 *
	 * @param obligatoriedad
	 *            el nuevo valor de obligatoriedad
	 */
	public void setObligatoriedad(final TypeFormularioObligatoriedad obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}

	/**
	 * @return the ayudaTexto
	 */
	public Literal getAyudaTexto() {
		return ayudaTexto;
	}

	/**
	 * @param ayudaTexto
	 *            the ayudaTexto to set
	 */
	public void setAyudaTexto(final Literal ayudaTexto) {
		this.ayudaTexto = ayudaTexto;
	}

	/**
	 * @return the ayudaFichero
	 */
	public Fichero getAyudaFichero() {
		return ayudaFichero;
	}

	/**
	 * @param ayudaFichero
	 *            the ayudaFichero to set
	 */
	public void setAyudaFichero(final Fichero ayudaFichero) {
		this.ayudaFichero = ayudaFichero;
	}

	/**
	 * @return the ayudaURL
	 */
	public String getAyudaURL() {
		return ayudaURL;
	}

	/**
	 * @param ayudaURL
	 *            the ayudaURL to set
	 */
	public void setAyudaURL(final String ayudaURL) {
		this.ayudaURL = ayudaURL;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

	/**
	 * @return the scriptObligatoriedad
	 */
	public Script getScriptObligatoriedad() {
		return scriptObligatoriedad;
	}

	/**
	 * @param scriptObligatoriedad
	 *            the scriptObligatoriedad to set
	 */
	public void setScriptObligatoriedad(final Script scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	/**
	 * @return the tipoPresentacion
	 */
	public TypePresentacion getTipoPresentacion() {
		return tipoPresentacion;
	}

	/**
	 * @param tipoPresentacion
	 *            the tipoPresentacion to set
	 */
	public void setTipoPresentacion(final TypePresentacion tipoPresentacion) {
		this.tipoPresentacion = tipoPresentacion;
	}

	/**
	 * @return the numeroInstancia
	 */
	public int getNumeroInstancia() {
		return numeroInstancia;
	}

	/**
	 * @param numeroInstancia
	 *            the numeroInstancia to set
	 */
	public void setNumeroInstancia(final int numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}

	/**
	 * @return the extensiones
	 */
	public String getExtensiones() {
		return extensiones;
	}

	/**
	 * @param extensiones
	 *            the extensiones to set
	 */
	public void setExtensiones(final String extensiones) {
		this.extensiones = extensiones;
	}

	/**
	 * @return the tamanyoMaximo
	 */
	public int getTamanyoMaximo() {
		return tamanyoMaximo;
	}

	/**
	 * @param tamanyoMaximo
	 *            the tamanyoMaximo to set
	 */
	public void setTamanyoMaximo(final int tamanyoMaximo) {
		this.tamanyoMaximo = tamanyoMaximo;
	}

	/**
	 * @return the tipoTamanyo
	 */
	public TypeTamanyo getTipoTamanyo() {
		return tipoTamanyo;
	}

	/**
	 * @param tipoTamanyo
	 *            the tipoTamanyo to set
	 */
	public void setTipoTamanyo(final TypeTamanyo tipoTamanyo) {
		this.tipoTamanyo = tipoTamanyo;
	}

	/**
	 * @return the debeConvertirPDF
	 */
	public boolean isDebeConvertirPDF() {
		return debeConvertirPDF;
	}

	/**
	 * @param debeConvertirPDF
	 *            the debeConvertirPDF to set
	 */
	public void setDebeConvertirPDF(final boolean debeConvertirPDF) {
		this.debeConvertirPDF = debeConvertirPDF;
	}

	/**
	 * @return the debeFirmarDigitalmente
	 */
	public boolean isDebeFirmarDigitalmente() {
		return debeFirmarDigitalmente;
	}

	/**
	 * @param debeFirmarDigitalmente
	 *            the debeFirmarDigitalmente to set
	 */
	public void setDebeFirmarDigitalmente(final boolean debeFirmarDigitalmente) {
		this.debeFirmarDigitalmente = debeFirmarDigitalmente;
	}

	/**
	 * @return the scriptFirmarDigitalmente
	 */
	public Script getScriptFirmarDigitalmente() {
		return scriptFirmarDigitalmente;
	}

	/**
	 * @param scriptFirmarDigitalmente
	 *            the scriptFirmarDigitalmente to set
	 */
	public void setScriptFirmarDigitalmente(final Script scriptFirmarDigitalmente) {
		this.scriptFirmarDigitalmente = scriptFirmarDigitalmente;
	}

	/**
	 * @return the debeAnexarFirmado
	 */
	public boolean isDebeAnexarFirmado() {
		return debeAnexarFirmado;
	}

	/**
	 * @param debeAnexarFirmado
	 *            the debeAnexarFirmado to set
	 */
	public void setDebeAnexarFirmado(final boolean debeAnexarFirmado) {
		this.debeAnexarFirmado = debeAnexarFirmado;
	}

	/**
	 * @return the scriptValidacion
	 */
	public Script getScriptValidacion() {
		return scriptValidacion;
	}

	/**
	 * @param scriptValidacion
	 *            the scriptValidacion to set
	 */
	public void setScriptValidacion(final Script scriptValidacion) {
		this.scriptValidacion = scriptValidacion;
	}

	/**
	 * @return the debeCompulsar
	 */
	public boolean isDebeCompulsar() {
		return debeCompulsar;
	}

	/**
	 * @param debeCompulsar
	 *            the debeCompulsar to set
	 */
	public void setDebeCompulsar(final boolean debeCompulsar) {
		this.debeCompulsar = debeCompulsar;
	}

	/**
	 * @return the debeFotocopiar
	 */
	public boolean isDebeFotocopiar() {
		return debeFotocopiar;
	}

	/**
	 * @param debeFotocopiar
	 *            the debeFotocopiar to set
	 */
	public void setDebeFotocopiar(final boolean debeFotocopiar) {
		this.debeFotocopiar = debeFotocopiar;
	}

}
