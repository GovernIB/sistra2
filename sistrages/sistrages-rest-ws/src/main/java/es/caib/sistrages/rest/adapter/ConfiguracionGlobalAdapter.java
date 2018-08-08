package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.utils.AdapterUtils;
/**
 * Adapter para convertir a modelo rest.
 *
 * @author Indra
 *
 */

@Component
public class ConfiguracionGlobalAdapter {
 
    /**
     * Conversion a modelo rest.
     * 
     * @param cg lista de configuracion global
     * @param pg lista de Plugins
     * @return configuracion global
     */
    public RConfiguracionGlobal convertir(List<ConfiguracionGlobal> cg, List<Plugin> pg) {    	
        RConfiguracionGlobal rConfiguracionGlobal = new RConfiguracionGlobal();
        rConfiguracionGlobal.setTimestamp(System.currentTimeMillis() + "");
        rConfiguracionGlobal.setPropiedades(toListaParametrosCG(cg));
        rConfiguracionGlobal.setPlugins(AdapterUtils.crearPlugins(pg));
		return rConfiguracionGlobal;
    }

    /** 
     * Conversion a modelo rest RListaParametros
     * @param cg lista de configuracion global
     * @return la lista de parametros
     */
    private static RListaParametros toListaParametrosCG( List<ConfiguracionGlobal> cg) {
    	 
    	RListaParametros params  = null;  	
    	if(cg!=null) {    	
	        params = new RListaParametros();
	        params.setParametros(new ArrayList<>());
	        for (final ConfiguracionGlobal c : cg) {
	            final RValorParametro p = new RValorParametro();
	            p.setCodigo(c.getPropiedad());
	            p.setValor(c.getValor());
	            params.getParametros().add(p);
	        }
        }
        return params;
    }

}