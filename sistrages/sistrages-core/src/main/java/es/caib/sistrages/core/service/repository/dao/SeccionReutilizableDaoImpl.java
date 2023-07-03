package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.HistorialSeccionReutilizable;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.comun.ValorIdentificadorCompuesto;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.service.component.ValidadorComponent;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JElementoFormulario;
import es.caib.sistrages.core.service.repository.model.JEntidad;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JHistorialSeccionReutilizable;
import es.caib.sistrages.core.service.repository.model.JLineaFormulario;
import es.caib.sistrages.core.service.repository.model.JPaginaFormulario;
import es.caib.sistrages.core.service.repository.model.JScriptSeccionReutilizable;
import es.caib.sistrages.core.service.repository.model.JSeccionReutilizable;

/**
 * La clase SeccionReutilizableDaoImpl.
 */
@Repository("seccionReutilizableDao")
public class SeccionReutilizableDaoImpl implements SeccionReutilizableDao {

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	ValidadorComponent validadorComponent;

	/** Literal. **/
	private static final String LITERAL_FALTAIDENTIFICADOR = "Falta el identificador";

	@Override
	public List<SeccionReutilizable> listSeccionReutilizable(Long idEntidad, String filtro, Boolean activo) {
		final List<SeccionReutilizable> listaSecciones = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"select a.codigo, a.identificador, a.descripcion, a.release, a.bloqueado, a.bloqueadoUsuario, (select max(hist.fecha) from JHistorialSeccionReutilizable hist where hist.seccionReutilizable.codigo = a.codigo ) from JSeccionReutilizable as a");

