package es.caib.sistramit.frontend.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Component
public class PostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String name) {
		// Establece SynchronizeOnSession
		if (bean instanceof RequestMappingHandlerAdapter) {
			final RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
			adapter.setSynchronizeOnSession(true);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

}