package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Script;

/**
 * JScript
 */
@Entity
@Table(name = "STG_SCRIPT")
public class JScript implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_SCRIPT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_SCRIPT_SEQ", sequenceName = "STG_SCRIPT_SEQ")
	@Column(name = "SCR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Lob
	@Column(name = "SCR_SCRIPT")
	private String script;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "script", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("codigo")
	private Set<JLiteralErrorScript> literales = new HashSet<>(0);

	/** Constructor. **/
	public JScript() {
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
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 */
	public void setScript(final String script) {
		this.script = script;
	}

	/**
	 * @return the literales
	 */
	public Set<JLiteralErrorScript> getLiterales() {
		return literales;
	}

	/**
	 * @param literales
	 *            the literales to set
	 */
	public void setLiterales(final Set<JLiteralErrorScript> literales) {
		this.literales = literales;
	}

	/**
	 * From model.
	 *
	 * @param mScript
	 * @return
	 */
	public static JScript fromModel(final Script mScript) {
		JScript jScript = null;
		if (mScript != null) {
			jScript = new JScript();
			jScript.setCodigo(mScript.getCodigo());
			jScript.setScript(mScript.getContenido());
			if (mScript.getMensajes() != null) {
				final Set<JLiteralErrorScript> literales = new HashSet<>(0);
				for (final LiteralScript literal : mScript.getMensajes()) {
					literales.add(JLiteralErrorScript.fromModel(literal, jScript));
				}
				jScript.setLiterales(literales);
			}
		}
		return jScript;
	}

	/**
	 * To model.
	 *
	 * @return
	 */
	public Script toModel() {
		final Script mScript = new Script();
		mScript.setCodigo(this.getCodigo());
		mScript.setContenido(this.getScript());
		if (this.getLiterales() != null) {
			final List<LiteralScript> mensajes = new ArrayList<>();
			for (final JLiteralErrorScript literal : this.getLiterales()) {
				mensajes.add(literal.toModel());
			}
			mScript.setMensajes(mensajes);
		}
		return mScript;
	}

	/**
	 * Clona el script
	 *
	 * @param origScript
	 * @return
	 */
	public static JScript clonar(final JScript origScript) {
		JScript jscript = null;
		if (origScript != null) {
			jscript = new JScript();
			jscript.setCodigo(null);
			jscript.setScript(origScript.getScript());
			if (origScript.getLiterales() != null) {
				final Set<JLiteralErrorScript> literales = new HashSet<>(0);
				for (final JLiteralErrorScript literal : origScript.getLiterales()) {
					literales.add(JLiteralErrorScript.clonar(literal, jscript));
				}
				jscript.setLiterales(literales);
			}
		}
		return jscript;
	}

	/**
	 * Realiza un merge entre el jscript y el mscript.
	 *
	 * @param jscript
	 * @param mscript
	 * @return
	 */
	public static JScript merge(JScript jscript, final Script mscript) {
		if (jscript == null) {
			jscript = new JScript();
		}
		jscript.setScript(mscript.getContenido());
		final Set<JLiteralErrorScript> literalesBorrar = new HashSet<>();

		// Buscamos si se han borrado literales y se añaden a la lista para borrar
		for (final JLiteralErrorScript jliteral : jscript.getLiterales()) {
			if (!mscript.containLiteral(jliteral.getCodigo())) {
				literalesBorrar.add(jliteral);
			}
		}
		if (!literalesBorrar.isEmpty()) {
			jscript.getLiterales().removeAll(literalesBorrar);
		}

		// Recorremos los literales del model. Dependiendo de si existe:
		if (mscript.getMensajes() != null) {
			for (final LiteralScript mensaje : mscript.getMensajes()) {
				// Si existe o no, se comprueba desde dentro del addLiteralMerge
				jscript.addLiteralMerge(mensaje);
			}
		}

		return jscript;
	}

	/**
	 * Merge los literales. Dependiendo de si existe o no:
	 * <ul>
	 * <li>Si no existe, se añade directamente al jscript.literales</li>
	 * <li>Si existe, se realiza un merge de sus literales</li>
	 * </ul>
	 *
	 * @param mensaje
	 */
	private void addLiteralMerge(final LiteralScript mensaje) {

		boolean existe = false;
		// Comparamos por codigo
		if (this.getLiterales() != null && mensaje.getCodigo() != null) {
			for (final JLiteralErrorScript literal : this.getLiterales()) {
				if (literal.getCodigo().equals(mensaje.getCodigo())) {
					literal.setLiteral(JLiteral.mergeModel(literal.getLiteral(), mensaje.getLiteral()));
					literal.setIdentificador(mensaje.getIdentificador());
					existe = true;
					break;
				}
			}
		}

		if (!existe) {
			// Si no lo contiene, lo añadimos
			this.getLiterales().add(JLiteralErrorScript.fromModel(mensaje, this));
		}
	}

}
