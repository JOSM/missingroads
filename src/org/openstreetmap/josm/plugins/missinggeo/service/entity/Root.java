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
package org.openstreetmap.josm.plugins.missinggeo.service.entity;

import java.util.List;
import org.openstreetmap.josm.plugins.missinggeo.entity.Cluster;
import org.openstreetmap.josm.plugins.missinggeo.entity.Comment;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;


/**
 *
 * @author Beata
 * @version $Revision: 6 $
 */
public class Root {

    private Status status;
    private List<Cluster> clusters;
    private List<Tile> tiles;
    private List<Comment> comments;

    public Root() {}

    /**
     * @param status
     * @param clusters
     * @param tiles
     * @param comments
     */
    public Root(final Status status, final List<Cluster> clusters, final List<Tile> tiles,
            final List<Comment> comments) {
        this.status = status;
        this.clusters = clusters;
        this.tiles = tiles;
        this.comments = comments;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Status getStatus() {
        return status;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}