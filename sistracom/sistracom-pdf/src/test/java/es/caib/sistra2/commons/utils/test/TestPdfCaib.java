/**
 * Copyright (c) 2020 Generalitat Valenciana - Todos los derechos reservados.
 */
package es.caib.sistra2.commons.utils.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.pdf.ImageStamp;
import es.caib.sistra2.commons.pdf.ObjectStamp;
import es.caib.sistra2.commons.pdf.UtilPDF;
import es.caib.sistra2.commons.pdfcaib.GeneradorPdf;
import es.caib.sistra2.commons.pdfcaib.model.Cabecera;
import es.caib.sistra2.commons.pdfcaib.model.CampoTexto;
import es.caib.sistra2.commons.pdfcaib.model.FormularioPdf;
import es.caib.sistra2.commons.pdfcaib.model.Linea;
import es.caib.sistra2.commons.pdfcaib.model.PersonalizacionTexto;
import es.caib.sistra2.commons.pdfcaib.model.Pie;
import es.caib.sistra2.commons.pdfcaib.model.Seccion;
import es.caib.sistra2.commons.pdfcaib.model.Texto;
import es.caib.sistra2.commons.pdfcaib.types.TypeFuente;

/**
 * @author amontoyah at http://www.organizacion.web[ORGANIZACION]
 *
 */
public class TestPdfCaib {

