package es.caib.sistramit.core.service.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.fundaciobit.plugins.validatesignature.api.IValidateSignaturePlugin;
import org.fundaciobit.plugins.validatesignature.api.SignatureDetailInfo;
import org.fundaciobit.plugins.validatesignature.api.SignatureRequestedInformation;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureRequest;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureResponse;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;
import org.fundaciobit.pluginsib.validatecertificate.InformacioCertificat;

public class TestPluginFirmaServidor {

	private final static String prefijoGlobal = "es.caib.sistra2.";
	private final static String prefijoPlugin = "afirmacxf.";

	public static void main(String[] args) throws Exception {

		final String classname = "org.fundaciobit.plugins.validatesignature.afirmacxf.AfirmaCxfValidateSignaturePlugin";

		final Properties prop = new Properties();
		/*
		 * prop.put(getBaseNameProperty("applicationID"), "generalitat.PTT_CLAVE");
		 * prop.put(getBaseNameProperty("TransformersTemplatesPath"),
		 * getPathResourceFromClasspath("validateSignature/transformersTemplates"));
		 * prop.put(getBaseNameProperty("endpoint"),
		 * "https://ulik2.accv.es:444/afirmaws/services/DSSAfirmaVerify");
		 * prop.put(getBaseNameProperty("printxml"), "true");
		 * prop.put(getBaseNameProperty("authorization.ks.path"),
		 * getPathResourceFromClasspath("validateSignature/keystoreClave.jks"));
		 * prop.put(getBaseNameProperty("authorization.ks.type"), "JKS");
		 * prop.put(getBaseNameProperty("authorization.ks.password"), "123456");
		 * prop.put(getBaseNameProperty("authorization.ks.cert.alias"), "ptt_pre");
		 * prop.put(getBaseNameProperty("authorization.ks.cert.password"), "3s1rca");
		 */

		prop.put(getBaseNameProperty("applicationID"), "CAIBDEV.SISTRA");
		prop.put(getBaseNameProperty("TransformersTemplatesPath"),
				getPathResourceFromClasspath("validateSignature/transformersTemplates"));
		prop.put(getBaseNameProperty("endpoint"), "https://afirmades.caib.es:4430/afirmaws/services/DSSAfirmaVerify");
		prop.put(getBaseNameProperty("printxml"), "true");
		prop.put(getBaseNameProperty("authorization.username"), "SISTRA");
		prop.put(getBaseNameProperty("authorization.password"), "SISTRA");

		System.out.println(new File(getPathResourceFromClasspath("validateSignature/transformersTemplates")).toURI().toURL());

		final IValidateSignaturePlugin plg = (IValidateSignaturePlugin) PluginsManager
				.instancePluginByClassName(classname, prefijoGlobal, prop);

		final ValidateSignatureRequest validateRequest = new ValidateSignatureRequest();
		validateRequest.setLanguage("es");
		// validateRequest.setSignedDocumentData(signedDocumentData);
		//validateRequest.setSignatureData(readResourceFromClasspath("validateSignature/firma.pdf"));
		validateRequest.setSignatureData(readResourceFromClasspath("validateSignature/Doc prueba_signed.pdf"));
		final SignatureRequestedInformation signatureRequestedInformation = new SignatureRequestedInformation();
		signatureRequestedInformation.setReturnCertificateInfo(true);
		signatureRequestedInformation.setReturnCertificates(true);
		signatureRequestedInformation.setReturnSignatureTypeFormatProfile(true);
		signatureRequestedInformation.setValidateCertificateRevocation(true);
		signatureRequestedInformation.setReturnValidationChecks(true);
		signatureRequestedInformation.setReturnTimeStampInfo(true);
		validateRequest.setSignatureRequestedInformation(signatureRequestedInformation);

		final ValidateSignatureResponse validateResponse = plg.validateSignature(validateRequest);

		final SignatureDetailInfo[] detalleFirmas = validateResponse.getSignatureDetailInfo();
		final SignatureDetailInfo detalleFirma = detalleFirmas[0];

		final InformacioCertificat datosCertificadoFirma = detalleFirma.getCertificateInfo();

		System.out.println("Clasificación del certificado: " + datosCertificadoFirma.getClassificacio());
		System.out.println("Clasificación EIDAS del certificado: " + datosCertificadoFirma.getClassificacioEidas());
		System.out.println("NIF del responsable: " + datosCertificadoFirma.getNifResponsable());
		System.out.println("Nombre del responsable: " + datosCertificadoFirma.getNomResponsable());
		System.out.println("Primer apellido del responsable: " + datosCertificadoFirma.getPrimerLlinatgeResponsable());
		System.out.println("Segundo apellido del responsable: " + datosCertificadoFirma.getSegonLlinatgeResponsable());


	}

	private static String getBaseNameProperty(String prop) {
		return prefijoGlobal + IValidateSignaturePlugin.VALIDATE_SIGNATURE_BASE_PROPERTY + prefijoPlugin + prop;
	}

	/**
	 * Lee recurso de classpath.
	 *
	 * @param filePath
	 *            path file
	 * @return contenido fichero
	 * @throws IOException
	 */
	private static byte[] readResourceFromClasspath(final String filePath) throws IOException {
		try (final InputStream isFile = TestPluginFirmaServidor.class.getClassLoader()
				.getResourceAsStream(filePath);) {
			final byte[] content = IOUtils.toByteArray(isFile);
			return content;
		}
	}

	/**
	 * Obtiene classpath.
	 *
	 * @param filePath
	 *            path file
	 * @return contenido fichero
	 * @throws IOException
	 */
	private static String getPathResourceFromClasspath(final String filePath) throws IOException {
		final URL r = TestPluginFirmaServidor.class.getClassLoader().getResource(filePath);
		return r.getPath();
	}

}
