package es.caib.sistrages.frontend.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import es.caib.sistrages.core.api.model.Fichero;

@ManagedBean
@RequestScoped
public class FicheroConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext fc, final UIComponent uic, final String value) {
		if (value != null && value.trim().length() > 0) {
			final Fichero fichero = new Fichero();
			fichero.setId(Long.valueOf(value));
			return fichero;

		} else {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext fc, final UIComponent uic, final Object object) {
		if (object != null) {
			return String.valueOf(((Fichero) object).getId());
		} else {
			return null;
		}
	}
}
