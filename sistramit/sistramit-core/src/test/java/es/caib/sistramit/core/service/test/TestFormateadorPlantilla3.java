package es.caib.sistramit.core.service.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl.FormateadorPlantilla;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.util.UtilsFormulario;

/**
 * Test de formateador para plantilla con todo tipo de valores.
 *
 * @author Indra
 *
 */
public class TestFormateadorPlantilla3 {

	public static void main(final String args[]) throws Exception {

		/** Para obtener el json. **/
		final RFormularioInterno formularioInterno = new RFormularioInterno();

		/** Para generar el formulario. **/
		final XmlFormulario formulario = new XmlFormulario();
		final List<ValorCampo> pValores = new ArrayList<>();
		pValores.add(new ValorCampoIndexado("CAMPO1", "CMP1", "Descripcion en el campo1"));
		final ValorCampoListaIndexados listaModelos = new ValorCampoListaIndexados();
		listaModelos.addValorIndexado("CMP2MDL1", "Descripción modelo 1");
		listaModelos.addValorIndexado("CMP2MDL2", "Descripcion modelo 2");
		listaModelos.setId("CAMPO2");
		pValores.add(listaModelos);

		formulario.setValores(pValores);

		final byte[] plantilla = IOUtils.toByteArray(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("formateador/template/plantillaFormateador3.pdf"));
		final FormateadorPlantilla formateador = new FormateadorPlantilla();
		final byte[] resultado = formateador.formatear(UtilsFormulario.valoresToXml(formulario), null, plantilla, "es",
				formularioInterno, "Tit. proced", "Título del trámite", "1234", "1234");
		final Path path = Paths.get("/formateadorTemplate3.pdf");
		Files.write(path, resultado);
	}

}
