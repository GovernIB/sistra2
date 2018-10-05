package es.caib.sistramit.core.service.component.flujo;

import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;

public interface FlujoTramitacionCacheComponent {

	void put(String idSesionTramitacion, FlujoTramitacionComponent flujoTramitacionComponent);

	FlujoTramitacionComponent get(String idSesionTramitacion);

	void purgar();

	// TODO Quitar!!! Borrar!!! Es para test.
	String testFirmaAddFichero(String idSession) throws Exception;

	String testFirmaActivar(String idSession) throws FirmaPluginException;

	String testFirmaEstado(String idSession) throws FirmaPluginException;

	void testFirmaCerrar(String idSession) throws FirmaPluginException;

	byte[] testFirmaDoc(String idSession, String idDoc) throws FirmaPluginException;

	String testFirmaCreateSesion() throws Exception;

}