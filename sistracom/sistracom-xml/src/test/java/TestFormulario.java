import java.io.ByteArrayOutputStream;

import org.apache.xmlbeans.XmlOptions;

import es.caib.sistra2.commons.xml.formulario.v1.model.CAMPO;
import es.caib.sistra2.commons.xml.formulario.v1.model.ELEMENTO;
import es.caib.sistra2.commons.xml.formulario.v1.model.FORMULARIO;
import es.caib.sistra2.commons.xml.formulario.v1.model.FORMULARIODocument;
import es.caib.sistra2.commons.xml.formulario.v1.model.VALOR;

public class TestFormulario {

	public static void main(final String[] args) throws Exception {

		final FORMULARIODocument fd = FORMULARIODocument.Factory.newInstance();

		final FORMULARIO form = fd.addNewFORMULARIO();

		// Campo simple
		final CAMPO c1 = form.addNewCAMPO();
		c1.setId("C1");
		final VALOR v1 = c1.addNewVALOR();
		v1.setStringValue("V1");

		// Campo indexado
		final CAMPO c2 = form.addNewCAMPO();
		c2.setId("C2");
		final VALOR v2 = c2.addNewVALOR();
		v2.setCodigo("C2");
		v2.setStringValue("D2");

		// Campo lista elementos
		final CAMPO c3 = form.addNewCAMPO();
		c3.setId("C3");
		final ELEMENTO e3 = c3.addNewELEMENTO();
		final CAMPO c3_1 = e3.addNewCAMPO();
		c3_1.setId("C3-1");
		final VALOR v3_1 = c3_1.addNewVALOR();
		v3_1.setStringValue("V3-1");

		byte[] xml = null;
		final XmlOptions xmlOptions = new XmlOptions();
		xmlOptions.setCharacterEncoding("UTF-8");
		xmlOptions.setSavePrettyPrint();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
		fd.save(baos, xmlOptions);
		xml = baos.toByteArray();
		baos.close();
		System.out.println("XML GENERADO");
		final String xmlStr = new String(xml, "UTF-8");
		System.out.println(xmlStr);
		System.out.println("-------------------------------");

		System.out.println("XML LEIDO");
		final FORMULARIODocument doc = FORMULARIODocument.Factory.parse(xmlStr);
		final ByteArrayOutputStream baos2 = new ByteArrayOutputStream(500);
		doc.save(baos2);
		System.out.println(new String(baos2.toByteArray(), "UTF-8"));
		baos2.close();

	}

}
