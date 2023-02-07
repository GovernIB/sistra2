package es.caib.sistrages.core.service.repository.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.util.UtilJSON;

/**
 * JPlugin
 */
@Entity
@Table(name = "STG_PLUGIN")
public class JPlugin implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PLUGIN_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PLUGIN_SEQ", sequenceName = "STG_PLUGIN_SEQ")
	@Column(name = "PLG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Ámbito: G (Global) , E (Entidad) */
	@Column(name = "PLG_AMBITO", nullable = false, length = 1)
	private String ambito;

	/**
	 * "Tipo plugin: - Global: LOG: Login, REP: Representación, DOM: Dominios
	 * remotos, FIR: Firma - Entidad: PRO: Catalogo procedimientos, REG: Registro,
	 * PAG: Pagos"
	 */
	@Column(name = "PLG_TIPO", nullable = false, length = 3)
	private String tipo;

	/** Descripción plugin */
	@Column(name = "PLG_DESCR", nullable = false)
	private String descripcion;

	/** Clase implementadora */
	@Column(name = "PLG_CLASS", nullable = false, length = 500)
	private String claseImplementadora;

	/** Lista serializada propiedades (codigo - valor) */
	@Column(name = "PLG_PROPS", length = 4000)
	private String propiedades;

	/** Entidad. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLG_CODENT", nullable = true)
	private JEntidad entidad;

	/** prefijo propiedades **/
	@Column(name = "PLG_PREPRO", length = 100)
	private String prefijoPropiedades;

	/** Clase real */
	@Column(name = "PLG_CLASSREAL", nullable = true, length = 500)
	private String claseReal;

	/** Clase mock */
	@Column(name = "PLG_CLASSMOCK", nullable = true, length = 500)
	private String claseMock;

	/** Constructor. **/
	public JPlugin() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the claseImplementadora
	 */
	public String getClaseImplementadora() {
		return claseImplementadora;
	}

	/**
	 * @param claseImplementadora
	 *            the claseImplementadora to set
	 */
	public void setClaseImplementadora(final String claseImplementadora) {
		this.claseImplementadora = claseImplementadora;
	}

	/**
	 * @return the propiedades
	 */
	public String getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades
	 *            the propiedades to set
	 */
	public void setPropiedades(final String propiedades) {
		this.propiedades = propiedades;
	}

	/**
	 * @return the entidad
	 */
	public JEntidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * Obtiene el valor de prefijoPropiedades.
	 *
	 * @return el valor de prefijo propiedades
	 */
	public String getPrefijoPropiedades() {
		return prefijoPropiedades;
	}

	/**
	 * Establece el valor de prefijo propiedades.
	 *
	 * @param prefijo
	 *            propiedades el nuevo valor de prefijo propiedades
	 */
	public void setPrefijoPropiedades(final String prefijoPropiedades) {
		this.prefijoPropiedades = prefijoPropiedades;
	}

	/**
	 * @return the claseReal
	 */
	public String getClaseReal() {
		return claseReal;
	}

	/**
	 * @param claseReal
	 *            the claseReal to set
	 */
	public void setClaseReal(final String claseReal) {
		this.claseReal = claseReal;
	}

	/**
	 * @return the claseMock
	 */
	public String getClaseMock() {
		return claseMock;
	}

	/**
	 * @param claseMock
	 *            the claseMock to set
	 */
	public void setClaseMock(final String claseMock) {
		this.claseMock = claseMock;
	}

	/**
	 * toModel.
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Plugin toModel() {
		final Plugin plugin = new Plugin();
		plugin.setCodigo(this.getCodigo());
		plugin.setAmbito(TypeAmbito.fromString(this.getAmbito()));
		plugin.setClassname(this.claseImplementadora);
		plugin.setDescripcion(this.descripcion);
		plugin.setTipo(TypePlugin.fromString(this.tipo));
		plugin.setPropiedades((List<Propiedad>) UtilJSON.fromListJSON(propiedades, Propiedad.class));
		plugin.setPrefijoPropiedades(prefijoPropiedades);
		plugin.setRealClassname(this.claseReal);
		plugin.setMockClassname(this.claseMock);
		return plugin;
	}

	/**
	 * From model.
	 *
	 * @param plugin
	 * @return
	 */
	public static JPlugin fromModel(final Plugin plugin) {
		JPlugin jPlugin = null;
		if (plugin != null) {
			jPlugin = new JPlugin();
			jPlugin.setCodigo(plugin.getCodigo());
			jPlugin.setAmbito(plugin.getAmbito().toString());
			jPlugin.setClaseImplementadora(plugin.getClassname());
			jPlugin.setDescripcion(plugin.getDescripcion());
			jPlugin.setPropiedades(UtilJSON.toJSON(plugin.getPropiedades()));
			jPlugin.setTipo(plugin.getTipo().toString());
			jPlugin.setPrefijoPropiedades(plugin.getPrefijoPropiedades());
			jPlugin.setClaseReal(plugin.getRealClassname());
			jPlugin.setClaseMock(plugin.getMockClassname());
		}
		return jPlugin;
	}

}
