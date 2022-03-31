package es.caib.sistrages.frontend.converter;

import java.util.List;
import java.util.function.Predicate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;

@ManagedBean
@RequestScoped
public class ConfiguracionConverter implements Converter {

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
