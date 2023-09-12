package es.caib.sistra2.commons.plugins.email.api;

import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.InitialContext;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

public class EmailSmtpPlugin extends AbstractPluginProperties implements IEmailPlugin {

	/** Encoding utilizado en la generación de XML */
	public static final String ENCODING = "UTF-8";

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "email.";

	/** Constructor. **/
	public EmailSmtpPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	public boolean envioEmail(final List<String> destinatarios, final String asunto, final String mensaje,
			final List<AnexoEmail> anexos) throws EmailPluginException {

		try {
			final InitialContext jndiContext = new InitialContext();
			final Session mailSession = (Session) jndiContext.lookup("java:/" + getPropiedad("jndi"));

			final MimeMessage msg = new MimeMessage(mailSession);

			final InternetAddress[] direcciones = new InternetAddress[destinatarios.size()];
			for (int i = 0; i < destinatarios.size(); i++) {
				final InternetAddress direccion = new InternetAddress();
				direccion.setAddress(destinatarios.get(i));
				direcciones[i] = direccion;
			}
			msg.setRecipients(javax.mail.Message.RecipientType.TO, direcciones);

			msg.setSubject(asunto);

			String contenido;
			if (isHtml(mensaje)) {
				contenido = new String(mensaje.getBytes(), ENCODING);
			} else {
				contenido = StringEscapeUtils.escapeHtml4(new String(mensaje.getBytes(), ENCODING));
			}

			msg.setHeader("X-Mailer", "JavaMailer");
			String mailFrom = null;
			if (mailSession.getProperty("mail.from") != null) {
				mailFrom = mailSession.getProperty("mail.from");
			} else if (mailSession.getProperty("mail.smtp.user") != null) {
				mailFrom = mailSession.getProperty("mail.smtp.user");
			} else {
				throw new EmailPluginException("Error, mail from no especificado");
			}
			msg.setFrom(new InternetAddress(mailFrom));

			if (anexos != null && !anexos.isEmpty()) {
				// Envio con anexos
				final Multipart multipart = new MimeMultipart("mixed");
				// Mensaje
				final MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(contenido, "text/html; charset=utf-8");
				multipart.addBodyPart(textPart);
				// Anexos
				for (final AnexoEmail a : anexos) {
					final DataSource source = new ByteArrayDataSource(a.getContent(), a.getContentType());
					final MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(a.getFileName());
					multipart.addBodyPart(messageBodyPart);
				}
				// Mensaje + Anexos
				msg.setContent(multipart);
			} else {
				// Envio sin anexos
				msg.setContent(contenido, "text/html; charset=utf-8");
			}

			Transport.send(msg);

		} catch (final Exception e) {
			throw new EmailPluginException("Error enviando. MSG:" + ExceptionUtils.getMessage(e), e);
		}
		return true;

	}

	public boolean envioEmail(final List<String> destinatarios, final String asunto, final String mensaje,
			final List<AnexoEmail> anexos, String base64Image) throws EmailPluginException {

		try {
			final InitialContext jndiContext = new InitialContext();
			final Session mailSession = (Session) jndiContext.lookup("java:/" + getPropiedad("jndi"));

			final MimeMessage msg = new MimeMessage(mailSession);

			final InternetAddress[] direcciones = new InternetAddress[destinatarios.size()];
			for (int i = 0; i < destinatarios.size(); i++) {
				final InternetAddress direccion = new InternetAddress();
				direccion.setAddress(destinatarios.get(i));
				direcciones[i] = direccion;
			}
			msg.setRecipients(javax.mail.Message.RecipientType.TO, direcciones);

			msg.setSubject(asunto);

			String contenido;
			if (isHtml(mensaje)) {
				contenido = new String(mensaje.getBytes(), ENCODING);
			} else {
				contenido = StringEscapeUtils.escapeHtml4(new String(mensaje.getBytes(), ENCODING));
			}

			msg.setHeader("X-Mailer", "JavaMailer");
			String mailFrom = null;
			if (mailSession.getProperty("mail.from") != null) {
				mailFrom = mailSession.getProperty("mail.from");
			} else if (mailSession.getProperty("mail.smtp.user") != null) {
				mailFrom = mailSession.getProperty("mail.smtp.user");
			} else {
				throw new EmailPluginException("Error, mail from no especificado");
			}
			msg.setFrom(new InternetAddress(mailFrom));

			if (anexos != null && !anexos.isEmpty()) {
				// Envio con anexos
				final Multipart multipart = new MimeMultipart("mixed");
				// Mensaje
				final MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(contenido, "text/html; charset=utf-8");
				multipart.addBodyPart(textPart);
				// Anexos
				for (final AnexoEmail a : anexos) {
					final DataSource source = new ByteArrayDataSource(a.getContent(), a.getContentType());
					final MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(a.getFileName());
					multipart.addBodyPart(messageBodyPart);
				}

				byte[] rawImage = Base64.getDecoder().decode(base64Image);
		        BodyPart imagePart = new MimeBodyPart();
		        ByteArrayDataSource imageDataSource = new ByteArrayDataSource(rawImage,"image/png");

		        imagePart.setDataHandler(new DataHandler(imageDataSource));
		        imagePart.setHeader("Content-ID", "<logo>");
		        imagePart.setFileName("logo.png");
				// Mensaje + Anexos
				msg.setContent(multipart);
			} else {
				// Envio sin anexos

				final Multipart multipart = new MimeMultipart("mixed");
				// create a new imagePart and add it to multipart so that the image is inline attached in the email
		        byte[] rawImage = Base64.getDecoder().decode(base64Image);
		        BodyPart imagePart = new MimeBodyPart();
		        ByteArrayDataSource imageDataSource = new ByteArrayDataSource(rawImage,"image/png");

		        imagePart.setDataHandler(new DataHandler(imageDataSource));
		        imagePart.setHeader("Content-ID", "<logo>");
		        imagePart.setFileName("logo.png");

				// Mensaje
				final MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(contenido, "text/html; charset=utf-8");

				multipart.addBodyPart(imagePart);
				multipart.addBodyPart(textPart);

				msg.setContent(multipart);
			}

			Transport.send(msg);

		} catch (final Exception e) {
			throw new EmailPluginException("Error enviando. MSG:" + ExceptionUtils.getMessage(e), e);
		}
		return true;

	}

	private static final String HTML_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
	private final Pattern pattern = Pattern.compile(HTML_PATTERN);

	public boolean isHtml(final String text) {
		final Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 * @throws AutenticacionPluginException
	 */
	private String getPropiedad(final String propiedad) throws EmailPluginException {
		final String res = getProperty(EMAIL_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new EmailPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}

}
