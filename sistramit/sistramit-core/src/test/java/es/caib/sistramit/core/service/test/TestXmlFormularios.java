package es.caib.sistramit.core.service.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaElementos;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorElemento;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import junit.framework.TestCase;

/**
 * Test XML Formularios.
 *
 * @author Indra
 *
 */
public class TestXmlFormularios extends TestCase {

	/**
	 * Logger available to subclasses.
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@Test
	public void testXml() throws Exception {

		final ValorCampoSimple valorCampoSimple = new ValorCampoSimple("C_SIMPLE", "VALOR SIMPLE");
		final ValorCampoIndexado valorCampoIndexado = new ValorCampoIndexado("C_INDEXADO", "1", "VALOR 1");
		final ValorCampoListaIndexados valorCampoListaIndexados = new ValorCampoListaIndexados();
		valorCampoListaIndexados.setId("C_MULTI");
		valorCampoListaIndexados.addValorIndexado("A", "VALOR A");
		valorCampoListaIndexados.addValorIndexado("B", "VALOR B");
		final ValorCampoListaElementos valorCampoListaElementos = new ValorCampoListaElementos();
		valorCampoListaElementos.setId("E_LISTA");
		final ValorElemento elemento = new ValorElemento();
		final List<ValorCampo> valoresElemento = new ArrayList<>();
		valoresElemento.add(new ValorCampoSimple("E_SIMPLE", "VALOR SIMPLE E"));
		valoresElemento.add(new ValorCampoIndexado("E_INDEXADO", "E", "VALOR E"));
		final ValorCampoListaIndexados valorCampoListaIndexadosE = new ValorCampoListaIndexados();
		valorCampoListaIndexadosE.setId("E_MULTI");
		valorCampoListaIndexadosE.addValorIndexado("E1", "VALOR E1");
		valorCampoListaIndexadosE.addValorIndexado("E2", "VALOR E2");
		valoresElemento.add(valorCampoListaIndexadosE);
		elemento.setElemento(valoresElemento);
		valorCampoListaElementos.addElemento(elemento);

		final List<ValorCampo> valores = new ArrayList<>();
		valores.add(valorCampoSimple);
		valores.add(valorCampoIndexado);
		valores.add(valorCampoListaIndexados);
		valores.add(valorCampoListaElementos);

		final XmlFormulario form = new XmlFormulario(valores, null);
		final byte[] xml = UtilsFormulario.valoresToXml(form);

		final String xmlAntes = new String(xml, "UTF-8");

		logger.debug("XML ANTES: " + xmlAntes);

		final XmlFormulario formNew = UtilsFormulario.xmlToValores(xml);
		final byte[] xmlNew = UtilsFormulario.valoresToXml(form);

		final String xmlDespues = new String(xmlNew, "UTF-8");
		logger.debug("XML DESPUES: " + xmlDespues);

		// Xml generado es el mismo
		assertEquals(xmlAntes, xmlDespues);

		// Coinciden los valores de campo
		for (int i = 0; i < form.getValores().size(); i++) {
			assertEquals(true, form.getValores().get(i).esValorIgual(formNew.getValores().get(i)));
		}

	}

}
