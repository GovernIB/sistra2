package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto número.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoNumero", description = "Descripcion de RPropiedadesTextoNumero")
public class RPropiedadesTextoNumero {

    /** Texto número: precision parte entera. */
	@ApiModelProperty(value = "Texto número: precision parte entera")
    private int precisionEntera;

    /** Texto número: precision parte decimal. */
	@ApiModelProperty(value = "Texto número: precision parte decimal")
    private int precisionDecimal;

    /**
     * Texto número: formato punto/coma ("PC"), coma/punto ("CP") y sin formato
     * ("SF").
     */
	@ApiModelProperty(value = "Texto número: formato punto/coma (PC), coma/punto (CP) y sin formato (SF)")
    private String formatoNumero;

    /** Texto número: rango. */
	@ApiModelProperty(value = "Texto número: rango")
    private boolean rango;

    /** Texto número: rango desde. */
	@ApiModelProperty(value = "Texto número: rango desde")
    private long rangoDesde;

    /** Texto número: rango hasta. */
	@ApiModelProperty(value = "Texto número: rango hasta")
    private long rangoHasta;

    /** Texto número: permite negativos. */
	@ApiModelProperty(value = "Texto número: permite negativos")
    private boolean negativos;

    /**
     * Método de acceso a precisionEntera.
     *
     * @return precisionEntera
     */
    public int getPrecisionEntera() {
        return precisionEntera;
    }

    /**
     * Método para establecer precisionEntera.
     *
     * @param precisionEntera
     *            precisionEntera a establecer
     */
    public void setPrecisionEntera(int precisionEntera) {
        this.precisionEntera = precisionEntera;
    }

    /**
     * Método de acceso a precisionDecimal.
     *
     * @return precisionDecimal
     */
    public int getPrecisionDecimal() {
        return precisionDecimal;
    }

    /**
     * Método para establecer precisionDecimal.
     *
     * @param precisionDecimal
     *            precisionDecimal a establecer
     */
    public void setPrecisionDecimal(int precisionDecimal) {
        this.precisionDecimal = precisionDecimal;
    }

    /**
     * Método de acceso a formatoNumero.
     *
     * @return formatoNumero
     */
    public String getFormatoNumero() {
        return formatoNumero;
    }

    /**
     * Método para establecer formatoNumero.
     *
     * @param formatoNumero
     *            formatoNumero a establecer
     */
    public void setFormatoNumero(String formatoNumero) {
        this.formatoNumero = formatoNumero;
    }

    /**
     * Método de acceso a rango.
     *
     * @return rango
     */
    public boolean isRango() {
        return rango;
    }

    /**
     * Método para establecer rango.
     *
     * @param rango
     *            rango a establecer
     */
    public void setRango(boolean rango) {
        this.rango = rango;
    }

    /**
     * Método de acceso a rangoDesde.
     *
     * @return rangoDesde
     */
    public long getRangoDesde() {
        return rangoDesde;
    }

    /**
     * Método para establecer rangoDesde.
     *
     * @param rangoDesde
     *            rangoDesde a establecer
     */
    public void setRangoDesde(long rangoDesde) {
        this.rangoDesde = rangoDesde;
    }

    /**
     * Método de acceso a rangoHasta.
     *
     * @return rangoHasta
     */
    public long getRangoHasta() {
        return rangoHasta;
    }

    /**
     * Método para establecer rangoHasta.
     *
     * @param rangoHasta
     *            rangoHasta a establecer
     */
    public void setRangoHasta(long rangoHasta) {
        this.rangoHasta = rangoHasta;
    }

    /**
     * Método de acceso a negativos.
     *
     * @return negativos
     */
    public boolean isNegativos() {
        return negativos;
    }

    /**
     * Método para establecer negativos.
     *
     * @param negativos
     *            negativos a establecer
     */
    public void setNegativos(boolean negativos) {
        this.negativos = negativos;
    }
}
