package es.caib.sistrages.frontend.util;

import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.FormularioDisenyo;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Literal;

public class FormularioMockBD {

	private static FormularioDisenyo formulario = null;

	private static void init() {
		formulario = new FormularioDisenyo();
		formulario.setId(1L);

		// Pagina 1
		final PaginaFormulario pagina = new PaginaFormulario();
		pagina.setId(1L);
		formulario.getPaginas().add(pagina);

		// Linea 1
		final LineaComponentesFormulario linea1 = new LineaComponentesFormulario();
		final ComponenteFormularioCampoTexto ctNif = new ComponenteFormularioCampoTexto();
		ctNif.setId(1L);
		ctNif.setCodigo("inputNIF");
		final Literal txtNif = new Literal();
		txtNif.add(new Traduccion("es", "NIF"));
		ctNif.setTexto(txtNif);
		ctNif.setObligatorio(true);
		linea1.getComponentes().add(ctNif);
		final ComponenteFormularioCampoTexto ctNom = new ComponenteFormularioCampoTexto();
		ctNom.setId(2L);
		ctNom.setCodigo("inputNom");
		final Literal txtNom = new Literal();
		txtNom.add(new Traduccion("es", "NOMBRE"));
		ctNom.setTexto(txtNom);
		linea1.getComponentes().add(ctNom);
		final ComponenteFormularioCampoTexto ctApe = new ComponenteFormularioCampoTexto();
		ctApe.setId(3L);
		ctApe.setCodigo("inputApellido");
		final Literal txtApe = new Literal();
		txtApe.add(new Traduccion("es", "APELLIDOS"));
		ctApe.setTexto(txtApe);
		linea1.getComponentes().add(ctApe);
		pagina.getLineas().add(linea1);

		// Linea 2
		final LineaComponentesFormulario linea2 = new LineaComponentesFormulario();
		final ComponenteFormularioCampoTexto ctEmail = new ComponenteFormularioCampoTexto();
		ctEmail.setId(4L);
		ctEmail.setCodigo("inputEmail");
		final Literal txtEmail = new Literal();
		txtEmail.add(new Traduccion("es", "EMAIL"));
		ctEmail.setTexto(txtEmail);
		linea2.getComponentes().add(ctEmail);
		final ComponenteFormularioCampoSelector ctSelector = new ComponenteFormularioCampoSelector();
		ctSelector.setId(5L);
		ctSelector.setCodigo("idSelect");
		final Literal txtSelector = new Literal();
		txtSelector.add(new Traduccion("es", "SELECTOR"));
		ctSelector.setTexto(txtSelector);
		linea2.getComponentes().add(ctSelector);
		final ComponenteFormularioEtiqueta ctEtiqueta1 = new ComponenteFormularioEtiqueta();
		ctEtiqueta1.setId(6L);
		ctEtiqueta1.setCodigo("ETIQUETA1");
		final Literal txtEtiq1 = new Literal();
		txtEtiq1.add(new Traduccion("es", "ETIQUETA 1"));
		ctEtiqueta1.setTexto(txtEtiq1);
		linea2.getComponentes().add(ctEtiqueta1);
		pagina.getLineas().add(linea2);
	}

	public static FormularioDisenyo recuperar(final long codigo) {
		if (formulario == null) {
			init();
		}
		return formulario;
	}

	public static void guardar(final FormularioDisenyo form) {
		formulario = form;
	}

}
