package es.caib.sistramit.core.ejb;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class EJBExceptionInterceptor {

    @AroundInvoke
    public Object interceptar(InvocationContext ctx) throws Exception {
        try {
            return ctx.proceed();
        } catch (AppEJBException e) {
            throw e;
        } catch (Exception e) {
            throw new AppEJBException(e.getMessage(), e);
        }
    }
}