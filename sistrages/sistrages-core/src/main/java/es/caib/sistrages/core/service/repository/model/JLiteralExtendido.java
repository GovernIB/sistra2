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
 * JLiteralesExtendido
 */
@Entity
@Table(name = "STG_TRADEX")
public class JLiteralExtendido implements IModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TRADEX_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TRADEX_SEQ", sequenceName = "STG_TRADEX_SEQ")
	@Column(name = "TEX_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "traduccion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JTraduccionLiteralExtendido> traduccionLiterales = new HashSet<>(0);

	/** Constructor vacio. **/
	public JLiteralExtendido() {
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
	public Set<JTraduccionLiteralExtendido> getTraduccionLiterales() {
		return this.traduccionLiterales;
	}

	/**
	 * Set traducciones
	 *
	 * @param traduccionLiterales
	 */
	public void setTraduccionLiterales(final Set<JTraduccionLiteralExtendido> traduccionLiterales) {
		this.traduccionLiterales = traduccionLiterales;
	}

	/**
	 * Get traduccion por idioma.
	 *
	 * @param idioma
	 * @return
	 */
	public JTraduccionLiteralExtendido getJTraduccionLiteral(final String idioma) {
		JTraduccionLiteralExtendido res = null;
		if (this.traduccionLiterales != null) {
			for (final JTraduccionLiteralExtendido jtraduccion : this.traduccionLiterales) {
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
			for (final JTraduccionLiteralExtendido jtraduccion : this.traduccionLiterales) {
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
	public static JLiteralExtendido fromModel(final Literal literal) {
		JLiteralExtendido jModel = null;
		if (literal != null) {
			jModel = new JLiteralExtendido();
			jModel.setCodigo(literal.getCodigo());
			if (literal.getTraducciones() != null) {
				jModel.setTraduccionLiterales(new HashSet<JTraduccionLiteralExtendido>());
				for (final Traduccion traduccion : literal.getTraducciones()) {
					final JTraduccionLiteralExtendido jtraduccion = JTraduccionLiteralExtendido.fromModel(traduccion);
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
	public static JLiteralExtendido mergeModel(final JLiteralExtendido jLiteral, final Literal mLiteral) {
		JLiteralExtendido jModel = null;
		if (mLiteral == null) {
			jModel = null;
		} else if (jLiteral == null) {
			jModel = JLiteralExtendido.fromModel(mLiteral);
		} else {
			// Borrar idiomas no pasados en modelo
			final List<JTraduccionLiteralExtendido> borrar = new ArrayList<>();
			for (final JTraduccionLiteralExtendido jTrad : jLiteral.getTraduccionLiterales()) {
				if (!mLiteral.getIdiomas().contains(jTrad.getIdioma().getIdentificador())) {
					borrar.add(jTrad);
				}
			}
			for (final JTraduccionLiteralExtendido jTrad : borrar) {
				jLiteral.getTraduccionLiterales().remove(jTrad);
			}

			// Actualizamos idiomas pasados en modelo
			for (final String idioma : mLiteral.getIdiomas()) {
				JTraduccionLiteralExtendido jTraduccionLiteral = jLiteral.getJTraduccionLiteral(idioma);
				if (jTraduccionLiteral != null) {
					jTraduccionLiteral.setLiteral(mLiteral.getTraduccion(idioma));
				} else {
					jTraduccionLiteral = new JTraduccionLiteralExtendido();
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
	public static JLiteralExtendido clonar(final JLiteralExtendido origLiteral) {
		JLiteralExtendido jliteral = null;
		if (origLiteral != null) {
			jliteral = new JLiteralExtendido();
			jliteral.setCodigo(null);
			if (origLiteral.getTraduccionLiterales() != null) {
				jliteral.setTraduccionLiterales(new HashSet<JTraduccionLiteralExtendido>());
				for (final JTraduccionLiteralExtendido traduccionOriginal : origLiteral.getTraduccionLiterales()) {
					if (traduccionOriginal != null) {
						final JTraduccionLiteralExtendido traduccion = JTraduccionLiteralExtendido.clonar(traduccionOriginal);
						traduccion.setTraduccion(jliteral);
						jliteral.getTraduccionLiterales().add(traduccion);
					}
				}
			}
		}
		return jliteral;
	}

}
