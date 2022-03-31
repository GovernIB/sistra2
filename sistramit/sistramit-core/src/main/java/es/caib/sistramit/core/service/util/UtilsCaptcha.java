package es.caib.sistramit.core.service.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.github.cage.Cage;
import com.github.cage.GCage;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.formulario.Captcha;
import es.caib.sistramit.core.api.model.formulario.types.TypeCaptcha;
import es.caib.sistramit.core.service.util.mixer.Mixer;

public class UtilsCaptcha {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilsCaptcha.class);

	/** Letras que pueden formar parte del captcha. */
	private static final String[] LETRAS = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "R", "S", "U", "X",
			"1", "2", "3", "4", "5", "6", "7", "8", "9" };

	// Caracteres problematicos para sonido/visualizacion
	// "T","P","0"

	/**
	 * Genera key captha.
	 *
	 * @return key captha
	 */
	public static String generarKeyCaptcha() {
		final SecureRandom sr = new SecureRandom();
		final StringBuffer sb = new StringBuffer(ConstantesNumero.N10);
		for (int i = 0; i < ConstantesNumero.N4; i++) {
			final int numChar = sr.nextInt(LETRAS.length);
			sb.append(LETRAS[numChar]);
		}
		return sb.toString();
	}

	/**
	 * Genera captcha.
	 *
	 * @param valor
	 *                  Key captcha
	 * @param tipo
	 *                  Tipo
	 * @return captcha
	 */
	public static Captcha generaCaptcha(final String valor, final String idioma, final TypeCaptcha tipo) {
		Captcha captcha = null;
		switch (tipo) {
		case IMAGEN:
			captcha = generaCaptchaImagen(valor);
			break;
		case SONIDO:
			captcha = generaCaptchaSonido(valor, idioma);
			break;
		default:
			throw new TipoNoControladoException("Tipus captcha no suportat: " + tipo.name());
		}

		return captcha;
	}

	/**
	 * Crea imagen captcha.
	 *
	 * @param valor
	 *                   Key captcha
	 * @param idioma
	 *                   idioma
	 * @return captcha
	 */
	private static Captcha generaCaptchaSonido(final String valor, final String idioma) {
		// Genera audio
		byte[] audioContent = null;
		try {
			// Audios en formato WAV (16K / 16bit / mono)
			final List<AudioInputStream> audioInputStreams = new ArrayList<>();
			final String valorMinus = valor.toLowerCase();
			for (int i = 0; i < valor.length(); i++) {
				final String fileLoc = "/sounds/" + idioma + "/" + valorMinus.charAt(i) + ".wav";
				final File file = new ClassPathResource(fileLoc).getFile();
				final AudioInputStream clip = AudioSystem.getAudioInputStream(file);
				audioInputStreams.add(clip);
			}
			final File fileNoise = new ClassPathResource("/sounds/noise.wav").getFile();
			final AudioInputStream audioInputStreamNoise = AudioSystem.getAudioInputStream(fileNoise);
			audioContent = Mixer.mergeAudio(audioInputStreams, audioInputStreamNoise);
		} catch (final Exception e) {
			LOGGER.error("Error al generar captcha audio: " + valor, e);
		}
		// Retorna captcha
		final Captcha captcha = new Captcha(TypeCaptcha.SONIDO, audioContent);
		return captcha;
	}

	/**
	 * Crea sonido captcha.
	 *
	 * @param valor
	 *                  Key captcha
	 * @return captcha
	 */
	private static Captcha generaCaptchaImagen(final String valor) {
		byte[] datosFichero = null;
		final ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
		try {
			final Cage cage = new GCage();
			cage.draw(valor, bos);
			datosFichero = bos.toByteArray();
			bos.close();
		} catch (final Exception e) {
			LOGGER.error("Error al generar captcha imagen: " + valor, e);
		} finally {
			IOUtils.closeQuietly(bos);
		}
		final Captcha captcha = new Captcha(TypeCaptcha.IMAGEN, datosFichero);
		return captcha;
	}

}
