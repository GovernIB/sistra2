package es.caib.sistramit.core.service.test;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.fundaciobit.plugins.documentconverter.openoffice.OpenOfficeDocumentConverterPlugin;
import org.fundaciobit.pluginsib.documentconverter.IDocumentConverterPlugin;
import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;

public class TestPluginConverter {

    /** Configuracion. */
    @Autowired
    private static ConfiguracionComponent configuracionComponent;

    public static void main(String[] args) {

        final String entidad = "";

        final IDocumentConverterPlugin plgDC = new OpenOfficeDocumentConverterPlugin();
        try {

            final List<Object> objs = OpenOfficeDocumentConverterPlugin.DFR
                    .getDocumentFormats();

            final Map<String, String> mimeByExtension = new HashMap<String, String>();

            for (final Object object : objs) {

                final com.artofsolving.jodconverter.DocumentFormat df = (com.artofsolving.jodconverter.DocumentFormat) object;

                System.out.println(df.getFileExtension() + "\t"
                        + df.getFileExtension().toUpperCase() + "_"
                        + df.getName().toUpperCase().replace(' ', '-') + "\t"
                        + df.getMimeType());

                mimeByExtension.put(df.getFileExtension(), df.getMimeType());
            }

            System.setProperty(OpenOfficeDocumentConverterPlugin.HOST_PROPERTY,
                    "sdesooflin1.caib.es");
            System.setProperty(OpenOfficeDocumentConverterPlugin.PORT_PROPERTY,
                    "8100");
            try {

                for (int i = 0; i < 2; i++) {
                    final boolean isMime = (i == 0);
                    System.out
                            .println(" ==================================== ");

                    // File initialFile = new File("test.doc");
                    final InputStream inputData = Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream("test.doc");
                    InputStream inputData2 = new ByteArrayInputStream(
                            IOUtils.toByteArray(inputData));
                    final InputStream inputData3 = inputData2;

                    final String bytes = Base64.getEncoder()
                            .encodeToString(IOUtils.toByteArray(inputData3));
                    System.out.printf(":" + bytes + ":");

                    inputData2 = new ByteArrayInputStream(
                            Base64.getDecoder().decode(bytes));
                    // InputStream inputData =
                    // testPluginConverter.class.getResourceAsStream(file);
                    final String inputFileExtension = "doc";

                    System.out.println(
                            "Convertint test.txt(" + inputFileExtension + ")");

                    final String outputFileExtension = "pdf";

                    final FileOutputStream outputData = new FileOutputStream(
                            (isMime ? "mime_" : "ext_") + "test.doc."
                                    + outputFileExtension);

                    if (isMime) {
                        final String inputMime = mimeByExtension
                                .get(inputFileExtension);
                        final String outputMime = mimeByExtension
                                .get(outputFileExtension);
                        plgDC.convertDocumentByMime(inputData2, inputMime,
                                outputData, outputMime);
                    } else {
                        plgDC.convertDocumentByExtension(inputData2,
                                inputFileExtension, outputData,
                                outputFileExtension);
                    }
                    inputData.close();
                    outputData.flush();
                    outputData.close();

                }

            } catch (final Exception e) {
                e.printStackTrace();
            }

        } catch (final Exception e) {

        }

    }

}
