package es.caib.sistrahelp.core.api.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import es.caib.sistrahelp.core.api.exception.CargaConfiguracionException;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;

/**
 * Filtros para la auditoria de tramites
 *
 * @author Indra
 *
 */
public class FiltroAuditoriaTramitacion extends ModelApi {
	/** Configuracion. */

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales = recuperarConfiguracionProperties();
	private static final long serialVersionUID = 1L;
	private List<String> listaAreas;
	private String idSesionTramitacion;
	private String nif;
	private String nombre;
	private Date fechaDesde;
	private Date fechaHasta;
	private TypeEvento evento;
	private String excepcion;

	private String idTramite;
	private String codSia;
	private Integer versionTramite;
	private String idProcedimientoCP;
	private String idProcedimientoSIA;

	private boolean errorPlataforma;
	private boolean soloContar;

	private String sortField;
	private String sortOrder;

	public FiltroAuditoriaTramitacion() {
		super();
		this.fechaDesde = calcularFecha();
	}

	public FiltroAuditoriaTramitacion(boolean aux) {
		super();
		if (!aux) {
			this.fechaDesde = this.getToday();
		} else {
			this.fechaDesde = calcularFecha();
		}
	}

	public FiltroAuditoriaTramitacion(final List<String> listaAreas, final boolean errorPlataforma, boolean aux) {
		super();
		this.listaAreas = listaAreas;
		this.errorPlataforma = errorPlataforma;
		if (!aux) {
			this.fechaDesde = this.getToday();
		} else {
			this.fechaDesde = calcularFecha();
		}

	}

	public FiltroAuditoriaTramitacion(final List<String> listaAreas) {
		super();
		this.listaAreas = listaAreas;
		this.fechaDesde = calcularFecha();
	}

	public FiltroAuditoriaTramitacion(final List<String> listaAreas, final boolean errorPlataforma) {
		super();
		this.listaAreas = listaAreas;
		this.errorPlataforma = errorPlataforma;
		this.fechaDesde = calcularFecha();
	}

	public FiltroAuditoriaTramitacion(final FiltroAuditoriaTramitacion pFiltroAuditoriaTramitacion) {
		super();
		if (pFiltroAuditoriaTramitacion != null) {
			this.listaAreas = pFiltroAuditoriaTramitacion.listaAreas;
			this.codSia = pFiltroAuditoriaTramitacion.codSia;
			this.idSesionTramitacion = pFiltroAuditoriaTramitacion.idSesionTramitacion;
			this.nif = pFiltroAuditoriaTramitacion.nif;
			this.nombre = pFiltroAuditoriaTramitacion.nombre;
			this.fechaDesde = pFiltroAuditoriaTramitacion.fechaDesde;
			this.fechaHasta = pFiltroAuditoriaTramitacion.fechaHasta;
			this.evento = pFiltroAuditoriaTramitacion.evento;
			this.idTramite = pFiltroAuditoriaTramitacion.idTramite;
			this.versionTramite = pFiltroAuditoriaTramitacion.versionTramite;
			this.idProcedimientoCP = pFiltroAuditoriaTramitacion.idProcedimientoCP;
			this.idProcedimientoSIA = pFiltroAuditoriaTramitacion.idProcedimientoSIA;
			this.errorPlataforma = pFiltroAuditoriaTramitacion.errorPlataforma;
			this.soloContar = pFiltroAuditoriaTramitacion.soloContar;
			this.excepcion = pFiltroAuditoriaTramitacion.excepcion;

			this.sortField = pFiltroAuditoriaTramitacion.sortField;
			this.sortOrder = pFiltroAuditoriaTramitacion.sortOrder;
		}
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

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
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

	public TypeEvento getEvento() {
		return evento;
	}

	public void setEvento(final TypeEvento evento) {
		this.evento = evento;
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

	public boolean isErrorPlataforma() {
		return errorPlataforma;
	}

	public void setErrorPlataforma(final boolean errorPlataforma) {
		this.errorPlataforma = errorPlataforma;
	}

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

	public Date getToday() {
		final Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar.getTime();
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

	/**
	 * @return the codSia
	 */
	public final String getCodSia() {
		return codSia;
	}

	/**
	 * @param codSia the codSia to set
	 */
	public final void setCodSia(String codSia) {
		this.codSia = codSia;
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
