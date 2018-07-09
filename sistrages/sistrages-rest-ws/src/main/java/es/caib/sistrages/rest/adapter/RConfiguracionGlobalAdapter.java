package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.utils.AdapterUtils;

public class RConfiguracionGlobalAdapter {
	private RConfiguracionGlobal rConfiguracionGlobal;


	public RConfiguracionGlobal getrConfiguracionGlobal() {
		return rConfiguracionGlobal;
	}


	public void setrConfiguracionGlobal(RConfiguracionGlobal rConfiguracionGlobal) {
		this.rConfiguracionGlobal = rConfiguracionGlobal;
	}


	public RConfiguracionGlobalAdapter(List <ConfiguracionGlobal> cg, List <Plugin> pg) {
		rConfiguracionGlobal  = new RConfiguracionGlobal();
		rConfiguracionGlobal.setPropiedades(toListaParametrosCG(cg));
		rConfiguracionGlobal.setPlugins(AdapterUtils.crearPlugins(pg));
	}


	  private static RListaParametros toListaParametrosCG(List <ConfiguracionGlobal> cg) {
	        final RListaParametros params = new RListaParametros();
	        params.setParametros(new ArrayList<>());
	        for (ConfiguracionGlobal c : cg) {
	            final RValorParametro p = new RValorParametro();
	            p.setCodigo(c.getCodigo()+"");
	            p.setValor(c.getValor());
	            params.getParametros().add(p);
	        }
	        return params;
	    }

}