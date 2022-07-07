package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFormateadorFormulario extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private FormateadorFormularioService fmtService;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private FormateadorFormulario data;

	/** Indica si cuando se carga, estaba bloqueado. **/
	private boolean estabaBloqueado;

	/** Indica si estamos en alta. **/
	private boolean isAlta;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		if (modo == TypeModoAcceso.ALTA) {
			setAlta(true);
			data = new FormateadorFormulario();
			estabaBloqueado = false;
			data.setDesactivarPersonalizacion(false);
			data.setGenerico(false);
		} else {
			setAlta(false);
			data = fmtService.getFormateadorFormulario(new Long(id));
			estabaBloqueado = data.isDesactivarPersonalizacion();
		}
	}

	/**
	 * Checkea si se ha activado el generico pero no está ya dado de alta (porque no
	 * se puede hacer eso).
	 */
	public void checkGenerico() {
		if (this.data.getCodigo() == null && this.data.isGenerico()) {
			addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogFormateadorFormulario.porDefecto.genericoNoAlta"));
			this.data.setGenerico(false);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);

		/**
		 * Comprueba si está marcado como por defecto y no tiene plantillas. Si es así,
		 * forzamos a que primero cree plantillas y luego que guarde.
		 **/
		if (acceso == TypeModoAcceso.EDICION && data.isGenerico()) {
			final List<PlantillaFormateador> plantillas = fmtService.getListaPlantillasFormateador(data.getCodigo());
			if (plantillas == null || plantillas.isEmpty()) {
				addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral("dialogFormateadorFormulario.porDefecto.faltanPlantillas"));
				return;
			}

			for (final String idioma : UtilJSF.getSessionBean().getIdiomas()) {
				boolean encontrado = false;
				for (final PlantillaFormateador plantilla : plantillas) {
					if (plantilla.getIdioma().equals(idioma)) {
						encontrado = true;
						break;
					}
				}

				if (!encontrado) {
					addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral("dialogFormateadorFormulario.porDefecto.faltanPlantillas"));
					return;
				}
			}
		}

		final Long idEntidad = UtilJSF.getIdEntidad();
		switch (acceso) {
		case ALTA:
			if (fmtService.getFormateadorFormulario(data.getIdentificador()) != null) {
				addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.codigoRepetido"));
				return;
			}
			// No puede guardarse como generico al ser alta (necesita los docs)
			data.setGenerico(false);
			fmtService.addFormateadorFormulario(idEntidad, data);
			break;
		case EDICION:
			// Comprobamos si cambia a bloqueado
			if (!estabaBloqueado && data.isDesactivarPersonalizacion()
					&& fmtService.tieneRelacionesFormateadorFormulario(data.getCodigo())) {
				addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.formateadorNoBloqueable"));
				return;
			}
			// Comprobamos si se repite
			final FormateadorFormulario f = fmtService.getFormateadorFormulario(data.getIdentificador());
			if (f != null && f.getCodigo().longValue() != data.getCodigo().longValue()) {
				addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.codigoRepetido"));
				return;
			}
			fmtService.updateFormateadorFormulario(data, idEntidad);

			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Edita la plantilla generico.
	 */
	public void editarPlantillaGenerico() {

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id);
		UtilJSF.openDialog(DialogPlantillaFormateador.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 430,
				170);
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

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("formateadorFormularioDialog");
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id el nuevo valor de id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de data.
	 *
	 * @return el valor de data
	 */
	public FormateadorFormulario getData() {
		return data;
	}

	/**
	 * Establece el valor de data.
	 *
	 * @param data el nuevo valor de data
	 */
	public void setData(final FormateadorFormulario data) {
		this.data = data;
	}

	/**
	 * @return the isAlta
	 */
	@Override
	public boolean isAlta() {
		return isAlta;
	}

	/**
	 * @param isAlta the isAlta to set
	 */
	public void setAlta(final boolean isAlta) {
		this.isAlta = isAlta;
	}

}
