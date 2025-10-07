package es.caib.sistrahelp.frontend.model;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;

import java.util.List;

public class NavegacionEventos {

    private List<EventoAuditoriaTramitacion> eventos;

    private int pageSize;

    private int first;

    private int rowIndex;

    private long total;

    private FiltroAuditoriaTramitacion filtros;

    public EventoAuditoriaTramitacion getPreviousEvento(){
        if (eventos != null && rowIndex > 0) {
            return eventos.get(--rowIndex);
        }
        return null;
    }

    public EventoAuditoriaTramitacion getNextEvento(){
        if (eventos != null && rowIndex < eventos.size() - 1) {
            return eventos.get(++rowIndex);
        }
        return null;
    }

    public int getPageFirst(){
        return rowIndex - (rowIndex % pageSize);
    }

    public boolean hasNext(){
        return eventos != null && first + rowIndex < (total-1);
    }

    public boolean hasPrevious(){
        return eventos != null && (rowIndex > 0 || first != 0);
    }

    public boolean ultimoDePagina(){
        return eventos != null && rowIndex == eventos.size() - 1;
    }

    public void avanzarPagina(List<EventoAuditoriaTramitacion> pagSig){
        if (pagSig != null && !pagSig.isEmpty()) {
            eventos = pagSig;
            rowIndex = 0; // Reset rowIndex to the first item of the new page
            first = first + pageSize; // Adjust first to the next page
        } else {
            eventos = null; // Clear eventos if no more items
            rowIndex = -1; // Set rowIndex to -1 to indicate no current item
        }
    }

    public void retrocedePagina(List<EventoAuditoriaTramitacion> pagAnt) {
        if (pagAnt != null && !pagAnt.isEmpty()) {
            eventos = pagAnt;
            rowIndex = eventos.size() - 1; // Set rowIndex to the last item of the new page
            first = first - pageSize; // Adjust first to the previous page
        } else {
            eventos = null; // Clear eventos if no more items
            rowIndex = -1; // Set rowIndex to -1 to indicate no current item
        }
    }

    public List<EventoAuditoriaTramitacion> getEventos() {
        return eventos;
    }


    public void setEventos(List<EventoAuditoriaTramitacion> eventos) {
        this.eventos = eventos;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public FiltroAuditoriaTramitacion getFiltros() {
        return filtros;
    }

    public void setFiltros(FiltroAuditoriaTramitacion filtros) {
        this.filtros = filtros;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }
}
