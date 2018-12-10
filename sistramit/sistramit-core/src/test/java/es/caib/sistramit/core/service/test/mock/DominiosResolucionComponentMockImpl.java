package es.caib.sistramit.core.service.test.mock;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistramit.core.service.component.integracion.DominiosResolucionComponent;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;

/**
 * Simula acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("dominiosResolucionComponentMock")
@Primary
public final class DominiosResolucionComponentMockImpl implements DominiosResolucionComponent {

	/**
	 * Resuelve dominio para Dominios de Fuente de Datos.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioFD(final RDominio dominio, final ParametrosDominio parametrosDominio) {
		final ValoresDominio valores = new ValoresDominio();
		valores.setError(false);
		if (dominio.getIdentificador().contains("FD1")) {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL11");
			valores.setValor(fila, "COD_FD2", "VAL12");
			valores.setValor(fila, "COD_FD3", "VAL13");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL21");
			valores.setValor(fila, "COD_FD2", "VAL22");
			valores.setValor(fila, "COD_FD3", "VAL23");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL31");
			valores.setValor(fila, "COD_FD2", "VAL32");
			valores.setValor(fila, "COD_FD3", "VAL33");
		} else if (dominio.getIdentificador().contains("FD2")) {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL11");
			valores.setValor(fila, "COD_FD2", "VAL12");
			valores.setValor(fila, "COD_FD3", "VAL13");
			valores.setValor(fila, "COD_FD4", "VAL14");
			valores.setValor(fila, "COD_FD5", "VAL15");
			valores.setValor(fila, "COD_FD6", "VAL16");
			valores.setValor(fila, "COD_FD7", "VAL17");
			valores.setValor(fila, "COD_FD8", "VAL18");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL21");
			valores.setValor(fila, "COD_FD2", "VAL22");
			valores.setValor(fila, "COD_FD3", "VAL23");
			valores.setValor(fila, "COD_FD4", "VAL24");
			valores.setValor(fila, "COD_FD5", "VAL25");
			valores.setValor(fila, "COD_FD6", "VAL26");
			valores.setValor(fila, "COD_FD7", "VAL27");
			valores.setValor(fila, "COD_FD8", "VAL28");
		} else {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL11");
			valores.setValor(fila, "COD_FD2", "VAL12");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL21");
			valores.setValor(fila, "COD_FD2", "VAL22");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL31");
			valores.setValor(fila, "COD_FD2", "VAL32");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL41");
			valores.setValor(fila, "COD_FD2", "VAL42");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL51");
			valores.setValor(fila, "COD_FD2", "VAL52");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL61");
			valores.setValor(fila, "COD_FD2", "VAL62");
			fila = valores.addFila();
			valores.setValor(fila, "COD_FD1", "VAL71");
			valores.setValor(fila, "COD_FD2", "VAL72");
		}
		return valores;
	}

	/**
	 * Resuelve dominio para Dominios de Lista Fija
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioLF(final RDominio dominio) {
		final ValoresDominio valores = new ValoresDominio();
		valores.setError(false);
		final int fila = valores.addFila();
		valores.setValor(fila, "COD_LF1", "VAL1");
		valores.setValor(fila, "COD_LF2", "VAL2");
		return valores;

	}

	/**
	 * Resuelve dominio para Dominios de SQL.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioSQL(final RDominio dominio, final ParametrosDominio parametrosDominio) {
		final ValoresDominio valores = new ValoresDominio();
		valores.setError(false);
		if (dominio.getIdentificador().contains("SQL1")) {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL11");
			valores.setValor(fila, "COD_SQL2", "VAL12");
			fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL21");
			valores.setValor(fila, "COD_SQL2", "VAL22");
		} else if (dominio.getIdentificador().contains("SQL2")) {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL11");
			valores.setValor(fila, "COD_SQL2", "VAL21");
			valores.setValor(fila, "COD_SQL3", "VAL31");
			valores.setValor(fila, "COD_SQL4", "VAL41");
			fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL21");
			valores.setValor(fila, "COD_SQL2", "VAL22");
			valores.setValor(fila, "COD_SQL3", "VAL23");
			valores.setValor(fila, "COD_SQL4", "VAL24");
		} else {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL11");
			valores.setValor(fila, "COD_SQL2", "VAL21");
			valores.setValor(fila, "COD_SQL3", "VAL31");
			fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL21");
			valores.setValor(fila, "COD_SQL2", "VAL22");
			valores.setValor(fila, "COD_SQL3", "VAL23");
			fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL31");
			valores.setValor(fila, "COD_SQL2", "VAL32");
			valores.setValor(fila, "COD_SQL3", "VAL33");
			fila = valores.addFila();
			valores.setValor(fila, "COD_SQL1", "VAL41");
			valores.setValor(fila, "COD_SQL2", "VAL42");
			valores.setValor(fila, "COD_SQL3", "VAL43");
		}
		return valores;
	}

	/**
	 * Resuelve dominio para Dominio de Web Service.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioWS(final RDominio dominio, final ParametrosDominio parametrosDominio) {
		final ValoresDominio valores = new ValoresDominio();
		valores.setError(false);
		if (dominio.getIdentificador().contains("SQL1")) {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_WS1", "VAL11");
			valores.setValor(fila, "COD_WS2", "VAL12");
			fila = valores.addFila();
			valores.setValor(fila, "COD_WS1", "VAL21");
			valores.setValor(fila, "COD_WS2", "VAL22");
		} else {
			int fila = valores.addFila();
			valores.setValor(fila, "COD_WS1", "VAL11");
			valores.setValor(fila, "COD_WS2", "VAL12");
			valores.setValor(fila, "COD_WS3", "VAL13");
			valores.setValor(fila, "COD_WS4", "VAL14");
			fila = valores.addFila();
			valores.setValor(fila, "COD_WS1", "VAL21");
			valores.setValor(fila, "COD_WS2", "VAL22");
			valores.setValor(fila, "COD_WS3", "VAL23");
			valores.setValor(fila, "COD_WS4", "VAL24");
		}
		return valores;

	}

}
