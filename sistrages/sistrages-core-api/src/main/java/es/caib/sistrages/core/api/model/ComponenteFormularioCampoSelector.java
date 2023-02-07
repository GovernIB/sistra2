package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeCampoIndexado;
import es.caib.sistrages.core.api.model.types.TypeListaValores;

/**
 * La clase ComponenteFormularioCampoSelector.
 */

public final class ComponenteFormularioCampoSelector extends ComponenteFormularioCampo {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** tipo campo indexado. */
	private TypeCampoIndexado tipoCampoIndexado;

	/** tipo lista valores. */
	private TypeListaValores tipoListaValores;

	/** script valores posibles. */
	private Script scriptValoresPosibles;

	/** codDominio. */
	private Long codDominio;
	/** Los siguientes datos de dominio es solo para cuando se importa un seccion reutilizable **/
	String dominioAmbito;
	String dominioIdentificador;
	String dominioArea;
	String dominioEntidad;

	/** campo codDominio codigo. */
	private String campoDominioCodigo;

	/** campo codDominio descripcion. */
	private String campoDominioDescripcion;

	/** indice alfabetico. */
	private boolean indiceAlfabetico;

	/** lista parametros codDominio. */
	private List<ParametroDominio> listaParametrosDominio;

	/** lista valor lista fija. */
	private List<ValorListaFija> listaValorListaFija;

	/** altura. */
	private int altura;

	/** Orientacion. **/
	private String orientacion;

	/**
	 * Crea una nueva instancia de ComponenteFormularioCampoSelector.
	 */
	public ComponenteFormularioCampoSelector() {
		super();
	}

	/**
	 * Obtiene el valor de tipoCampoIndexado.
	 *
	 * @return el valor de tipoCampoIndexado
	 */
	public TypeCampoIndexado getTipoCampoIndexado() {
		return tipoCampoIndexado;
	}

	/**
	 * Establece el valor de tipoCampoIndexado.
	 *
	 * @param tipoCampoIndexado el nuevo valor de tipoCampoIndexado
	 */
	public void setTipoCampoIndexado(final TypeCampoIndexado tipoCampoIndexado) {
		this.tipoCampoIndexado = tipoCampoIndexado;
	}

	/**
	 * Obtiene el valor de tipoListaValores.
	 *
	 * @return el valor de tipoListaValores
	 */
	public TypeListaValores getTipoListaValores() {
		return tipoListaValores;
	}

	/**
	 * Establece el valor de tipoListaValores.
	 *
	 * @param tipoListaValores el nuevo valor de tipoListaValores
	 */
	public void setTipoListaValores(final TypeListaValores tipoListaValores) {
		this.tipoListaValores = tipoListaValores;
	}

	/**
	 * Obtiene el valor de scriptValoresPosibles.
	 *
	 * @return el valor de scriptValoresPosibles
	 */
	public Script getScriptValoresPosibles() {
		return scriptValoresPosibles;
	}

	/**
	 * Establece el valor de scriptValoresPosibles.
	 *
	 * @param scriptValoresPosibles el nuevo valor de scriptValoresPosibles
	 */
	public void setScriptValoresPosibles(final Script scriptValoresPosibles) {
		this.scriptValoresPosibles = scriptValoresPosibles;
	}

	/**
	 * Obtiene el valor de codDominio.
	 *
	 * @return el valor de codDominio
	 */
	public Long getCodDominio() {
		return codDominio;
	}

	/**
	 * Establece el valor de codDominio.
	 *
	 * @param codDominio el nuevo valor de codDominio
	 */
	public void setCodDominio(final Long pCodDominio) {
		this.codDominio = pCodDominio;
	}

	/**
	 * Obtiene el valor de campoDominioCodigo.
	 *
	 * @return el valor de campoDominioCodigo
	 */
	public String getCampoDominioCodigo() {
		return campoDominioCodigo;
	}

