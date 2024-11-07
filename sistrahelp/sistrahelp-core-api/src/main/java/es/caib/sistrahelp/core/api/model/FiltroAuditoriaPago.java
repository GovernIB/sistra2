package es.caib.sistrahelp.core.api.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import es.caib.sistrahelp.core.api.exception.CargaConfiguracionException;
import es.caib.sistrahelp.core.api.model.types.TypeAutenticacion;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;

/**
 * Filtros para la auditoria de tramites
 *
 * @author Indra
 *
 */
public class FiltroAuditoriaPago extends ModelApi {

	/** Propiedades configuración especificadas en properties. */
	private Properties propiedadesLocales = recuperarConfiguracionProperties();

	private static final long serialVersionUID = 1L;
	private List<String> listaAreas;
	private TypeAutenticacion tipoAcceso;
	private String idSesionTramitacion;
	private String nif;
	private Date fechaDesde;
	private Date fechaHasta;
	private boolean mostrarErrores;

	private boolean soloContar;

	private String sortField;
	private String sortOrder;

	public FiltroAuditoriaPago() {
		super();
		this.fechaDesde = calcularFecha();
	}

	public FiltroAuditoriaPago(final List<String> listaAreas) {
		super();
		this.listaAreas = listaAreas;
		this.fechaDesde = calcularFecha();
	}

	public FiltroAuditoriaPago(final FiltroAuditoriaPago pFiltroAuditoriaPago) {
		super();
		if (pFiltroAuditoriaPago != null) {
			this.listaAreas = pFiltroAuditoriaPago.listaAreas;
			this.tipoAcceso = pFiltroAuditoriaPago.tipoAcceso;
			this.idSesionTramitacion = pFiltroAuditoriaPago.idSesionTramitacion;
			this.nif = pFiltroAuditoriaPago.nif;
			this.fechaDesde = pFiltroAuditoriaPago.fechaDesde;
			this.fechaHasta = pFiltroAuditoriaPago.fechaHasta;
			this.mostrarErrores = pFiltroAuditoriaPago.mostrarErrores;
			this.soloContar = pFiltroAuditoriaPago.soloContar;

			this.sortField = pFiltroAuditoriaPago.sortField;
			this.sortOrder = pFiltroAuditoriaPago.sortOrder;
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

	public boolean isMostrarErrores() {
		return mostrarErrores;
	}

	public void setMostrarErrores(boolean mostrarErrores) {
		this.mostrarErrores = mostrarErrores;
	}

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

	public TypeAutenticacion getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(final TypeAutenticacion tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	private Date getToday() {
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

}
