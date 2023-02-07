package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Script.
 */

public class Script extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. */
	private Long codigo;

	/** Contenido. */
	private String contenido;

	/** Mensajes. **/
	private List<LiteralScript> mensajes = new ArrayList<>();

	/** Indica si los mensajes han sido alterados. Por defecto, a falso **/
	private Boolean mensajesAlterado = false;

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
	 * Obtiene el valor de contenido.
	 *
	 * @return el valor de contenido
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * Establece el valor de contenido.
	 *
	 * @param contenido el nuevo valor de contenido
	 */
	public void setContenido(final String contenido) {
		this.contenido = contenido;
	}

	/**
	 * @return the mensajes
	 */
	public List<LiteralScript> getMensajes() {
		return mensajes;
	}

	/**
	 * @param mensajes the mensajes to set
	 */
	public void setMensajes(final List<LiteralScript> mensajes) {
		this.mensajes = mensajes;
	}

	/**
	 * @return the mensajesAlterado
	 */
	public Boolean isMensajesAlterado() {
		return mensajesAlterado;
	}

	/**
	 * @param mensajesAlterado the mensajesAlterado to set
	 */
	public void setMensajesAlterado(final Boolean mensajesAlterado) {
		this.mensajesAlterado = mensajesAlterado;
	}

	/**
	 * Clonar.
	 *
	 * @param iScript
	 * @return
	 */
	public static Script clonar(final Script iScript) {
		Script script = null;
		if (iScript != null) {
			script = new Script();
			script.setContenido(iScript.getContenido());
			if (iScript.getMensajes() != null && !iScript.getMensajes().isEmpty()) {
				final List<LiteralScript> mensajes = new ArrayList<>();
				for (final LiteralScript mensaje : iScript.getMensajes()) {
					mensajes.add(LiteralScript.clonar(mensaje));
				}
				script.setMensajes(mensajes);
			}
		}
		return script;
	}

	/**
	 * Limpia de identificadores.
	 *
	 * @param iScript
	 * @return
	 */
	public void limpiarIds() {
		this.setCodigo(null);
		if (this.getMensajes() != null && !this.getMensajes().isEmpty()) {
			for (final LiteralScript mensaje : this.getMensajes()) {
				mensaje.limpiarIds();
			}
		}
	}

	/**
	 * Busca entre los literales si contiene el literal según el identificador
	 *
	 * @param identificador
	 * @return
	 */
	public boolean containLiteral(final String identificador) {
		boolean existe = false;
		if (this.mensajes != null) {
			for (final LiteralScript mensaje : this.mensajes) {
				if (mensaje.getIdentificador().equals(identificador)) {
					existe = true;
					break;
				}
			}
		}
		return existe;
	}

	/**
	 * Busca entre los literales si contiene el literal según el codigo
	 *
	 * @param codigo
	 * @return
	 */
	public boolean containLiteral(final Long codigo) {
		boolean existe = false;
		if (this.mensajes != null) {
			for (final LiteralScript mensaje : this.mensajes) {
				// El identificador puede estar a nulo
				if (mensaje.getCodigo() != null && mensaje.getCodigo().compareTo(codigo) == 0) {
					existe = true;
					break;
				}
			}
		}
		return existe;
	}

	public static Script createInstance() {
		Script script = new Script();
		return script;
	}

	public boolean estaVacio() {
		return this.contenido == null || contenido.trim().isEmpty();
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
           StringBuilder texto = new StringBuilder(tabulacion +"\t Script. ");
           texto.append(tabulacion +"\t Codi:" + codigo + "\n");
           if (contenido != null) {
        	   	texto.append(tabulacion +"\t Contingut: \n\t\t" + tabulacion + contenido.replaceAll("\n", "\n\t\t"+tabulacion) + "\n");
           }
           texto.append(tabulacion +"\t MissatgeAlterat:" + mensajesAlterado + "\n");
           if (mensajes != null && !mensajes.isEmpty()) {
        	   texto.append(tabulacion +"\t Missatges \n");
               for(LiteralScript mensaje : mensajes) {
        		   texto.append(mensaje.toString(tabulacion+"\t", idioma));
        	   }
           }
           return texto.toString();
     }

}
