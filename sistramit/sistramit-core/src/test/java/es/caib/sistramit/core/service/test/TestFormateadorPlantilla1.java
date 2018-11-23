package es.caib.sistramit.core.service.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl.FormateadorPlantilla;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;

/**
 * Test de formateador a partir de plantilla con valor campo simple.
 *
 * @author Indra
 *
 */
public class TestFormateadorPlantilla1 {

	public static void main(final String args[]) throws Exception {

		/** El formulario interno no se usa para nada. **/
		final RFormularioInterno formularioInterno = new RFormularioInterno();

		/** Para generar el formulario. **/
		final XmlFormulario formulario = new XmlFormulario();
		final List<ValorCampo> pValores = new ArrayList<>();
		// Sección datos personales
		pValores.add(new ValorCampoSimple("SUJETOPASIVONIF", "123456789F"));
		pValores.add(new ValorCampoSimple("SUJETOPASIVONOMBRE", "Nombre ape1 ape2"));
		pValores.add(new ValorCampoSimple("MODELO", "Model x"));
		pValores.add(new ValorCampoSimple("CONCEPTO", "Concepto"));
		pValores.add(new ValorCampoSimple("IMPORTE", "123.63"));
		pValores.add(new ValorCampoSimple("FECHACREACION", new Date().toString()));
		pValores.add(new ValorCampoSimple("LOCALIZADOR", "Localizador"));
		pValores.add(new ValorCampoSimple("FECHAPAGO", new Date().toString()));
		formulario.setValores(pValores);

		final byte[] plantilla = IOUtils.toByteArray(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("formateador/template/plantillaFormateador1.pdf"));
		final FormateadorPlantilla formateador = new FormateadorPlantilla();
		final byte[] resultado = formateador.formatear(UtilsFormulario.valoresToXml(formulario), plantilla, "es",
				formularioInterno, "Tit. proced");
		final Path path = Paths.get("/formateadorTemplate1.pdf");
		Files.write(path, resultado);
	}

}
