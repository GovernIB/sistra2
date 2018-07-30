package es.caib.sistramit.core.service.model.formulario;

import es.caib.sistramit.core.service.model.formulario.types.TypeValor;

/**
 * Valor de un campo indexado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValorCampoIndexado extends ValorCampo {

    /**
     * Valor del campo.
     */
    private ValorIndexado valor;

    /**
     * Constructor.
     *
     * @param pId
     *            Id campo
     * @param pCodigo
     *            Código
     * @param pValor
     *            Valor
     */
    public ValorCampoIndexado(final String pId, final String pCodigo,
            final String pValor) {
        super();
        this.setTipo(TypeValor.INDEXADO);
        this.setId(pId);
        this.setValor(new ValorIndexado(pCodigo, pValor));
    }

    /**
     * Constructor.
     */
    public ValorCampoIndexado() {
        super();
        this.setTipo(TypeValor.INDEXADO);
    }

    /**
     * Método de acceso a valor.
     *
     * @return valor
     */
    public ValorIndexado getValor() {
        return valor;
    }

    /**
     * Método para establecer valor.
     *
     * @param pValor
     *            valor a establecer
     */
    public void setValor(final ValorIndexado pValor) {
        valor = pValor;
    }

    /**
     * Crea instancia.
     *
     * @return ValorCampoIndexado
     */
    public static ValorCampoIndexado createNewValorCampoIndexado() {
        return new ValorCampoIndexado();
    }

    /**
     * Crea valor vacio.
     *
     * @param idCampo
     *            id campo
     * @return ValorCampoIndexado vacio
     */
    public static ValorCampoIndexado createValorVacio(final String idCampo) {
        final ValorCampoIndexado vci = new ValorCampoIndexado();
        vci.setId(idCampo);
        vci.setValor(null);
        return vci;
    }

    @Override
    public boolean esVacio() {
        return (getValor() == null);
    }

    @Override
    public String print() {
        String res = null;
        if (!this.esVacio()) {
            res = this.getValor().getValor() + " - "
                    + this.getValor().getDescripcion();
        } else {
            res = "";
        }
        return res;
    }

    @Override
    public boolean esValorIgual(final ValorCampo pValorCampo) {
        boolean res = false;
        if (pValorCampo.esVacio() == this.esVacio()
                && pValorCampo.getTipo() == this.getTipo()) {
            if (!pValorCampo.esVacio()) {
                final ValorCampoIndexado vco = (ValorCampoIndexado) pValorCampo;
                res = vco.getValor().equals(this.getValor());
            } else {
                res = true;
            }
        }
        return res;
    }

    @Override
    public ValorCampo duplicar() {
        ValorCampo res;
        if (this.esVacio()) {
            res = createValorVacio(this.getId());
        } else {
            res = new ValorCampoIndexado(this.getId(),
                    this.getValor().getValor(),
                    this.getValor().getDescripcion());
        }
        return res;
    }
}
