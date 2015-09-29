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
package org.openstreetmap.josm.plugins.missinggeo.gui.layer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.Icon;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.dialogs.LayerListDialog;
import org.openstreetmap.josm.gui.dialogs.LayerListPopup;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.missinggeo.entity.DataSet;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;
import org.openstreetmap.josm.plugins.missinggeo.util.Util;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.Config;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.IconConfig;


/**
 * Defines the missing geometry layer.
 *
 * @author Beata
 * @version $Revision: 40 $
 */
public class MissingGeometryLayer extends Layer {

    private final PaintHandler paintHandler;
    private DataSet dataSet;
    private List<Tile> selectedTiles;


    /**
     * Builds a new missing geometry layer.
     */
    public MissingGeometryLayer() {
        super(GuiConfig.getInstance().getPluginName());
        this.paintHandler = new PaintHandler();
        this.selectedTiles = new ArrayList<Tile>();
    }

    @Override
    public Icon getIcon() {
        return IconConfig.getInstance().getLayerIcon();
    }

    @Override
    public Object getInfoComponent() {
        return GuiConfig.getInstance().getPluginTooltip();
    }

    @Override
    public Action[] getMenuEntries() {
        final LayerListDialog layerListDialog = LayerListDialog.getInstance();
        return new Action[] { layerListDialog.createActivateLayerAction(this),
                layerListDialog.createShowHideLayerAction(), layerListDialog.createDeleteLayerAction(),
                SeparatorLayerAction.INSTANCE, new LayerListPopup.InfoAction(this) };
    }

    /**
     * Returns the selected tiles. If no tile(s) is selected the method returns an empty list.
     *
     * @return a list of {@code Tile}s
     */
    public List<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    @Override
    public String getToolTipText() {
        return GuiConfig.getInstance().getPluginTooltip();
    }

    @Override
    public boolean isMergable(final Layer otherLayer) {
        return false;
    }

    /**
     * Returns the last selected tile. If no tile is selected the method returns null.
     *
     * @return a {@code Tile}s
     */
    public Tile lastSelectedTile() {
        return selectedTiles.isEmpty() ? null : selectedTiles.get(selectedTiles.size() - 1);
    }

    @Override
    public void mergeFrom(final Layer otherLayer) {
        // this operation is not supported
    }

    /**
     * Returns the tile near to the given point. The method returns null if there is no nearby tile.
     *
     * @param point a {@code Point} represents the location where the user clicked
     * @param multiSelect specifies if multiple elements are selected or not
     * @return a {@code Tile}
     */
    public Tile nearbyTile(final Point point, final boolean multiSelect) {
        final Tile tile = dataSet != null ? Util.nearbyTile(dataSet.getTiles(), point) : null;
        if (!multiSelect) {
            selectedTiles.clear();
        }
        return tile;
    }

    @Override
    public void paint(final Graphics2D g2D, final MapView mv, final Bounds bounds) {
        mv.setDoubleBuffered(true);
        if (dataSet != null) {
            final int zoom = Util.zoom();
            if (zoom > Config.getInstance().getMaxClusterZoom()) {
                if (dataSet.getTiles() != null && !dataSet.getTiles().isEmpty()) {
                    paintHandler.drawTiles(g2D, mv, dataSet.getTiles(), selectedTiles);
                }
            } else if (dataSet.getClusters() != null && !dataSet.getClusters().isEmpty()) {
                paintHandler.drawClusters(g2D, mv, zoom, dataSet.getClusters());
            }
        }
    }

    /**
     * Sets the layer's data set. Previously selected tiles will be unselected if the new data set does not contains
     * these elements.
     *
     * @param dataSet a {@code DataSet} containing the tiles/clusters from the current view
     */
    public void setDataSet(final DataSet dataSet) {
        this.dataSet = dataSet;
        if (!selectedTiles.isEmpty() && this.dataSet.getTiles() != null) {
            final List<Tile> newSelectedTiles = new ArrayList<>();
            for (final Tile tile : this.selectedTiles) {
                if (this.dataSet.getTiles().contains(tile)) {
                    final int idx = this.dataSet.getTiles().indexOf(tile);
                    final Tile newTile = this.dataSet.getTiles().get(idx);
                    newSelectedTiles.add(newTile);
                }
            }
            this.selectedTiles = newSelectedTiles;
        }
    }


    /**
     * Updates the currently selected tile.
     *
     * @param tile a {@code Tile} representing the selected object
     */
    public void updateSelectedTile(final Tile tile) {
        if (tile == null) {
            selectedTiles.clear();
        } else {
            final int idx = selectedTiles.indexOf(tile);
            if (idx > -1) {
                selectedTiles.remove(tile);
                selectedTiles.add(idx, tile);
            } else {
                selectedTiles.add(tile);
            }
        }
    }

    @Override
    public void visitBoundingBox(final BoundingXYVisitor visitor) {
        // this operation is not supported
    }
}