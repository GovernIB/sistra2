package es.caib.sistramit.rest.interna;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistramit.core.api.exception.ErrorJsonException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltrosAuditoria;
import es.caib.sistramit.rest.api.interna.RInvalidacion;
import es.caib.sistramit.rest.api.util.JsonException;
import es.caib.sistramit.rest.api.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Operaciones requeridas desde el resto de módulos de Sistra2. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/interna")
@Api(value = "interna", produces = "application/json")
public class ApiInternaRestController {

	/** Service system. */
	@Autowired
	private SystemService systemService;

	@Autowired
	private RestApiInternaService restApiInternaService;

	/**
	 * Invalidación.
	 *
	 */
	@ApiOperation(value = "Invalidación caché", notes = "Invalidación caché")
	@RequestMapping(value = "/invalidacion", method = RequestMethod.POST)
	public boolean invalidacion(
			@ApiParam("{\"tipo\":\"tipo\",\"identificador\":\"id\"}") @RequestParam(name = "invalidacion") final String invalidacionJSON) {

		// Parseamos parametro enviado por POST
		RInvalidacion pars = null;
		if (StringUtils.isNotBlank(invalidacionJSON)) {
			try {
				pars = (RInvalidacion) JsonUtil.fromJson(invalidacionJSON, RInvalidacion.class);
			} catch (final JsonException e) {
				throw new RuntimeException(e);
			}
		}

		// Añade invalidación
		final Invalidacion invalidacion = new Invalidacion();
		invalidacion.setTipo(TypeInvalidacion.fromString(pars.getTipo()));
		invalidacion.setIdentificador(pars.getIdentificador());
		systemService.invalidar(invalidacion);

		return true;

	}

	@ApiOperation(value = "Auditoría de eventos", notes = "Auditoría de eventos", response = REventoAuditoria.class, responseContainer = "List")
	@RequestMapping(value = "/auditoria/evento", method = RequestMethod.GET)
	public List<REventoAuditoria> obtenerAuditoriaEvento(@RequestParam(name = "filtros") final String filtros) {
		List<REventoAuditoria> resListaEventos = null;

		RFiltrosAuditoria rFiltros = null;
		try {
			rFiltros = (RFiltrosAuditoria) JsonUtil.fromJson(filtros, RFiltrosAuditoria.class);
		} catch (final JsonException e) {
			throw new ErrorJsonException(e);
		}

		final List<EventoAuditoria> listaEventos = restApiInternaService.recuperarLogSesionTramitacionArea(rFiltros);

		if (listaEventos != null && !listaEventos.isEmpty()) {
			resListaEventos = new ArrayList<>();

			for (final EventoAuditoria eventoAuditoria : listaEventos) {
				final REventoAuditoria nuevo = new REventoAuditoria();
				nuevo.setId(eventoAuditoria.getId());
				nuevo.setFecha(eventoAuditoria.getFecha());
				nuevo.setTipo(eventoAuditoria.getTipoEvento().toString());
				nuevo.setIdSesion(eventoAuditoria.getIdSesionTramitacion());
				nuevo.setNif(eventoAuditoria.getNif());
				resListaEventos.add(nuevo);
			}

		}

		return resListaEventos;
	}
}
