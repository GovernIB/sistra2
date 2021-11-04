package es.caib.sistrages.frontend.converter;

import java.util.List;
import java.util.function.Predicate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;

@ManagedBean
@RequestScoped
public class ConfiguracionConverter implements Converter {

	@Inject
	private ConfiguracionAutenticacionService gestorService;

//	@Override
//	public Object getAsObject(final FacesContext fc, final UIComponent uic, final String value) {
//		if (value != null && value.trim().length() > 0) {
//			if (value.equals("null")) {
//				return null;
//			} else {
//				return gestorService.getConfiguracionAutenticacion(Long.parseLong(value));
//			}
//		} else {
//			return null;
//		}
//	}
//
//	@Override
//	public String getAsString(final FacesContext fc, final UIComponent uic, final Object object) {
//		if (object != null) {
//			return String.valueOf(((ConfiguracionAutenticacion) object).getCodigo());
//		} else {
//			return null;
//		}
//	}
//

	@Override
    public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
        Object o = null;
        if (!(value == null || value.isEmpty())) {
            o = this.getSelectedItemAsEntity(comp, value);
        }
        return o;
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
        String s = "";
        if (value != null) {
            s = ((ConfiguracionAutenticacion) value).getIdentificador().toString();
        }
        return s;
    }

    private ConfiguracionAutenticacion getSelectedItemAsEntity(UIComponent comp, String value) {
    	ConfiguracionAutenticacion item = null;

        List<ConfiguracionAutenticacion> selectItems = null;
        for (UIComponent uic : comp.getChildren()) {
            if (uic instanceof UISelectItems) {
                selectItems = (List<ConfiguracionAutenticacion>) ((UISelectItems) uic).getValue();

                if (value != null && selectItems != null && !selectItems.isEmpty()) {
                    Predicate<ConfiguracionAutenticacion> predicate = i -> i.getIdentificador().equals(value);
                    item = selectItems.stream().filter(predicate).findFirst().orElse(null);
                }
            }
        }

        return item;
    }
}
