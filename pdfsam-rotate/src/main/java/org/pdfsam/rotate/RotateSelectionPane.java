/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 16/apr/2014
 * Copyright 2013-2014 by Andrea Vacondio (andrea.vacondio@gmail.com).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.rotate;

import java.util.function.Consumer;

import org.pdfsam.i18n.DefaultI18nContext;
import org.pdfsam.pdf.PdfDocumentDescriptor;
import org.pdfsam.support.params.TaskParametersBuildStep;
import org.pdfsam.ui.selection.multiple.FileColumn;
import org.pdfsam.ui.selection.multiple.IntColumn;
import org.pdfsam.ui.selection.multiple.LoadingColumn;
import org.pdfsam.ui.selection.multiple.LongColumn;
import org.pdfsam.ui.selection.multiple.MultipleSelectionPane;
import org.pdfsam.ui.selection.multiple.SelectionTableColumn;
import org.pdfsam.ui.selection.multiple.SelectionTableRowData;

/**
 * Selection panel for the rotate module.
 * 
 * @author Andrea Vacondio
 *
 */
public class RotateSelectionPane extends MultipleSelectionPane
        implements TaskParametersBuildStep<RotateParametersBuilder> {

    public RotateSelectionPane(String ownerModule) {
        super(ownerModule, false, false, new SelectionTableColumn<?>[] { new LoadingColumn(ownerModule),
                FileColumn.NAME, LongColumn.SIZE, IntColumn.PAGES, LongColumn.LAST_MODIFIED });
    }

    public void apply(RotateParametersBuilder builder, Consumer<String> onError) {
        if (!table().getItems().isEmpty()) {
            table().getItems().stream().map(SelectionTableRowData::descriptor)
                    .map(PdfDocumentDescriptor::toPdfFileSource).forEach(builder::addSource);
        } else {
            onError.accept(DefaultI18nContext.getInstance().i18n("No PDF document has been selected"));
        }
    }
}
