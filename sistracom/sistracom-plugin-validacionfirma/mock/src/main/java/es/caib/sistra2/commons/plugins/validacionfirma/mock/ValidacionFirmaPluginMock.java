package es.caib.sistra2.commons.plugins.validacionfirma.mock;

import java.util.Properties;

import org.fundaciobit.plugins.certificate.InformacioCertificat;
import org.fundaciobit.plugins.validatesignature.api.IValidateSignaturePlugin;
import org.fundaciobit.plugins.validatesignature.api.SignatureDetailInfo;
import org.fundaciobit.plugins.validatesignature.api.SignatureRequestedInformation;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureRequest;
import org.fundaciobit.plugins.validatesignature.api.ValidateSignatureResponse;
import org.fundaciobit.plugins.validatesignature.api.ValidationStatus;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

/**
 * Plugin mock de validacion de firma.
 *
 * @author Indra
 *
 */
public class ValidacionFirmaPluginMock extends AbstractPluginProperties implements IValidateSignaturePlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "mock.";

	public ValidacionFirmaPluginMock(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String filter(final ValidateSignatureRequest arg0) {
		return null;
	}

	@Override
	public SignatureRequestedInformation getSupportedSignatureRequestedInformation() {
		return null;
	}

	@Override
	public SignatureRequestedInformation getSupportedSignatureRequestedInformationBySignatureType(final String arg0) {
		return null;
	}

	@Override
	public ValidateSignatureResponse validateSignature(final ValidateSignatureRequest firmaAValidar) throws Exception {
		final ValidateSignatureResponse res = new ValidateSignatureResponse();
		final ValidationStatus estadoFirma = new ValidationStatus();
		final SignatureDetailInfo detalleFirma = new SignatureDetailInfo();
		final InformacioCertificat datosCertificadoFirma = new InformacioCertificat();
		final SignatureDetailInfo[] detalleFirmas = new SignatureDetailInfo[1];

		datosCertificadoFirma.setClassificacio(5);
		datosCertificadoFirma.setNifResponsable("00000000T");

		detalleFirma.setCertificateInfo(datosCertificadoFirma);
		detalleFirmas[0] = detalleFirma;

		estadoFirma.setStatus(ValidationStatus.SIGNATURE_VALID);
		res.setValidationStatus(estadoFirma);
		res.setSignatureDetailInfo(detalleFirmas);
		return res;
	}

}
