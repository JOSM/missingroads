/*
 *  Copyright 2015 Telenav, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.openstreetmap.josm.plugins.missinggeo.gui.details.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.missinggeo.argument.ClusterFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.SearchFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.TileFilter;
import org.openstreetmap.josm.plugins.missinggeo.gui.CancelAction;
import org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder;
import org.openstreetmap.josm.plugins.missinggeo.gui.ModalDialog;
import org.openstreetmap.josm.plugins.missinggeo.util.Util;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.Config;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.IconConfig;
import org.openstreetmap.josm.plugins.missinggeo.util.pref.PreferenceManager;


/**
 * Dialog window that displays the data filters.
 *
 * @author Beata
 * @version $Revision: 41 $
 */
public class FilterDialog extends ModalDialog {

    /* The action to be executed when the user click's on the 'OK' button */
    private class OkAction extends AbstractAction {

        private static final long serialVersionUID = -2928306625791941775L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            final PreferenceManager prefManager = PreferenceManager.getInstance();
            final SearchFilter newFilter = pnlFilter.selectedFilters();
            if (newFilter != null) {
                final Class<? extends SearchFilter> filterType = tileFilter ? TileFilter.class : ClusterFilter.class;
                final SearchFilter oldFilter = prefManager.loadSearchFilter(filterType);
                if (oldFilter.equals(newFilter)) {
                    prefManager.saveFiltersChangedFlag(false);
                } else {
                    prefManager.saveSearchFilter(newFilter);
                    prefManager.saveFiltersChangedFlag(true);
                }
                dispose();
            }
        }
    }

    /* The action to be executed when the user click's on the 'Reset' button */
    private class ResetAction extends AbstractAction {

        private static final long serialVersionUID = 4708883950358902356L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            pnlFilter.resetFilters();
        }
    }

    private static final long serialVersionUID = 8338794995278931588L;

    /* dialog minimal size */
    private static final Dimension DIM = new Dimension(350, 150);

    /* UI components */
    private FilterPanel pnlFilter;
    private JPanel pnlButton;

    /* flag indicating if tile filters are displayed or not */
    private final boolean tileFilter;


    /**
     * Builds a new filter dialog window
     */
    public FilterDialog() {
        super(GuiConfig.getInstance().getDlgFilterTitle(), IconConfig.getInstance().getFilterIcon().getImage());
        this.tileFilter = Util.zoom() > Config.getInstance().getMaxClusterZoom();
        setSize(DIM);
        setMinimumSize(DIM);
        createComponents();
    }

    @Override
    protected void createComponents() {
        pnlFilter = new FilterPanel(tileFilter);
        final JButton btnReset = GuiBuilder.buildButton(new ResetAction(), GuiConfig.getInstance().getBtnReset());
        final JButton btnOk = GuiBuilder.buildButton(new OkAction(), GuiConfig.getInstance().getBtnOk());
        final JButton btnCancel =
                GuiBuilder.buildButton(new CancelAction(this), GuiConfig.getInstance().getBtnCancel());
        pnlButton = GuiBuilder.buildFlowLayoutPanel(FlowLayout.RIGHT, btnReset, btnOk, btnCancel);
        add(pnlFilter, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
    }
}