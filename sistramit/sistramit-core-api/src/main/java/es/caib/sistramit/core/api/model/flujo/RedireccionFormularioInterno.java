package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypeFormulario;

/**
 *
 * Respuesta a la acción de abrir un formulario interno. Devolverá el html del
 * formulario y los datos del formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RedireccionFormularioInterno extends RedireccionFormulario {

    /**
     * Constructor.
     */
    public RedireccionFormularioInterno() {
        super();
        setTipo(TypeFormulario.INTERNO);
    }

    /**
     * Ticket de acceso a la sesión de formulario.
     */
    private String ticket;

    /**
     * Método de acceso a ticket abrir formulario interno.
     *
     * @return ticket abrir formulario interno.
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Método para establecer ticket abrir formulario interno.
     *
     * @param pTicket
     *            ticket abrir formulario interno a establecer
     */
    public void setTicket(final String pTicket) {
        ticket = pTicket;
    }

}
