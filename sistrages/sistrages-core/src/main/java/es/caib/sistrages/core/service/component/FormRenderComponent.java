package es.caib.sistrages.core.service.component;

/**
 * Componentes FormRender.
 *
 * @author indra
 *
 */
public interface FormRenderComponent {

	/**
	 * Genera pagina HTML editor.
	 *
	 * @param pIdForm
	 *            id form
	 * @param pPage
	 *            page
	 * @param pIdComponente
	 *            id componente
	 * @param pLang
	 *            lang
	 * @param pContexto
	 *            contexto
	 * @return the string
	 */
	String generaPaginaHTMLEditor(final Long pIdForm, final Long pPage, final String pIdComponente, final String pLang,
			final String pContexto);

	/**
	 * Genera pagina HTML asistente.
	 *
	 * @param pPage
	 *            page
	 * @param pLang
	 *            lang
	 * @return the string
	 */
	String generaPaginaHTMLAsistente(Long pPage, String pLang);

}