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
package org.openstreetmap.josm.plugins.missinggeo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.missinggeo.argument.BoundingBox;
import org.openstreetmap.josm.plugins.missinggeo.argument.SearchFilter;
import org.openstreetmap.josm.plugins.missinggeo.entity.Comment;
import org.openstreetmap.josm.plugins.missinggeo.entity.DataSet;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;
import org.openstreetmap.josm.plugins.missinggeo.service.MissingGeometryService;
import org.openstreetmap.josm.plugins.missinggeo.service.MissingGeometryServiceException;
import org.openstreetmap.josm.plugins.missinggeo.util.pref.PreferenceManager;


/**
 * Executes the service operations corresponding to the user's action.
 *
 * @author Beata
 * @version $Revision: 8 $
 */
public class ServiceHandler {

    private static final ServiceHandler UNIQUE_INSTANCE = new ServiceHandler();

    /**
     * Returns the unique instance of the {@code ServiceHandler} object.
     *
     * @return a {@code ServiceHandler}
     */
    static ServiceHandler getInstance() {
        return UNIQUE_INSTANCE;
    }

    private final MissingGeometryService missingGeoService = new MissingGeometryService();

    /**
     * Adds a comment/change the status of the given tiles.
     *
     * @param comment the comment to be added
     * @param tiles the list of tiles to be commented/changed
     */
    public void comment(final Comment comment, final List<Tile> tiles) {
        try {
            missingGeoService.comment(comment, tiles);
        } catch (final MissingGeometryServiceException e) {
            handleException(e, false);
        }
    }

    /**
     * Retrieves the comments of the given tile. If the tile has no comments the method returns an empty list.
     *
     * @param tileX the X identifier of the tile
     * @param tileY the Y identifier of the tile
     * @return a list of {@code Comment}s
     */
    public List<Comment> retrieveComments(final Integer tileX, final Integer tileY) {
        List<Comment> result = new ArrayList<>();
        try {
            result = missingGeoService.retrieveComments(tileX, tileY);
        } catch (final MissingGeometryServiceException e) {
            handleException(e, false);
        }
        return result;
    }

    /**
     * Searches for tiles/clusters in the given bounding box based on the filters.
     *
     * @param bbox defines the searching area
     * @param filter defines the filters to be applied
     * @param zoom the zoom level
     * @return a {@code DataSet} containing a list of clusters/tiles
     */
    public DataSet search(final BoundingBox bbox, final SearchFilter filter, final int zoom) {
        DataSet result = new DataSet();
        try {
            result = missingGeoService.search(bbox, filter, zoom);
        } catch (final MissingGeometryServiceException e) {
            handleException(e, true);
        }
        return result;
    }

    private void handleException(final Exception e, final boolean suppress) {
        if (suppress) {
            if (!PreferenceManager.getInstance().loadErrorSupressFlag()) {
                PreferenceManager.getInstance().saveErrorSupressFlag(suppress);
                JOptionPane.showMessageDialog(Main.parent, e.getMessage(), "Operation failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(Main.parent, e.getMessage(), "Operation failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}