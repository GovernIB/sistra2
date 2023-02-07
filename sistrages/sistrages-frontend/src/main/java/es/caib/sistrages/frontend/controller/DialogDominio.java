package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeCache;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.service.VariablesAreaService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.DialogResultMessage;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominio extends DialogControllerBase {

	/** security service. */
	@Inject
	private SecurityService securityService;

	/** ConfiguracionAutenticacion Service **/
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

	/** Dominio service. */
	@Inject
	private DominioService dominioService;

	/** Enlace servicio. */
	@Inject
	private TramiteService tramiteService;

	@Inject
	private SystemService systemService;

	@Inject
	private VariablesAreaService vaService;

	@Inject
	private EntidadService entidadService;

	/** Id elemento a tratar. */
	private String id;

	/** Id area. */
	private String idArea;

	/** Id entidad. */
	private String idEntidad;

	/** Ambito. */
	private String ambito;

	/** Datos elemento. */
	private Dominio data;

	/** Propiedad seleccionada */
	private Propiedad propiedadSeleccionada;

	/** Valor seleccionado. **/
	private Propiedad valorSeleccionado;

	/** Indica si es visible los textos relacionados a JNDI **/
	private boolean visibleJNDI;

	/** Indica si es visible la lista para indicar una lista de valores. **/
	private boolean visibleLista;

	/** Indica si es visible los textos relacionados con remoto. **/
	private boolean visibleRemoto;

	/** Indica si es visible fuente de datos. **/
	private boolean visibleFuente;

	/** Lista con las fuentes de datos. **/
	private List<FuenteDatos> fuentes;

	/** Id de la fuente de datos seleccionada. **/
	private Long idFuenteDato;

	/** Identificador original. */
	private String identificadorOriginal;

	/**
	 * Tipos. Es el único enumerado que funciona así, porque el tipo GLOBAL debe
	 * tener un tipo menos.
	 **/
	private List<TypeDominio> tipos;

	/** Is Dialogo Propiedad (o dialogo lista valores). **/
	private boolean isDialogoPropiedad;

	/** Mostrar advertencia al guardar. **/
	private boolean mostrarAdvertencia = false;

	/** Max long parametros. */
	private static final int MAXLENGTH_PARAMETROS = 4000;

	/** Max long lista valores. */
	private static final int MAXLENGTH_LISTAVALORES = 4000;

	/** Se utiliza para indicar si ha habido algún cambio. **/
	private boolean identificadorCambiado;

	/** Lista de configuraciones. **/
	private List<ConfiguracionAutenticacion> configuraciones;

	/** Indica si es visible el botón de consultar **/
	private Boolean desactivarConsulta = false;

	/** Indica si hay cambios en el dominio **/
	private Boolean hayCambios = false;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		// No se puede comprobar antes porque puede que el dominio no tenga id (al ser
		// de mochila si viene de otra BBDD).
		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new Dominio();
			data.setParametros(new ArrayList<>());
			data.setListaFija(new ArrayList<>());
			data.setAmbito(TypeAmbito.fromString(ambito));
			data.setTipo(TypeDominio.CONSULTA_BD);
			data.setCache(TypeCache.CACHE_IMPLICITA);
		} else {
			data = dominioService.loadDominio(Long.valueOf(id));
			identificadorOriginal = data.getIdentificador();
			if (data.getParametros() == null) {
				data.setParametros(new ArrayList<>());
			}
			if (data.getListaFija() == null) {
				data.setListaFija(new ArrayList<>());
			}
			if (data.getTipo() == TypeDominio.FUENTE_DATOS && data.getIdFuenteDatos() != null) {
				idFuenteDato = data.getIdFuenteDatos();
			}

		}

		generarTipos();

		visibleJNDI = false;
		visibleLista = false;
		visibleRemoto = false;
		visibleFuente = false;

		if (this.data.getTipo() == TypeDominio.CONSULTA_BD) {
			visibleJNDI = true;
		} else if (this.data.getTipo() == TypeDominio.LISTA_FIJA) {
			visibleLista = true;
		} else if (this.data.getTipo() == TypeDominio.CONSULTA_REMOTA) {
			visibleRemoto = true;
		} else if (this.data.getTipo() == TypeDominio.FUENTE_DATOS) {
			visibleFuente = true;
		}

		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);

		if (typeAmbito == TypeAmbito.AREA) {
			fuentes = dominioService.listFuenteDato(TypeAmbito.AREA, Long.valueOf(idArea), null);
		}
		if (typeAmbito == TypeAmbito.ENTIDAD) {
			fuentes = dominioService.listFuenteDato(TypeAmbito.ENTIDAD, UtilJSF.getIdEntidad(), null);

		}

		final Long area = (idArea == null) ? null : Long.valueOf(idArea);
		configuraciones = configuracionAutenticacionService.listConfiguracionAutenticacion(typeAmbito, area,
				UtilJSF.getIdEntidad(), TypeIdioma.fromString(UtilJSF.getSessionBean().getLang()), null);
		final ConfiguracionAutenticacion configAutSinAutenticacion = new ConfiguracionAutenticacion();
		configAutSinAutenticacion.setCodigo(null);
		configAutSinAutenticacion.setIdentificador(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"));
		configuraciones.add(0, configAutSinAutenticacion);
		actualizarConf();

		if (id != null) {
			final List<DominioTramite> tramites = tramiteService.getTramiteVersionByDominio(Long.valueOf(id));
			if (tramites == null || tramites.isEmpty()) {
				mostrarAdvertencia = false;
			} else {
				mostrarAdvertencia = true;
			}
		}

	}

	/**
	 * Comprueba si puede guardar.
	 *
	 * @return
	 */
	public boolean permiteGuardar() {

		// Si está en modo consulta, no permite guardar
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo != null && modo == TypeModoAcceso.CONSULTA) {
			return false;
		}

		boolean res = false;
		final TypeAmbito ambitoType = TypeAmbito.fromString(ambito);
		switch (ambitoType) {
		case GLOBAL:
			// Entra como SuperAdmin
			res = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN);
			break;
		case ENTIDAD:
			res = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
			break;
		case AREA:

			if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT
					|| UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN) {
				res = true;
			} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

				if (modo == TypeModoAcceso.EDICION) {

					if (data == null || data.getArea() == null) {
						res = false;
					} else {
						final List<TypeRolePermisos> permisos = securityService
								.getPermisosDesarrolladorEntidadByArea(data.getArea().getCodigo());

						res = permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
								|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA);
					}
				}

				if (modo == TypeModoAcceso.ALTA) {
					res = true;
				}

			}

			break;
		}
		return res;

	}

	/** Genera los tipos dependiendo del ambito puede que quite uno. **/
	private void generarTipos() {
		/**
		 * Excepcionalidad pq el tipo global no debe tener el tipo FUENTE DATOS .
		 **/
		tipos = new ArrayList<>();
		tipos.add(TypeDominio.CONSULTA_BD);
		tipos.add(TypeDominio.LISTA_FIJA);
		tipos.add(TypeDominio.CONSULTA_REMOTA);
		if (TypeAmbito.fromString(ambito) != TypeAmbito.GLOBAL) {
			tipos.add(TypeDominio.FUENTE_DATOS);
		}
	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				if (isDialogoPropiedad) {
					// Refrescamos datos
					final Propiedad propiedad = (Propiedad) respuesta.getResult();
					boolean duplicado = false;

					for (final Propiedad prop : data.getParametros()) {
						if (prop.getCodigo().equals(propiedad.getCodigo())) {
							duplicado = true;
							break;
						}
					}

					if (duplicado) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
					} else {
						this.data.getParametros().add(propiedad);
						setHayCambios(true);
					}

				} else {
					// Refrescamos datos
					final Propiedad propiedad = (Propiedad) respuesta.getResult();
					boolean duplicado = false;

					for (final Propiedad prop : data.getListaFija()) {
						if (prop.getCodigo().equals(propiedad.getCodigo())) {
							duplicado = true;
							break;
						}
					}

					if (duplicado) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
					} else {
						this.data.getListaFija().add(propiedad);
					}
				}

				break;

			case EDICION:
				if (isDialogoPropiedad) {
					// Actualizamos fila actual
					final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
					boolean duplicado = false;

					// Muestra dialogo
					final int posicion = this.data.getParametros().indexOf(this.propiedadSeleccionada);

					for (final Propiedad prop : data.getParametros()) {
						if (prop.getCodigo().equals(propiedadEdicion.getCodigo())) {
							duplicado = true;
							break;
						}
					}

					if (duplicado && !propiedadSeleccionada.getCodigo().equals(propiedadEdicion.getCodigo())) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
					} else {
						this.data.getParametros().remove(posicion);
						this.data.getParametros().add(posicion, propiedadEdicion);
						if (!propiedadEdicion.getCodigo().equals(this.propiedadSeleccionada.getCodigo())) {
							setHayCambios(true);
						}
						this.propiedadSeleccionada = propiedadEdicion;

					}

				} else {
					// Actualizamos fila actual
					final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
					boolean duplicado = false;

					// Muestra dialogo
					final int posicion = this.data.getListaFija().indexOf(this.valorSeleccionado);

					for (final Propiedad prop : data.getListaFija()) {
						if (prop.getCodigo().equals(propiedadEdicion.getCodigo())) {
							duplicado = true;
							break;
						}
					}

					if (duplicado && !valorSeleccionado.getCodigo().equals(propiedadEdicion.getCodigo())) {
						addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
					} else {
						this.data.getListaFija().remove(posicion);
						this.data.getListaFija().add(posicion, propiedadEdicion);
						this.valorSeleccionado = propiedadEdicion;
					}

				}

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Retorno dialogo de seleccionar variable area.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoSeleccionVarArea(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			this.data.setUrl("{@@" + respuesta.getResult() + "@@}" + this.data.getUrl());
		}
	}

	/**
	 * Abre dialogo seleccionar variable área
	 *
	 * @param event respuesta dialogo
	 */
	public void seleccionarVariableArea() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.idArea);
		UtilJSF.openDialog(DialogSeleccionarVariableArea.class, TypeModoAcceso.CONSULTA, params, true, 800, 390);
	}

	/**
	 * Crea nueva propiedad.
	 */
	public void nuevaPropiedad() {
		isDialogoPropiedad = true;
		final Map<String, String> params = new HashMap<>();
		params.put("OCULTARVALOR", "S");
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, params, true, 430, 120);
	}

	/**
	 * Crea nueva propiedad.
	 */
	public void nuevaConfiguracion() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AREA.toString(), this.idArea);
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, TypeModoAcceso.ALTA, params, true, 550, 195);
	}

	/**
	 * Consultar configuración
	 */
	public void consultarConfiguracion() {

		if (this.data.getConfiguracionAutenticacion() != null) {
			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.AMBITO.toString(), ambito);
			if (TypeAmbito.AREA.toString().equals(ambito)) {
				params.put(TypeParametroVentana.AREA.toString(), this.idArea);
			} else if (TypeAmbito.ENTIDAD.toString().equals(ambito)) {
				params.put(TypeParametroVentana.ENTIDAD.toString(), this.idEntidad);
			}
			params.put(TypeParametroVentana.ID.toString(),
					this.data.getConfiguracionAutenticacion().getCodigo().toString());
			UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, TypeModoAcceso.CONSULTA, params, true, 550, 195);
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoConf(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			final String message = UtilJSF.getLiteral("info.alta.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// La ponemos por defecto
			final ConfiguracionAutenticacion conf = (ConfiguracionAutenticacion) ((DialogResult) event.getObject())
					.getResult();
			this.data.setConfiguracionAutenticacion(conf);
			this.configuraciones.add(conf);
		}
	}

	/**
	 * Crea nuevo valor.
	 */
	public void nuevoValor() {
		isDialogoPropiedad = false;
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, null, true, 430, 120);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarPropiedad() {

		if (!verificarFilaSeleccionada())
			return;

		isDialogoPropiedad = true;
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.propiedadSeleccionada));
		params.put("OCULTARVALOR", "S");
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.EDICION, params, true, 430, 100);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarValor() {

		if (!verificarFilaSeleccionadaValor())
			return;

		isDialogoPropiedad = false;
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.valorSeleccionado));
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.EDICION, params, true, 430, 120);
	}

	/**
	 * Quita una propiedad.
	 */
	public void quitarPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		this.data.getParametros().remove(this.propiedadSeleccionada);
		setHayCambios(true);

	}

	/**
	 * Quita una propiedad.
	 */
	public void quitarValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		this.data.getListaFija().remove(this.valorSeleccionado);

	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getParametros().indexOf(this.propiedadSeleccionada);
		if (posicion >= this.data.getParametros().size() - 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Propiedad propiedad = this.data.getParametros().remove(posicion);
		this.data.getParametros().add(posicion + 1, propiedad);

		for (int i = 0; i < this.data.getParametros().size(); i++) {
			this.data.getParametros().get(i).setOrden(i + 1);
		}
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		final int posicion = this.data.getListaFija().indexOf(this.valorSeleccionado);
		if (posicion >= this.data.getListaFija().size() - 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Propiedad propiedad = this.data.getListaFija().remove(posicion);
		this.data.getListaFija().add(posicion + 1, propiedad);
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getParametros().indexOf(this.propiedadSeleccionada);
		if (posicion <= 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.data.getParametros().remove(posicion);
		this.data.getParametros().add(posicion - 1, propiedad);

		for (int i = 0; i < this.data.getParametros().size(); i++) {
			this.data.getParametros().get(i).setOrden(i + 1);
		}
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		final int posicion = this.data.getListaFija().indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.data.getListaFija().remove(posicion);
		this.data.getListaFija().add(posicion - 1, propiedad);
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.propiedadSeleccionada == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaValor() {
		boolean filaSeleccionada = true;
		if (this.valorSeleccionado == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);

		if (acceso != TypeModoAcceso.CONSULTA) {
			final boolean correcto = guardar();
			if (!correcto) {
				return;
			}
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getIdentificador());
		if (noExisteVarArea()) {
			DialogResultMessage msg = new DialogResultMessage();
			msg.setNivel(TypeNivelGravedad.WARNING);
			msg.setMensaje(UtilJSF.getLiteral("variable.area.no.existe"));
			result.setMensaje(msg);
		} else if (data.getTipo().equals(TypeDominio.CONSULTA_REMOTA)
				&& data.getUrl().matches(".*\\{@@[A-Za-z0-9\\_\\-]{1,}@@\\}.*")) {
			DialogResultMessage msg = new DialogResultMessage();
			msg.setNivel(TypeNivelGravedad.INFO);
			msg.setMensaje(UtilJSF.getLiteral("variable.area.existe"));
			result.setMensaje(msg);
		}
		/*
		 * if (identificadorCambiado) { final DialogResultMessage dm = new
		 * DialogResultMessage(); dm.setNivel(TypeNivelGravedad.WARNING);
		 * if(UtilJSF.getIdioma().equals("es")) { dm.
		 * setMensaje("TODO:Pendiente controlar si se esta usando dominio para sacar aviso"
		 * ); }else { dm.
		 * setMensaje("TODO:Pendent controlar si s'està utilitzant domini per treure avis"
		 * ); } result.setMensaje(dm); }
		 */

		UtilJSF.closeDialog(result);
	}

	public boolean noExisteVarArea() {
		if (data.getAmbito().equals(TypeAmbito.AREA) && data.getTipo().equals(TypeDominio.CONSULTA_REMOTA)
				&& data.getUrl().matches(".*\\{@@[A-Za-z0-9\\_\\-]{1,}@@\\}.*")
				&& vaService.loadVariableAreaByIdentificador(
						data.getUrl().substring(this.data.getUrl().indexOf('{'), this.data.getUrl().indexOf('}') + 1)
								.replace("{@@", "").replace("@@}", ""),
						Long.valueOf(idArea)) == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indica si hay que mostrar el aviso de guardado. Si es true (consulta), no
	 * aparecerá el aviso mientras que si es false (alta/edición), aparecerá el
	 * aviso.
	 *
	 * @return
	 */
	public boolean isDisabledAvisoGuardado() {
		boolean mostrar;
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		if (acceso == TypeModoAcceso.CONSULTA) {
			mostrar = true;
		} else {
			mostrar = false;
		}
		return mostrar;
	}

	/**
	 * Realiza la acción de guardar
	 *
	 * @return
	 */
	private boolean guardar() {

		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		if (data.getSql() != null) {
			data.setSqlDecoded(new String(Base64.decodeBase64(data.getSql())));
		}

		identificadorCambiado = false;

		if (this.data.getTipo() == TypeDominio.FUENTE_DATOS) {

			if (idFuenteDato == null) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.fuenteDatos"));
				return false;
			} else {
				final FuenteDatos fuenteDato = dominioService.loadFuenteDato(idFuenteDato);
				this.data.setIdFuenteDatos(fuenteDato.getCodigo());
			}
		} else {
			this.data.setIdFuenteDatos(null);
		}

		// if (this.getData().getTipo() == TypeDominio.CONSULTA_REMOTA &&
		// this.configuracion != null) {
		// //final ConfiguracionAutenticacion configuracion =
		// configuracionAutenticacionService.getConfiguracionAutenticacion(idConfiguracion);
		// //this.data.setConfiguracionAutenticacion(configuracion);
		// this.data.setConfiguracionAutenticacion(configuracion);
		// }

		if (this.getData().getTipo() == TypeDominio.CONSULTA_REMOTA
				&& this.getData().getConfiguracionAutenticacion() != null
				&& this.getData().getConfiguracionAutenticacion().getCodigo() == null) {
			this.getData().setConfiguracionAutenticacion(null);
		}
		Long longIdArea = (idArea == null) ? null : Long.valueOf(idArea);
		switch (acceso) {
		case ALTA:
			if (!verificarGuardar()) {
				return false;
			}

			Long lIdEntidad = null;
			Long lIdArea = null;
			if (idEntidad != null) {
				lIdEntidad = Long.valueOf(idEntidad);
			}
			if (idArea != null) {
				lIdArea = Long.valueOf(idArea);
			}
			// Verifica unicidad codigo dominio
			if (dominioService.existeDominioByIdentificador(this.data.getAmbito(), this.data.getIdentificador(),
					UtilJSF.getIdEntidad(), longIdArea, null)) {
				Object[] valueHolder = new Object[2];
				valueHolder = mensaje();
				addMessageContext(TypeNivelGravedad.ERROR,
						UtilJSF.getLiteral((String) valueHolder[0], (Object[]) valueHolder[1]));
				return false;
			}
			// Alta dominio
			final Long idDominio = dominioService.addDominio(this.data, lIdEntidad, lIdArea);
			this.data.setCodigo(idDominio);
			break;
		case EDICION:
			if (!verificarGuardar()) {
				return false;
			}

			// Verifica unicidad codigo dominio
			if (dominioService.existeDominioByIdentificador(this.data.getAmbito(), this.data.getIdentificador(),
					UtilJSF.getIdEntidad(), longIdArea, this.data.getCodigo())) {
				addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.codigoRepetido"));
				return false;
			}
			// En caso de cambio de identificador hay que controlar si se esta
			// usando y
			// mostrar mensaje
			if (!StringUtils.equals(this.data.getIdentificador(), identificadorOriginal)) {
				// TODO Pendiente verificar si se puede
				identificadorCambiado = true;
			}
			// Realiza update
			dominioService.updateDominio(this.data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		return true;
	}

	/**
	 * Ping.
	 */
	public void ping() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		String idDominio;
		if (modo == TypeModoAcceso.CONSULTA) {
			idDominio = this.id;
		} else {
			if (hayCambios && !guardar()) {
				return;
			}
			hayCambios = false;
			idDominio = this.data.getCodigo().toString();
			modoAcceso = TypeModoAcceso.EDICION.toString();
		}
		realizarPing(idDominio);
	}

	public void actualizarConf() {
		desactivarConsulta = false;
		if (this.data.getConfiguracionAutenticacion() == null
				|| this.data.getConfiguracionAutenticacion().getIdentificadorCompuesto() == null) {
			desactivarConsulta = true;
		}
	}

	/**
	 * Realiza el ping.
	 *
	 * @param idDominio
	 */
	private void realizarPing(final String idDominio) {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), idDominio);
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		UtilJSF.openDialog(DialogDominioPing.class, TypeModoAcceso.CONSULTA, params, true, 770, 600);
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

	/**
	 * Cuando se actualiza el tipo (radiobutton), se actualiza el click.
	 */
	public void actualizarTipo() {
		visibleJNDI = false;
		visibleLista = false;
		visibleRemoto = false;
		visibleFuente = false;
		if (TypeDominio.CONSULTA_BD == this.data.getTipo()) {
			visibleJNDI = true;
		} else if (TypeDominio.CONSULTA_REMOTA == this.data.getTipo()) {
			visibleRemoto = true;
		} else if (TypeDominio.LISTA_FIJA == this.data.getTipo()) {
			visibleLista = true;
		} else if (TypeDominio.FUENTE_DATOS == this.data.getTipo()) {
			visibleFuente = true;
		}
	}

	/**
	 * Abre dialogo de tramites.
	 *
	 */
	public void tramites() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));
		if (ambito != null) {
			params.put(TypeParametroVentana.AMBITO.toString(), ambito);
			final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
			if (typeAmbito == TypeAmbito.AREA) {
				params.put("AREA", id);
			}
			if (typeAmbito == TypeAmbito.ENTIDAD) {
				params.put("ENTIDAD", id);
			}
		}
		UtilJSF.openDialog(DialogDominioTramites.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
	}

	/**
	 * Ver la estructura de la fuente de datos en modo consulta.
	 */
	public void fuentedatosEST() {

		if (this.idFuenteDato == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
		} else {

			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), this.idFuenteDato.toString());
			params.put(TypeParametroVentana.AMBITO.toString(), this.ambito);
			if (this.idArea != null) {
				params.put(TypeParametroVentana.AREA.toString(), this.idArea);
			}
			UtilJSF.openDialog(DialogFuente.class, TypeModoAcceso.CONSULTA, params, true, 740, 450);
		}

	}

	/**
	 * Ver los datos de la fuente de datos en modo consulta.
	 */
	public void fuentedatosDAT() {
		if (this.idFuenteDato == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
		} else {

			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), this.idFuenteDato.toString());
			UtilJSF.openDialog(DialogFuenteDatos.class, TypeModoAcceso.CONSULTA, params, true, 740, 330);
		}

	}

	/**
	 * Mostrar la fila de parámetros si no es de tipo lista fija
	 *
	 * @return
	 */
	public boolean mostrarParametros() {
		boolean mostrar = true;
		if (this.data.getTipo() == TypeDominio.LISTA_FIJA) {
			mostrar = false;
		}
		return mostrar;
	}

	/**
	 * Checkea si el tipo debe ocultarse o no.
	 *
	 * @param tipo
	 * @return
	 */
	public boolean checkTipo(final TypeDominio tipo) {
		boolean mostrar = false;
		if (TypeAmbito.fromString(ambito) == TypeAmbito.GLOBAL && tipo == TypeDominio.FUENTE_DATOS) {
			mostrar = true;
		}
		return mostrar;
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {
		if (TypeDominio.CONSULTA_REMOTA.equals(data.getTipo()) || TypeDominio.CONSULTA_BD.equals(data.getTipo())) {
			if (MAXLENGTH_PARAMETROS
					- (data.getParametros() == null ? 0 : UtilJSON.toJSON(data.getParametros()).length()) < 0) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.parametros.tamanyosuperado"));
				return false;
			}
		}

		if (TypeDominio.LISTA_FIJA.equals(data.getTipo())) {
			if (MAXLENGTH_LISTAVALORES
					- (data.getListaFija() == null ? 0 : UtilJSON.toJSON(data.getListaFija()).length()) < 0) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.lista.tamanyosuperado"));
				return false;
			}
		}

		return true;
	}

	public boolean isAmbitoArea() {
		if (TypeAmbito.AREA.toString().equals(ambito)) {
			return true;
		} else {
			return false;
		}
	}

	public Object[] mensaje() {
		Long longIdArea = (idArea == null) ? null : Long.valueOf(idArea);
		final Dominio dataNuevo = dominioService.loadDominioByIdentificador(this.data.getAmbito(),
				this.data.getIdentificador(), UtilJSF.getIdEntidad(), longIdArea, this.data.getCodigo());
		final Object[] propiedades = new Object[2];
		final Object[] valueHolder = new Object[2];
		if (dataNuevo != null && dataNuevo.getAmbito() == TypeAmbito.AREA && dataNuevo.getArea() != null
				&& dataNuevo.getArea().getIdentificador() != null) {
			propiedades[0] = dataNuevo.getArea().getCodigoDIR3Entidad();
			propiedades[1] = dataNuevo.getArea().getIdentificador();
			valueHolder[0] = "dialogDominio.error.duplicated.area";
			valueHolder[1] = propiedades;
		} else if (dataNuevo != null && dataNuevo.getAmbito() == TypeAmbito.ENTIDAD && dataNuevo.getEntidad() != null) {
			propiedades[0] = entidadService.loadEntidad(dataNuevo.getEntidad()).getCodigoDIR3();
			valueHolder[0] = "dialogDominio.error.duplicated.entidad";
			valueHolder[1] = propiedades;
		} else {
			valueHolder[0] = "dialogDominio.error.codigoRepetido";
			valueHolder[1] = null;
		}
		return valueHolder;
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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public Dominio getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final Dominio data) {
		this.data = data;
	}

	// ------- GETTERS / SETTERS --------------------------------
	/**
	 * @return the propiedadSeleccionada
	 */
	public Propiedad getPropiedadSeleccionada() {
		return propiedadSeleccionada;
	}

	/**
	 * @param propiedadSeleccionada the propiedadSeleccionada to set
	 */
	public void setPropiedadSeleccionada(final Propiedad propiedadSeleccionada) {
		this.propiedadSeleccionada = propiedadSeleccionada;
	}

	/**
	 * @return the visibleJNDI
	 */
	public boolean isVisibleJNDI() {
		return visibleJNDI;
	}

	/**
	 * @param visibleJNDI the visibleJNDI to set
	 */
	public void setVisibleJNDI(final boolean visibleJNDI) {
		this.visibleJNDI = visibleJNDI;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public Propiedad getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final Propiedad valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the visibleLista
	 */
	public boolean isVisibleLista() {
		return visibleLista;
	}

	/**
	 * @param visibleLista the visibleLista to set
	 */
	public void setVisibleLista(final boolean visibleLista) {
		this.visibleLista = visibleLista;
	}

	/**
	 * @return the visibleRemoto
	 */
	public boolean isVisibleRemoto() {
		return visibleRemoto;
	}

	/**
	 * @param visibleRemoto the visibleRemoto to set
	 */
	public void setVisibleRemoto(final boolean visibleRemoto) {
		this.visibleRemoto = visibleRemoto;
	}

	/**
	 * @return the visibleFuente
	 */
	public boolean isVisibleFuente() {
		return visibleFuente;
	}

	/**
	 * @param visibleFuente the visibleFuente to set
	 */
	public void setVisibleFuente(final boolean visibleFuente) {
		this.visibleFuente = visibleFuente;
	}

	/**
	 * @return the fuentes
	 */
	public List<FuenteDatos> getFuentes() {
		return fuentes;
	}

	/**
	 * @param fuentes the fuentes to set
	 */
	public void setFuentes(final List<FuenteDatos> fuentes) {
		this.fuentes = fuentes;
	}

	/**
	 * @return the tipos
	 */
	public List<TypeDominio> getTipos() {
		return tipos;
	}

	/**
	 * @param tipos the tipos to set
	 */
	public void setTipos(final List<TypeDominio> tipos) {
		this.tipos = tipos;
	}

	/**
	 * @return the dominioService
	 */
	public DominioService getDominioService() {
		return dominioService;
	}

	/**
	 * @param dominioService the dominioService to set
	 */
	public void setDominioService(final DominioService dominioService) {
		this.dominioService = dominioService;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad the idEntidad to set
	 */
	public void setIdEntidad(final String idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * @return the idFuenteDato
	 */
	public Long getIdFuenteDato() {
		return idFuenteDato;
	}

	/**
	 * @param idFuenteDato the idFuenteDato to set
	 */
	public void setIdFuenteDato(final Long idFuenteDato) {
		this.idFuenteDato = idFuenteDato;
	}

	/**
	 * @return the mostrarAdvertencia
	 */
	public boolean isMostrarAdvertencia() {
		return mostrarAdvertencia;
	}

	/**
	 * @return the mostrarAdvertencia
	 */
	public boolean getMostrarAdvertencia() {
		return mostrarAdvertencia;
	}

	/**
	 * @param mostrarAdvertencia the mostrarAdvertencia to set
	 */
	public void setMostrarAdvertencia(final boolean mostrarAdvertencia) {
		this.mostrarAdvertencia = mostrarAdvertencia;
	}

	/**
	 * @return the configuraciones
	 */
	public List<ConfiguracionAutenticacion> getConfiguraciones() {
		return configuraciones;
	}

	/**
	 * @param configuraciones the configuraciones to set
	 */
	public void setConfiguraciones(final List<ConfiguracionAutenticacion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("dominioDialog");
	}

	public Boolean getDesactivarConsulta() {
		return desactivarConsulta;
	}

	public void setDesactivarConsulta(final Boolean desactivarConsulta) {
		this.desactivarConsulta = desactivarConsulta;
	}

	public final Boolean getHayCambios() {
		return hayCambios;
	}

	public final void setHayCambios(Boolean hayCambios) {
		this.hayCambios = hayCambios;
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update("dialogDominio:inputEscondida");
	}

}
