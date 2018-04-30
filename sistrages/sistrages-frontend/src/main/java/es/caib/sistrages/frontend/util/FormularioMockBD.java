package es.caib.sistrages.frontend.util;

import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Traduccion;

public class FormularioMockBD {

	private static FormularioInterno formulario1 = null;
	private static FormularioInterno formulario2 = null;

	private static void init() {
		formulario1 = new FormularioInterno();
		formulario1.setId(1L);

		formulario2 = new FormularioInterno();
		formulario2.setId(2L);

		formulario1.setCabeceraFormulario(true);
		formulario2.setCabeceraFormulario(true);

		final Literal txtCabecera = new Literal();
		txtCabecera.add(new Traduccion("es", "Sol·licitud de primera expedició o renovació de llicència"));
		formulario1.setTextoCabecera(txtCabecera);
		formulario2.setTextoCabecera(txtCabecera);

		// Pagina 1
		final PaginaFormulario pagina = new PaginaFormulario();
		pagina.setId(1L);
		pagina.setOrden(1);
		formulario1.getPaginas().add(pagina);
		formulario2.getPaginas().add(pagina);

		// Linea 1
		final LineaComponentesFormulario linea1 = new LineaComponentesFormulario();

		final ComponenteFormularioCampoTexto ctNif = new ComponenteFormularioCampoTexto();
		ctNif.setId(1L);
		ctNif.setCodigo("inputNIF");
		ctNif.setColumnas(1);
		final Literal txtNif = new Literal();
		txtNif.add(new Traduccion("es", "NIF"));
		ctNif.setTexto(txtNif);
		ctNif.setObligatorio(true);
		linea1.getComponentes().add(ctNif);

		final ComponenteFormularioCampoTexto ctNom = new ComponenteFormularioCampoTexto();
		ctNom.setId(2L);
		ctNom.setCodigo("inputNom");
		ctNom.setColumnas(1);
		final Literal txtNom = new Literal();
		txtNom.add(new Traduccion("es", "NOMBRE"));
		ctNom.setTexto(txtNom);
		linea1.getComponentes().add(ctNom);

		final ComponenteFormularioCampoTexto ctApe1 = new ComponenteFormularioCampoTexto();
		ctApe1.setId(3L);
		ctApe1.setCodigo("inputApellido1");
		ctApe1.setColumnas(1);
		final Literal txtApe1 = new Literal();
		txtApe1.add(new Traduccion("es", "APELLIDO 1"));
		ctApe1.setTexto(txtApe1);
		linea1.getComponentes().add(ctApe1);

		final ComponenteFormularioCampoTexto ctApe2 = new ComponenteFormularioCampoTexto();
		ctApe2.setId(4L);
		ctApe2.setCodigo("inputApellido2");
		ctApe2.setColumnas(1);
		final Literal txtApe2 = new Literal();
		txtApe2.add(new Traduccion("es", "APELLIDO 2"));
		ctApe2.setTexto(txtApe2);
		linea1.getComponentes().add(ctApe2);

		final ComponenteFormularioCampoTexto ctEdad = new ComponenteFormularioCampoTexto();
		ctEdad.setId(5L);
		ctEdad.setCodigo("inputEdad");
		ctEdad.setColumnas(1);
		final Literal txtEdad = new Literal();
		txtEdad.add(new Traduccion("es", "Edad"));
		ctEdad.setTexto(txtEdad);
		linea1.getComponentes().add(ctEdad);

		final ComponenteFormularioCampoTexto ctNick = new ComponenteFormularioCampoTexto();
		ctNick.setId(6L);
		ctNick.setCodigo("inputNick");
		ctNick.setColumnas(1);
		final Literal txtNick = new Literal();
		txtNick.add(new Traduccion("es", "Nick"));
		ctNick.setTexto(txtNick);
		linea1.getComponentes().add(ctNick);

		pagina.getLineas().add(linea1);

		// Linea 2
		final LineaComponentesFormulario linea2 = new LineaComponentesFormulario();

		final ComponenteFormularioCampoTexto ctEmail = new ComponenteFormularioCampoTexto();
		ctEmail.setId(7L);
		ctEmail.setCodigo("inputEmail");
		ctEmail.setColumnas(2);
		final Literal txtEmail = new Literal();
		txtEmail.add(new Traduccion("es", "EMAIL"));
		ctEmail.setTexto(txtEmail);
		linea2.getComponentes().add(ctEmail);

		pagina.getLineas().add(linea2);

		// Linea 3
		final LineaComponentesFormulario linea3 = new LineaComponentesFormulario();

		final ComponenteFormularioCampoTexto ctDireccion = new ComponenteFormularioCampoTexto();
		ctDireccion.setId(8L);
		ctDireccion.setCodigo("inputDireccion");
		ctDireccion.setColumnas(3);
		final Literal txtDireccion = new Literal();
		txtDireccion.add(new Traduccion("es", "DIRECCION"));
		ctDireccion.setTexto(txtDireccion);
		linea3.getComponentes().add(ctDireccion);

		pagina.getLineas().add(linea3);

		// pagina 2
		final PaginaFormulario pagina2 = new PaginaFormulario();
		pagina2.setId(2L);
		pagina2.setOrden(2);
		formulario2.getPaginas().add(pagina2);
		pagina2.getLineas().add(linea2);

	}

	public static FormularioInterno recuperar(final long idForm) {
		FormularioInterno formulario = null;
		if (formulario1 == null) {
			init();
		}

		if (idForm == 1) {
			formulario = formulario1;
		} else if (idForm == 2) {
			formulario = formulario2;
		}

		return formulario;
	}

	public static PaginaFormulario recuperarPagina(final long idForm, final int page) {
		PaginaFormulario paginaFormulario = null;
		if (formulario1 == null) {
			init();
		}

		for (final PaginaFormulario pagina : recuperar(idForm).getPaginas()) {
			if (pagina.getOrden() == page) {
				paginaFormulario = pagina;
				break;
			}
		}

		return paginaFormulario;
	}

	public static void guardar(final FormularioInterno form) {
		if (form.getId() == 1) {
			formulario1 = form;
		} else if (form.getId() == 2) {
			formulario2 = form;
		}
	}

}
