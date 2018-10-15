package es.caib.sistramit.core.service.test;

import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import org.fundaciobit.plugins.documentconverter.openoffice.OpenOfficeDocumentConverterPlugin;
import org.fundaciobit.pluginsib.documentconverter.IDocumentConverterPlugin;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testPluginConverter {

    /** Configuracion. */
    @Autowired
    private static ConfiguracionComponent configuracionComponent;


    public static void main(String[] args) {

        String entidad = "";

        final IDocumentConverterPlugin plgDC = new OpenOfficeDocumentConverterPlugin();
        try {

            List<Object> objs = OpenOfficeDocumentConverterPlugin.DFR.getDocumentFormats();

            Map<String, String> mimeByExtension = new HashMap<String, String>();

            for (Object object : objs) {

                com.artofsolving.jodconverter.DocumentFormat df = (com.artofsolving.jodconverter.DocumentFormat) object;

                System.out.println(df.getFileExtension() + "\t" + df.getFileExtension().toUpperCase()
                        + "_" + df.getName().toUpperCase().replace(' ', '-') + "\t" + df.getMimeType());

                mimeByExtension.put(df.getFileExtension(), df.getMimeType());
            }

            System.setProperty(OpenOfficeDocumentConverterPlugin.HOST_PROPERTY, "sdesooflin1.caib.es");

            System.setProperty(OpenOfficeDocumentConverterPlugin.PORT_PROPERTY, "8100");
            try {



                for (int i = 0; i < 2; i++) {
                    boolean isMime = (i == 0);
                    System.out.println(" ==================================== ");


                        //File initialFile = new File("test.doc");
                        InputStream inputData =  Thread.currentThread().getContextClassLoader().getResourceAsStream("test.txt");

                        //InputStream inputData = testPluginConverter.class.getResourceAsStream(file);
                        String inputFileExtension = "txt";

                        System.out.println("Convertint test.txt(" + inputFileExtension + ")");

                        final String outputFileExtension = "pdf";

                        FileOutputStream outputData = new FileOutputStream((isMime? "mime_" : "ext_") +  "test.txt." + outputFileExtension);

                        if (isMime) {
                            String inputMime = mimeByExtension.get(inputFileExtension);
                            String outputMime = mimeByExtension.get(outputFileExtension);
                            plgDC.convertDocumentByMime(inputData, inputMime , outputData,
                                    outputMime);
                        } else {
                            plgDC.convertDocumentByExtension(inputData, inputFileExtension, outputData,
                                    outputFileExtension);
                        }
                        inputData.close();
                        outputData.flush();
                        outputData.close();

                    }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (final Exception e) {

        }


    }

    private static IDocumentConverterPlugin getPlugin(final String idEntidad) {
        if(configuracionComponent==null){
            return (IDocumentConverterPlugin) configuracionComponent
                    .obtenerPluginEntidad(TypePluginEntidad.DOCUMENT_CONVERTER, idEntidad);

        }
        else return null;
    }
}
