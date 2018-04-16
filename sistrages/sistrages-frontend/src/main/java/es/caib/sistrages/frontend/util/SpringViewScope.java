package es.caib.sistrages.frontend.util;

import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Implements the JSF View Scope for use by Spring. This class is registered as
 * a Spring bean with the CustomScopeConfigurer.
 *
 * PARA PODER INTEGRAR JSF + SPRING SERIA NECESARIO DEFINIR UN SCOPE DE TIPO
 * VIEW PARA SPRING Y ANOTAR LOS BEANS JSF COMO SPRING BEANS:
 *
 * @ Named
 *
 * @ Scope("view")
 *
 * ENLACE A LOS EJBs COMO @ AutoWired (USANDO EL MAPEO AL EJB DEFINIDO EN
 * sistrages-ejb-core-api.xml)
 *
 *
 */
public class SpringViewScope implements Scope {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Object get(final String name, final ObjectFactory<?> objectFactory) {
		// VIEW ROOT
		final UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();

		Object object = null;

		if (uiViewRoot != null) {
			// VIEW MAP
			final Map<String, Object> viewMap = uiViewRoot.getViewMap();

			if (viewMap.containsKey(name)) {
				object = viewMap.get(name);
			} else {
				object = objectFactory.getObject();
				viewMap.put(name, object);
			}
		}

		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getConversationId() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void registerDestructionCallback(final String name, final Runnable callback) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Object remove(final String name) {
		// VIEW ROOT
		final UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();

		Object object = null;

		if (uiViewRoot != null) {
			object = uiViewRoot.getViewMap().remove(name);
		}

		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Object resolveContextualObject(final String key) {
		return null;
	}
}