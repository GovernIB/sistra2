package es.caib.sistrages.rest.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.utils.AdapterUtils;

/**
 * Adapter para convertir a modelo rest.
 *
 * @author Indra
 *
 */
@Component
public class AvisosEntidadAdapter {

	/** Servicio rest. */
	@Autowired
	private RestApiInternaService restApiService;

	/**
	 * Conversion a modelo rest.
	 *
	 * @param idEntidad
	 *            id entidad
	 * @return modelo rest
	 */
	public RAvisosEntidad convertir(final String idEntidad) {
		final RAvisosEntidad rAvisosEntidad = new RAvisosEntidad();
		final List<AvisoEntidad> la = this.restApiService.getAvisosEntidad(idEntidad);
		rAvisosEntidad.setAvisos(generaListaAvisos(la));
		rAvisosEntidad.setTimestamp(System.currentTimeMillis() + "");
		return rAvisosEntidad;
	}

	/**
	 * Genera la Lista de Avisos
	 *
	 * @param la
	 * @return la lista de avisos
	 */
	private List<RAviso> generaListaAvisos(final List<AvisoEntidad> la) {
		List<RAviso> lres = null;
		final SimpleDateFormat f = new SimpleDateFormat(AdapterUtils.FORMATO_FECHA);
		if (la != null) {
			lres = new ArrayList<>();
			for (final AvisoEntidad a : la) {
				final RAviso res = new RAviso();
				res.setBloquear(a.isBloqueado());
				res.setFechaFin(a.getFechaFin() == null ? null : f.format(a.getFechaFin()));
				res.setFechaInicio(a.getFechaInicio() == null ? null : f.format(a.getFechaInicio()));
				res.setListaVersiones(getListaVersiones(a.getListaSerializadaTramites()));
				res.setMensaje(AdapterUtils.generarLiteral(a.getMensaje()));
				res.setTipo(a.getTipo() == null ? null : a.getTipo().toString());
				lres.add(res);
			}

		}
		return lres;
	}

	/**
	 * Genera la lista de versiones. Hay que entender que se almacena como: <br />
	 * CODIGO_VERSION#NUM_VERSION;CODIGO_VERSION#NUM_VERSION <br />
	 * Por: IDENTIFICADOR_TRAMITE#NUM_VERSION;IDENTIFICADOR_TRAMITE#NUM_VERSION
	 * <br />
	 * En resumen, cambiar código de version por identificador trámite.
	 *
	 * @param listaSerializadaTramites
	 * @return
	 */
	private String getListaVersiones(final String listaSerializadaTramites) {
		final StringBuilder listaVersiones = new StringBuilder();
		final String[] trams = listaSerializadaTramites.split(";");
		if (trams.length > 0) {
			for (final String tram : trams) {
				final String codigoVersion = tram.split("#")[0];
				final String numeroVersion = tram.split("#")[1];
				final String identificador = restApiService
						.getIdentificadorByCodigoVersion(Long.valueOf(codigoVersion));
				listaVersiones.append(identificador + "#" + numeroVersion);
			}
		}
		String resultado = listaVersiones.toString();
		if (resultado.endsWith(";")) {
			resultado = resultado.substring(0, resultado.length() - 1);
		}
		return resultado;
	}

}
