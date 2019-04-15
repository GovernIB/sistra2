package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RPasoTramitacionRellenar", description = "Descripcion de RPasoTramitacionRellenar", parent = RPasoTramitacion.class)
public class RPasoTramitacionRellenar extends RPasoTramitacion {

	/** Formularios. */
	@ApiModelProperty(value = "Formularios")
	private List<RFormularioTramite> formularios;

	/**
	 * Método de acceso a formularios.
	 *
	 * @return formularios
	 */
	public List<RFormularioTramite> getFormularios() {
		return formularios;
	}

	/**
	 * Método para establecer formularios.
	 *
	 * @param formularios
	 *            formularios a establecer
	 */
	public void setFormularios(final List<RFormularioTramite> formularios) {
		this.formularios = formularios;
	}

}
