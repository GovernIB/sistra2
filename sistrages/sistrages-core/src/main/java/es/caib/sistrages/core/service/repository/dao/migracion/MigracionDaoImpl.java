package es.caib.sistrages.core.service.repository.dao.migracion;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.migracion.MigracionException;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.model.comun.migracion.ConstantesMigracion;
import es.caib.sistrages.core.api.model.migracion.ComponSistra;
import es.caib.sistrages.core.api.model.migracion.DocumSistra;
import es.caib.sistrages.core.api.model.migracion.FormulSistra;
import es.caib.sistrages.core.api.model.migracion.PantalSistra;
import es.caib.sistrages.core.api.model.migracion.TraverSistra;

@Repository("migracionDao")
public class MigracionDaoImpl implements MigracionDao {

	private static final String CHARSET_UTF_8 = "UTF-8";

	/** Path jndi sistra. */
	@Value("${jndi.migracion.sistra}")
	private String jndiSistra;

	/** Path jndi sistra. */
	@Value("${jndi.migracion.formrol}")
	private String jndiFormrol;

	/** EntityManager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Crea una nueva instancia de MigracionDaoImpl.
	 */
	public MigracionDaoImpl() {
		super();
	}

	@Override
	public List<Tramite> getTramiteSistra() {
		final String query = "select t.* from STR_TRAMIT t order by TRA_IDENTI asc";

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			return jdbcTemplate.query(query, new RowMapper<Tramite>() {
				@Override
				public Tramite mapRow(final ResultSet rs, final int rownumber) throws SQLException {
					final Tramite t = new Tramite();
					t.setCodigo(rs.getLong("TRA_CODIGO"));
					t.setIdentificador(rs.getString("TRA_IDENTI"));
					return t;
				}
			});
		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

	}

	@Override
	public List<TramiteVersion> getTramiteVersionSistra(final Long pIdTramite) {
		final String query = "select tv.* from STR_TRAVER tv where tv.TRV_CODTRA = :p_codigo order by TRV_VERSIO desc";

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pIdTramite);

