package es.caib.sistra2.commons.utils.test;

import es.caib.sistra2.commons.pdf.UtilPDF;
import org.apache.commons.io.IOUtils;

public class TestUtilPdf {

    public static void main(String[] args) throws Exception {

        verificarAdjuntos();

    }

    private static void verificarAdjuntos() throws Exception {
        String[] files = {"PDF-CON-ANEXOS-A.pdf", "PDF-CON-ANEXOS-B.pdf", "VACIO_signed.PDF"};
        for (String f : files) {
            final byte[] arrayBytes = IOUtils.toByteArray(TestPdfCaib.class.getResourceAsStream("/" + f));
            System.out.println(f + " tiene adjuntos? " + UtilPDF.tieneAdjuntos(arrayBytes));
        }
    }
}
