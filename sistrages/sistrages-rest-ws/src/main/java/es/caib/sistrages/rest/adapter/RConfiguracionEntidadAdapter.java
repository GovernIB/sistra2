package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.utils.AdapterUtils;

public class RConfiguracionEntidadAdapter {
	private RConfiguracionEntidad rConfiguracionEntidad;

	public RConfiguracionEntidadAdapter(Entidad entidad, RestApiInternaService restApiService) {

		rConfiguracionEntidad = new RConfiguracionEntidad();
		rConfiguracionEntidad
				.setIdentificador(entidad.getCodigoDIR3() != null ? entidad.getCodigoDIR3().toString() : null);
		rConfiguracionEntidad.setLogo(restApiService.getReferenciaFichero(
				entidad.getLogoAsistente() != null ? entidad.getLogoAsistente().getCodigo() : null));
		rConfiguracionEntidad.setCss(
				restApiService.getReferenciaFichero(entidad.getCss() != null ? entidad.getCss().getCodigo() : null));
		rConfiguracionEntidad.setEmail(entidad.getEmail());
		rConfiguracionEntidad.setContactoHTML(AdapterUtils.generarLiteral(entidad.getPie()));
		rConfiguracionEntidad.setUrlCarpeta(AdapterUtils.generarLiteral(entidad.getUrlCarpetaCiudadana()));
		rConfiguracionEntidad.setAyudaTelefono(entidad.getTelefono());
		rConfiguracionEntidad.setAyudaUrl(entidad.getUrlSoporte());

		rConfiguracionEntidad.setPlugins(
				AdapterUtils.crearPlugins(restApiService.listPlugin(TypeAmbito.ENTIDAD, entidad.getCodigo(), null)));
		rConfiguracionEntidad.setAyudaFormulario(generaFormularios(entidad.getFormularioIncidencias()));
	}

	private List<ROpcionFormularioSoporte> generaFormularios(List<FormularioSoporte> formularioIncidencias) {
		try {
	    	ROpcionFormularioSoporte opc;
	         final List<ROpcionFormularioSoporte> opciones = new ArrayList<>();
	         for(FormularioSoporte fs : formularioIncidencias) {
		         opc = new ROpcionFormularioSoporte();
		         opc.setTipo(AdapterUtils.generarLiteral( fs.getTipoIncidencia()) );
		         opc.setDescripcion(AdapterUtils.generarLiteral(fs.getDescripcion()));
		         opc.setDestinatario(fs.getTipoDestinatario()!=null? fs.getTipoDestinatario().toString():null);
		         opc.setListaEmails(fs.getListaEmails());
		         opciones.add(opc);
	         }
	         return opciones;
		} catch (Exception e) {
			return null;
		}
	}

	public RConfiguracionEntidad getrConfiguracionEntidad() {
		return rConfiguracionEntidad;
	}

	public void setrConfiguracionEntidad(RConfiguracionEntidad rConfiguracionEntidad) {
		this.rConfiguracionEntidad = rConfiguracionEntidad;
	}

}