	/**
	 * Establece el valor de campoDominioCodigo.
	 *
	 * @param campoDominioCodigo el nuevo valor de campoDominioCodigo
	 */
	public void setCampoDominioCodigo(final String campoDominioCodigo) {
		this.campoDominioCodigo = campoDominioCodigo;//.toUpperCase();
	}

	/**
	 * Obtiene el valor de campoDominioDescripcion.
	 *
	 * @return el valor de campoDominioDescripcion
	 */
	public String getCampoDominioDescripcion() {
		return campoDominioDescripcion;
	}

	/**
	 * Establece el valor de campoDominioDescripcion.
	 *
	 * @param campoDominioDescripcion el nuevo valor de campoDominioDescripcion
	 */
	public void setCampoDominioDescripcion(final String campoDominioDescripcion) {
		this.campoDominioDescripcion = campoDominioDescripcion;//.toUpperCase();
	}

	/**
	 * Verifica si es indice alfabetico.
	 *
	 * @return true, si es indice alfabetico
	 */
	public boolean isIndiceAlfabetico() {
		return indiceAlfabetico;
	}

	/**
	 * Establece el valor de indiceAlfabetico.
	 *
	 * @param indiceAlfabetico el nuevo valor de indiceAlfabetico
	 */
	public void setIndiceAlfabetico(final boolean indiceAlfabetico) {
		this.indiceAlfabetico = indiceAlfabetico;
	}

	/**
	 * Obtiene el valor de listaParametrosDominio.
	 *
	 * @return el valor de listaParametrosDominio
	 */
	public List<ParametroDominio> getListaParametrosDominio() {
		return listaParametrosDominio;
	}

	/**
	 * Establece el valor de listaParametrosDominio.
	 *
	 * @param parametrosDominio el nuevo valor de listaParametrosDominio
	 */
	public void setListaParametrosDominio(final List<ParametroDominio> parametrosDominio) {
		this.listaParametrosDominio = parametrosDominio;
	}

	/**
	 * Obtiene el valor de listaValorListaFija.
	 *
	 * @return el valor de listaValorListaFija
	 */
	public List<ValorListaFija> getListaValorListaFija() {
		return listaValorListaFija;
	}

	/**
	 * Establece el valor de listaValorListaFija.
	 *
	 * @param listaValorListaFija el nuevo valor de listaValorListaFija
	 */
	public void setListaValorListaFija(final List<ValorListaFija> listaValorListaFija) {
		this.listaValorListaFija = listaValorListaFija;
	}

	/**
	 * Obtiene el valor de altura.
	 *
	 * @return el valor de altura
	 */
	public int getAltura() {
		return altura;
	}

	/**
	 * Establece el valor de altura.
	 *
	 * @param altura el nuevo valor de altura
	 */
	public void setAltura(final int altura) {
		this.altura = altura;
	}

	/**
	 * @return the orientacion
	 */
	public String getOrientacion() {
		return orientacion;
	}

	/**
	 * @param orientacion the orientacion to set
	 */
	public void setOrientacion(final String orientacion) {
		this.orientacion = orientacion;
	}

	/**
	 * @return the dominioAmbito
	 */
	public String getDominioAmbito() {
		return dominioAmbito;
	}

	/**
	 * @param dominioAmbito the dominioAmbito to set
	 */
	public void setDominioAmbito(String dominioAmbito) {
		this.dominioAmbito = dominioAmbito;
	}

	/**
	 * @return the dominioIdentificador
	 */
	public String getDominioIdentificador() {
		return dominioIdentificador;
	}

	/**
	 * @param dominioIdentificador the dominioIdentificador to set
	 */
	public void setDominioIdentificador(String dominioIdentificador) {
		this.dominioIdentificador = dominioIdentificador;
	}

	/**
	 * @return the dominioArea
	 */
	public String getDominioArea() {
		return dominioArea;
	}

	/**
	 * @param dominioArea the dominioArea to set
	 */
	public void setDominioArea(String dominioArea) {
		this.dominioArea = dominioArea;
	}

	/**
	 * @return the dominioEntidad
	 */
	public String getDominioEntidad() {
		return dominioEntidad;
	}

