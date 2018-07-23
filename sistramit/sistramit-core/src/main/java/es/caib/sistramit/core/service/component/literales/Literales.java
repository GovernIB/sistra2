package es.caib.sistramit.core.service.component.literales;

/**
 * Componente para recuperar literales de la capa de negocio.
 *
 * El fichero de literales están estructurado por secciones para que los
 * diferentes elementos no compartan literales.
 *
 * @author Indra
 *
 */
public interface Literales {

    // Estructura del fichero de literales: cada elemento tiene sus literales.
    // Los literales de cada elemento
    // comenzarán con el prefijo correspondiente.

    /**
     * Literales comunes para los flujos.
     */
    String FLUJO = "flujo";

    /**
     * Literales específicos flujo normalizado.
     */
    String FLUJO_NORMALIZADO = "flujoNormalizado";

    /**
     * Literales específicos flujo personalizado.
     */
    String FLUJO_PERSONALIZADO = "flujoPersonalizado";

    /**
     * Literales específicos paso Debe saber.
     */
    String PASO_DEBE_SABER = "pasoDebeSaber";

    /**
     * Literales específicos paso Rellenar.
     */
    String PASO_RELLENAR = "pasoRellenar";

    /**
     * Literales específicos paso Anexar.
     */
    String PASO_ANEXAR = "pasoAnexar";

    /**
     * Literales específicos paso Guardar.
     */
    String PASO_GUARDAR = "pasoGuardar";

    /**
     * Literales específicos paso Anexar.
     */
    String PASO_REGISTRAR = "pasoRegistrar";

    /**
     * Literales específicos paso Pagar.
     */
    String PASO_PAGAR = "pasoPagar";

    /**
     * Literales específicos paso Info.
     */
    String PASO_INFO = "pasoInfo";

    /**
     * Literales específicos paso Captura.
     */
    String PASO_CAPTURA = "pasoCaptura";

    /**
     * Literales específicos pasarela pagos.
     */
    String PASARELA_PAGOS = "pago";

    /**
     * Literales específicos gestor formularios interno.
     */
    String GESTOR_FORMULARIOS_INTERNO = "gestorFormulariosInterno";

    /**
     * Literales email incidencias.
     */
    String SOPORTE_INCIDENCIAS = "incidencias";

    /**
     * Obtiene literal.
     *
     * @param prefijo
     *            Parámetro prefijo
     * @param codigo
     *            Codigo literal
     * @param idioma
     *            Idioma
     * @return Literal
     */
    String getLiteral(String prefijo, String codigo, String idioma);

    /**
     * Obtiene literal.
     *
     * @param elemento
     *            Elemento
     * @param codigo
     *            Codigo literal
     * @param parametros
     *            Parámetros a substituir en el literal
     * @param idioma
     *            Idioma
     * @return Literal
     */
    String getLiteral(String elemento, String codigo, String[] parametros,
            String idioma);

}
