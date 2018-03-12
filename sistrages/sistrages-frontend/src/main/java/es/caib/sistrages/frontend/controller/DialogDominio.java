package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Fuente;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilJSON;

@ManagedBean
@ViewScoped
public class DialogDominio extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

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

	/** Lista de propiedades. **/
	private List<Propiedad> propiedades;

	/** Lista valores. **/
	private List<Propiedad> listaValores;

	/** Lista con las fuentes de datos. **/
	private List<Fuente> fuentes;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Dominio();
			propiedades = new ArrayList<>();
			listaValores = new ArrayList<>();

		} else {
			data = new Dominio();// dominioGlobalService.load(id);
			data.setId(Long.valueOf(id));
			data.setCodigo("Entidad 1");
			data.setDescripcion("Descripc");
			data.setCacheable(true);
			data.setTipo(TypeDominio.CONSULTA_BD);

			// las propiedades se leerían del JSON
			propiedades = new ArrayList<>();
			final Propiedad p1 = new Propiedad();
			p1.setCodigo("COD 1");
			p1.setValor("VAL 1");
			p1.setOrden(1);
			propiedades.add(p1);
			final Propiedad p2 = new Propiedad();
			p2.setCodigo("COD 2");
			p2.setValor("VAL 2");
			p2.setOrden(2);
			propiedades.add(p2);

			// la lista se leería del JSON
			listaValores = new ArrayList<>();
			final Propiedad lista1 = new Propiedad();
			lista1.setCodigo("COD L 1");
			lista1.setValor("VAL 1");
			listaValores.add(lista1);
			final Propiedad lista2 = new Propiedad();
			lista2.setCodigo("COD L 2");
			lista2.setValor("VAL 2");
			listaValores.add(lista2);
		}

		visibleJNDI = true;
		visibleLista = false;
		visibleRemoto = false;
		visibleFuente = false;

		fuentes = new ArrayList<>();
		final Fuente f1 = new Fuente();
		f1.setCodigo("Fuente 1");
		f1.setDescripcion("Descripcion");
		f1.setId(1l);
		final Fuente f2 = new Fuente();
		f2.setCodigo("Fuente 2");
		f2.setDescripcion("Descripcion 2");
		f2.setId(2l);
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

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				if (isDialogoPropiedad) {
					// Refrescamos datos
					final Propiedad propiedad = (Propiedad) respuesta.getResult();
					this.propiedades.add(propiedad);
				} else {
					// Refrescamos datos
					final Propiedad propiedad = (Propiedad) respuesta.getResult();
					this.listaValores.add(propiedad);
				}
				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:
				if (isDialogoPropiedad) {
					// Actualizamos fila actual
					final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
					// Muestra dialogo
					final int posicion = this.propiedades.indexOf(this.propiedadSeleccionada);

					this.propiedades.remove(posicion);
					this.propiedades.add(posicion, propiedadEdicion);
					this.propiedadSeleccionada = propiedadEdicion;

				} else {
					// Actualizamos fila actual
					final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
					// Muestra dialogo
					final int posicion = this.listaValores.indexOf(this.valorSeleccionado);

					this.listaValores.remove(posicion);
					this.listaValores.add(posicion, propiedadEdicion);
					this.valorSeleccionado = propiedadEdicion;

				}

				// Mensaje
				message = UtilJSF.getLiteral("info.modificado.ok");
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
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
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, null, true, 430, 120);
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
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.EDICION, params, true, 430, 120);
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

		this.propiedades.remove(this.propiedadSeleccionada);

	}

	/**
	 * Quita una propiedad.
	 */
	public void quitarValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		this.listaValores.remove(this.valorSeleccionado);

	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.propiedades.indexOf(this.propiedadSeleccionada);
		if (posicion >= this.propiedades.size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Propiedad propiedad = this.propiedades.remove(posicion);
		this.propiedades.add(posicion + 1, propiedad);

		for (int i = 0; i < this.propiedades.size(); i++) {
			this.propiedades.get(i).setOrden(i + 1);
		}
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		final int posicion = this.listaValores.indexOf(this.valorSeleccionado);
		if (posicion >= this.listaValores.size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Propiedad propiedad = this.listaValores.remove(posicion);
		this.listaValores.add(posicion + 1, propiedad);
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.propiedades.indexOf(this.propiedadSeleccionada);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.propiedades.remove(posicion);
		this.propiedades.add(posicion - 1, propiedad);

		for (int i = 0; i < this.propiedades.size(); i++) {
			this.propiedades.get(i).setOrden(i + 1);
		}
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		final int posicion = this.listaValores.indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.listaValores.remove(posicion);
		this.listaValores.add(posicion - 1, propiedad);
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
			/*
			 * if (dominioGlobalService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; } dominioGlobalService.add(data);
			 */

			break;
		case EDICION:
			// dominioGlobalService.update(data);
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

	public void mensaje() {
		UtilJSF.showMessageDialog(TypeNivelGravedad.INFO, "Atento", "Ojo al dato.");
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Dominio getData() {
		return data;
	}

	public void setData(final Dominio data) {
		this.data = data;
	}

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
	 * @return the propiedades
	 */
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades
	 *            the propiedades to set
	 */
	public void setPropiedades(final List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}

	/**
	 * @return the listaValores
	 */
	public List<Propiedad> getListaValores() {
		return listaValores;
	}

	/**
	 * @param listaValores
	 *            the listaValores to set
	 */
	public void setListaValores(final List<Propiedad> listaValores) {
		this.listaValores = listaValores;
	}

	/**
	 * @return the fuentes
	 */
	public List<Fuente> getFuentes() {
		return fuentes;
	}

	/**
	 * @param fuentes
	 *            the fuentes to set
	 */
	public void setFuentes(final List<Fuente> fuentes) {
		this.fuentes = fuentes;
	}

}
