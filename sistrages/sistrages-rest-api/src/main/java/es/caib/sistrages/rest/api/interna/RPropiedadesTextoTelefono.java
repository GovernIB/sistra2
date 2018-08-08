package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Propiedades campo texto telefono.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPropiedadesTextoTelefono", description = "Descripcion de RPropiedadesTextoTelefono")
public class RPropiedadesTextoTelefono {

    /** Texto teléfono: móvil. */
	@ApiModelProperty(value = "Texto teléfono: móvil")
    private boolean movil;

    /** Texto teléfono: fijo. */
	@ApiModelProperty(value = "Texto teléfono: fijo")
    private boolean fijo;

    /**
     * Método de acceso a movil.
     *
     * @return movil
     */
    public boolean isMovil() {
        return movil;
    }

    /**
     * Método para establecer movil.
     *
     * @param movil
     *            movil a establecer
     */
    public void setMovil(boolean movil) {
        this.movil = movil;
    }

    /**
     * Método de acceso a fijo.
     *
     * @return fijo
     */
    public boolean isFijo() {
        return fijo;
    }

    /**
     * Método para establecer fijo.
     *
     * @param fijo
     *            fijo a establecer
     */
    public void setFijo(boolean fijo) {
        this.fijo = fijo;
    }
}
