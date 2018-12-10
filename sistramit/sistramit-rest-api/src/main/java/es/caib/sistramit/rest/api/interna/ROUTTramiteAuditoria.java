package es.caib.sistramit.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Resultado de tramites
 *
 * @author Indra
 *
 */
@ApiModel(value = "ROUTTramiteAuditoria", description = "salida de datos de tramites y paginacion")
public class ROUTTramiteAuditoria {

	@ApiModelProperty(value = "Lista eventos")
	private List<RPersistenciaAuditoria> listaPersistencia;

	@ApiModelProperty(value = "Num. elementos")
	private Long numElementos;

	public List<RPersistenciaAuditoria> getListaPersistencia() {
		return listaPersistencia;
	}

	public void setListaPersistencia(final List<RPersistenciaAuditoria> listaPersistencia) {
		this.listaPersistencia = listaPersistencia;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
