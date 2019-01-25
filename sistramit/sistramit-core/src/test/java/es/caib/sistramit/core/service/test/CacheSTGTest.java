package es.caib.sistramit.core.service.test;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.service.component.integracion.SistragesComponent;
import es.caib.sistramit.core.service.test.mock.SistragesMock;

/**
 * Testing cache configuracion STG.
 *
 * @author Indra
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CacheSTGTest extends BaseDbUnit {

	/** Acceso STG (simulado). */
	@Autowired
	private SistragesComponent sistragesComponent;

	/** Publicación JNDI Datasource y creación de BBDD en memoria. */
	@BeforeClass
	public static void setUpClass() throws Exception {
		JndiBean.doSetup();
	}

	/**
	 * Verificación cacheo definiciones: se verifica el funcionamiento de las caches
	 * para configuración global, configuración entidad, avisos entidad y
	 * definiciones de trámite.
	 */
	@Test
	public void test1_cache() {

		String timestamp1, timestamp2, timestamp3;

		// Configuracion global
		RConfiguracionGlobal confGlobal = null;
		confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
		timestamp1 = confGlobal.getTimestamp();
		confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
		timestamp2 = confGlobal.getTimestamp();
		Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
		sistragesComponent.evictConfiguracionGlobal();
		confGlobal = sistragesComponent.obtenerConfiguracionGlobal();
		timestamp3 = confGlobal.getTimestamp();
		Assert.isTrue(!timestamp2.equals(timestamp3), "No ha variado timestamp");

		// Configuracion entidad
		RConfiguracionEntidad confEntidad = null;
		confEntidad = sistragesComponent.obtenerConfiguracionEntidad(SistragesMock.ID_ENTIDAD);
		timestamp1 = confEntidad.getTimestamp();
		confEntidad = sistragesComponent.obtenerConfiguracionEntidad(SistragesMock.ID_ENTIDAD);
		timestamp2 = confEntidad.getTimestamp();
		Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
		sistragesComponent.evictConfiguracionEntidad(SistragesMock.ID_ENTIDAD);
		confEntidad = sistragesComponent.obtenerConfiguracionEntidad(SistragesMock.ID_ENTIDAD);
		timestamp3 = confEntidad.getTimestamp();
		Assert.isTrue(!timestamp2.equals(timestamp3), "No ha variado timestamp");

		// Avisos entidad
		RAvisosEntidad avisosEntidad = null;
		avisosEntidad = sistragesComponent.obtenerAvisosEntidad(SistragesMock.ID_ENTIDAD);
		timestamp1 = avisosEntidad.getTimestamp();
		avisosEntidad = sistragesComponent.obtenerAvisosEntidad(SistragesMock.ID_ENTIDAD);
		timestamp2 = avisosEntidad.getTimestamp();
		Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
		sistragesComponent.evictAvisosEntidad(SistragesMock.ID_ENTIDAD);
		avisosEntidad = sistragesComponent.obtenerAvisosEntidad(SistragesMock.ID_ENTIDAD);
		timestamp3 = avisosEntidad.getTimestamp();
		Assert.isTrue(!timestamp2.equals(timestamp3), "No ha variado timestamp");

		// Definicion tramite
		RVersionTramite defTramite = sistragesComponent.recuperarDefinicionTramite(SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA);
		timestamp1 = defTramite.getTimestamp();
		defTramite = sistragesComponent.recuperarDefinicionTramite(SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA);
		timestamp2 = defTramite.getTimestamp();
		Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
		sistragesComponent.evictDefinicionTramite(SistragesMock.ID_TRAMITE, SistragesMock.VERSION_TRAMITE,
				SistragesMock.IDIOMA);
		defTramite = sistragesComponent.recuperarDefinicionTramite(SistragesMock.ID_TRAMITE,
				SistragesMock.VERSION_TRAMITE, SistragesMock.IDIOMA);
		timestamp3 = defTramite.getTimestamp();
		Assert.isTrue(!timestamp2.equals(timestamp3), "No ha variado timestamp");

		// Dominios
		RDominio dominio = sistragesComponent.recuperarDefinicionDominio(SistragesMock.ID_DOMINIO);
		timestamp1 = dominio.getTimestamp();
		dominio = sistragesComponent.recuperarDefinicionDominio(SistragesMock.ID_DOMINIO);
		timestamp2 = dominio.getTimestamp();
		Assert.isTrue(timestamp1.equals(timestamp2), "No coincide timestamp");
		sistragesComponent.evictDefinicionDominio(SistragesMock.ID_DOMINIO);
		dominio = sistragesComponent.recuperarDefinicionDominio(SistragesMock.ID_DOMINIO);
		timestamp3 = dominio.getTimestamp();
		Assert.isTrue(!timestamp2.equals(timestamp3), "No ha variado timestamp");

	}

}
