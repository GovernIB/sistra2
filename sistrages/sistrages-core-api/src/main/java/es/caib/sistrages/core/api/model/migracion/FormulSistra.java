package es.caib.sistrages.core.api.model.migracion;

import java.util.Date;
import java.util.List;

public class FormulSistra {

	private Long forCodi;

	private String forModelo;
	private Long forUlnuse;
	private String forUrlen1;
	private String forUrlen2;
	private Long forHasbco;
	private Long forBcodex;
	private Long forBcodey;
	private Long forDtd;
	private Long forLogti1;
	private Long forLogti2;
	private Long forEsbloq;
	private String forMtvblq;
	private Long forVersio;
	private Long forLastve;
	private String forTagcar;
	private Date forFeccar;
	private Long forVerfun;

	private List<PantalSistra> paginas;

	public FormulSistra() {
		super();
	}

	public Long getForCodi() {
		return forCodi;
	}

	public void setForCodi(final Long forCodi) {
		this.forCodi = forCodi;
	}

	public String getForModelo() {
		return forModelo;
	}

	public void setForModelo(final String forModelo) {
		this.forModelo = forModelo;
	}

	public Long getForUlnuse() {
		return forUlnuse;
	}

	public void setForUlnuse(final Long forUlnuse) {
		this.forUlnuse = forUlnuse;
	}

	public String getForUrlen1() {
		return forUrlen1;
	}

	public void setForUrlen1(final String forUrlen1) {
		this.forUrlen1 = forUrlen1;
	}

	public String getForUrlen2() {
		return forUrlen2;
	}

	public void setForUrlen2(final String forUrlen2) {
		this.forUrlen2 = forUrlen2;
	}

	public Long getForHasbco() {
		return forHasbco;
	}

	public void setForHasbco(final Long forHasbco) {
		this.forHasbco = forHasbco;
	}

	public Long getForBcodex() {
		return forBcodex;
	}

	public void setForBcodex(final Long forBcodex) {
		this.forBcodex = forBcodex;
	}

	public Long getForBcodey() {
		return forBcodey;
	}

	public void setForBcodey(final Long forBcodey) {
		this.forBcodey = forBcodey;
	}

	public Long getForDtd() {
		return forDtd;
	}

	public void setForDtd(final Long forDtd) {
		this.forDtd = forDtd;
	}

	public Long getForLogti1() {
		return forLogti1;
	}

	public void setForLogti1(final Long forLogti1) {
		this.forLogti1 = forLogti1;
	}

	public Long getForLogti2() {
		return forLogti2;
	}

	public void setForLogti2(final Long forLogti2) {
		this.forLogti2 = forLogti2;
	}

	public Long getForEsbloq() {
		return forEsbloq;
	}

	public void setForEsbloq(final Long forEsbloq) {
		this.forEsbloq = forEsbloq;
	}

	public String getForMtvblq() {
		return forMtvblq;
	}

	public void setForMtvblq(final String forMtvblq) {
		this.forMtvblq = forMtvblq;
	}

	public Long getForVersio() {
		return forVersio;
	}

	public void setForVersio(final Long forVersio) {
		this.forVersio = forVersio;
	}

	public Long getForLastve() {
		return forLastve;
	}

	public void setForLastve(final Long forLastve) {
		this.forLastve = forLastve;
	}

	public String getForTagcar() {
		return forTagcar;
	}

	public void setForTagcar(final String forTagcar) {
		this.forTagcar = forTagcar;
	}

	public Date getForFeccar() {
		return forFeccar;
	}

	public void setForFeccar(final Date forFeccar) {
		this.forFeccar = forFeccar;
	}

	public Long getForVerfun() {
		return forVerfun;
	}

	public void setForVerfun(final Long forVerfun) {
		this.forVerfun = forVerfun;
	}

	public List<PantalSistra> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<PantalSistra> paginas) {
		this.paginas = paginas;
	}

}
