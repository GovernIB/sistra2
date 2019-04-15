package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Paso anexar.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPasoTramitacionAnexar", description = "Descripcion de RPasoTramitacionAnexar", parent = RPasoTramitacion.class)
public class RPasoTramitacionAnexar extends RPasoTramitacion {

	/** Anexos. */
	@ApiModelProperty(value = "Anexos")
	private List<RAnexoTramite> anexos;

	/** Script anexos dinámicos. */
	@ApiModelProperty(value = "Script anexos dinámicos")
	private RScript scriptAnexosDinamicos;

	/** Permite subsanar. **/
	@ApiModelProperty(value = "Permite subsanar")
	private boolean permiteSubsanar;

	/**
	 * Método de acceso a anexos.
	 *
	 * @return anexos
	 */
	public List<RAnexoTramite> getAnexos() {
		return anexos;
	}

	/**
	 * Método para establecer anexos.
	 *
	 * @param anexos
	 *            anexos a establecer
	 */
	public void setAnexos(final List<RAnexoTramite> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Método de acceso a scriptAnexosDinamicos.
	 *
	 * @return scriptAnexosDinamicos
	 */
	public RScript getScriptAnexosDinamicos() {
		return scriptAnexosDinamicos;
	}

	/**
	 * Método para establecer scriptAnexosDinamicos.
	 *
	 * @param scriptAnexosDinamicos
	 *            scriptAnexosDinamicos a establecer
	 */
	public void setScriptAnexosDinamicos(final RScript scriptAnexosDinamicos) {
		this.scriptAnexosDinamicos = scriptAnexosDinamicos;
	}

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

}
