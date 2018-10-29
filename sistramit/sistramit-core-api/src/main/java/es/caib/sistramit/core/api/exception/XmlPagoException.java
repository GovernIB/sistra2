package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando hay un problema con el xml de pago.
 *
 */
@SuppressWarnings("serial")
public final class XmlPagoException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor XmlPagoException.
     *
     * @param message
     *            Mensaje
     * @param cause
     *            Causa
     */
    public XmlPagoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor XmlPagoException.
     *
     * @param message
     *            Mensaje
     * @param xml
     *            xml
     * @param cause
     *            Causa
     */
    public XmlPagoException(final String message, final String xml,
            final Throwable cause) {
        super(message, cause);
        final ListaPropiedades props = new ListaPropiedades();
        if (xml != null) {
            props.addPropiedad("xml", xml);
        } else {
            props.addPropiedad("xml", "nulo");
        }
        this.setDetallesExcepcion(props);
    }

    /**
     * Constructor XmlPagoException.
     *
     * @param message
     *            Mensaje
     */
    public XmlPagoException(final String message) {
        super(message);
    }

}
