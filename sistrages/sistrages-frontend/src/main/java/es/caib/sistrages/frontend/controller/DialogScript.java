package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.ToggleEvent;

import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogScript extends DialogControllerBase {

	private static final long serialVersionUID = 20111020L;

	/** Id del script. **/
	private String id;
	/** Data del script. **/
	private Script data;
	/** Data del script en formato JSON. **/
	private String iData;

	/** Lenguaje de programacion (javascript/css/java..). **/
	private String mode = "javascript";
	/** Tema. **/
	private final String theme = "blackboard";
	/** Keymap. **/
	private final String keymap = "default";

	private boolean visibleFormularios = true;
	private boolean visibleHerramientas = false;
	private boolean visibleMensajes = true;
	private boolean visibleDominios = true;

	public DialogScript() {
		// Constructor vacio.
	}

	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Script();
		} else {
			if (iData == null) {
				data = new Script();
			} else {
				data = (Script) UtilJSON.fromJSON(iData, Script.class);
			}
		}
	}

	public void changeMode() {
		if (mode.equals("css")) {
			mode = "javascript";
		} else {
			mode = "css";
		}
	}

	/**
	 * Cuando se maximiza un panel.
	 *
	 * @param event
	 */
	public void onToggle(final ToggleEvent event) {
		if ("VISIBLE".equals(event.getVisibility().name())) {

			visibleFormularios = true;
			visibleHerramientas = true;
			visibleMensajes = true;
			visibleDominios = true;

			if ("panelFormularios".equals(event.getComponent().getId())) {
				visibleFormularios = false;
			}

			if ("panelHerramientas".equals(event.getComponent().getId())) {
				visibleHerramientas = false;
			}

			if ("panelMensajes".equals(event.getComponent().getId())) {
				visibleMensajes = false;
			}

			if ("panelDominios".equals(event.getComponent().getId())) {
				visibleDominios = false;
			}
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(this.data);
		UtilJSF.closeDialog(result);

	}

	/**
	 * Aceptar.
	 */
	public void borrar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(null);
		UtilJSF.closeDialog(result);

	}

	/**
	 * Aceptar.
	 */
	public void cancelar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public List<String> getThemes() {
		final List<String> results = new ArrayList<String>();

		results.add("3024-day");
		results.add("3024-night");
		results.add("abcdef");
		results.add("ambiance");
		results.add("ambiance-mobile");
		results.add("base16-dark");
		results.add("base16-light");
		results.add("bespin");
		results.add("blackboard");
		results.add("cobalt");
		results.add("colorforth");
		results.add("dracula");
		results.add("eclipse");
		results.add("elegant");
		results.add("erlang-dark");
		results.add("hopscotch");
		results.add("icecoder");
		results.add("isotope");
		results.add("lesser-dark");
		results.add("liquibyte");
		results.add("material");
		results.add("mbo");
		results.add("mdn-like");
		results.add("midnight");
		results.add("monokai");
		results.add("neat");
		results.add("neo");
		results.add("night");
		results.add("panda-syntax");
		results.add("paraiso-dark");
		results.add("paraiso-light");
		results.add("pastel-on-dark");
		results.add("railscasts");
		results.add("rubyblue");
		results.add("seti");
		results.add("solarized");
		results.add("the-matrix");
		results.add("tomorrow-night-bright");
		results.add("tomorrow-night-eighties");
		results.add("ttcn");
		results.add("twilight");
		results.add("vibrant-ink");
		results.add("xq-dark");
		results.add("xq-light");
		results.add("yeti");
		results.add("zenburn");

		Collections.sort(results);
		return results;
	}

	public List<String> getModes() {
		final List<String> results = new ArrayList<String>();

		results.add("apl");
		results.add("asn.1");
		results.add("asterisk");
		results.add("asciiarmor");
		results.add("brainfuck");
		results.add("clike");
		results.add("clojure");
		results.add("cmake");
		results.add("cobol");
		results.add("coffeescript");
		results.add("commonlisp");
		results.add("crystal");
		results.add("css");
		results.add("cypher");
		results.add("d");
		results.add("dart");
		results.add("diff");
		results.add("django");
		results.add("dtd");
		results.add("dylan");
		results.add("ebnf");
		results.add("ecl");
		results.add("eiffel");
		results.add("eml");
		results.add("erlang");
		results.add("fcl");
		results.add("fortran");
		results.add("freemarker");
		results.add("gfm");
		results.add("gas");
		results.add("gherkin");
		results.add("go");
		results.add("groovy");
		results.add("haml");
		results.add("haskell");
		results.add("haskell-literate");
		results.add("haxe");
		results.add("htmlembedded");
		results.add("htmlmixed");
		results.add("http");
		results.add("idl");
		results.add("javascript");
		results.add("jinja2");
		results.add("julia");
		results.add("jsx");
		results.add("lua");
		results.add("markdown");
		results.add("mathematica");
		results.add("mbox");
		results.add("mirc");
		results.add("mllike");
		results.add("modelica");
		results.add("mscgen");
		results.add("mumps");
		results.add("nginx");
		results.add("ntriples");
		results.add("octave");
		results.add("oz");
		results.add("pascal");
		results.add("pegjs");
		results.add("perl");
		results.add("php");
		results.add("pig");
		results.add("powershell");
		results.add("properties");
		results.add("protobuf");
		results.add("python");
		results.add("pug");
		results.add("puppet");
		results.add("q");
		results.add("r");
		results.add("rpm");
		results.add("rst");
		results.add("ruby");
		results.add("sass");
		results.add("scala");
		results.add("scheme");
		results.add("shell");
		results.add("sieve");
		results.add("slim");
		results.add("smalltalk");
		results.add("smarty");
		results.add("solr");
		results.add("soy");
		results.add("sparql");
		results.add("swift");
		results.add("spreadsheet");
		results.add("stylus");
		results.add("sql");
		results.add("stex");
		results.add("tcl");
		results.add("textile");
		results.add("tiddlywiki");
		results.add("tiki");
		results.add("toml");
		results.add("tornado");
		results.add("troff");
		results.add("ttcn");
		results.add("ttcn-cfg");
		results.add("turtle");
		results.add("twig");
		results.add("vb");
		results.add("vbscript");
		results.add("velocity");
		results.add("verilog");
		results.add("vhdl");
		results.add("vue");
		results.add("webidl");
		results.add("xml");
		results.add("xquery");
		results.add("yaml");
		results.add("yaml-frontmatter");
		results.add("z80");

		Collections.sort(results);
		return results;
	}

	public List<String> getKeymaps() {
		final List<String> results = new ArrayList<String>();

		results.add("default");
		results.add("emacs");
		results.add("sublime");
		results.add("vim");

		Collections.sort(results);
		return results;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(final String mode) {
		this.mode = mode;
	}

	/**
	 * @return the visibleFormularios
	 */
	public boolean isVisibleFormularios() {
		return visibleFormularios;
	}

	/**
	 * @param visibleFormularios
	 *            the visibleFormularios to set
	 */
	public void setVisibleFormularios(final boolean visibleFormularios) {
		this.visibleFormularios = visibleFormularios;
	}

	/**
	 * @return the visibleHerramientas
	 */
	public boolean isVisibleHerramientas() {
		return visibleHerramientas;
	}

	/**
	 * @param visibleHerramientas
	 *            the visibleHerramientas to set
	 */
	public void setVisibleHerramientas(final boolean visibleHerramientas) {
		this.visibleHerramientas = visibleHerramientas;
	}

	/**
	 * @return the visibleMensajes
	 */
	public boolean isVisibleMensajes() {
		return visibleMensajes;
	}

	/**
	 * @param visibleMensajes
	 *            the visibleMensajes to set
	 */
	public void setVisibleMensajes(final boolean visibleMensajes) {
		this.visibleMensajes = visibleMensajes;
	}

	/**
	 * @return the visibleDominios
	 */
	public boolean isVisibleDominios() {
		return visibleDominios;
	}

	/**
	 * @param visibleDominios
	 *            the visibleDominios to set
	 */
	public void setVisibleDominios(final boolean visibleDominios) {
		this.visibleDominios = visibleDominios;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @return the keymap
	 */
	public String getKeymap() {
		return keymap;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public Script getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Script data) {
		this.data = data;
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

}
