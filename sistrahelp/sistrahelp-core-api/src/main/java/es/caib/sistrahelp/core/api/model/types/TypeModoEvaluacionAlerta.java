package es.caib.sistrahelp.core.api.model.types;

public enum TypeModoEvaluacionAlerta {

    ACUMULADO(1), POR_INTERVALO(2);

    private int codigo;

    private TypeModoEvaluacionAlerta(final int iCodigo) {
        this.codigo = iCodigo;
    }

    /**
     * Convierte un string en enumerado.
     *
     * @param iCodigo
     * @return
     */
    public static TypeModoEvaluacionAlerta fromCodigo(final int iCodigo) {
        TypeModoEvaluacionAlerta respuesta = null;

            for (final TypeModoEvaluacionAlerta b : TypeModoEvaluacionAlerta.values()) {
                if (iCodigo == b.getCodigo()) {
                    respuesta = b;
                    break;
                }
            }

        return respuesta;
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "" + codigo;
    }
}
