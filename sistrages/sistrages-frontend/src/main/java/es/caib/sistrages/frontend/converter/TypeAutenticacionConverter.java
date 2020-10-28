package es.caib.sistrages.frontend.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import es.caib.sistrages.core.api.model.types.TypeAutenticacion;

@ManagedBean
@RequestScoped
public class TypeAutenticacionConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext fc, final UIComponent uic, final String value) {
		if (value != null && value.trim().length() > 0) {
			return TypeAutenticacion.fromString(value);

		} else {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext fc, final UIComponent uic, final Object object) {
		if (object != null) {
			return ((TypeAutenticacion) object).toString();
		} else {
			return null;
		}
	}
}
