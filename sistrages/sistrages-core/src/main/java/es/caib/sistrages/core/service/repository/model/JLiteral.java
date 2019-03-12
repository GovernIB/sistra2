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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;

/**
 * JLiterales
 */
@Entity
@Table(name = "STG_TRADUC")
public class JLiteral implements IModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TRADUC_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TRADUC_SEQ", sequenceName = "STG_TRADUC_SEQ")
	@Column(name = "TRA_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "traduccion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JTraduccionLiteral> traduccionLiterales = new HashSet<>(0);

	/** Constructor vacio. **/
	public JLiteral() {
		// Constructor vacio.
	}

	/**
	 * Get codigo.
	 *
	 * @return
	 */
	public Long getCodigo() {
		return this.codigo;
	}

	/**
	 * Set codigo.
	 *
	 * @param codigo
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Get traducciones.
	 *
	 * @return
	 */
	public Set<JTraduccionLiteral> getTraduccionLiterales() {
		return this.traduccionLiterales;
	}

	/**
	 * Set traducciones
	 *
	 * @param traduccionLiterales
	 */
	public void setTraduccionLiterales(final Set<JTraduccionLiteral> traduccionLiterales) {
		this.traduccionLiterales = traduccionLiterales;
	}

	/**
	 * Get traduccion por idioma.
	 *
	 * @param idioma
	 * @return
	 */
	public JTraduccionLiteral getJTraduccionLiteral(final String idioma) {
		JTraduccionLiteral res = null;
		if (this.traduccionLiterales != null) {
			for (final JTraduccionLiteral jtraduccion : this.traduccionLiterales) {
				if (jtraduccion.getIdioma().getIdentificador().equals(idioma)) {
					res = jtraduccion;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * ToModel.
	 *
	 * @return
	 */
	public Literal toModel() {
		final Literal literal = new Literal();
		literal.setCodigo(this.codigo);
		if (this.traduccionLiterales != null) {
			for (final JTraduccionLiteral jtraduccion : this.traduccionLiterales) {
				literal.add(jtraduccion.toModel());
			}
		}

		return literal;
	}

	/**
	 * FromModel.
	 *
	 * @param literal
	 * @return
	 */
	public static JLiteral fromModel(final Literal literal) {
		JLiteral jModel = null;
		if (literal != null) {
			jModel = new JLiteral();
			jModel.setCodigo(literal.getCodigo());
			if (literal.getTraducciones() != null) {
				jModel.setTraduccionLiterales(new HashSet<JTraduccionLiteral>());
				for (final Traduccion traduccion : literal.getTraducciones()) {
					final JTraduccionLiteral jtraduccion = JTraduccionLiteral.fromModel(traduccion);
					jtraduccion.setTraduccion(jModel);
					jModel.getTraduccionLiterales().add(jtraduccion);
				}
			}
		}
		return jModel;
	}

	/**
	 * Hace merge de las traducciones comparando lo almacenado con el modelo.
	 *
	 * @param jLiteral
	 *            datos almacenados
	 * @param mLiteral
	 *            datos modelo
	 * @return datos mergeados
	 */
	public static JLiteral mergeModel(final JLiteral jLiteral, final Literal mLiteral) {
		JLiteral jModel = null;
		if (mLiteral == null) {
			jModel = null;
		} else if (jLiteral == null) {
			jModel = JLiteral.fromModel(mLiteral);
		} else {
			// Borrar idiomas no pasados en modelo
			final List<JTraduccionLiteral> borrar = new ArrayList<>();
			for (final JTraduccionLiteral jTrad : jLiteral.getTraduccionLiterales()) {
				if (!mLiteral.getIdiomas().contains(jTrad.getIdioma().getIdentificador())) {
					borrar.add(jTrad);
				}
			}
			for (final JTraduccionLiteral jTrad : borrar) {
				jLiteral.getTraduccionLiterales().remove(jTrad);
			}

			// Actualizamos idiomas pasados en modelo
			for (final String idioma : mLiteral.getIdiomas()) {
				JTraduccionLiteral jTraduccionLiteral = jLiteral.getJTraduccionLiteral(idioma);
				if (jTraduccionLiteral != null) {
					jTraduccionLiteral.setLiteral(mLiteral.getTraduccion(idioma));
				} else {
					jTraduccionLiteral = new JTraduccionLiteral();
					jTraduccionLiteral.setIdioma(new JIdioma(idioma));
					jTraduccionLiteral.setLiteral(mLiteral.getTraduccion(idioma));
					jTraduccionLiteral.setTraduccion(jLiteral);
					jLiteral.getTraduccionLiterales().add(jTraduccionLiteral);
				}
			}
			jModel = jLiteral;
		}
		return jModel;
	}

	/**
	 * Clona el objeto.
	 *
	 * @return
	 */
	public static JLiteral clonar(final JLiteral origLiteral) {
		JLiteral jliteral = null;
		if (origLiteral != null) {
			jliteral = new JLiteral();
			jliteral.setCodigo(null);
			if (origLiteral.getTraduccionLiterales() != null) {
				jliteral.setTraduccionLiterales(new HashSet<JTraduccionLiteral>());
				for (final JTraduccionLiteral traduccionOriginal : origLiteral.getTraduccionLiterales()) {
					if (traduccionOriginal != null) {
						final JTraduccionLiteral traduccion = JTraduccionLiteral.clonar(traduccionOriginal);
						traduccion.setTraduccion(jliteral);
						jliteral.getTraduccionLiterales().add(traduccion);
					}
				}
			}
		}
		return jliteral;
	}

}
