package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

@Component("calculoDatosFormularioHelper")
public final class CalculoDatosFormularioHelperImpl implements CalculoDatosFormularioHelper {

	@Override
	public ResultadoEvaluarCambioCampo calcularDatosPaginaCambioCampo(DatosSesionFormularioInterno datosSesion,
			String idCampo, List<ValorCampo> valoresPagina) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calcularCamposOcultosPagina(DatosSesionFormularioInterno datosSesion) {
		// TODO Auto-generated method stub

	}

}
