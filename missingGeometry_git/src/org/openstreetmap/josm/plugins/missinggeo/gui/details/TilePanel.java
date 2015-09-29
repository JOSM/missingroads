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
package org.openstreetmap.josm.plugins.missinggeo.gui.details;

import static org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder.BOLD_12;
import static org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder.FM_BOLD_12;
import static org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder.FM_PLAIN_12;
import static org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder.PLAIN_12;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.missinggeo.entity.Status;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;
import org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder;


/**
 * Builds a panel for displaying the details of the selected {@code Tile}.
 *
 * @author Beata
 * @version $Revision: 41 $
 */
class TilePanel extends InfoPanel<Tile> {

    private static final long serialVersionUID = -6396508570987012079L;
    private int y = 0;
    private int pnlWidth = 0;

    private void addIdentifier(final Integer tileX, final Integer tileY, final int widthLbl) {
        if (tileX != null && tileY != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblId(), BOLD_12,
                    new Rectangle(RECT_X, RECT_Y, widthLbl, LHEIGHT)));
            final String id = Formatter.formatTileIdentifier(tileX, tileY);
            final int widthVal = FM_PLAIN_12.stringWidth(id.toString());
            add(GuiBuilder.buildLabel(id.toString(), PLAIN_12, new Rectangle(widthLbl, RECT_Y, widthVal, LHEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = RECT_Y + LHEIGHT;
        }
    }

    private void addNumberOfPoints(final List<LatLon> points, final int widthLbl) {
        if (points != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblPointCount(), BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final String numberOfPoints = "" + points.size();
            final int widthVal = FM_PLAIN_12.stringWidth(numberOfPoints);
            add(GuiBuilder.buildLabel(numberOfPoints, PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addNumberOfTrips(final Integer numberOfTrips, final int widthLbl) {
        if (numberOfTrips != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblTripCount(), BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final int widthVal = FM_PLAIN_12.stringWidth(numberOfTrips.toString());
            add(GuiBuilder.buildLabel(numberOfTrips.toString(), PLAIN_12,
                    new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addStatus(final Status status, final int widthLbl) {
        if (status != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblStatus(), BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final int widthVal = FM_PLAIN_12.stringWidth(status.name());
            add(GuiBuilder.buildLabel(status.name(), PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addTimestamp(final Long timestamp, final int widthLbl) {
        if (timestamp != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblTimestamp(), BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final String timestampStr = Formatter.formatTimestamp(timestamp);
            final int widthVal = FM_PLAIN_12.stringWidth(timestampStr);
            add(GuiBuilder.buildLabel(timestampStr, PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addType(final Type type, final int widthLbl) {
        if (type != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblType(), BOLD_12, new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final int widthVal = FM_PLAIN_12.stringWidth(type.toString());
            add(GuiBuilder.buildLabel(type.toString(), PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    @Override
    void createComponents(final Tile tile) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(FM_BOLD_12, getGuiCnf().getLblType(), getGuiCnf().getLblPointCount(),
                getGuiCnf().getLblTripCount(), getGuiCnf().getLblStatus(), getGuiCnf().getLblTimestamp());

        addIdentifier(tile.getX(), tile.getY(), widthLbl);
        addType(tile.getType(), widthLbl);
        addStatus(tile.getStatus(), widthLbl);
        addTimestamp(tile.getTimestamp(), widthLbl);
        addNumberOfTrips(tile.getNumberOfTrips(), widthLbl);
        addNumberOfPoints(tile.getPoints(), widthLbl);
        final int pnlHeight = y + SPACE_Y;
        setPreferredSize(new Dimension(pnlWidth + SPACE_Y, pnlHeight));
    }
}