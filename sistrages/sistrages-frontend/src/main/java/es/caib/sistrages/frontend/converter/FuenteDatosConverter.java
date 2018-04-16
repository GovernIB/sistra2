package es.caib.sistrages.frontend.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.service.DominioService;

@ManagedBean
@RequestScoped
public class FuenteDatosConverter implements Converter {

	@Inject
	private DominioService dominioService;

	@Override
	public Object getAsObject(final FacesContext fc, final UIComponent uic, final String value) {
		if (value != null && value.trim().length() > 0) {
			return dominioService.loadFuenteDato(Long.parseLong(value));

		} else {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext fc, final UIComponent uic, final Object object) {
		if (object != null) {
			return String.valueOf(((FuenteDatos) object).getCodigo());
		} else {
			return null;
		}
	}
}
