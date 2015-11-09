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
package org.openstreetmap.josm.plugins.missinggeo.argument;

import java.util.List;
import org.openstreetmap.josm.plugins.missinggeo.entity.Status;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;


/**
 * Defines the filters that can be applied when searching for tiles.
 *
 * @author Beata
 * @version $Revision: 57 $
 */
public class TileFilter extends SearchFilter {

    public static final TileFilter DEFAULT = new TileFilter(Status.OPEN, null, null);
    private final Integer numberOfTrips;

    /**
     * Builds a new filter with the given arguments.
     *
     * @param status a {@code Status} defines the tile's status
     * @param type a {@code Type} defines the tile's type
     * @param numberOfTrips the number of trips passing the tile
     */
    public TileFilter(final Status status, final List<Type> types, final Integer numberOfTrips) {
        super(status, types);
        this.numberOfTrips = numberOfTrips;
    }

    public Integer getNumberOfTrips() {
        return numberOfTrips;
    }
}