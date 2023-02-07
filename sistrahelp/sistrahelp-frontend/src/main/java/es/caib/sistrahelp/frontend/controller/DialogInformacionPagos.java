package es.caib.sistrahelp.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import es.caib.sistrahelp.core.api.model.DatosSesionPago;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaDetallePago;
import es.caib.sistrahelp.core.api.model.VerificacionPago;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEstadoPago;
import es.caib.sistrahelp.core.api.model.types.TypePresentacion;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogInformacionPagos extends DialogControllerBase {

	@Inject
	private HelpDeskService helpDeskService;

	private String portapapeles;

	private String errorCopiar;
	private PagoAuditoria dato;
	private DatosSesionPago datosSesionPago;
	private VerificacionPago verificacion;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {
			dato = (PagoAuditoria) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_PAGO);
			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_PAGO);

			if (dato != null) {
				final ResultadoAuditoriaDetallePago detallePago = helpDeskService
						.obtenerAuditoriaDetallePago(dato.getCodigoPago());

				if (detallePago != null) {
					datosSesionPago = detallePago.getDatos();
					verificacion = detallePago.getVerificacion();
				}
			}
		}

	}

	/**
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public PagoAuditoria getDato() {
		return dato;
	}

	public void setDato(final PagoAuditoria dato) {
		this.dato = dato;
	}

	public DatosSesionPago getDatosSesionPago() {
		return datosSesionPago;
	}

	public void setDatosSesionPago(final DatosSesionPago datosSesionPago) {
		this.datosSesionPago = datosSesionPago;
	}

	public VerificacionPago getVerificacion() {
		return verificacion;
	}

	public void setVerificacion(final VerificacionPago verificacion) {
		this.verificacion = verificacion;
	}

	public boolean getMostrarDetalle() {
		boolean resultado = false;

		if (dato != null && TypeEstadoPago.EN_CURSO.equals(dato.getEstadoPago()) && datosSesionPago != null
				&& TypePresentacion.ELECTRONICA.equals(datosSesionPago.getPresentacion())) {
			resultado = true;
		}

		return resultado;

	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewAuditoriaTramites.copiar"));
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("dialogoInformacionPagos");
	}

}
