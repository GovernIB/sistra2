package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que no se ha pasado la validación del anexo (script
 * validar anexo).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValidacionAnexoException extends ServiceRollbackException {

    /*
     * ValidacionAnexoException WARNING.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.WARNING;
    }

    /**
     * Constructor ValidacionAnexoException.
     *
     * @param pMessage
     *            Mensaje de error.
     * @param idAnexo
     *            Parámetro id anexo
     */
    public ValidacionAnexoException(final String pMessage,
            final String idAnexo) {
        super(pMessage);
        final ListaPropiedades props = new ListaPropiedades();
        props.addPropiedad("idAnexo", idAnexo);
        this.setDetallesExcepcion(props);
    }

}
