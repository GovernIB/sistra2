package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominio extends DialogControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

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

	/**
	 * Tipos. Es el único enumerado que funciona así, porque el tipo GLOBAL debe
	 * tener un tipo menos.
	 **/
	private List<TypeDominio> tipos;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Dominio();
			data.setParametros(new ArrayList<>());
			data.setListaFija(new ArrayList<>());
			data.setAmbito(TypeAmbito.fromString(ambito));
			data.setTipo(TypeDominio.CONSULTA_BD);
		} else {
			data = dominioService.loadDominio(Long.valueOf(id));
			if (data.getParametros() == null) {
				data.setParametros(new ArrayList<>());
			}
			if (data.getListaFija() == null) {
				data.setListaFija(new ArrayList<>());
			}
		}

		/** Excepcionalidad pq el tipo global no debe tener el tipo FUENTE DATOS . **/
		tipos = new ArrayList<>();
		tipos.add(TypeDominio.CONSULTA_BD);
		tipos.add(TypeDominio.LISTA_FIJA);
		tipos.add(TypeDominio.CONSULTA_REMOTA);
		if (TypeAmbito.fromString(ambito) != TypeAmbito.GLOBAL) {
			tipos.add(TypeDominio.FUENTE_DATOS);
		}

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
		fuentes = new ArrayList<>();
		final FuenteDatos f1 = new FuenteDatos();
		f1.setIdentificador("Fuente 1");
		f1.setDescripcion("Descripcion");
		f1.setCodigo(1l);
		final FuenteDatos f2 = new FuenteDatos();
		f2.setIdentificador("Fuente 2");
		f2.setDescripcion("Descripcion 2");
		f2.setCodigo(2l);
		fuentes.add(f1);
		fuentes.add(f2);

	}

	/** Is Dialogo Propiedad (o dialogo lista valores). **/
	boolean isDialogoPropiedad;

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				if (isDialogoPropiedad) {
					// Refrescamos datos
					final Propiedad propiedad = (Propiedad) respuesta.getResult();
					this.data.getParametros().add(propiedad);
				} else {
					// Refrescamos datos
					final Propiedad propiedad = (Propiedad) respuesta.getResult();
					this.data.getListaFija().add(propiedad);
				}

				break;

			case EDICION:
				if (isDialogoPropiedad) {
					// Actualizamos fila actual
					final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
					// Muestra dialogo
					final int posicion = this.data.getParametros().indexOf(this.propiedadSeleccionada);

					this.data.getParametros().remove(posicion);
					this.data.getParametros().add(posicion, propiedadEdicion);
					this.propiedadSeleccionada = propiedadEdicion;

				} else {
					// Actualizamos fila actual
					final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
					// Muestra dialogo
					final int posicion = this.data.getListaFija().indexOf(this.valorSeleccionado);

					this.data.getListaFija().remove(posicion);
					this.data.getListaFija().add(posicion, propiedadEdicion);
					this.valorSeleccionado = propiedadEdicion;

				}

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Refresca la cache
	 */
	public void refrescarCache() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Crea nueva propiedad.
	 */
	public void nuevaPropiedad() {
		isDialogoPropiedad = true;
		final Map<String, String> params = new HashMap<>();
		params.put("OCULTARVALOR", "S");
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, null, true, 430, 100);
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
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
		switch (acceso) {
		case ALTA:
			Long lIdEntidad = null;
			Long lIdArea = null;
			if (idEntidad != null) {
				lIdEntidad = Long.valueOf(idEntidad);
			}
			if (idArea != null) {
				lIdArea = Long.valueOf(idArea);
			}
			dominioService.addDominio(this.data, lIdEntidad, lIdArea);
			break;
		case EDICION:
			dominioService.updateDominio(this.data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getCodigo());
		UtilJSF.closeDialog(result);
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param data
	 *            the data to set
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
	 * @param propiedadSeleccionada
	 *            the propiedadSeleccionada to set
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
	 * @param visibleJNDI
	 *            the visibleJNDI to set
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
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
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
	 * @param visibleLista
	 *            the visibleLista to set
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
	 * @param visibleRemoto
	 *            the visibleRemoto to set
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
	 * @param visibleFuente
	 *            the visibleFuente to set
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
	 * @param fuentes
	 *            the fuentes to set
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
	 * @param tipos
	 *            the tipos to set
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
	 * @param dominioService
	 *            the dominioService to set
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
	 * @param ambito
	 *            the ambito to set
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
	 * @param idArea
	 *            the idArea to set
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
	 * @param idEntidad
	 *            the idEntidad to set
	 */
	public void setIdEntidad(final String idEntidad) {
		this.idEntidad = idEntidad;
	}

}
