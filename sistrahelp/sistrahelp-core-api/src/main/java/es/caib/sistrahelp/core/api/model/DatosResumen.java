package es.caib.sistrahelp.core.api.model;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class DatosResumen {

    private String fechaDesde;
    private String fechaHasta;
    private List<ErroresPorTramiteCM> listaErrores;
    private List<EventoCM> listaTramErrores;

    private List<EventoCM> listaErrPlat;
    private List<ErroresPorTramiteCM> listaInacabados;
    private Pair<Integer, Integer> formIniFin;
    private Pair<Integer, Integer> firmaIniFinOk;

    private Pair<Integer, Integer> pagIniFin;

    private Pair<Integer, Integer> regIniFin;

    private Pair<Integer, Integer> tramIniFin;

    private int errTot;
    private int errPlat;
    private Integer umbralNormalAtencion;

    private Integer umbralAtencionRevisar;

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<ErroresPorTramiteCM> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<ErroresPorTramiteCM> listaErrores) {
        this.listaErrores = listaErrores;
    }

    public List<EventoCM> getListaTramErrores() {
        return listaTramErrores;
    }

    public void setListaTramErrores(List<EventoCM> listaTramErrores) {
        this.listaTramErrores = listaTramErrores;
    }

    public List<EventoCM> getListaErrPlat() {
        return listaErrPlat;
    }

    public void setListaErrPlat(List<EventoCM> listaErrPlat) {
        this.listaErrPlat = listaErrPlat;
    }

    public List<ErroresPorTramiteCM> getListaInacabados() {
        return listaInacabados;
    }

    public void setListaInacabados(List<ErroresPorTramiteCM> listaInacabados) {
        this.listaInacabados = listaInacabados;
    }

    public Pair<Integer, Integer> getFormIniFin() {
        return formIniFin;
    }

    public void setFormIniFin(Pair<Integer, Integer> formIniFin) {
        this.formIniFin = formIniFin;
    }

    public Pair<Integer, Integer> getFirmaIniFinOk() {
        return firmaIniFinOk;
    }

    public void setFirmaIniFinOk(Pair<Integer, Integer> firmaIniFinOk) {
        this.firmaIniFinOk = firmaIniFinOk;
    }

    public Pair<Integer, Integer> getPagIniFin() {
        return pagIniFin;
    }

    public void setPagIniFin(Pair<Integer, Integer> pagIniFin) {
        this.pagIniFin = pagIniFin;
    }

    public Pair<Integer, Integer> getRegIniFin() {
        return regIniFin;
    }

    public void setRegIniFin(Pair<Integer, Integer> regIniFin) {
        this.regIniFin = regIniFin;
    }

    public Pair<Integer, Integer> getTramIniFin() {
        return tramIniFin;
    }

    public void setTramIniFin(Pair<Integer, Integer> tramIniFin) {
        this.tramIniFin = tramIniFin;
    }

    public int getErrTot() {
        return errTot;
    }

    public void setErrTot(int errTot) {
        this.errTot = errTot;
    }

    public int getErrPlat() {
    	return errPlat;
    }

    public void setErrPlat(int errPlat) {
    	this.errPlat = errPlat;
    }

    public Integer getUmbralNormalAtencion() {
        return umbralNormalAtencion;
    }

    public void setUmbralNormalAtencion(Integer umbralNormalAtencion) {
        this.umbralNormalAtencion = umbralNormalAtencion;
    }

    public Integer getUmbralAtencionRevisar() {
        return umbralAtencionRevisar;
    }

    public void setUmbralAtencionRevisar(Integer umbralAtencionRevisar) {
        this.umbralAtencionRevisar = umbralAtencionRevisar;
    }
}
