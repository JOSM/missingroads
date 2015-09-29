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
package org.openstreetmap.josm.plugins.missinggeo.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represent's the data set of the {@code MissingGeometryLayer}.
 *
 * @author Beata
 * @version $Revision: 30 $
 */
public class DataSet {

    private final List<Cluster> clusters;
    private final List<Tile> tiles;

    /**
     * Creates and empty object.
     */
    public DataSet() {
        this.clusters = new ArrayList<>();
        this.tiles = new ArrayList<>();
    }

    /**
     * Builds a new data set with the given arguments.
     *
     * @param clusters a list of {@code Cluster}s
     * @param tiles a list of {@code Tile}s
     */
    public DataSet(final List<Cluster> clusters, final List<Tile> tiles) {
        this.clusters = clusters == null ? new ArrayList<Cluster>() : clusters;
        Collections.sort(this.clusters);
        this.tiles = tiles == null ? new ArrayList<Tile>() : tiles;
    }


    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}