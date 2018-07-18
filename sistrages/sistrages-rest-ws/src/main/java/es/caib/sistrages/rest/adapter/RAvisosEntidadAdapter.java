package es.caib.sistrages.rest.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RAviso;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.utils.AdapterUtils;

public class RAvisosEntidadAdapter {

	private RAvisosEntidad rAvisosEntidad;
	private RestApiInternaService restApiService;

	public RAvisosEntidadAdapter(RestApiInternaService restApiService, String idEntidad) {
		this.restApiService = restApiService;
		rAvisosEntidad = new RAvisosEntidad();
		List<AvisoEntidad> la = this.restApiService.getAvisosEntidad(idEntidad);
		rAvisosEntidad.setAvisos(generaListaAvisos(la));
		rAvisosEntidad.setTimestamp(System.currentTimeMillis() +"");
	}

	private List<RAviso> generaListaAvisos(List<AvisoEntidad> la) {
		SimpleDateFormat f = new SimpleDateFormat(AdapterUtils.FORMATO_FECHA);

		if (la != null) {
			List<RAviso> lres = new ArrayList<RAviso>();
			for (AvisoEntidad a : la) {
				RAviso res = new RAviso();
				res.setBloquear(a.isBloqueado());
				res.setFechaFin(a.getFechaFin() == null ? null : f.format(a.getFechaFin()));
				res.setFechaInicio(a.getFechaInicio() == null ? null : f.format(a.getFechaInicio()));
				res.setListaVersiones(a.getListaSerializadaTramites());
				res.setMensaje(AdapterUtils.generarLiteral(a.getMensaje()));
				res.setTipo(a.getTipo() == null ? null : a.getTipo().toString());
				lres.add(res);
			}
			return lres;
		}		
		return null;
	}

	public RAvisosEntidad getrAvisosEntidad() {
		return rAvisosEntidad;
	}

	public void setrAvisosEntidad(RAvisosEntidad rAvisosEntidad) {
		this.rAvisosEntidad = rAvisosEntidad;
	}

}
