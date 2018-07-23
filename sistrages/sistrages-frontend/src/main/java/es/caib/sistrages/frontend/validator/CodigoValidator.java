package es.caib.sistrages.frontend.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import es.caib.sistrages.frontend.util.UtilJSF;

@FacesValidator("CodigoValidator")
public class CodigoValidator implements Validator {
	private static final String CODIGO_PATTERN = "/[a-z0-9_-]/i";

	private final Pattern pattern;
	private Matcher matcher;

	public CodigoValidator() {
		pattern = Pattern.compile(CODIGO_PATTERN);
	}

	@Override
	public void validate(final FacesContext context, final UIComponent component, final Object value) {

		matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			final FacesMessage msg = new FacesMessage(UtilJSF.getLiteral("error.validacion.codigo.titulo"),
					UtilJSF.getLiteral("error.validacion.codigo"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
