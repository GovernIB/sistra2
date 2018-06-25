package es.caib.sistrages.rest.api.interna;

/**
 * Componente sección.
 *
 * @author Indra
 *
 */
public class RComponenteSeccion extends RComponente {

    /** Letra sección. */
    private String letra;

    /**
     * Método de acceso a letra.
     *
     * @return letra
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Método para establecer letra.
     *
     * @param letra
     *            letra a establecer
     */
    public void setLetra(String letra) {
        this.letra = letra;
    }

}
