package es.caib.sistramit.core.service.component.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.system.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.PerdidaClave;
import es.caib.sistramit.core.api.model.system.VerificacionPago;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.pagar.ControladorPasoPagarHelper;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.integracion.PagoComponentVerificacion;
import es.caib.sistramit.core.service.model.system.PerdidaClaveFichero;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;

@Component("restApiInternaComponen")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RestApiInternaComponentImpl implements RestApiInternaComponent {

	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Autowired
	private FlujoTramiteDao flujoTramiteDao;

	@Autowired
	private FlujoPasoDao flujoPasoDao;

	@Autowired
	private PagoComponent pagoComponent;

	@Override
	public OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda) {
		final OUTPerdidaClave resPedidaClave = new OUTPerdidaClave();

		final String numMax = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PERDIDA_CLAVE_NUMMAXTRAMITES);

		final Long ntramites = flujoTramiteDao.countTramitesPerdidaClave(pFiltroBusqueda);

		if (Long.valueOf(numMax) < ntramites) {
			resPedidaClave.setResultado(-1);
		} else {

			final List<PerdidaClaveFichero> listaTramites = flujoTramiteDao
					.obtenerTramitesPerdidaClave(pFiltroBusqueda);

			resPedidaClave.setListaClaves(new ArrayList<>());

			for (final PerdidaClaveFichero perdidaClaveFichero : listaTramites) {
				final DatosFicheroPersistencia fichero = flujoPasoDao
						.recuperarFicheroPersistencia(perdidaClaveFichero.getFichero());

				final XmlFormulario xmlFormulario = UtilsFormulario.xmlToValores(fichero.getContenido());

				if (xmlFormulario != null && !xmlFormulario.getValores().isEmpty()) {

					for (final ValorCampo valorCampo : xmlFormulario.getValores()) {

						if (valorCampo instanceof ValorCampoSimple) {
							if (((ValorCampoSimple) valorCampo).getValor()
									.equals(pFiltroBusqueda.getDatoFormulario())) {
								resPedidaClave.getListaClaves().add(convertPerdidaClave(perdidaClaveFichero));
							}
						} else if (valorCampo instanceof ValorCampoIndexado) {
							if (((ValorCampoIndexado) valorCampo).getValor().getDescripcion()
									.equals(pFiltroBusqueda.getDatoFormulario())) {
								resPedidaClave.getListaClaves().add(convertPerdidaClave(perdidaClaveFichero));
							}
						} else if (valorCampo instanceof ValorCampoListaIndexados) {
							for (final ValorIndexado valor : ((ValorCampoListaIndexados) valorCampo).getValor()) {
								if (valor.getDescripcion().equals(pFiltroBusqueda.getDatoFormulario())) {
									resPedidaClave.getListaClaves().add(convertPerdidaClave(perdidaClaveFichero));
								}
							}
						}

					}
				}
			}

			if (!resPedidaClave.getListaClaves().isEmpty()) {
				resPedidaClave.setResultado(1);
			}

		}

		return resPedidaClave;
	}



	/**
	 * Convert perdida clave.
	 *
	 * @param pPerdidaClaveFichero
	 *            perdida clave fichero
	 * @return the perdida clave
	 */
	private PerdidaClave convertPerdidaClave(final PerdidaClaveFichero pPerdidaClaveFichero) {
		PerdidaClave res = null;

		if (pPerdidaClaveFichero != null) {
			res = new PerdidaClave();
			res.setClaveTramitacion(pPerdidaClaveFichero.getClaveTramitacion());
			res.setFecha(pPerdidaClaveFichero.getFecha());
			res.setIdTramite(pPerdidaClaveFichero.getIdTramite());
			res.setVersionTramite(pPerdidaClaveFichero.getVersionTramite());
			res.setIdProcedimientoCP(pPerdidaClaveFichero.getIdProcedimientoCP());
		}
		return res;
	}


}
