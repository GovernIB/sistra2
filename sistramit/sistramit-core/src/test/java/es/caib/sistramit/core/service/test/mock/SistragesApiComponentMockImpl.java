package es.caib.sistramit.core.service.test.mock;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.service.component.integracion.SistragesApiComponent;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;

/**
 * Simula acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesApiComponentMock")
@Primary
public final class SistragesApiComponentMockImpl implements SistragesApiComponent {

	@Override
	public RConfiguracionGlobal obtenerConfiguracionGlobal() {
		return SistragesMock.crearConfiguracionGlobal();
	}

	@Override
	public RConfiguracionEntidad obtenerConfiguracionEntidad(final String idEntidad) {
		final RConfiguracionEntidad c = SistragesMock.crearEntidad();
		return c;
	}

	@Override
	public RVersionTramite recuperarDefinicionTramite(final String idTramite, final int version, final String idioma) {
		final RVersionTramite definicionVersion = SistragesMock.crearVersionTramite(idTramite);
		return definicionVersion;
	}

	@Override
	public RAvisosEntidad obtenerAvisosEntidad(final String idEntidad) {
		final RAvisosEntidad avisos = SistragesMock.crearAvisos();
		return avisos;
	}

	@Override
	public RValoresDominio resuelveDominioFuenteDatos(final RDominio dominio,
			final ParametrosDominio parametrosDominio) {
		final RValoresDominio valoresDominio = SistragesMock.crearValoresDominioFD(dominio);
		return valoresDominio;
	}

	@Override
	public RValoresDominio resuelveDominioListaFija(final RDominio dominio) {
		final RValoresDominio valoresDominio = SistragesMock.crearValoresDominioLF(dominio);
		return valoresDominio;
	}

	@Override
	public RDominio recuperarDefinicionDominio(final String idDominio) {
		final RDominio dominio = SistragesMock.crearDominio(idDominio);
		return dominio;
	}
}
