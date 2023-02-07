package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase DisenyoFormulario.
 */

public final class DisenyoFormulario extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/**
	 * codigo.
	 */
	private Long codigo;

	/**
	 * script plantilla.
	 */
	private Script scriptPlantilla;

	/**
	 * texto de cabecera.
	 */
	private Literal textoCabecera;

	/**
	 * permitir acciones personalizadas.
	 */
	private boolean permitirAccionesPersonalizadas;

	/**
	 * Permitir guardar sin finalizar
	 */
	private boolean permitirGuardarSinFinalizar;

	/**
	 * mostrar cabecera.
	 */
	private boolean mostrarCabecera;

	/**
	 * paginas.
	 */
	private List<PaginaFormulario> paginas = new ArrayList<>();

	/**
	 * plantillas.
	 */
	private List<PlantillaFormulario> plantillas = new ArrayList<>();

	/**
	 * Crea una nueva instancia de DisenyoFormulario.
	 */
	public DisenyoFormulario() {
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
	 * @param codigo el nuevo valor de codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el valor de paginas.
	 *
	 * @return el valor de paginas
	 */
	public List<PaginaFormulario> getPaginas() {
		return paginas;
	}

	/**
	 * Obtiene el valor de scriptPlantilla.
	 *
	 * @return el valor de scriptPlantilla
	 */
	public Script getScriptPlantilla() {
		return scriptPlantilla;
	}

	/**
	 * Establece el valor de scriptPlantilla.
	 *
	 * @param scriptPlantilla el nuevo valor de scriptPlantilla
	 */
	public void setScriptPlantilla(final Script scriptPlantilla) {
		this.scriptPlantilla = scriptPlantilla;
	}

	/**
	 * Obtiene el valor de textoCabecera.
	 *
	 * @return el valor de textoCabecera
	 */
	public Literal getTextoCabecera() {
		return textoCabecera;
	}

	/**
	 * Establece el valor de textoCabecera.
	 *
	 * @param textoCabecera el nuevo valor de textoCabecera
	 */
	public void setTextoCabecera(final Literal textoCabecera) {
		this.textoCabecera = textoCabecera;
	}

	/**
	 * Verifica si es permitir acciones personalizadas.
	 *
	 * @return true, si es permitir acciones personalizadas
	 */
	public boolean isPermitirAccionesPersonalizadas() {
		return permitirAccionesPersonalizadas;
	}

	/**
	 * Establece el valor de permitirAccionesPersonalizadas.
	 *
	 * @param permitirAccionesPersonalizadas el nuevo valor de
	 *                                       permitirAccionesPersonalizadas
	 */
	public void setPermitirAccionesPersonalizadas(final boolean permitirAccionesPersonalizadas) {
		this.permitirAccionesPersonalizadas = permitirAccionesPersonalizadas;
	}

	/**
	 * Verifica si es mostrar cabecera.
	 *
	 * @return true, si es mostrar cabecera
	 */
	public boolean isMostrarCabecera() {
		return mostrarCabecera;
	}

	/**
	 * Establece el valor de mostrarCabecera.
	 *
	 * @param cabeceraFormulario el nuevo valor de mostrarCabecera
	 */
	public void setMostrarCabecera(final boolean cabeceraFormulario) {
		this.mostrarCabecera = cabeceraFormulario;
	}

	/**
	 * Obtiene el valor de plantillas.
	 *
	 * @return el valor de plantillas
	 */
	public List<PlantillaFormulario> getPlantillas() {
		return plantillas;
	}

	/**
	 * @param paginas the paginas to set
	 */
	public void setPaginas(final List<PaginaFormulario> paginas) {
		this.paginas = paginas;
	}

	/**
	 * @param plantillas the plantillas to set
	 */
	public void setPlantillas(final List<PlantillaFormulario> plantillas) {
		this.plantillas = plantillas;
	}

	/**
	 * Reemplaza una pagina.
	 *
	 * @param pagina
	 * @param posicion
	 */
	public void setPagina(final PaginaFormulario pagina, final int posicion) {
		this.paginas.remove(posicion);
		this.paginas.add(posicion, pagina);
	}

	/**
	 * @return the permitirGuardarSinFinalizar
	 */
	public boolean isPermitirGuardarSinFinalizar() {
		return permitirGuardarSinFinalizar;
	}

	/**
	 * @param permitirGuardarSinFinalizar the permitirGuardarSinFinalizar to set
	 */
	public void setPermitirGuardarSinFinalizar(final boolean permitirGuardarSinFinalizar) {
		this.permitirGuardarSinFinalizar = permitirGuardarSinFinalizar;
	}

	@Override
	public String toString() {
        return toString("", "ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "DisenyFormulari. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           if (scriptPlantilla != null) {
        	   texto.append(tabulacion +"\t ScriptPlantilla: \n");
        	   texto.append(scriptPlantilla.toString(tabulacion, idioma)+ "\n");
           }
           if (textoCabecera != null) {
        	   texto.append(tabulacion +"\t TextoCabecera: \n");
        	   texto.append(textoCabecera.toString(tabulacion, idioma)+ "\n");
           }
           texto.append(tabulacion +"\t PermitirAccionsPersonalitzades:" + permitirAccionesPersonalizadas + "\n");
           texto.append(tabulacion +"\t PermetreEsmenaSenseFinalitzar:" + permitirGuardarSinFinalizar + "\n");
           texto.append(tabulacion +"\t MostrarCabecera:" + mostrarCabecera + "\n");
           if (plantillas != null && !plantillas.isEmpty()) {
        	   texto.append(tabulacion +"\t Plantillas \n");
        	   for(PlantillaFormulario plantilla : plantillas) {
        		   texto.append(tabulacion +"\t" + plantilla.toString(tabulacion +"\t", idioma));
        	   }
           }
           if (paginas != null && !paginas.isEmpty()) {
        	   texto.append(tabulacion +"\t Paginas \n");
        	   for(PaginaFormulario pagina : paginas) {
        		   texto.append(tabulacion +"\t" + pagina.toString(tabulacion +"\t", idioma));
        	   }
           }
           return texto.toString();
     }

}
