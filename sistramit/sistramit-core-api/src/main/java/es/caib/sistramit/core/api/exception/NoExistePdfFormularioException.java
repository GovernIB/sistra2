package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que no se puede visualizar como pdf un formulario. En la
 * capa de front se deberá capturar este error para mostrar un mensaje al
 * ciudadano.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NoExistePdfFormularioException
        extends ServiceRollbackException {

    /*
     * NoExistePdfFormularioException WARNING.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.WARNING;
    }

    /**
     * Constructor NoExistePdfFormularioException.
     *
     * @param idFormulario
     *            Parámetro id formulario
     */
    public NoExistePdfFormularioException(final String idFormulario) {
        super("No existe pdf de visualizacion para el formulario");
        final ListaPropiedades props = new ListaPropiedades();
        props.addPropiedad("idFormulario", idFormulario);
        this.setDetallesExcepcion(props);
    }

}