	public static void main(final String[] args) throws Exception {
		final FormularioPdf formularioPdf = new FormularioPdf();
		final List<Linea> lineas = new ArrayList<Linea>();
		final Logger log = LoggerFactory.getLogger(TestPdfCaib.class);

		final Cabecera cabecera = new Cabecera();
		cabecera.setTitulo("Solicitud de acreditaciones de entidades especializadas \r\n"
		/* + "PARA SERVICIOS DE PREVENCIÓN AJENOS A LAS EMPRESAS" */);
		final PersonalizacionTexto personalizacicionTextoTituloCabecera = new PersonalizacionTexto(true, true,
				TypeFuente.NOTOSANS, 20);
		final PersonalizacionTexto personalizacicionTextoSubTituloCabecera = new PersonalizacionTexto(true, true,
				TypeFuente.NOTOSANS, 16);

		final PersonalizacionTexto personalizacicionTextoCodigoSia = new PersonalizacionTexto(true, true,
				TypeFuente.NOTOSANS, 12);
		cabecera.setSubtitulo("para servicios de prevencion ajenos a las empresas");
		cabecera.setCodigoSia("2212");
		cabecera.setPersonalizacionCodigoSiaTitulo(personalizacicionTextoCodigoSia);
		cabecera.setPersonalizacionTextoTitulo(personalizacicionTextoTituloCabecera);
		cabecera.setPersonalizacionTextoSubtitulo(personalizacicionTextoSubTituloCabecera);
		cabecera.setAltoLogo(140);
		cabecera.setAnchoLogo(140);

		/** Ejemplo logo cargando directamente la imagen */
		// cabecera.setLogo(
		// "G:\\workspace\\trunk-simac-lib-pdjf\\fuentes\\java\\src\\main\\resources\\logo.png");

		/** Ejemplo logo cargado como array de bytes */
		final byte[] arrayBytes = IOUtils.toByteArray(TestPdfCaib.class.getResourceAsStream("/logocaib.jpg"));
		cabecera.setLogoByte(arrayBytes);
		formularioPdf.setCabecera(cabecera);

		final PersonalizacionTexto personalizacicionTextoPie = new PersonalizacionTexto(true, true, TypeFuente.NOTOSANS,
				10);
		final Pie pie = new Pie("adios-pie", personalizacicionTextoPie);
		formularioPdf.setPie(pie);

		Linea linea = new Linea();
		Seccion seccion = new Seccion("", "Datos de la persona solicitante");
		linea.getObjetosLinea().add(seccion);
		lineas.add(linea);
		linea = new Linea();
		CampoTexto campoTexto1 = new CampoTexto(6, true, "Apellidos y nombre o razón social",
				"Mrtinez\n Sanchez\n Pepe");
		CampoTexto campoTexto2 = new CampoTexto(6, false, "NIE", "0000000T");
		CampoTexto campoTexto3 = new CampoTexto(6, false, "Código cuenta coticación SS", "ES02 0000 0000 0000 0000");
		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto2);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto3);

		lineas.add(linea);

		linea = new Linea();
		campoTexto1 = new CampoTexto(6, false, "Domicilio (Calle/Plaza,Número y puerta)", "Calle tal numero 12 5ºD");
		campoTexto2 = new CampoTexto(6, false, "CP", "30752");
		campoTexto3 = new CampoTexto(6, false, "Localidad", "Murcia");
		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto2);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto3);
		lineas.add(linea);

		linea = new Linea();
		campoTexto1 = new CampoTexto(6, false, "Provincia", "Asturias");
		campoTexto2 = new CampoTexto(6, false, "Teléfono", "654123123");
		campoTexto3 = new CampoTexto(6, false, "Fax", "654123123");
		CampoTexto campoTexto4 = new CampoTexto(6, false, "Correo Electrónico", "123@123.es");

		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto2);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto3);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto4);
		lineas.add(linea);

		linea = new Linea();
		seccion = new Seccion("", "Datos de la persona representante");
		linea.getObjetosLinea().add(seccion);
		lineas.add(linea);
		linea = new Linea();
		campoTexto1 = new CampoTexto(6, false, "Apellidos", "Mrtinez Sanchez");
		campoTexto2 = new CampoTexto(6, false, "Nombre", "Pepe");
		campoTexto3 = new CampoTexto(6, false, "NIE", "58847821S");
		campoTexto4 = new CampoTexto(6, false, "Teléfono", "654123123");
		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto2);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto3);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto4);
		lineas.add(linea);

		linea = new Linea();
		campoTexto1 = new CampoTexto(6, false, "Correo Electrónico ", "asd@asd.com");
		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);

		linea = new Linea();
		seccion = new Seccion("", "Notificaciones");
		linea.getObjetosLinea().add(seccion);
		lineas.add(linea);

		linea = new Linea();
		campoTexto1 = new CampoTexto(6, false, "Domicilio (Calle/Plaza,Número y puerta)", "Calle del olvido Nº11 5ºD");
		campoTexto2 = new CampoTexto(6, false, "CP", "30007");

		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto2);
		lineas.add(linea);

		linea = new Linea();
		campoTexto1 = new CampoTexto(6, false, "Localidad", "MurciaMurciaMurciaMurciaMur\nciaMurciaMurciaMurciaMurcia");
		campoTexto2 = new CampoTexto(6, false, "Provincia", "Murcia");
		campoTexto3 = new CampoTexto(6, false, "Teléfono", "654123123");
		campoTexto4 = new CampoTexto(6, false, "Fax", "654123123");

		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto2);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto3);
		lineas.add(linea);
		linea = new Linea();
		linea.getObjetosLinea().add(campoTexto4);
		lineas.add(linea);

		linea = new Linea();
		campoTexto1 = new CampoTexto(6, false, "Correo Electrónico ", "asd@asd.com");
		linea.getObjetosLinea().add(campoTexto1);
		lineas.add(linea);

		linea = new Linea();
		seccion = new Seccion("D", "Consulta interactiva de documentación / no autorización");
		linea.getObjetosLinea().add(seccion);
		lineas.add(linea);

		linea = new Linea();
		final PersonalizacionTexto personalizacicionTexto = new PersonalizacionTexto(false, true, TypeFuente.NOTOSANS,
				6);
		final Texto texto = new Texto(personalizacicionTexto,
				"De acuerdo con lo que dispuesto en el artículo 28 de la Ley 39/2015, de 1 de octubre, del Procedmiento Administrativo Común de las\r\n"
						+ "Administraciones Públicas, en ausencia de oposición expresa por parte del interesado, el órgano gestor del procedimiento estará autorizado para\r\n"
						+ "obtener directamente los datos de los documentos elaborados por cualquier administración y que para este procedimiento son los señalados a\r\n"
						+ "continuación: datos de identidad del titular, y en su caso, del representante legal.\r\n"
						+ "En caso de no autorizarlo, deberá marcar la casilla, indicando los datos que no autorice y deberá aportar los documentos correspondientes.",
				6);
		linea.getObjetosLinea().add(texto);
		lineas.add(linea);

		formularioPdf.setLineas(lineas);
		final GeneradorPdf generadorPdf = new GeneradorPdf();
		byte[] pdf;
		final String path = new File(".").getCanonicalPath();
		final String FILE_NAME = path + "/target/itext-test-file.pdf";
		try {
			pdf = generadorPdf.generarPdf(formularioPdf);
			System.out.println(FILE_NAME);
			final OutputStream out = new FileOutputStream(FILE_NAME);
			out.write(pdf);
			out.close();
		} catch (final FileNotFoundException e) {
			log.error("Error al crear el pdf");
		} catch (final IOException e) {
			log.error("Error al crear el pdf");
		}

		// Stamp marca agua
		final String FILE_NAME_STAMP = path + "/target/itext-test-file-stamp.pdf";
		final OutputStream out2 = new FileOutputStream(FILE_NAME_STAMP);
		final InputStream in2 = new FileInputStream(FILE_NAME);
		final ImageStamp imgStamp = new ImageStamp();
		imgStamp.setImagen(IOUtils.toByteArray(TestPdfCaib.class.getResourceAsStream("/goib-marca-agua.png")));
		imgStamp.setOverContent(false);
		imgStamp.setScalePerCent(true);
		imgStamp.setXScale((float) 50);
		imgStamp.setYScale((float) 50);
		imgStamp.setX(90);
		imgStamp.setY(70);
		final ObjectStamp[] objects = new ObjectStamp[1];
		objects[0] = imgStamp;
		UtilPDF.stamp(out2, in2, objects);
		System.out.println(FILE_NAME_STAMP);

	}
}
