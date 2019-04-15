import es.caib.sistra2.commons.utils.NifUtils;

/**
 * Prueba de pago.
 *
 * @author Indra
 *
 */
public class TestNifUtils {

	/**
	 * Pruebas del nif utils
	 *
	 * @param args
	 */
	public static void main(final String args[]) {

		System.out.println(" *** COMPROBACIONES DEL NIF UTILS ***");
		System.out.println(" ");
		System.out.println(" ");

		final String dniCorrecto = "13666048T";
		final String dniIncorrecto = "60251273D";
		System.out.println("DNICorrecto . EsDNI:" + NifUtils.esDni(dniCorrecto));
		System.out.println("DNIInorrecto. EsDNI:" + NifUtils.esDni(dniIncorrecto));

		System.out.println(" ");
		System.out.println(" ");

		final String nieCorrecto = "X7758125H";
		final String nieIncorrecto = "Z6998585R";
		System.out.println("NIECorrecto . EsNIE:" + NifUtils.esNie(nieCorrecto));
		System.out.println("NIEInorrecto. EsNIE:" + NifUtils.esNie(nieIncorrecto));

		System.out.println(" ");
		System.out.println(" ");

		final String nifOtrosCorrecto = "K2481154Y";
		final String nifOtrosIncorrecto = "J2411154Y";
		System.out.println("NIFOtrosCorrecto . EsNIFOtros:" + NifUtils.esNifOtros(nifOtrosCorrecto));
		System.out.println("NIFOtrosInorrecto. EsNIFOtros:" + NifUtils.esNifOtros(nifOtrosIncorrecto));

		System.out.println(" ");
		System.out.println(" ");

		final String nifCorrectoA = "A80298839";
		final String nifCorrectoB = "B83029355";
		final String nifCorrectoC = "C12249728";
		final String nifCorrectoD = "D64092182";
		final String nifCorrectoE = "E49563448";
		final String nifCorrectoF = "F7706910B";
		final String nifCorrectoG = "G87241535";
		final String nifCorrectoH = "H44088706";
		final String nifCorrectoJ = "J61934394";
		final String nifCorrectoN = "N2991987E";
		final String nifCorrectoP = "P9175204H";
		final String nifCorrectoQ = "Q8493806G";
		final String nifCorrectoR = "R0049325D";
		final String nifCorrectoS = "S5011825F";
		final String nifCorrectoU = "U65045593";
		final String nifCorrectoV = "V3950695A";
		final String nifCorrectoW = "W7567692D";

		final String nifIncorrecto = "A76381906";
		System.out.println("NIFCorrectoA . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoA));
		System.out.println("NIFCorrectoB . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoB));
		System.out.println("NIFCorrectoC . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoC));
		System.out.println("NIFCorrectoD . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoD));
		System.out.println("NIFCorrectoE . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoE));
		System.out.println("NIFCorrectoF . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoF));
		System.out.println("NIFCorrectoG . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoG));
		System.out.println("NIFCorrectoH . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoH));
		System.out.println("NIFCorrectoJ . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoJ));
		System.out.println("NIFCorrectoN . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoN));
		System.out.println("NIFCorrectoP . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoP));
		System.out.println("NIFCorrectoQ . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoQ));
		System.out.println("NIFCorrectoR . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoR));
		System.out.println("NIFCorrectoS . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoS));
		System.out.println("NIFCorrectoU . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoU));
		System.out.println("NIFCorrectoV . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoV));
		System.out.println("NIFCorrectoW . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifCorrectoW));
		System.out.println("NIFInorrecto . EsNifPersonaJuridica:" + NifUtils.esNifPersonaJuridica(nifIncorrecto));

		System.out.println(" ");
		System.out.println(" ");

		final String nssCorrecto = "A76281906";
		final String nssIncorrecto = "D75226779";
		System.out.println("NSSCorrecto . EsNSS:" + NifUtils.esNSS(nssCorrecto));
		System.out.println("NSSInorrecto. EsNSS:" + NifUtils.esNSS(nssIncorrecto));

	}
}
