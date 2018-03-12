package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JFormulario
 */
@Entity
@Table(name = "STG_FORMUL")
public class JFormulario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORMUL_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORMUL_SEQ", sequenceName = "STG_FORMUL_SEQ")
	@Column(name = "FOR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOR_CABLOG")
	private JFichero logoCabecera;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOR_SCRPLT")
	private JScript scriptPlantilla;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOR_CABTXT")
	private JLiterales textoCabecera;

	@Column(name = "FOR_ACCPER", nullable = false, precision = 1, scale = 0)
	private boolean permitirAccionesPersonalizadas;

	@Column(name = "FOR_CABFOR", nullable = false, precision = 1, scale = 0)
	private boolean cabeceraFormulario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario")
	private Set<JPlantillaFormulario> plantillas = new HashSet<JPlantillaFormulario>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario")
	private Set<JPaginaFormulario> paginas = new HashSet<JPaginaFormulario>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario")
	private Set<JFormularioTramite> tramites = new HashSet<JFormularioTramite>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formulario")
	private Set<JAccionPersonalizada> accionesPersonalizadas = new HashSet<JAccionPersonalizada>(0);

	public JFormulario() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JFichero getLogoCabecera() {
		return this.logoCabecera;
	}

	public void setLogoCabecera(final JFichero logoCabecera) {
		this.logoCabecera = logoCabecera;
	}

	public JScript getScriptPlantilla() {
		return this.scriptPlantilla;
	}

	public void setScriptPlantilla(final JScript scriptPlantilla) {
		this.scriptPlantilla = scriptPlantilla;
	}

	public JLiterales getTextoCabecera() {
		return this.textoCabecera;
	}

	public void setTextoCabecera(final JLiterales textoCabecera) {
		this.textoCabecera = textoCabecera;
	}

	public boolean isPermitirAccionesPersonalizadas() {
		return this.permitirAccionesPersonalizadas;
	}

	public void setPermitirAccionesPersonalizadas(final boolean permitirAccionesPersonalizadas) {
		this.permitirAccionesPersonalizadas = permitirAccionesPersonalizadas;
	}

	public boolean isCabeceraFormulario() {
		return this.cabeceraFormulario;
	}

	public void setCabeceraFormulario(final boolean cabeceraFormulario) {
		this.cabeceraFormulario = cabeceraFormulario;
	}

	public Set<JPlantillaFormulario> getPlantillas() {
		return this.plantillas;
	}

	public void setPlantillas(final Set<JPlantillaFormulario> plantillas) {
		this.plantillas = plantillas;
	}

	public Set<JPaginaFormulario> getPaginas() {
		return this.paginas;
	}

	public void setPaginas(final Set<JPaginaFormulario> paginas) {
		this.paginas = paginas;
	}

	public Set<JFormularioTramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(final Set<JFormularioTramite> tramites) {
		this.tramites = tramites;
	}

	public Set<JAccionPersonalizada> getAccionesPersonalizadas() {
		return this.accionesPersonalizadas;
	}

	public void setAccionesPersonalizadas(final Set<JAccionPersonalizada> accionesPersonalizadas) {
		this.accionesPersonalizadas = accionesPersonalizadas;
	}

}
