package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Valor compuesto por codigo / descripción.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValorIndexado implements Serializable {
    /**
     * Codigo valor indexado.
     */
    private String valor;
    /**
     * Descripción valor indexado.
     */
    private String descripcion;

    /**
     * Constructor ValorIndexado.
     *
     * @param pCodigo
     *            Código
     * @param pDescripcion
     *            Descripción
     */
    public ValorIndexado(final String pCodigo, final String pDescripcion) {
        super();
        valor = pCodigo;
        descripcion = pDescripcion;
    }

    /**
     * Constructor ValorIndexado.
     */
    public ValorIndexado() {
        super();
    }

    /**
     * Indica codigo valor indexado.
     *
     * @return codigo valor indexado
     */
    public String getValor() {
        return valor;
    }

    /**
     * Indica codigo valor indexado.
     *
     * @param pCodigo
     *            codigo valor indexado
     */
    public void setValor(final String pCodigo) {
        valor = pCodigo;
    }

    /**
     * Indica descripción valor indexado.
     *
     * @return descripción valor indexado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Indica descripción valor indexado.
     *
     * @param pDescripcion
     *            descripción valor indexado
     */
    public void setDescripcion(final String pDescripcion) {
        descripcion = pDescripcion;
    }

    /**
     * Crea valor indexado.
     *
     * @param pCodigo
     *            Código
     * @param pDescripcion
     *            Descripción
     * @return ValorIndexado
     */
    public static ValorIndexado createNewValorIndexado(final String pCodigo,
            final String pDescripcion) {
        return new ValorIndexado(pCodigo, pDescripcion);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = ConstantesNumero.N31;
        int result = ConstantesNumero.N1;
        int hash = 0;
        if (valor != null) {
            hash = valor.hashCode();
        }
        result = prime * result + hash;
        hash = 0;
        if (descripcion != null) {
            hash = descripcion.hashCode();
        }
        result = prime * result + hash;
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        boolean res = false;
        if (this == obj) {
            res = true;
        }
        if (!res && obj == null) {
            res = false;
        }
        if (!res && getClass() != obj.getClass()) {
            res = false;
        }
        if (!res) {
            final ValorIndexado other = (ValorIndexado) obj;
            if (valor == null) {
                if (other.valor != null) {
                    res = false;
                }
            } else if (!valor.equals(other.valor)) {
                res = false;
            }
            if (descripcion == null) {
                if (other.descripcion != null) {
                    res = false;
                }
            } else if (!descripcion.equals(other.descripcion)) {
                res = false;
            }
            res = true;
        }
        return res;
    }

}
