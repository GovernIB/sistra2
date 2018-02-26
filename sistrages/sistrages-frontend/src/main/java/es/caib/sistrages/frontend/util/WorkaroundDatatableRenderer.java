package es.caib.sistrages.frontend.util;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.DataTableRenderer;

/**
 * Clase de renderizado de tabla por el bug de esta versión.
 *
 * @author Indra
 *
 */
public class WorkaroundDatatableRenderer extends DataTableRenderer {

	@Override
	protected void encodeCell(final FacesContext context, final DataTable table, final UIColumn column,
			final String clientId, final boolean selected) throws IOException {
		if (!column.isRendered()) {
			return;
		}

		final ResponseWriter writer = context.getResponseWriter();
		final boolean selectionEnabled = column.getSelectionMode() != null;
		final int priority = column.getPriority();
		final String style = column.getStyle();
		String styleClass = selectionEnabled ? DataTable.SELECTION_COLUMN_CLASS
				: (column.getCellEditor() != null && column.getCellEditor().isRendered())
						? DataTable.EDITABLE_COLUMN_CLASS
						: null;
		styleClass = (column.isSelectRow()) ? styleClass
				: (styleClass == null) ? DataTable.UNSELECTABLE_COLUMN_CLASS
						: styleClass + " " + DataTable.UNSELECTABLE_COLUMN_CLASS;
		styleClass = (column.isVisible()) ? styleClass
				: (styleClass == null) ? DataTable.HIDDEN_COLUMN_CLASS
						: styleClass + " " + DataTable.HIDDEN_COLUMN_CLASS;
		final String userStyleClass = column.getStyleClass();
		styleClass = userStyleClass == null ? styleClass
				: (styleClass == null) ? userStyleClass : styleClass + " " + userStyleClass;

		if (priority > 0) {
			styleClass = (styleClass == null) ? "ui-column-p-" + priority : styleClass + " ui-column-p-" + priority;
		}

		final int colspan = column.getColspan();
		final int rowspan = column.getRowspan();

		writer.startElement("td", null);
		writer.writeAttribute("role", "gridcell", null);
		if (colspan != 1) {
			writer.writeAttribute("colspan", colspan, null);
		}
		if (rowspan != 1) {
			writer.writeAttribute("rowspan", rowspan, null);
		}
		if (style != null) {
			writer.writeAttribute("style", style, null);
		}
		if (styleClass != null) {
			writer.writeAttribute("class", styleClass, null);
		}

		if (selectionEnabled) {
			encodeColumnSelection(context, table, clientId, column, selected);
		}

		// This is the fix for the issue #2337
		if (column instanceof DynamicColumn) {
			column.encodeAll(context);
		} else {
			column.renderChildren(context);
		}

		writer.endElement("td");
	}
}