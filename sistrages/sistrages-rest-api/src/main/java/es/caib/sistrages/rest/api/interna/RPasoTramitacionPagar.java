package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Paso pagar.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPasoTramitacionPagar", description = "Descripcion de RPasoTramitacionPagar", parent = RPasoTramitacion.class)
public class RPasoTramitacionPagar extends RPasoTramitacion {

	/** Pagos. */
	@ApiModelProperty(value = "Pagos")
	private List<RPagoTramite> pagos;

	/** Permite subsanar. **/
	@ApiModelProperty(value = "Permite subsanar")
	private boolean permiteSubsanar;

	/**
	 * @return the permiteSubsanar
	 */
	public final boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar
	 *            the permiteSubsanar to set
	 */
	public final void setPermiteSubsanar(final boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
	}

	/**
	 * @return the instruccionesSubsanacion
	 */
	public final RLiteral getInstruccionesSubsanacion() {
		return instruccionesSubsanacion;
	}

	/**
	 * @param instruccionesSubsanacion
	 *            the instruccionesSubsanacion to set
	 */
	public final void setInstruccionesSubsanacion(final RLiteral instruccionesSubsanacion) {
		this.instruccionesSubsanacion = instruccionesSubsanacion;
	}

	/** Instrucciones subsanación */
	@ApiModelProperty(value = "Instrucciones subsanacion")
	private RLiteral instruccionesSubsanacion;

	/**
	 * Método de acceso a pagos.
	 *
	 * @return pagos
	 */
	public List<RPagoTramite> getPagos() {
		return pagos;
	}

	/**
	 * Método para establecer pagos.
	 *
	 * @param pagos
	 *            pagos a establecer
	 */
	public void setPagos(final List<RPagoTramite> pagos) {
		this.pagos = pagos;
	}

}