			return jdbcTemplate.query(query, param, new RowMapper<TramiteVersion>() {
				@Override
				public TramiteVersion mapRow(final ResultSet rs, final int rownumber) throws SQLException {
					final TramiteVersion t = new TramiteVersion();
					t.setCodigo(rs.getLong("TRV_CODIGO"));
					t.setIdTramite(rs.getLong("TRV_CODTRA"));
					t.setNumeroVersion(rs.getInt("TRV_VERSIO"));
					return t;
				}
			});
		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}
	}

	@Override
	public Long getCodigoTramiteVersionSistra(final Long pIdTramite, final int pNumVersion) {
		final String query = "select tv.TRV_CODIGO from STR_TRAVER tv where tv.TRV_CODTRA = :p_codigo and tv.TRV_VERSIO = :p_version";

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pIdTramite);
			param.addValue("p_version", pNumVersion);

			return jdbcTemplate.queryForObject(query, param, Long.class);
		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}
	}

	@Override
	public TraverSistra getTramiteVersionSistra(final Long pIdTramite, final int pNumVersion) {
		final String query = "select tv.* from STR_TRAVER tv where tv.TRV_CODTRA = :p_codigo and tv.TRV_VERSIO = :p_version";
		TraverSistra traver = null;
		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pIdTramite);
			param.addValue("p_version", pNumVersion);

			traver = jdbcTemplate.queryForObject(query, param,
					new BeanPropertyRowMapper<TraverSistra>(TraverSistra.class));

			if (traver != null && !"B".equals(traver.getTrvDestin()) && !"R".equals(traver.getTrvDestin())) {
				throw new MigracionException("No tiene el destino adecuado");
			}

			if (traver != null) {
				traver.setScriptPersonalizacion(getScript(traver.getTrvCodigo(), "Personalizacion"));

				final String autenticacion = getAutenticacion(traver.getTrvCodigo());

				if (autenticacion != null) {
					traver.setNoAutenticado(autenticacion.contains("A"));
					traver.setAutenticado(autenticacion.contains("C") || autenticacion.contains("U"));
				}

				traver.setInstruccionesIniciales(getInstruccionesHTML(traver.getTrvCodigo(), "Iniciales"));

				traver.setScriptRepresentante(getScript(traver.getTrvCodigo(), "Representante"));

				traver.setScriptPresentador(getScript(traver.getTrvCodigo(), "Presentador"));

				traver.setScriptValidarRegistrar(getScript(traver.getTrvCodigo(), "ValidarRegistrar"));

				traver.setInstruccionesTramitacion(getInstruccionesHTML(traver.getTrvCodigo(), "Tramitacion"));

				traver.setInstruccionesEntregaPresencial(
						getInstruccionesHTML(traver.getTrvCodigo(), "EntregaPresencial"));

			}
		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return traver;
	}

	@Override
	public List<DocumSistra> getDocumSistra(final Long pIdTramiteVersion) {
		List<DocumSistra> resulDOCUM = null;

		final String queryDOCUM = "SELECT DISTINCT DOC.DOC_CODIGO codigo, DOC.DOC_IDENTI identi, DOC.DOC_TIPO tipo, DOC.DOC_ANEEXT extensiones, DOC.DOC_ANEPDF debeConvertirPDF,"
				+ "DOC.DOC_ANEPLA ayudaURL, DOC.DOC_ANETAM tamanyoMaximo, DOC.DOC_ANEGCO generico, DOC.DOC_ANEGMA numeroInstancia, DOC.DOC_ANETEL telematico FROM STR_DOCUM DOC WHERE DOC.DOC_CODTRV = :p_codigo ORDER BY DOC.DOC_ORDEN ASC";
		final String queryDOCNIVFORM = "SELECT DISTINCT DN.DNV_FORFOR forfor, DN.DNV_FORVER forver FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";

		final String queryDOCNIV = "SELECT DISTINCT DN.DNV_OBLIGA obligatorio, DN.DNV_FIRMAR firmar, DN.DNV_FIRMTE scriptEstablecerFirmantes, DN.DNV_NIVAUT nivaut FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pIdTramiteVersion);

			resulDOCUM = jdbcTemplate.query(queryDOCUM, param,
					new BeanPropertyRowMapper<DocumSistra>(DocumSistra.class));

			if (resulDOCUM != null && !resulDOCUM.isEmpty()) {
				for (final DocumSistra documSistra : resulDOCUM) {

					// Para los que son de tipo formulario
					if (ConstantesMigracion.DOCUM_TIPO_FORMULARIO.equals(documSistra.getTipo())) {
						final MapSqlParameterSource paramDOCNIVFORM = new MapSqlParameterSource().addValue("p_codigo",
								documSistra.getCodigo());

						try {
							final DocumSistra resulDOCNIVFORM = jdbcTemplate.queryForObject(queryDOCNIVFORM,
									paramDOCNIVFORM, new BeanPropertyRowMapper<DocumSistra>(DocumSistra.class));

							if (resulDOCNIVFORM != null) {

								documSistra.setForfor(resulDOCNIVFORM.getForfor());
								documSistra.setForver(resulDOCNIVFORM.getForver());
							}
						} catch (final DataAccessException e) {
							if (e.contains(org.springframework.dao.IncorrectResultSizeDataAccessException.class)) {
								throw new MigracionException(
										"Hay distintos niveles que enlazan a diferentes formularios");
							}
						}

					}

					// miramos valores
					final MapSqlParameterSource paramDOCNIV = new MapSqlParameterSource().addValue("p_codigo",
							documSistra.getCodigo());

					final List<DocumSistra> listResulDOCNIV = jdbcTemplate.query(queryDOCNIV, paramDOCNIV,
							new BeanPropertyRowMapper<DocumSistra>(DocumSistra.class));

					if (listResulDOCNIV.stream().anyMatch(d -> "S".equals(d.getObligatorio()))) {
						documSistra.setObligatorio("S");
					} else if (listResulDOCNIV.stream().anyMatch(d -> "D".equals(d.getObligatorio()))) {
						documSistra.setObligatorio("D");
						documSistra.setScriptObligatorio(getScript(documSistra.getCodigo(), "Obligatorio"));
					} else {
						documSistra.setObligatorio("N");
					}

					if (listResulDOCNIV.stream().anyMatch(d -> "S".equals(d.getFirmar()))) {
						documSistra.setFirmar("S");
						documSistra.setScriptEstablecerFirmantes(listResulDOCNIV.stream()
								.filter(d -> d.getScriptEstablecerFirmantes() != null)
								.map(d -> "/* TODO: Revisar script especificaciones niveles " + d.getNivaut()
										+ System.getProperty("line.separator") + d.getScriptEstablecerFirmantes()
										+ System.getProperty("line.separator") + " */")
								.collect(Collectors.joining((System.getProperty("line.separator")))));
					} else {
						documSistra.setFirmar("N");
					}

					// traduccion descripcion
					documSistra.setDescripcion(getTraducciones(jndiSistra, "TRADOC", documSistra.getCodigo()));

					if (ConstantesMigracion.DOCUM_TIPO_FORMULARIO.equals(documSistra.getTipo())) {
						documSistra.setScriptDatosIniciales(getScript(documSistra.getCodigo(), "DatosIniciales"));

						documSistra.setScriptParametros(getScript(documSistra.getCodigo(), "Parametros"));

						documSistra.setScriptPostGuardar(StringUtils.stripToNull(
								StringUtils.defaultIfBlank(getScript(documSistra.getCodigo(), "PostGuardar1"), "")
										.concat(StringUtils.defaultIfBlank(
												getScript(documSistra.getCodigo(), "PostGuardar2"), ""))));
					} else if (ConstantesMigracion.DOCUM_TIPO_TASA.equals(documSistra.getTipo())) {
						documSistra.setScriptPago(getScript(documSistra.getCodigo(), "Pago"));
					}
				}
			}

		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return resulDOCUM;
	}

	/**
	 * Obtiene el valor de traducciones.
	 *
	 * @param pJndi   jndi
	 * @param pTabla  tabla
	 * @param pCodigo codigo
	 * @return el valor de traducciones
	 */
	private Literal getTraducciones(final String pJndi, final String pTabla, final Long pCodigo) {
		final String queryTRADOC = "SELECT T.TRD_CODIDI idioma,T.TRD_DESC literal FROM STR_TRADOC T WHERE TRD_CODDOC = :p_codigo";
		final String queryTRASEC = "SELECT T.TRS_CODIDI idioma,T.TRS_ETIQUE literal FROM RFR_TRASEC T WHERE TRS_CODSEC = :p_codigo";
		final String queryTRALAB = "SELECT T.TRL_CODIDI idioma,T.TRL_ETIQUE literal FROM RFR_TRALAB T WHERE TRL_CODLAB = :p_codigo";
		final String queryTRACAM = "SELECT T.TRC_CODIDI idioma,T.TRC_NOMBRE literal FROM RFR_TRACAM T WHERE TRC_CODCAM = :p_codigo";
		final String queryTRACAM_AYUDA = "SELECT T.TRC_CODIDI idioma,T.TRC_AYUDA literal FROM RFR_TRACAM T WHERE TRC_CODCAM = :p_codigo AND TRC_AYUDA IS NOT NULL";
		final String queryTRACAM_MENVAL = "SELECT T.TRC_CODIDI idioma,T.TRC_MENVAL literal FROM RFR_TRACAM T WHERE TRC_CODCAM = :p_codigo AND TRC_MENVAL IS NOT NULL";
		final String queryTRVAPO = "SELECT T.TRV_CODIDI idioma,T.TRV_ETIQUE literal FROM RFR_TRVAPO T WHERE TRV_CODVAP = :p_codigo";
		Literal descripcion = null;
		String query = null;

		switch (pTabla) {
		case "TRADOC":
			query = queryTRADOC;
			break;
		case "TRASEC":
			query = queryTRASEC;
			break;
		case "TRALAB":
			query = queryTRALAB;
			break;
		case "TRACAM":
			query = queryTRACAM;
			break;
		case "TRACAM_AYUDA":
			query = queryTRACAM_AYUDA;
			break;
		case "TRACAM_MENVAL":
			query = queryTRACAM_MENVAL;
			break;
		case "TRVAPO":
			query = queryTRVAPO;
			break;
		default:
			break;
		}

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(pJndi);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pCodigo);

			final List<Traduccion> resul = jdbcTemplate.query(query, param,
					new BeanPropertyRowMapper<Traduccion>(Traduccion.class));

			if (resul != null && !resul.isEmpty()) {

				for (final Traduccion traduccion : resul) {
					if (traduccion.getLiteral() != null) {
						traduccion.setLiteral(Jsoup.parse(traduccion.getLiteral()).text());

						// hay etiquetas cuyo contenido es unicamente html, por lo que al limpiarlo se
						// quedan vacias
						if (StringUtils.isEmpty(traduccion.getLiteral())) {
							traduccion.setLiteral("*** LITERAL VACIO REVISAR ***");
						}
					}
				}

				descripcion = new Literal();
				descripcion.setTraducciones(resul);
			}

		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return descripcion;
	}

	@Override
	public FormulSistra getFormSistra(final DocumSistra pDocSistra) {
		final String queryFORMUL = "SELECT * FROM RFR_FORMUL WHERE for_modelo = :p_modelo and FOR_VERSIO = :p_version";
		FormulSistra resulFORMUL = null;
		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiFormrol);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource paramFORMUL = new MapSqlParameterSource().addValue("p_modelo",
					pDocSistra.getForfor());
			paramFORMUL.addValue("p_version", pDocSistra.getForver());

			resulFORMUL = jdbcTemplate.queryForObject(queryFORMUL, paramFORMUL,
					new BeanPropertyRowMapper<FormulSistra>(FormulSistra.class));

			// if (resulFORMUL != null && resulFORMUL.getForVerfun() != 2) {
			// throw new MigracionException("No tiene la version adecuada");
			// }

			// recuperamos paginas
			if (resulFORMUL != null) {
				resulFORMUL.setPaginas(getPantalSistra(resulFORMUL));
			}
		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return resulFORMUL;
	}

	/**
	 * Obtiene el valor de pantalSistra.
	 *
	 * @param pFormulSistra formul sistra
	 * @return el valor de pantalSistra
	 */
	private List<PantalSistra> getPantalSistra(final FormulSistra pFormulSistra) {
		final String queryPANTAL = "SELECT * FROM RFR_PANTAL WHERE PAN_CODFOR = :p_codigo ORDER BY PAN_ORDEN ASC";
		List<PantalSistra> resulPANTAL = null;

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiFormrol);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource paramPANTAL = new MapSqlParameterSource().addValue("p_codigo",
					pFormulSistra.getForCodi());

			resulPANTAL = jdbcTemplate.query(queryPANTAL, paramPANTAL,
					new BeanPropertyRowMapper<PantalSistra>(PantalSistra.class));

			if (resulPANTAL != null) {
				for (final PantalSistra pantalSistra : resulPANTAL) {
					pantalSistra.setComponentes(getComponSistra(pantalSistra));
				}
			}
		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return resulPANTAL;
	}

	/**
	 * Obtiene el valor de componSistra.
	 *
	 * @param pPantalSistra pantal sistra
	 * @return el valor de componSistra
	 */
	private List<ComponSistra> getComponSistra(final PantalSistra pPantalSistra) {
		final String queryCOMPON = "SELECT * FROM RFR_COMPON WHERE COM_CODPAN = :p_codigo ORDER BY COM_ORDEN ASC";

		List<ComponSistra> resulCOMPON = null;
		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiFormrol);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource paramCOMPON = new MapSqlParameterSource().addValue("p_codigo",
					pPantalSistra.getPanCodi());

			resulCOMPON = jdbcTemplate.query(queryCOMPON, paramCOMPON,
					new BeanPropertyRowMapper<ComponSistra>(ComponSistra.class));

			for (final ComponSistra componSistra : resulCOMPON) {

				switch (componSistra.getComType()) {
				case "seccion":
					componSistra.setTexto(getTraducciones(jndiFormrol, "TRASEC", componSistra.getComCodi()));
					break;
				case "label":
					componSistra.setTexto(getTraducciones(jndiFormrol, "TRALAB", componSistra.getComCodi()));
					break;
				case "textbox":
				case "combobox":
				case "radiobutton":
				case "checkbox":
				case "listbox":
					componSistra.setTexto(getTraducciones(jndiFormrol, "TRACAM", componSistra.getComCodi()));
					componSistra.setAyuda(getTraducciones(jndiFormrol, "TRACAM_AYUDA", componSistra.getComCodi()));
					componSistra.setMensajeValidacion(
							getTraducciones(jndiFormrol, "TRACAM_MENVAL", componSistra.getComCodi()));
					break;
				default:
					break;
				}

				if ("textbox".equals(componSistra.getComType())) {
					final String[] valMaxlength = getComponExtra(componSistra, "maxlength");
					final String[] valRequired = getComponExtra(componSistra, "required");
					final String[] valEmail = getComponExtra(componSistra, "email");
					final String[] valExpReg = getComponExtra(componSistra, "mask");

					if (valMaxlength != null && valMaxlength.length > 0) {
						componSistra.setMaxlength(Integer.valueOf(valMaxlength[0]));
					}

					if (valRequired != null) {
						componSistra.setRequired(Boolean.TRUE);
					}

					if (valEmail != null) {
						componSistra.setTipoEmail(Boolean.TRUE);
					}

					if (valExpReg != null) {
						componSistra.setTipoExpRegular(Boolean.TRUE);

						if (valExpReg.length > 0) {
							componSistra.setExpRegular(valExpReg[0]);
						}
					}
				} else if ("combobox".equals(componSistra.getComType())
						|| "radiobutton".equals(componSistra.getComType())
						|| "listbox".equals(componSistra.getComType())) {
					componSistra.setListaValores(getValPos(componSistra));
				}

			}

		} catch (

		final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return resulCOMPON;
	}

	/**
	 * Obtiene el valor de scriptPersonalizacion.
	 *
	 * @param pId id tramite version
	 * @return el valor de scriptPersonalizacion
	 */
	private String getScript(final Long pId, final String pTabla) {
		final String queryPersonalizacion = "SELECT ESP.ETN_VALINI SCRIPT  FROM STR_TRAVER TV, STR_ESPNIV ESP WHERE TV.TRV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";
		final String queryNivelesPersonalizacion = "SELECT TN.TNV_NIVAUT NIVAUT,ESP.ETN_VALINI SCRIPT FROM STR_TRAVER TV,STR_TRANIV TN, STR_ESPNIV ESP WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TN.TNV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";
		final String queryRepresentante = "SELECT ESP.ETN_RDODAT SCRIPT FROM STR_TRAVER TV, STR_ESPNIV ESP WHERE TV.TRV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";
		final String queryNivelesRepresentante = "SELECT TN.TNV_NIVAUT NIVAUT, ESP.ETN_RDODAT SCRIPT FROM STR_TRAVER TV,STR_TRANIV TN, STR_ESPNIV ESP WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TN.TNV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";
		final String queryPresentador = "SELECT ESP.ETN_RTEDAT SCRIPT FROM STR_TRAVER TV, STR_ESPNIV ESP WHERE TV.TRV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";
		final String queryNivelesPresentador = "SELECT TN.TNV_NIVAUT NIVAUT, ESP.ETN_RTEDAT  SCRIPT FROM STR_TRAVER TV,STR_TRANIV TN, STR_ESPNIV ESP WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TN.TNV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";
		final String queryValidarRegistrar = "SELECT ESP.ETN_CHKENV SCRIPT FROM STR_TRAVER TV, STR_ESPNIV ESP WHERE TV.TRV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";
		final String queryNivelesValidarRegistrar = "SELECT TN.TNV_NIVAUT NIVAUT, ESP.ETN_CHKENV SCRIPT FROM STR_TRAVER TV,STR_TRANIV TN, STR_ESPNIV ESP WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TN.TNV_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";

		final String queryNivelesObligatorio = "SELECT DISTINCT DN.DNV_NIVAUT NIVAUT, DN.DNV_OBLSCR SCRIPT FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";

		final String queryNivelesDatosIniciales = "SELECT DISTINCT DN.DNV_NIVAUT NIVAUT, DN.DNV_FORINI SCRIPT FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";
		final String queryNivelesParametros = "SELECT DISTINCT DN.DNV_NIVAUT NIVAUT, DN.DNV_FORCON SCRIPT FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";
		final String queryNivelesPostGuardar1 = "SELECT DISTINCT DN.DNV_NIVAUT NIVAUT, DN.DNV_FORPOS SCRIPT FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";
		final String queryNivelesPostGuardar2 = "SELECT DISTINCT DN.DNV_NIVAUT NIVAUT, DN.DNV_FORMOD SCRIPT FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";

		final String queryNivelesPago = "SELECT DISTINCT DN.DNV_NIVAUT NIVAUT, DN.DNV_PAGDAT SCRIPT FROM STR_DOCNIV DN WHERE DN.DNV_CODDOC = :p_codigo";

		StringBuilder script = null;
		String resultado = null;
		String query = null;
		String queryNiveles = null;

		switch (pTabla) {
		case "Personalizacion":
			query = queryPersonalizacion;
			queryNiveles = queryNivelesPersonalizacion;
			break;
		case "Representante":
			query = queryRepresentante;
			queryNiveles = queryNivelesRepresentante;
			break;
		case "Presentador":
			query = queryPresentador;
			queryNiveles = queryNivelesPresentador;
			break;
		case "ValidarRegistrar":
			query = queryValidarRegistrar;
			queryNiveles = queryNivelesValidarRegistrar;
			break;
		case "Obligatorio":
			queryNiveles = queryNivelesObligatorio;
			break;
		case "DatosIniciales":
			queryNiveles = queryNivelesDatosIniciales;
			break;
		case "Parametros":
			queryNiveles = queryNivelesParametros;
			break;
		case "PostGuardar1":
			queryNiveles = queryNivelesPostGuardar1;
			break;
		case "PostGuardar2":
			queryNiveles = queryNivelesPostGuardar2;
			break;
		case "Pago":
			queryNiveles = queryNivelesPago;
			break;
		default:
			break;
		}

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pId);

			if (query != null) {
				final byte[] scriptGenerico = jdbcTemplate.queryForObject(query, param, byte[].class);
				if (scriptGenerico != null) {
					script = new StringBuilder();

					script.append("/* TODO: Revisar script especificaciones genericas");
					script.append(System.getProperty("line.separator"));
					script.append((new String(scriptGenerico)));
					script.append(System.getProperty("line.separator"));
					script.append("*/");
					script.append(System.getProperty("line.separator"));
				}
			}

			if (queryNiveles != null) {
				final List<String> scriptNiveles = jdbcTemplate.query(queryNiveles, param, new RowMapper<String>() {
					@Override
					public String mapRow(final ResultSet rs, final int rownumber) throws SQLException {
						String scriptRow = null;

						final byte[] scriptNivel = rs.getBytes("SCRIPT");

						try {
							if (scriptNivel != null) {
								final StringBuilder scriptRowAux = new StringBuilder();
								scriptRowAux.append("/* TODO: Revisar script especificaciones niveles ");
								scriptRowAux.append(rs.getString("NIVAUT"));
								scriptRowAux.append(System.getProperty("line.separator"));
								scriptRowAux.append((new String(scriptNivel, CHARSET_UTF_8)));
								scriptRowAux.append(System.getProperty("line.separator"));
								scriptRowAux.append("*/");
								scriptRowAux.append(System.getProperty("line.separator"));

								scriptRow = scriptRowAux.toString();
							}
						} catch (final UnsupportedEncodingException e) {
							throw new SQLException(e);
						}

						return scriptRow;
					}
				});

				if (scriptNiveles != null) {
					for (final String scriptNiv : scriptNiveles) {
						if (scriptNiv != null) {

							if (script == null) {
								script = new StringBuilder();
							}

							script.append(scriptNiv);
						}
					}
				}
			}

		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		if (script != null) {
			resultado = script.toString();
		}

		return resultado;
	}

	private String getAutenticacion(final Long pIdTramiteVersion) {
		final String query = "SELECT TN.TNV_NIVAUT  FROM STR_TRAVER TV,STR_TRANIV TN WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TV.TRV_CODIGO = :p_codigo";

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pIdTramiteVersion);

			final List<String> lista = jdbcTemplate.queryForList(query, param, String.class);

			return String.join("", lista);

		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

	}

	private Literal getInstruccionesHTML(final Long pIdTramiteVersion, final String pTabla) {
		final String queryIniciales = "SELECT TET_CODIDI idioma,TET_INSINI literal FROM STR_TRAVER TV, STR_ESPNIV ESP, STR_TRAETN TE WHERE TV.TRV_CODETN = ESP.ETN_CODIGO  AND TE.TET_CODETN = ESP.ETN_CODIGO AND TE.TET_INSINI IS NOT NULL AND TV.TRV_CODIGO = :p_codigo";
		final String queryNivelesIniciales = "SELECT TN.TNV_NIVAUT nivaut,TET_CODIDI idioma,TET_INSINI literal FROM STR_TRAVER TV, STR_TRANIV TN, STR_ESPNIV ESP,STR_TRAETN TE WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TN.TNV_CODETN = ESP.ETN_CODIGO AND TE.TET_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";

		final String queryTramitacion = "SELECT TET_CODIDI idioma,TET_INSFIN literal FROM STR_TRAVER TV, STR_ESPNIV ESP, STR_TRAETN TE WHERE TV.TRV_CODETN = ESP.ETN_CODIGO  AND TE.TET_CODETN = ESP.ETN_CODIGO AND TE.TET_INSINI IS NOT NULL AND TV.TRV_CODIGO = :p_codigo";
		final String queryNivelesTramitacion = "SELECT TN.TNV_NIVAUT nivaut,TET_CODIDI idioma,TET_INSFIN  literal FROM STR_TRAVER TV, STR_TRANIV TN, STR_ESPNIV ESP,STR_TRAETN TE WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TN.TNV_CODETN = ESP.ETN_CODIGO AND TE.TET_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";

		final String queryEntregaPresencial = "SELECT TET_CODIDI idioma,TET_INSENT literal FROM STR_TRAVER TV, STR_ESPNIV ESP, STR_TRAETN TE WHERE TV.TRV_CODETN = ESP.ETN_CODIGO  AND TE.TET_CODETN = ESP.ETN_CODIGO AND TE.TET_INSINI IS NOT NULL AND TV.TRV_CODIGO = :p_codigo";
		final String queryNivelesEntregaPresencial = "SELECT TN.TNV_NIVAUT nivaut,TET_CODIDI idioma,TET_INSENT  literal FROM STR_TRAVER TV, STR_TRANIV TN, STR_ESPNIV ESP,STR_TRAETN TE WHERE TV.TRV_CODIGO = TN.TNV_CODTRV AND TN.TNV_CODETN = ESP.ETN_CODIGO AND TE.TET_CODETN = ESP.ETN_CODIGO AND TV.TRV_CODIGO = :p_codigo";

		Literal descripcion = null;

		String query = null;
		String queryNiveles = null;

		switch (pTabla) {
		case "Iniciales":
			query = queryIniciales;
			queryNiveles = queryNivelesIniciales;
			break;
		case "Tramitacion":
			query = queryTramitacion;
			queryNiveles = queryNivelesTramitacion;
			break;
		case "EntregaPresencial":
			query = queryEntregaPresencial;
			queryNiveles = queryNivelesEntregaPresencial;
			break;
		default:
			break;
		}

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiSistra);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo", pIdTramiteVersion);

			final List<Traduccion> listaTrad = jdbcTemplate.query(query, param, new RowMapper<Traduccion>() {
				@Override
				public Traduccion mapRow(final ResultSet rs, final int rownumber) throws SQLException {
					String litAux = null;
					Traduccion resListaTrad = null;

					try {
						if (rs.getBytes("literal") != null) {
							litAux = "TODO: generico" + "<br>"
									+ Jsoup.clean(new String(rs.getBytes("literal"), CHARSET_UTF_8), Whitelist.basic())
									+ "<br>";

							resListaTrad = new Traduccion(rs.getString("idioma"), litAux);
						}
					} catch (final UnsupportedEncodingException e) {
						throw new SQLException(e);
					}

					return resListaTrad;
				}
			});

			final List<Traduccion> listaTradNiveles = jdbcTemplate.query(queryNiveles, param,
					new RowMapper<Traduccion>() {
						@Override
						public Traduccion mapRow(final ResultSet rs, final int rownumber) throws SQLException {
							String litAux = null;
							Traduccion resListaTradNiveles = null;

							try {
								if (rs.getBytes("literal") != null) {
									litAux = "TODO: especifico nivel " + rs.getString("nivaut") + "<br>" + Jsoup.clean(
											new String(rs.getBytes("literal"), CHARSET_UTF_8), Whitelist.basic()) + "<br>";

									resListaTradNiveles = new Traduccion(rs.getString("idioma"), litAux);
								}
							} catch (final UnsupportedEncodingException e) {
								throw new SQLException(e);
							}

							return resListaTradNiveles;
						}
					});

			if (listaTrad != null && !listaTrad.isEmpty()) {
				listaTrad.removeIf(t -> t == null);
				if (!listaTrad.isEmpty()) {
					descripcion = new Literal();
					descripcion.setTraducciones(listaTrad);
				}
			}

			if (listaTradNiveles != null && !listaTradNiveles.isEmpty()) {
				listaTradNiveles.removeIf(t -> t == null);
				if (descripcion == null && !listaTradNiveles.isEmpty()) {
					descripcion = new Literal();
					descripcion.setTraducciones(new ArrayList<>());
				}

				for (final Traduccion traduccion : listaTradNiveles) {
					if (StringUtils.isNotEmpty(traduccion.getLiteral())) {
						final StringBuilder texto = new StringBuilder();

						final String textAux = descripcion.getTraduccion(traduccion.getIdioma());
						if (textAux != null) {
							texto.append(textAux);
						}

						texto.append(traduccion.getLiteral());

						Traduccion tradAux = descripcion.getTraducciones().stream()
								.filter(t -> traduccion.getIdioma().equals(t.getIdioma())).findAny().orElse(null);
						if (tradAux != null) {
							tradAux.setLiteral(texto.toString());
						} else {
							tradAux = new Traduccion(traduccion.getIdioma(), texto.toString());
							descripcion.getTraducciones().add(tradAux);
						}
					}
				}
			}

		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return descripcion;

	}

	private String[] getComponExtra(final ComponSistra pComponente, final String pPropiedad) {
		final String query = "SELECT VAL_VALORE FROM RFR_VALIDA WHERE VAL_CODCAM = :p_codigo AND VAL_CODMAS = (SELECT MAS_CODI FROM RFR_MASCAR WHERE MAS_NOMBRE = :p_propiedad)";
		String[] resultado = null;
		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiFormrol);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo",
					pComponente.getComCodi());
			param.addValue("p_propiedad", pPropiedad);

			final List<byte[]> lista = jdbcTemplate.queryForList(query, param, byte[].class);

			if (lista != null && !lista.isEmpty()) {
				resultado = SerializationUtils.deserialize(lista.get(0));
			}

		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return resultado;

	}

	private List<ValorListaFija> getValPos(final ComponSistra pComponente) {
		final String query = "SELECT VAP_CODI codigo, VAP_VALOR valor,VAP_DEFECT porDefecto,VAP_ORDEN orden FROM RFR_VALPOS WHERE VAP_CODCAM = :p_codigo ORDER BY VAP_ORDEN ASC";

		List<ValorListaFija> listaValores = null;

		// Obtenemos datasource
		Context ctext;
		try {
			ctext = new InitialContext();
			final DataSource ds = (DataSource) ctext.lookup(jndiFormrol);

			// Realizamos consulta
			final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);

			final MapSqlParameterSource param = new MapSqlParameterSource().addValue("p_codigo",
					pComponente.getComCodi());

			listaValores = jdbcTemplate.query(query, param,
					new BeanPropertyRowMapper<ValorListaFija>(ValorListaFija.class));

			for (final ValorListaFija valorpos : listaValores) {
				valorpos.setDescripcion(getTraducciones(jndiFormrol, "TRVAPO", valorpos.getCodigo()));
				valorpos.setCodigo(null);
			}

		} catch (final NamingException e) {
			throw new MigracionException(e.getMessage());
		}

		return listaValores;
	}

}
