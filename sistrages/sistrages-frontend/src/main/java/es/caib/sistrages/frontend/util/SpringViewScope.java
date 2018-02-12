package es.caib.sistrages.frontend.util;

import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Implements the JSF View Scope for use by Spring. This class is registered as
 * a Spring bean with the CustomScopeConfigurer.
 */
public class SpringViewScope implements Scope {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Object get(String name, ObjectFactory<?> objectFactory) {
		// VIEW ROOT
		UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();

		Object object = null;

		if (uiViewRoot != null) {
			// VIEW MAP
			Map<String, Object> viewMap = uiViewRoot.getViewMap();

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
	public final void registerDestructionCallback(String name, Runnable callback) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Object remove(String name) {
		// VIEW ROOT
		UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();

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
	public final Object resolveContextualObject(String key) {
		return null;
	}
}