package es.caib.sistrages.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.service.RestApiService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;

@Service
@Transactional
public class RestApiServiceImpl implements RestApiService {

    @Override
    @NegocioInterceptor
    public String test(String echo) {
        return "Echo: " + echo;
    }

}
