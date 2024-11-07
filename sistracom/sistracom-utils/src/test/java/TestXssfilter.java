import es.caib.sistra2.commons.utils.XssFilter;

public class TestXssfilter {

    public static void main(final String args[]) {


        String nombreFichero = "fichero con´ y` y ' CON . Y . .txt";

        System.out.println(nombreFichero);

        System.out.println(XssFilter.normalizarFilename(nombreFichero));

    }

}
