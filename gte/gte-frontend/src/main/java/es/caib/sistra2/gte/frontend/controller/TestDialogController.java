package es.caib.sistra2.gte.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistra2.gte.core.api.model.TestData;
import es.caib.sistra2.gte.core.api.service.TestService;
import es.caib.sistra2.gte.frontend.model.DialogResult;
import es.caib.sistra2.gte.frontend.model.types.TypeModoAcceso;
import es.caib.sistra2.gte.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.gte.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class TestDialogController {

	/**
	 * Servicio.
	 */
	@Inject
	private TestService testService;

	// TODO Heredar dialogos de una misma clase (getViewName, modoAcceso,
	// isConsulta, isAlta, isEdicion)
	/**
	 * Nombre de la vista.
	 */
	public final static String VIEW_NAME = "dialogTestData";

	/**
	 * Modo acceso ventana.
	 */
	private String modoAcceso;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private TestData data;

	/**
	 * Inicialización.
	 */
	public void init() {
		TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new TestData();
		} else {
			data = testService.load(id);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			if (testService.load(data.getCodigo()) != null) {
				// TODO Literales
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Ya existe dato con ese codigo");
				return;
			}
			testService.add(data);
			break;
		case EDICION:
			testService.update(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getCodigo());
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public void mensaje() {
		UtilJSF
				.showMessageDialog(TypeNivelGravedad.INFO, "Atento", "Ojo al dato.");
	}

	public String getModoAcceso() {
		return modoAcceso;
	}

	public void setModoAcceso(String modoAcceso) {
		this.modoAcceso = modoAcceso;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TestData getData() {
		return data;
	}

	public void setData(TestData data) {
		this.data = data;
	}

	// TODO pasar a clase padre
	public boolean isAlta() {
		TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.ALTA);
	}

	public boolean isEdicion() {
		TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.EDICION);
	}

	public boolean isConsulta() {
		TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		return (modo == TypeModoAcceso.CONSULTA);
	}

}
