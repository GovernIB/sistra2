package es.caib.sistramit.core.service.test;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;

import es.caib.sistramit.core.api.model.formulario.Captcha;
import es.caib.sistramit.core.api.model.formulario.types.TypeCaptcha;
import es.caib.sistramit.core.service.util.UtilsCaptcha;

public class TestCaptcha {

	public static void main(final String[] args) throws Exception {

		final String captchaValor = "7ACX";
		final String idioma = "ca";
		Captcha c = null;

		// Sonido
		c = UtilsCaptcha.generaCaptcha(captchaValor, idioma, TypeCaptcha.SONIDO);
		IOUtils.copy(new ByteArrayInputStream(c.getContenido()), new FileOutputStream("P:/temp/captcha.wav"));

		// Sonido
		c = UtilsCaptcha.generaCaptcha(captchaValor, idioma, TypeCaptcha.IMAGEN);
		IOUtils.copy(new ByteArrayInputStream(c.getContenido()), new FileOutputStream("/temp/captcha.png"));

	}

}
