package es.caib.sistrahelp.core.api.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import es.caib.sistrahelp.core.api.exception.CargaConfiguracionException;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.model.types.TypeTramitePersistencia;

/**
 * Filtros para la persistencia
 *
 * @author Indra
 *
 */
public class FiltroPersistenciaAuditoria extends ModelApi {

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales = recuperarConfiguracionProperties();
	private static final long serialVersionUID = 1L;
	private List<String> listaAreas;
	private String idSesionTramitacion;
	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;
	private TypeTramitePersistencia tipoTramitePersistencia;
	private String idTramite;
	private Integer versionTramite;
	private String idProcedimientoCP;
	private String idProcedimientoSIA;

	private boolean soloContar;

	private String sortField;
	private String sortOrder;

	public FiltroPersistenciaAuditoria() {
		super();
		this.fechaDesde = getToday();
	}

	public FiltroPersistenciaAuditoria(final FiltroPersistenciaAuditoria pNewFiltroPersistenciaAuditoria) {
		super();

		if (pNewFiltroPersistenciaAuditoria != null) {
			this.listaAreas = pNewFiltroPersistenciaAuditoria.getListaAreas();
			this.idSesionTramitacion = pNewFiltroPersistenciaAuditoria.getIdSesionTramitacion();
			this.nif = pNewFiltroPersistenciaAuditoria.getNif();
			this.fechaDesde = pNewFiltroPersistenciaAuditoria.getFechaDesde();
			this.fechaHasta = pNewFiltroPersistenciaAuditoria.getFechaHasta();
			this.tipoTramitePersistencia = pNewFiltroPersistenciaAuditoria.getTipoTramitePersistencia();
			this.idTramite = pNewFiltroPersistenciaAuditoria.getIdTramite();
			this.versionTramite = pNewFiltroPersistenciaAuditoria.getVersionTramite();
			this.idProcedimientoCP = pNewFiltroPersistenciaAuditoria.getIdProcedimientoCP();
			this.idProcedimientoSIA = pNewFiltroPersistenciaAuditoria.getIdProcedimientoSIA();
			this.soloContar = pNewFiltroPersistenciaAuditoria.isSoloContar();

			this.sortField = pNewFiltroPersistenciaAuditoria.getSortField();
			this.sortOrder = pNewFiltroPersistenciaAuditoria.getSortOrder();
		}
	}

	public FiltroPersistenciaAuditoria(final List<String> listaAreas) {
		super();
		this.listaAreas = listaAreas;
		this.fechaDesde = calcularFecha();
	}

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(final Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(final Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public TypeTramitePersistencia getTipoTramitePersistencia() {
		return tipoTramitePersistencia;
	}

	public void setTipoTramitePersistencia(final TypeTramitePersistencia tipoTramitePersistencia) {
		this.tipoTramitePersistencia = tipoTramitePersistencia;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public Integer getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final Integer versionTramite) {
		this.versionTramite = versionTramite;
	}

	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	public void setIdProcedimientoCP(final String idProcedimientoCP) {
		this.idProcedimientoCP = idProcedimientoCP;
	}

	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

	private Date getToday() {
		final Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar.getTime();
	}

	public Date calcularFecha() {
		String propiedad = propiedadesLocales.getProperty(TypePropiedadConfiguracion.FILTRO_INICIAL.toString());
		try {
			int num = Integer.parseInt(propiedad);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getToday()); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, -num); // numero de días a añadir, o restar en caso de días<0
			return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		} catch (NumberFormatException e) {
			return getToday();
		}

	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(final String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final String sortOrder) {
		this.sortOrder = sortOrder;
	}

	private Properties recuperarConfiguracionProperties() {
		final String pathProperties = System.getProperty(TypePropiedadConfiguracion.PATH_PROPERTIES.toString());
		try (FileInputStream fis = new FileInputStream(pathProperties);) {
			final Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (final IOException e) {
			throw new CargaConfiguracionException(
					"Error al cargar la configuracion del properties '" + pathProperties + "' : " + e.getMessage(), e);
		}
	}
}
