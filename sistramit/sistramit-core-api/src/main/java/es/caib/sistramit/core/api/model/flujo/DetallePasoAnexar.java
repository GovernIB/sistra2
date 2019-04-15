package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle del paso "Anexar".
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasoAnexar extends DetallePaso {

	/**
	 * Contador documentos.
	 */
	private ContadorDocumentos contador = new ContadorDocumentos();

	/**
	 * Anexos.
	 */
	private List<Anexo> anexos = new ArrayList<>();

	/**
	 * Indica si el paso es subsanable.
	 */
	private TypeSiNo subsanable = TypeSiNo.NO;

	/**
	 * Constructor.
	 */
	public DetallePasoAnexar() {
		super();
		this.setTipo(TypePaso.ANEXAR);
	}

	/**
	 * Método de acceso a anexos.
	 *
	 * @return anexos
	 */
	public List<Anexo> getAnexos() {
		return anexos;
	}

	/**
	 * Método para establecer anexos.
	 *
	 * @param pAnexos
	 *            anexos a establecer
	 */
	public void setAnexos(final List<Anexo> pAnexos) {
		anexos = pAnexos;
	}

	/**
	 * Obtiene anexo a partir del id.
	 *
	 * @param idAnexo
	 *            Identificador anexo.
	 * @return Anexo
	 */
	public Anexo getAnexo(final String idAnexo) {
		Anexo r = null;
		for (final Anexo f : this.getAnexos()) {
			if (f.getId().equals(idAnexo)) {
				r = f;
				break;
			}
		}
		return r;
	}

	@Override
	public String print() {
		final String ident = "    ";
		final String identForms = "        ";
		final int capacity = ConstantesNumero.N2 * ConstantesNumero.N1024;
		final StringBuffer strb = new StringBuffer(capacity);

		strb.append(ident).append("\n");
		strb.append(ident).append("ID paso:" + getId() + "\n");
		strb.append(ident).append("Sólo lectura:" + getSoloLectura() + "\n");
		strb.append(ident).append("Tipo:" + getTipo() + "\n");
		strb.append(ident).append("Anexos:\n");
		for (final Anexo anexo : this.getAnexos()) {
			strb.append(identForms).append(ident).append(" Id:" + anexo.getId() + "\n");
			strb.append(identForms).append(ident).append(" Titulo:" + anexo.getTitulo() + "\n");
			strb.append(identForms).append(ident).append(" Max instancias:" + anexo.getMaxInstancias() + "\n");
			strb.append(identForms).append(ident).append(" Ayuda:" + anexo.getAyuda() + "\n");
			strb.append(identForms).append(ident).append(" Extensiones:" + anexo.getExtensiones() + "\n");
			strb.append(identForms).append(ident).append(" Firmar:" + anexo.getFirmar().toString() + "\n");
			strb.append(identForms).append(ident)
					.append(" Anexar firmado:" + anexo.getAnexarfirmado().toString() + "\n");
			strb.append(identForms).append(ident).append(" Obligatorio:" + anexo.getObligatorio().toString() + "\n");
			strb.append(identForms).append(ident).append(" Rellenado:" + anexo.getRellenado() + "\n");
			strb.append(identForms).append(ident).append(" Presentacion:" + anexo.getPresentacion() + "\n");
			if (anexo.getFirmantes() != null) {
				strb.append(identForms).append(ident).append("  Firmantes:\n");
				for (final Persona firmante : anexo.getFirmantes()) {
					strb.append(identForms).append(ident).append(ident).append("   NIF:" + firmante.getNif() + "\n");
					strb.append(identForms).append(ident).append(ident)
							.append("   nombre:" + firmante.getNombre() + "\n");
				}
			}
			if (anexo.getFicheros() != null) {
				strb.append(identForms).append(ident).append("  Ficheros:\n");
				for (final Fichero fichero : anexo.getFicheros()) {
					strb.append(identForms).append(ident).append(ident)
							.append("   Fichero:" + fichero.getFichero() + "\n");
					strb.append(identForms).append(ident).append(ident)
							.append("   Titulo (genéricos):" + fichero.getTitulo() + "\n");
				}
			}
		}
		return strb.toString();
	}

	/**
	 * Método de acceso a contador.
	 *
	 * @return contador
	 */
	public ContadorDocumentos getContador() {
		return contador;
	}

	/**
	 * Método para establecer contador.
	 *
	 * @param contador
	 *            contador a establecer
	 */
	public void setContador(final ContadorDocumentos contador) {
		this.contador = contador;
	}

	/**
	 * Método de acceso a subsanable.
	 *
	 * @return subsanable
	 */
	public TypeSiNo getSubsanable() {
		return subsanable;
	}

	/**
	 * Método para establecer subsanable.
	 *
	 * @param subsanable
	 *            subsanable a establecer
	 */
	public void setSubsanable(final TypeSiNo subsanable) {
		this.subsanable = subsanable;
	}

}
