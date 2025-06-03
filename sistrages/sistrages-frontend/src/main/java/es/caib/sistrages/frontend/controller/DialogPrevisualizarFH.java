package es.caib.sistrages.frontend.controller;

import es.caib.sistrages.core.api.model.*;
import es.caib.sistrages.core.api.service.SistramitApiExternaService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class DialogPrevisualizarFH extends DialogControllerBase {

	@Inject
	private SistramitApiExternaService sistramitApiExternaService;


	private TramiteFH tramiteFH = new TramiteFH();

	private FuncionarioHabilitadoInfo funcionarioHabilitado;

	private PersonaInfo interesado;

	private PersonaInfo representante;


	private String url;


	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		funcionarioHabilitado = new FuncionarioHabilitadoInfo();

		funcionarioHabilitado.setUsername("usuario");
		funcionarioHabilitado.setNif("44444444A");
		funcionarioHabilitado.setNombre("Nombre");
		funcionarioHabilitado.setApellido1("Apellido1");
		funcionarioHabilitado.setApellido2("Apellido2");
		funcionarioHabilitado.setDir3("A04003003");

		interesado = new PersonaInfo();

		interesado.setNif("99999972C");
		interesado.setNombre("PRUEBAS");
		interesado.setApellido1("EIDAS");
		interesado.setApellido2("CERTIFICADO");

		representante = new PersonaInfo();
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (!validarNif()) {
			return;
		}

		if (!validarDatosRepresentante()) {
			return;
		}

		if (!validarDatosInteresado()) {
			return;
		}

		// Cambiar atributos vacíos de funcionarioHabilitado a null
		if (funcionarioHabilitado != null) {
			if ("".equals(funcionarioHabilitado.getApellido2())) funcionarioHabilitado.setApellido2(null);
		}

		// Cambiar atributos vacíos de interesado a null
		if (interesado != null) {
			if ("".equals(interesado.getApellido1())) interesado.setApellido1(null);
			if ("".equals(interesado.getApellido2())) interesado.setApellido2(null);
		}

		// Cambiar atributos vacíos de representante a null
		if (representante != null) {
			if ("".equals(representante.getApellido2())) representante.setApellido2(null);
		}

		// Llamamos a API externa STT
		setUrl(sistramitApiExternaService.obtenerTicketAccesoFH(funcionarioHabilitado, interesado, (representante.getNif() != null && !representante.getNif().isEmpty()) ? representante : null, tramiteFH));
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	private boolean validarNif() {
		String fHnif = funcionarioHabilitado.getNif();
		if (fHnif != null && !fHnif.matches("^(\\d{8}[A-HJ-NP-TV-Z])|([XYZ]\\d{7}[A-HJ-NP-TV-Z])|(\\d{12})$")) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogPrevisualizarFH.error.NifFuncionarioHabilitadoNoValido"));
			return false;
		}
		String iNif = interesado.getNif();
		if (iNif != null && !iNif.matches("^(\\d{8}[A-HJ-NP-TV-Z])|([XYZ]\\d{7}[A-HJ-NP-TV-Z])|(\\d{12})$")) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogPrevisualizarFH.error.NifInteresadoNoValido"));
			return false;
		}
		String rNif = representante.getNif();
		if (rNif != null && !rNif.isEmpty() && !rNif.matches("^(\\d{8}[A-HJ-NP-TV-Z])|([XYZ]\\d{7}[A-HJ-NP-TV-Z])|(\\d{12})$")) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogPrevisualizarFH.error.NifRepresentanteNoValido"));
			return false;
		}

		return true;
	}

	private boolean validarDatosRepresentante() {
		boolean tieneDatos = representante != null && (tieneValor(representante.getNif()) || tieneValor(representante.getNombre()) ||
				tieneValor(representante.getApellido1()) || tieneValor(representante.getApellido2()));

		boolean datosIncompletos = representante != null && (!tieneValor(representante.getNif()) || !tieneValor(representante.getNombre()) ||
				!tieneValor(representante.getApellido1()));

		if (tieneDatos && datosIncompletos) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogPrevisualizarFH.error.representanteVacio"));
			return false;
		}
		return true;
	}

	private boolean validarDatosInteresado() {
		boolean tieneDatosRep = representante != null && (tieneValor(representante.getNif()) || tieneValor(representante.getNombre()) ||
				tieneValor(representante.getApellido1()) || tieneValor(representante.getApellido2()));

		if (!tieneDatosRep && !tieneValor(interesado.getApellido1())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogPrevisualizarFH.error.interesadoApellido1Vacio"));
			return false;
		}
		return true;
	}

	private boolean tieneValor(String valor) {
		return valor != null && !valor.isEmpty();
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
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("previsualizarFHDialog");
	}

	public TramiteFH getTramiteFH() {
		return tramiteFH;
	}

	public void setTramiteFH(TramiteFH tramiteFH) {
		this.tramiteFH = tramiteFH;
	}

	public FuncionarioHabilitadoInfo getFuncionarioHabilitado() {
		return funcionarioHabilitado;
	}

	public void setFuncionarioHabilitado(FuncionarioHabilitadoInfo funcionarioHabilitado) {
		this.funcionarioHabilitado = funcionarioHabilitado;
	}

	public PersonaInfo getInteresado() {
		return interesado;
	}

	public void setInteresado(PersonaInfo interesado) {
		this.interesado = interesado;
	}

	public PersonaInfo getRepresentante() {
		return representante;
	}

	public void setRepresentante(PersonaInfo representante) {
		this.representante = representante;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
