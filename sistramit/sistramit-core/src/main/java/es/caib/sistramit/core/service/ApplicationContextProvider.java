package es.caib.sistramit.core.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Permite acceder al contexto de aplicación.
 *
 * @author Indra
 */
@Component(value = "applicationContextProvider")
public class ApplicationContextProvider implements ApplicationContextAware {
	/**
	 * Aplication context negocio.
	 */
	private static ApplicationContext applicationContext;

	/**
	 * Obtiene el contexto de negocio.
	 *
	 * @return contexto aplicación
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Establece el contexto de negocio.
	 *
	 * @param papplicationContext
	 *            contexto de aplicación
	 */
	@Override
	public final void setApplicationContext(final ApplicationContext papplicationContext) {
		// Assign the ApplicationContext into a static variable
		ApplicationContextProvider.applicationContext = papplicationContext;
	}
}
