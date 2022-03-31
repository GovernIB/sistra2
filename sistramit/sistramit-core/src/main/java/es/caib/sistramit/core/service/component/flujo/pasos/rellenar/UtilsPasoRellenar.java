package es.caib.sistramit.core.service.component.flujo.pasos.rellenar;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.Formulario;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRellenar;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.ValoresFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Utilidades paso rellenar.
 *
 * @author Indra
 *
 */
public final class UtilsPasoRellenar {

	/**
	 * Constructor.
	 */
	private UtilsPasoRellenar() {
		super();
	}

	/**
	 * Obtiene documentos completados paso.
	 *
	 * @param pDipa
	 *                               Datos internos paso
	 * @param pDpp
	 *                               Datos persistencia paso
	 * @param pDefinicionTramite
	 *                               Definicion tramite
	 * @param pIdFormularioHasta
	 *                               Id formulario hasta que busca (si null todos
	 *                               los completados)
	 *
	 * @return lista documentos completados
	 */
	public static List<DatosDocumento> obtenerDocumentosCompletados(final DatosInternosPasoRellenar pDipa,
			final DatosPersistenciaPaso pDpp, final DefinicionTramiteSTG pDefinicionTramite,
			final String pIdFormularioHasta) {
		// Busca definicion paso
		final RPasoTramitacionRellenar pasoDef = (RPasoTramitacionRellenar) UtilsSTG
				.devuelveDefinicionPaso(pDipa.getIdPaso(), pDefinicionTramite);
		// Buscamos los formularios completados.
		final List<DatosDocumento> res = new ArrayList<>();
		for (final Formulario f : ((DetallePasoRellenar) pDipa.getDetallePaso()).getFormularios()) {

			if (pIdFormularioHasta != null && f.getId().equals(pIdFormularioHasta)) {
				break;
			}

			if (f.getRellenado() == TypeEstadoDocumento.RELLENADO_CORRECTAMENTE) {
				final RFormularioTramite formularioDef = UtilsSTG.devuelveDefinicionFormulario(pasoDef, f.getId());
				if (formularioDef == null) {
					throw new ErrorConfiguracionException("No existeix formulari " + f.getId()
							+ " a la definició de la passa " + pasoDef.getIdentificador());
				}

				// TODO Ver datos firmantes

				final DatosDocumentoFormulario ddf = crearDatosDocumentoFormulario(pDpp, f,
						pDipa.getValoresFormulario(f.getId()));
				res.add(ddf);
			}
		}
		return res;
	}

	/**
	 * Crea datos documento accesibles desde los otros pasos.
	 *
	 * @param dpp
	 *                              Datos persistencia paso
	 * @param detalleFormulario
	 *                              Detalle formulario
	 * @param valoresFormulario
	 *                              Valores formulario
	 * @return DatosDocumentoFormulario
	 */
	public static DatosDocumentoFormulario crearDatosDocumentoFormulario(final DatosPersistenciaPaso dpp,
			final Formulario detalleFormulario, final ValoresFormulario valoresFormulario) {
		final DocumentoPasoPersistencia docPers = dpp.getDocumentoPasoPersistencia(detalleFormulario.getId(),
				ConstantesNumero.N1);
		final DatosDocumentoFormulario ddf = DatosDocumentoFormulario.createNewDatosDocumentoFormulario();
		ddf.setIdPaso(dpp.getId());
		ddf.setId(detalleFormulario.getId());
		ddf.setTipoENI("TD14");
		ddf.setFormularioCaptura(false);
		ddf.setTitulo(detalleFormulario.getTitulo());
		ddf.setFichero(new ReferenciaFichero(docPers.getFichero().getId(), docPers.getFichero().getClave()));
		if (docPers.getFormularioPdf() != null) {
			ddf.setPdf(
					new ReferenciaFichero(docPers.getFormularioPdf().getId(), docPers.getFormularioPdf().getClave()));
		}
		ddf.setCampos(valoresFormulario);
		ddf.setFirmar(detalleFormulario.getFirmar());
		ddf.setFirmantes(detalleFormulario.getFirmantes());
		return ddf;
	}

}
