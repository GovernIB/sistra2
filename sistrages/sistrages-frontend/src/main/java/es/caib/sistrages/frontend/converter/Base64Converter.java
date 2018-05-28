package es.caib.sistrages.frontend.converter;

import java.util.Base64;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean
@RequestScoped
public class Base64Converter implements Converter {

	@Override
	public Object getAsObject(final FacesContext fc, final UIComponent uic, final String value) {
		if (value != null && value.trim().length() > 0) {
			return Base64.getEncoder().encodeToString(value.getBytes());

		} else {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext fc, final UIComponent uic, final Object object) {
		if (object != null) {
			return new String(Base64.getDecoder().decode(String.valueOf(object)));
		} else {
			return null;
		}
	}
}