		sql.append(" where a.entidad.codigo = :idEntidad ");
		if (StringUtils.isNotBlank(filtro)) {
			sql.append("  AND (LOWER(a.descripcion) LIKE :filtro OR LOWER(a.identificador) LIKE :filtro) ");
		}
		if (activo != null) {
			sql.append(" AND a.activa = :activo ");
		}
		sql.append("  order by a.identificador, a.codigo");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idEntidad", idEntidad);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}
		if (activo != null) {
			query.setParameter("activo", activo);
		}
		final List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final Object[] jseccion : results) {
				SeccionReutilizable seccion = new SeccionReutilizable();
				seccion.setCodigo((Long) jseccion[0]);
				seccion.setIdentificador((String) jseccion[1]);
				seccion.setDescripcion((String) jseccion[2]);
				seccion.setRelease((Integer) jseccion[3]);
				seccion.setBloqueado((Boolean) jseccion[4]);
				seccion.setBloqueadoUsuario((String) jseccion[5]);
				seccion.setFecha((Date) jseccion[6]);
				listaSecciones.add(seccion);
			}
		}
		return listaSecciones;
	}

	@Override
	public SeccionReutilizable getSeccionReutilizable(Long id) {
		SeccionReutilizable seccion = null;
		if (id == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		final JSeccionReutilizable jSeccion = entityManager.find(JSeccionReutilizable.class, id);
		if (jSeccion == null) {
			throw new NoExisteDato("No existe la seccion " + id);
		} else {
			seccion = jSeccion.toModel();
		}

		return seccion;
	}

	@Override
	public SeccionReutilizable addSeccion(Long idEntidad, SeccionReutilizable seccion, final Long idFormulario,
			final String username) {
		JSeccionReutilizable jseccion = JSeccionReutilizable.fromModel(seccion);
		final JFormulario jformulario = entityManager.find(JFormulario.class, idFormulario);
		final JEntidad jentidad = entityManager.find(JEntidad.class, idEntidad);
		jseccion.setFormularioAsociado(jformulario);
		jseccion.setEntidad(jentidad);
		jseccion.setRelease(0);
		jseccion.setBloqueadoUsuario(username);
		jseccion.setBloqueado(true);
		jseccion.setActiva(seccion.isActivado());
		entityManager.persist(jseccion);

		// Anyadimos un historial
		this.anyadirHistorial(jseccion.getCodigo(), username, TypeAccionHistorial.CREACION, null);
		return jseccion.toModel();
	}

	@Override
	public boolean removeSeccion(Long id) {
		if (id == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		// Borramos el historial
		final String sql = "delete From JHistorialSeccionReutilizable t where t.seccionReutilizable.id = :idSeccionReutilizable";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSeccionReutilizable", id);
		query.executeUpdate();

		// Borramos los scripts
		final String sqlScripts = "delete From JScriptSeccionReutilizable t where t.seccionReutilizable.id = :idSeccionReutilizable";
		final Query queryScripts = entityManager.createQuery(sqlScripts);
		queryScripts.setParameter("idSeccionReutilizable", id);
		queryScripts.executeUpdate();

		final JSeccionReutilizable jSeccion = entityManager.find(JSeccionReutilizable.class, id);
		if (jSeccion == null) {
			throw new NoExisteDato("No existe la seccion " + id);
		}

		entityManager.remove(jSeccion.getFormularioAsociado());
		entityManager.remove(jSeccion);
		return true;
	}

	@Override
	public void updateSeccionReutilizable(SeccionReutilizable seccion, List<ScriptSeccionReutilizable> scripts) {
		final JSeccionReutilizable jseccion = entityManager.find(JSeccionReutilizable.class, seccion.getCodigo());
		jseccion.setDescripcion(seccion.getDescripcion());
		jseccion.setActiva(seccion.isActivado());
		entityManager.merge(jseccion);
		entityManager.flush();
		updateScriptsSeccionReutilizable(scripts, jseccion);
	}

	private void updateScriptsSeccionReutilizable(List<ScriptSeccionReutilizable> scripts,
			JSeccionReutilizable jseccion) {
		// Primero hay que borrar las que se han borrado o no se utilizan
		StringBuilder sql = new StringBuilder(
				"select a from JScriptSeccionReutilizable as a where a.seccionReutilizable.codigo = :idSeccion ");
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idSeccion", jseccion.getCodigo());
		List<JScriptSeccionReutilizable> jscripts = query.getResultList();
		List<JScriptSeccionReutilizable> jscriptsBorrar = new ArrayList<>();
		if (jscripts != null) {
			for (JScriptSeccionReutilizable jscript : jscripts) {
				for (ScriptSeccionReutilizable script : scripts) {
					if (script.getTipoScript().toString().equalsIgnoreCase(jscript.getTipoScript())) {
						if ((script.getScript() == null)
								|| (script.getScript() != null && script.getScript().estaVacio())) {
							jscriptsBorrar.add(jscript);
						}
						break;
					}
				}
			}
		}

		// Borramos los jscripts
		for (JScriptSeccionReutilizable jscriptBorrar : jscriptsBorrar) {
			entityManager.remove(jscriptBorrar);
			entityManager.flush();
		}

		if (scripts != null) {
			for (ScriptSeccionReutilizable script : scripts) {
				// Solo guardar si está relleno
				if (script.getScript() != null && !script.getScript().estaVacio()) {
					JScriptSeccionReutilizable jscript = JScriptSeccionReutilizable.fromModel(script);
					jscript.setSeccionReutilizable(jseccion);
					if (jscript.getCodigo() == null) {
						entityManager.persist(jscript);
					} else {
						entityManager.merge(jscript);
					}
				}
			}
		}
	}

	@Override
	public void bloquearSeccion(Long idSeccion, String username) {
		final JSeccionReutilizable jseccion = entityManager.find(JSeccionReutilizable.class, idSeccion);
		if (!jseccion.isBloqueado()) {
			jseccion.setBloqueado(true);
			jseccion.setBloqueadoUsuario(username);
			entityManager.merge(jseccion);

			this.anyadirHistorial(idSeccion, username, TypeAccionHistorial.BLOQUEAR, null);

		}

	}

	@Override
	public void desbloquearSeccion(Long idSeccion, String username, String detalle) {
		final JSeccionReutilizable jSeccion = entityManager.find(JSeccionReutilizable.class, idSeccion);
		if (jSeccion.isBloqueado()) {
			jSeccion.setBloqueado(false);
			jSeccion.setBloqueadoUsuario("");
			jSeccion.setRelease(jSeccion.getRelease() + 1);
			final String huella = getHuella();
			jSeccion.setHuella(huella);
			entityManager.merge(jSeccion);

			this.anyadirHistorial(idSeccion, username, TypeAccionHistorial.DESBLOQUEAR, detalle);
		}
	}

	/**
	 * Genera una huella de 15 caracteres aleatorios y 5 numeros
	 *
	 * @return
	 */
	private String getHuella() {
		final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		final StringBuilder sb = new StringBuilder(15);
		final Random random = new Random();
		for (int i = 0; i < 15; i++) {
			final char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		final String output = sb.toString();
		final int numero = random.nextInt(99999);

		return output + numero;
	}

	@Override
	public List<HistorialSeccionReutilizable> listHistorialSeccionReutilizable(Long idSeccion, String filtro) {
		final List<HistorialSeccionReutilizable> listaHistorial = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select a from JHistorialSeccionReutilizable as a");

		sql.append(" where a.seccionReutilizable.codigo = :idSeccion ");
		if (StringUtils.isNotBlank(filtro)) {
			sql.append("  AND (LOWER(a.descripcion) LIKE :filtro OR LOWER(a.identificador) LIKE :filtro) ");
		}

		sql.append("  order by a.codigo");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idSeccion", idSeccion);

		if (StringUtils.isNotBlank(filtro)) {
			query.setParameter("filtro", "%" + filtro.toLowerCase() + "%");
		}

		final List<JHistorialSeccionReutilizable> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JHistorialSeccionReutilizable jhistorialseccion : results) {
				listaHistorial.add(jhistorialseccion.toModel());
			}
		}
		return listaHistorial;
	}

	@Override
	public HistorialSeccionReutilizable getHistorialSeccionReutilizable(Long id) {
		HistorialSeccionReutilizable historialSeccion = null;
		if (id == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		final JHistorialSeccionReutilizable jhistorial = entityManager.find(JHistorialSeccionReutilizable.class, id);
		if (jhistorial == null) {
			throw new NoExisteDato("No existe el historial seccion " + id);
		} else {
			historialSeccion = jhistorial.toModel();
		}

		return historialSeccion;
	}

	@Override
	public void actualizarFechaSeccion(final Long id, final String username, final String literal,
			final TypeAccionHistorial accion) {
		if (id == null) {
			throw new FaltanDatosException(this.LITERAL_FALTAIDENTIFICADOR);
		}

		final JSeccionReutilizable jseccionReutilizable = entityManager.find(JSeccionReutilizable.class, id);
		final JHistorialSeccionReutilizable historial = new JHistorialSeccionReutilizable();
		historial.setFecha(new java.util.Date());
		historial.setUsuario(username);
		historial.setTipoAccion(accion.toString());
		if (literal == null || literal.isEmpty()) {
			historial.setDetalleCambio(" ");
		} else {
			historial.setDetalleCambio(literal);
		}
		historial.setSeccionReutilizable(jseccionReutilizable);
		historial.setRelease(jseccionReutilizable.getRelease());
		historial.setHuella(jseccionReutilizable.getHuella());
		entityManager.persist(historial);
	}

	@Override
	public void borradoHistorial(final Long idSeccionReutilizable, final String username) {

		// Borramos el historial
		final String sql = "delete From JHistorialSeccionReutilizable t where t.seccionReutilizable.id = :idSeccionReutilizable";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("idSeccionReutilizable", idSeccionReutilizable);
		query.executeUpdate();

		// Ponemos la release a 1
		final JSeccionReutilizable jseccionTramite = entityManager.find(JSeccionReutilizable.class,
				idSeccionReutilizable);
		jseccionTramite.setRelease(1);
		entityManager.persist(jseccionTramite);

		// Anyadimos un historial
		this.anyadirHistorial(idSeccionReutilizable, username, TypeAccionHistorial.REINICIADO, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.service.repository.dao.HistorialVersionDao#add(java.
	 * lang.Long, es.caib.sistrages.core.api.model.Tramite)
	 */
	@Override
	public void anyadirHistorial(final Long idSeccionReutilizable, final String username,
			final TypeAccionHistorial accion, final String detalleCambio) {
		if (idSeccionReutilizable == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}

		final JSeccionReutilizable jseccion = entityManager.find(JSeccionReutilizable.class, idSeccionReutilizable);
		final JHistorialSeccionReutilizable historial = new JHistorialSeccionReutilizable();
		historial.setFecha(new java.util.Date());
		historial.setUsuario(username);
		historial.setTipoAccion(accion.toString());
		if (detalleCambio == null || detalleCambio.isEmpty()) {
			historial.setDetalleCambio(" ");
		} else {
			historial.setDetalleCambio(detalleCambio);
		}
		historial.setSeccionReutilizable(jseccion);
		historial.setRelease(jseccion.getRelease());
		historial.setHuella(jseccion.getHuella());
		entityManager.persist(historial);
	}

	@Override
	public boolean existeIdentificador(Long idEntidad, String identificador) {

		StringBuilder sql = new StringBuilder("select count(a) from JSeccionReutilizable as a");
		sql.append(" where a.entidad.codigo = :idEntidad AND LOWER(a.identificador) LIKE :identificador escape '@' ");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idEntidad", idEntidad);
		query.setParameter("identificador", identificador.toLowerCase().replaceAll("_", "@_"));

		final Long total = (Long) query.getSingleResult();
		return total > 0l;
	}

	@Override
	public SeccionReutilizable getSeccionReutilizableByIdentificador(TypeAmbito ambito, String identificador,
			Long idEntidad, Long idArea) {

		final StringBuilder sql = new StringBuilder(
				"select d from JSeccionReutilizable d where d.entidad.codigo = :idEntidad and d.identificador like :identificador");
		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificador);
		query.setParameter("idEntidad", idEntidad);
		SeccionReutilizable seccionReutilizable = null;
		final List<JSeccionReutilizable> jsecciones = query.getResultList();
		if (jsecciones != null && !jsecciones.isEmpty()) {
			seccionReutilizable = jsecciones.get(0).toModel();
		}
		return seccionReutilizable;
	}

	/**
	 * Sólo importa el código, el formulario importado va por otro lado.
	 */
	@Override
	public Long importar(FilaImportarSeccion filaSeccion, Long idEntidad, Long idFormularioAsociado) {
		// Si es reemplazar, hacemos la acción.
		if (filaSeccion.getAccion() == TypeImportarAccion.CREAR) {

			JSeccionReutilizable seccionAlmacenar = JSeccionReutilizable.fromModel(filaSeccion.getSeccion());
			seccionAlmacenar.setCodigo(null);
			final JEntidad jEntidad = entityManager.find(JEntidad.class, idEntidad);
			seccionAlmacenar.setEntidad(jEntidad);
			JFormulario formularioAsociado = entityManager.find(JFormulario.class, idFormularioAsociado);
			seccionAlmacenar.setFormularioAsociado(formularioAsociado);
			entityManager.persist(seccionAlmacenar);
			entityManager.flush();

			if (filaSeccion.getScripts() != null && !filaSeccion.getScripts().isEmpty()) {
				importarSeccionesScripts(seccionAlmacenar, filaSeccion.getScripts());
			}

			return seccionAlmacenar.getCodigo();

		} else if (filaSeccion.getAccion() == TypeImportarAccion.REEMPLAZAR) {

			JSeccionReutilizable seccionAlmacenar = entityManager.find(JSeccionReutilizable.class,
					filaSeccion.getSeccionActual().getCodigo());
			seccionAlmacenar.setBloqueado(false);
			seccionAlmacenar.setBloqueadoUsuario("");
			seccionAlmacenar.setDescripcion(filaSeccion.getSeccion().getDescripcion());
			seccionAlmacenar.setHuella(filaSeccion.getSeccion().getHuella());
			seccionAlmacenar.setRelease(filaSeccion.getSeccion().getRelease());
			entityManager.merge(seccionAlmacenar);
			entityManager.flush();

			// Borramos todos los scripts que tenga
			final List<ScriptSeccionReutilizable> listaSecciones = new ArrayList<>();
			StringBuilder sql = new StringBuilder(
					"select a from JScriptSeccionReutilizable as a where a.seccionReutilizable.codigo = :idSeccionReutilizable order by a.codigo");

			final Query query = entityManager.createQuery(sql.toString());
			query.setParameter("idSeccionReutilizable", seccionAlmacenar.getCodigo());

			final List<JScriptSeccionReutilizable> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				for (JScriptSeccionReutilizable jscript : results) {
					entityManager.remove(jscript);
				}
			}

			if (filaSeccion.getScripts() != null && !filaSeccion.getScripts().isEmpty()) {
				importarSeccionesScripts(seccionAlmacenar, filaSeccion.getScripts());
			}

			return seccionAlmacenar.getCodigo();
		} else { // MANTENER o NADA
			return filaSeccion.getSeccionActual().getCodigo();
		}
	}

	private void importarSeccionesScripts(JSeccionReutilizable jseccion, List<ScriptSeccionReutilizable> scripts) {
		for (ScriptSeccionReutilizable script : scripts) {
			JScriptSeccionReutilizable jscript = JScriptSeccionReutilizable.fromModel(script);
			jscript.setSeccionReutilizable(jseccion);
			jscript.setCodigo(null);
			jscript.getScript().setCodigo(null);
			entityManager.merge(jscript);
			entityManager.flush();
		}
	}

	@Override
	public List<ScriptSeccionReutilizable> getScriptsByIdSeccionReutilizable(Long idSeccionReutilizable) {
		final List<ScriptSeccionReutilizable> listaSecciones = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"select a from JScriptSeccionReutilizable as a where a.seccionReutilizable.codigo = :idSeccionReutilizable order by a.codigo");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("idSeccionReutilizable", idSeccionReutilizable);

		final List<JScriptSeccionReutilizable> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JScriptSeccionReutilizable jscript : results) {
				listaSecciones.add(jscript.toModel());
			}
		}
		return listaSecciones;
	}

	@Override
	public ScriptSeccionReutilizable getScriptById(Long idScript) {
		if (idScript == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}
		final JScriptSeccionReutilizable jseccion = entityManager.find(JScriptSeccionReutilizable.class, idScript);
		if (jseccion == null) {
			throw new FaltanDatosException(LITERAL_FALTAIDENTIFICADOR);
		}
		return jseccion.toModel();
	}

	@Override
	public List<Dominio> getDominiosByIdentificadorSeccion(String identificadorSeccion) {
		List<Dominio> dominios = new ArrayList<>();
		JFormulario formularioAsociado = getJFormularioByIdentificadorSeccion(identificadorSeccion);
		// Recorremos los elementos de un formulario asociado a una seccion reutilizable
		if (formularioAsociado != null && formularioAsociado.getPaginas() != null) {
			for (JPaginaFormulario pagina : formularioAsociado.getPaginas()) {
				if (pagina.getLineasFormulario() != null) {
					for (JLineaFormulario linea : pagina.getLineasFormulario()) {
						if (linea.getElementoFormulario() != null) {
							for (JElementoFormulario elemento : linea.getElementoFormulario()) {

								// Obtenemos los posibles dominios del elemento
								List<Dominio> dominiosElemento = getDominiosByElemento(elemento);

								// Miramos de asignar dominio si no lo tenemos asignado
								if (dominiosElemento != null && !dominiosElemento.isEmpty()) {
									for (Dominio dominioElemento : dominiosElemento) {
										if (!dominios.contains(dominioElemento)) {
											dominios.add(dominioElemento);
										}
									}

								}
							}
						}
					}
				}
			}
		}
		List<ScriptSeccionReutilizable> scripts = getScriptsByIdentificadorSeccionReutilizable(identificadorSeccion);
		for (ScriptSeccionReutilizable script : scripts) {
			List<Dominio> dominiosScript = getDominiosByScript(script.getScript().getContenido());
			dominios.addAll(dominiosScript);
		}
		return dominios;
	}

	private List<ScriptSeccionReutilizable> getScriptsByIdentificadorSeccionReutilizable(String identificadorSeccion) {
		final List<ScriptSeccionReutilizable> listaSecciones = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"select a from JScriptSeccionReutilizable as a where a.seccionReutilizable.identificador = :identificadorSeccion order by a.codigo");

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificadorSeccion", identificadorSeccion);

		final List<JScriptSeccionReutilizable> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			for (final JScriptSeccionReutilizable jscript : results) {
				listaSecciones.add(jscript.toModel());
			}
		}
		return listaSecciones;
	}

	private List<Dominio> getDominiosByElemento(JElementoFormulario elemento) {
		List<Dominio> idDominios = new ArrayList<>();
		TypeObjetoFormulario tipo = (elemento == null) ? null : TypeObjetoFormulario.fromString(elemento.getTipo());
		if (tipo != null) {

			switch (tipo) {
			case SELECTOR:
				if (elemento != null && elemento.getCampoFormulario() != null
						&& elemento.getCampoFormulario().getCampoFormularioIndexado() != null
						&& elemento.getCampoFormulario().getCampoFormularioIndexado().getDominio() != null) {
					final JDominio jdom = elemento.getCampoFormulario().getCampoFormularioIndexado().getDominio();
					idDominios.add(jdom.toModel());
				}

				if (elemento.getCampoFormulario().getCampoFormularioIndexado().getScriptValoresPosibles() != null) {
					List<Dominio> dominiosScript = getDominiosByScript(elemento.getCampoFormulario()
							.getCampoFormularioIndexado().getScriptValoresPosibles().getScript());
					for (Dominio dominio : dominiosScript) {
						if (!idDominios.contains(dominio)) {
							idDominios.add(dominio);
						}
					}
				}

				break;
			case CAMPO_OCULTO:
			case CAMPO_TEXTO:
			default:
				break;
			}
		}
		if (elemento != null && elemento.getCampoFormulario() != null) {
			if (elemento.getCampoFormulario().getScriptAutocalculado() != null
					&& elemento.getCampoFormulario().getScriptAutocalculado().getScript() != null
					&& !elemento.getCampoFormulario().getScriptAutocalculado().getScript().isEmpty()) {
				List<Dominio> dominiosScript = getDominiosByScript(
						elemento.getCampoFormulario().getScriptAutocalculado().getScript());
				for (Dominio dominio : dominiosScript) {
					if (!idDominios.contains(dominio)) {
						idDominios.add(dominio);
					}
				}
			}

			if (elemento.getCampoFormulario().getScriptSoloLectura() != null
					&& elemento.getCampoFormulario().getScriptSoloLectura().getScript() != null
					&& !elemento.getCampoFormulario().getScriptSoloLectura().getScript().isEmpty()) {
				List<Dominio> dominiosScript = getDominiosByScript(
						elemento.getCampoFormulario().getScriptSoloLectura().getScript());
				for (Dominio dominio : dominiosScript) {
					if (!idDominios.contains(dominio)) {
						idDominios.add(dominio);
					}
				}
			}

			if (elemento.getCampoFormulario().getScriptValidaciones() != null
					&& elemento.getCampoFormulario().getScriptValidaciones().getScript() != null
					&& !elemento.getCampoFormulario().getScriptValidaciones().getScript().isEmpty()) {
				List<Dominio> dominiosScript = getDominiosByScript(
						elemento.getCampoFormulario().getScriptValidaciones().getScript());
				for (Dominio dominio : dominiosScript) {
					if (!idDominios.contains(dominio)) {
						idDominios.add(dominio);
					}
				}
			}
		}
		return idDominios;
	}

	private List<Dominio> getDominiosByScript(String script) {
		List<Dominio> dominios = new ArrayList<>();
		if (script != null && !script.isEmpty()) {
			List<String> idDominios = validadorComponent.buscarInvocacionesDominios(script);
			for (String idenDominio : idDominios) {
				ValorIdentificadorCompuesto valor = new ValorIdentificadorCompuesto(idenDominio);
				Dominio dom = getDominioByIdentificador(valor.getIdentificador(), valor.getAmbito(),
						valor.getIdentificadorEntidad(), valor.getIdentificadorArea());
				dominios.add(dom);
			}
		}
		return dominios;
	}

	private Dominio getDominioByIdentificador(String identificadorDominio, final TypeAmbito ambito,
			final String idEntidad, final String idArea) {

		final StringBuilder sql = new StringBuilder(
				"select d from JDominio d where d.ambito = :ambito and d.identificador = :identificador ");
		if (ambito == TypeAmbito.ENTIDAD) {
			sql.append(" AND d.entidad.identificador = :idEntidad");
		}
		if (ambito == TypeAmbito.AREA) {
			sql.append(" AND d.area.identificador = :idArea");
			sql.append(" AND d.area.entidad.identificador = :idEntidad");
		}

		final Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificador", identificadorDominio);
		query.setParameter("ambito", ambito.toString());
		if (ambito == TypeAmbito.ENTIDAD) {
			query.setParameter("idEntidad", idEntidad);
		}
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("idArea", idArea);
			query.setParameter("idEntidad", idEntidad);
		}

		final List<JDominio> jDominios = query.getResultList();
		Dominio resultado = null;
		if (jDominios != null && !jDominios.isEmpty()) {
			resultado = jDominios.get(0).toModel();

		}
		return resultado;
	}

	private JFormulario getJFormularioByIdentificadorSeccion(String identificadorSeccion) {
		StringBuilder sql = new StringBuilder(
				"select a.formularioAsociado from JSeccionReutilizable as a where a.identificador like :identificadorSeccion order by a.codigo");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("identificadorSeccion", identificadorSeccion);
		List<JFormulario> jformularios = query.getResultList();
		JFormulario jformulario = null;
		if (jformularios != null && !jformularios.isEmpty()) {
			jformulario = jformularios.get(0);
		}
		return jformulario;
	}

}
