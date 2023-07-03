package es.caib.sistramit.core.service.test;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl.FormateadorGenerico;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.util.UtilsFormulario;

/**
 * Clase test para probar el formateador. Genera un pdf en la raiz del disco
 * duro.
 *
 * Como base deformulario interno utiliza un fichero de properties,
 * formularioInternoTest.json.
 *
 * @author Indra
 *
 */
public class TestFormateadorGenerico {

	public static void main(final String args[]) throws Exception {

		/** Para obtener el json. **/
		final InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("formateador/generico/formularioInternoTest.json");
		final StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, "UTF-8");
		final String formularioInternoJSON = writer.toString();
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final RFormularioInterno formularioInterno = mapper.readValue(formularioInternoJSON, RFormularioInterno.class);
		final List<String> paginasFormulario = new ArrayList<>();
		for (final RPaginaFormulario rpg : formularioInterno.getPaginas()) {
			paginasFormulario.add(rpg.getIdentificador());
		}

		/** Para generar el formulario. **/
		final XmlFormulario formulario = new XmlFormulario();
		final String VALENCIA = "Valencia";
		final List<ValorCampo> pValores = new ArrayList<>();
		// Sección datos personales
		pValores.add(new ValorCampoSimple("ID_1540200310032", "Pepe"));
		pValores.add(new ValorCampoSimple("ID_1540217970178", "López"));
		pValores.add(new ValorCampoSimple("ID_1540218013263", "Pérez"));
		pValores.add(new ValorCampoSimple("ID_FECHA", "2019-01-01"));

		// Sección Estudios universitarios
		final ValorCampoListaIndexados listaTitulos = new ValorCampoListaIndexados();
		listaTitulos.addValorIndexado("FP", "Formación profesional");
		listaTitulos.addValorIndexado("GS", "Grado superior");
		listaTitulos.setId("ID_1540219234562");
		pValores.add(listaTitulos);
		pValores.add(new ValorCampoSimple("ID_1540219396253", "Sí"));
		pValores.add(new ValorCampoSimple("ID_1538490286865", VALENCIA));
		pValores.add(new ValorCampoSimple("ID_1538490534185", "Univ. Politécnica de València"));
		pValores.add(new ValorCampoIndexado("ID_1538557979878", "ITIG", "Ingeniero tec. informática de gestión"));
		pValores.add(new ValorCampoSimple("ID_1540219071464", "C1 en Inglés, B2 en francés"));
		pValores.add(new ValorCampoSimple("ID_1540219110471", "Sí"));

		// Sección spam
		pValores.add(new ValorCampoSimple("ID_1540220994236", "inventado@indra.es"));
		pValores.add(new ValorCampoIndexado("ID_1540221034402", "ES", "Castellano"));
		pValores.add(new ValorCampoSimple("ID_1540221106317", "Sí"));

		// Sección dirección
		pValores.add(new ValorCampoIndexado("ID_1540218472145", "CA", "Calle"));
		pValores.add(new ValorCampoSimple("ID_1540218422622", "Calle colon"));
		pValores.add(new ValorCampoSimple("ID_1540218786901", "4"));
		pValores.add(new ValorCampoSimple("ID_1540218839803", "3 piso 2"));
		pValores.add(new ValorCampoSimple("ID_1540218425516", "46021"));
		pValores.add(new ValorCampoSimple("ID_1540218431563", VALENCIA));
		pValores.add(new ValorCampoSimple("ID_1540218803869", VALENCIA));
		pValores.add(new ValorCampoSimple("ID_1540218985749", "963858585"));
		pValores.add(new ValorCampoSimple("ID_1540219032190", "inventado.personal@indra.es"));

		formulario.setValores(pValores);

		final InputStream inputpropiedad = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("formateador/generico/fichero.properties");

		final byte[] plantilla = IOUtils.toByteArray(inputpropiedad);

		final Properties properties = new Properties();
		properties.load(inputpropiedad);

		final FormateadorGenerico formateador = new FormateadorGenerico();
		final byte[] resultado = formateador.formatear(UtilsFormulario.valoresToXml(formulario), paginasFormulario,
				plantilla, "es", formularioInterno, "Título del procedimiento", "Título del trámite", "1234", "1234");
		final Path path = Paths.get("/formateador.pdf");
		Files.write(path, resultado);
	}
}
