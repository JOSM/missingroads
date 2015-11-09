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

import static org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder.BOLD_12;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.openstreetmap.josm.plugins.missinggeo.argument.ClusterFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.SearchFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.TileFilter;
import org.openstreetmap.josm.plugins.missinggeo.entity.Status;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;
import org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder;
import org.openstreetmap.josm.plugins.missinggeo.gui.RadioButtonGroup;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.missinggeo.util.pref.PreferenceManager;


/**
 * Displays the possible data filters. The filters are based on the current zoom level:
 * <ul>
 * <li>small zoom level filters: status, type, number of points</li>
 * <li><big zoom level filters: status, type, number of trips/li>
 * </ul>
 *
 *
 * @author Beata
 * @version $Revision: 57 $
 */
class FilterPanel extends JPanel {

    private static final long serialVersionUID = -2499158212977162960L;
    /* UI components */
    private JRadioButton rbStatusOpen;
    private JRadioButton rbStatusSolved;
    private JRadioButton rbStatusInvalid;
    private RadioButtonGroup btnGroupStatus;
    private JCheckBox cbTypeParking;
    private JCheckBox cbTypeRoad;
    private JCheckBox cbTypeBoth;
    private JTextField txtCount;

    private final boolean tileFilter;

    /**
     * Builds a new filter panel.
     *
     * @param tileFilter specifies if the tile or cluster filters needs to be displayed
     */
    FilterPanel(final boolean tileFilter) {
        super(new GridBagLayout());
        this.tileFilter = tileFilter;

        if (tileFilter) {
            final TileFilter filter = (TileFilter) PreferenceManager.getInstance().loadSearchFilter(TileFilter.class);
            addStatusFilter(filter.getStatus());
            addTypesFilter(filter.getTypes());
            addCountFilter(GuiConfig.getInstance().getLblTripCount(), filter.getNumberOfTrips());
        } else {
            final ClusterFilter filter =
                    (ClusterFilter) PreferenceManager.getInstance().loadSearchFilter(ClusterFilter.class);
            addStatusFilter(filter.getStatus());
            addTypesFilter(filter.getTypes());
            addCountFilter(GuiConfig.getInstance().getLblPointCount(), filter.getNumberOfPoints());
        }
    }

    private void addCountFilter(final String label, final Integer value) {
        add(GuiBuilder.buildLabel(label, BOLD_12, null), Constraints.LBL_COUNT);
        final String valueStr = value != null ? value.toString() : "";
        txtCount = GuiBuilder.buildTextField(valueStr, Color.white);
        txtCount.setInputVerifier(new IntegerVerifier(txtCount, GuiConfig.getInstance().getTxtInvalidInteger()));
        add(txtCount, Constraints.TXT_COUNT);
    }

    private void addStatusFilter(final Status status) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getDlgFilterLblStatus(), BOLD_12, null),
                Constraints.LBL_STATUS);
        rbStatusOpen = GuiBuilder.buildRadioButton(Status.OPEN.toString(), Status.OPEN.toString(), getBackground());
        rbStatusSolved =
                GuiBuilder.buildRadioButton(Status.SOLVED.toString(), Status.SOLVED.toString(), getBackground());
        rbStatusInvalid =
                GuiBuilder.buildRadioButton(Status.INVALID.toString(), Status.INVALID.toString(), getBackground());
        btnGroupStatus = GuiBuilder.buildButtonGroup(rbStatusOpen, rbStatusSolved, rbStatusInvalid);
        selectStatus(status);
        add(rbStatusOpen, Constraints.RB_OPEN);
        add(rbStatusSolved, Constraints.RB_SOLVED);
        add(rbStatusInvalid, Constraints.RB_INVALID);
    }

    private void addTypesFilter(final List<Type> types) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getDlgFilterLblType(), BOLD_12, null), Constraints.LBL_TYPE);
        cbTypeParking = GuiBuilder.buildCheckBox(Type.PARKING.displayValue(), Type.PARKING.name(), getBackground());
        cbTypeRoad = GuiBuilder.buildCheckBox(Type.ROAD.displayValue(), Type.ROAD.name(), getBackground());
        cbTypeBoth = GuiBuilder.buildCheckBox(Type.BOTH.displayValue(), Type.BOTH.name(), getBackground());
        selectTypes(types);
        add(cbTypeParking, Constraints.RB_PARKING);
        add(cbTypeRoad, Constraints.RB_ROAD);
        add(cbTypeBoth, Constraints.RB_BOTH);
    }

    private List<Type> selectedTypes() {
        final List<Type> types = new ArrayList<>();
        if (cbTypeRoad.isSelected()) {
            types.add(Type.ROAD);
        }
        if (cbTypeParking.isSelected()) {
            types.add(Type.PARKING);
        }
        if (cbTypeBoth.isSelected()) {
            types.add(Type.BOTH);
        }
        return types;
    }

    private void selectStatus(final Status status) {
        if (status != null) {
            switch (status) {
                case OPEN:
                    rbStatusOpen.setSelected(true);
                    break;
                case SOLVED:
                    rbStatusSolved.setSelected(true);
                    break;
                default:
                    rbStatusInvalid.setSelected(true);
                    break;
            }
        } else {
            btnGroupStatus.clearSelection();
        }
    }

    private void selectTypes(final List<Type> types) {
        if (types != null && !types.isEmpty()) {
            boolean selected = types.contains(Type.ROAD);
            cbTypeRoad.setSelected(selected);
            selected = types.contains(Type.PARKING);
            cbTypeParking.setSelected(selected);
            selected = types.contains(Type.BOTH);
            cbTypeBoth.setSelected(selected);
        } else {
            cbTypeRoad.setSelected(false);
            cbTypeParking.setSelected(false);
            cbTypeBoth.setSelected(false);
        }
    }

    /**
     * Resets the filters to it's initial state.
     */
    void resetFilters() {
        Status status = null;
        List<Type> types = null;
        Integer count = null;
        if (tileFilter) {
            status = TileFilter.DEFAULT.getStatus();
            types = TileFilter.DEFAULT.getTypes();
            count = TileFilter.DEFAULT.getNumberOfTrips();
        } else {
            status = ClusterFilter.DEFAULT.getStatus();
            types = ClusterFilter.DEFAULT.getTypes();
            count = ClusterFilter.DEFAULT.getNumberOfPoints();
        }
        selectStatus(status);
        selectTypes(types);
        final String txt = count != null ? count.toString() : "";
        txtCount.setText(txt);
        txtCount.setBackground(Color.white);
    }

    /**
     * Returns the filters set by the user.
     *
     * @return a {@code SearchFilter} object
     */
    SearchFilter selectedFilters() {
        SearchFilter filter = null;
        if (txtCount.getInputVerifier().verify(txtCount)) {
            Status status = null;
            if (btnGroupStatus.getSelection() != null) {
                status = Status.valueOf(btnGroupStatus.getSelection().getActionCommand());
            }
            final String countStr = txtCount.getText().trim();
            final Integer count = countStr.isEmpty() ? null : Integer.parseInt(countStr);
            final List<Type> types = selectedTypes();
            if (tileFilter) {
                filter = new TileFilter(status, types, count);
            } else {
                filter = new ClusterFilter(status, types, count);
            }
        }
        return filter;
    }
}