	/**
	 * @param dominioEntidad the dominioEntidad to set
	 */
	public void setDominioEntidad(String dominioEntidad) {
		this.dominioEntidad = dominioEntidad;
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "ComponenteFormularioSelector. ");
           if (this.getIdComponente() != null) { texto.append(tabulacion +"\t IdComponente:" + this.getIdComponente() + "\n"); }
           if (this.getTipo() != null) { texto.append(tabulacion +"\t Tipus:" + this.getTipo() + "\n");}
           texto.append(tabulacion +"\t Ordre:" + this.getOrden() + "\n");
           texto.append(tabulacion +"\t NumColumnas:" + this.getNumColumnas() + "\n");
           texto.append(tabulacion +"\t NoMostrarText:" + this.isNoMostrarTexto() + "\n");
           if (this.getAlineacionTexto() != null) { texto.append(tabulacion +"\t AlineacioText:" + this.getAlineacionTexto() + "\n");}
           texto.append(tabulacion +"\t TipoSeccionReutilizable:" + this.isTipoSeccionReutilizable() + "\n");
           if (this.getIdFormSeccion() != null) { texto.append(tabulacion +"\t IdFormSeccion:" + this.getIdFormSeccion() + "\n");}

           if (this.getTexto() != null) {
        	   texto.append(tabulacion +"\t Text: \n");
        	   texto.append(this.getTexto().toString(tabulacion, idioma) + "\n");
           }
           if (this.getAyuda() != null) {
        	   texto.append(tabulacion +"\t Ajuda: \n");
        	   texto.append(this.getAyuda().toString(tabulacion, idioma) + "\n");
           }
           if (this.tipoCampoIndexado != null) { texto.append(tabulacion +"\t TipusCampIndexat:" + tipoCampoIndexado + "\n");}
           if (this.tipoListaValores != null) { texto.append(tabulacion +"\t TipusLlistaValors:" + tipoListaValores + "\n");}
           if (this.codDominio != null) { texto.append(tabulacion +"\t CodDominio:" + codDominio + "\n");}
           if (scriptValoresPosibles != null) {
        	   texto.append(tabulacion +"\t ScriptValorsPosibles: \n");
        	   texto.append(scriptValoresPosibles.toString(tabulacion, idioma)+ "\n");
           }


           if (this.dominioAmbito != null) { texto.append(tabulacion +"\t DominiAmbito:" + dominioAmbito + "\n");}
           if (this.dominioIdentificador != null) { texto.append(tabulacion +"\t DominiIdentificador:" + dominioIdentificador + "\n");}
           if (this.dominioArea != null) { texto.append(tabulacion +"\t DominiArea:" + dominioArea + "\n");}
           if (this.dominioEntidad != null) { texto.append(tabulacion +"\t DominiEntitat:" + dominioEntidad + "\n");}

           if (this.campoDominioCodigo != null) { texto.append(tabulacion +"\t CampDominiCodi:" + campoDominioCodigo + "\n");}
           if (this.campoDominioDescripcion != null) { texto.append(tabulacion +"\t CampDominiDescripcio:" + campoDominioDescripcion + "\n");}

           texto.append(tabulacion +"\t OndiceAlfabetico:" + this.indiceAlfabetico + "\n");
           texto.append(tabulacion +"\t Altura:" + this.altura + "\n");
           texto.append(tabulacion +"\t Orientacion:" + this.orientacion + "\n");

           if (listaParametrosDominio != null) {
        	   texto.append(tabulacion +"\t ListaParametresDomini: \n");
        	   for(ParametroDominio parametro : listaParametrosDominio) {
        		   texto.append(parametro.toString(tabulacion, idioma)+ "\n");
        	   }
           }

           if (listaValorListaFija != null) {
        	   texto.append(tabulacion +"\t ListaValorListaFija: \n");
        	   for(ValorListaFija valor : listaValorListaFija) {
        		   texto.append(valor.toString(tabulacion, idioma)+ "\n");
        	   }
           }

           return texto.toString();
     }

}
