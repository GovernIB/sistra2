package es.caib.sistramit.core.service.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.fundaciobit.pluginsib.core.utils.PluginsManager;

public class TestPluginFirmaServidor {

	private final static String prefijoGlobal = "es.caib.sistra2.";
	private final static String prefijoPlugin = "afirmacxf.";

	public static void main(String[] args) throws Exception {

		final String classname = "org.fundaciobit.plugins.validatesignature.afirmacxf.AfirmaCxfValidateSignaturePlugin";

		final Properties prop = new Properties();
		prop.put(getBaseNameProperty("applicationID"), "generalitat.PTT_CLAVE");
		prop.put(getBaseNameProperty("TransformersTemplatesPath"),
				getPathResourceFromClasspath("validateSignature/transformersTemplates"));
		prop.put(getBaseNameProperty("endpoint"), "https://ulik2.accv.es:444/afirmaws/services/DSSAfirmaVerify");
		prop.put(getBaseNameProperty("printxml"), "true");
		prop.put(getBaseNameProperty("authorization.ks.path"),
				getPathResourceFromClasspath("validateSignature/keystoreClave.jks"));
		prop.put(getBaseNameProperty("authorization.ks.type"), "JKS");
		prop.put(getBaseNameProperty("authorization.ks.password"), "123456");
		prop.put(getBaseNameProperty("authorization.ks.cert.alias"), "ptt_pre");
		prop.put(getBaseNameProperty("authorization.ks.cert.password"), "3s1rca");

		/*
		final IValidateSignaturePlugin plg = (IValidateSignaturePlugin) PluginsManager
				.instancePluginByClassName(classname, prefijoGlobal, prop);

		final ValidateSignatureRequest validateRequest = new ValidateSignatureRequest();
		validateRequest.setLanguage("es");
		// validateRequest.setSignedDocumentData(signedDocumentData);
		validateRequest.setSignatureData(readResourceFromClasspath("validateSignature/firma.pdf"));
		final SignatureRequestedInformation signatureRequestedInformation = new SignatureRequestedInformation();
		signatureRequestedInformation.setReturnCertificateInfo(true);
		signatureRequestedInformation.setReturnCertificates(true);
		signatureRequestedInformation.setReturnSignatureTypeFormatProfile(true);
		signatureRequestedInformation.setValidateCertificateRevocation(true);
		signatureRequestedInformation.setReturnValidationChecks(true);
		signatureRequestedInformation.setReturnTimeStampInfo(true);
		validateRequest.setSignatureRequestedInformation(signatureRequestedInformation);

		final ValidateSignatureResponse validateResponse = plg.validateSignature(validateRequest);

		System.out.println("Status: " + validateResponse.getValidationStatus().getStatus());
		*/

	}

	private static String getBaseNameProperty(String prop) {
		return "";
		//return prefijoGlobal + IValidateSignaturePlugin.VALIDATE_SIGNATURE_BASE_PROPERTY + prefijoPlugin + prop;
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
		try (final InputStream isFile = TestPluginFirmaServidor.class.getClassLoader().getResourceAsStream(filePath);) {
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
