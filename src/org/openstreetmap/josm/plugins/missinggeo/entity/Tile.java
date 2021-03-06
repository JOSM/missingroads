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

import java.util.List;
import org.openstreetmap.josm.data.coor.LatLon;


/**
 * Defines the attributes of the 'Tile' business entity.
 *
 * @author Beata
 * @version $Revision: 15 $
 */
public class Tile {

    private final Integer x;
    private final Integer y;
    private Long timestamp;
    private Integer numberOfTrips;
    private List<LatLon> points;
    private Status status;
    private Type type;

    /**
     * Builds a new object with the given arguments.
     *
     * @param x the X identifier of a tile
     * @param y the Y identifier of a tile
     */
    public Tile(final Integer x, final Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Builds a new object with the given arguments.
     *
     * @param x the X identifier of a tile
     * @param y the Y identifier of a tile
     * @param timestamp the time of the last change on the tile
     * @param numberOfTrips the number of tiles that passed through the tile
     * @param points the list of missing points
     * @param status the status of the tile
     * @param type the type of the tile
     */
    public Tile(final Integer x, final Integer y, final Long timestamp, final Integer numberOfTrips,
            final List<LatLon> points, final Status status, final Type type) {
        this(x, y);
        this.timestamp = timestamp;
        this.numberOfTrips = numberOfTrips;
        this.points = points;
        this.status = status;
        this.type = type;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof Tile) {
            final Tile other = (Tile) obj;
            result = (x == null && other.getX() == null) || (x != null && x.equals(other.getX()));
            result = result && ((y == null && other.getY() == null) || (y != null && y.equals(other.getY())));
        }
        return result;
    }

    public Integer getNumberOfTrips() {
        return numberOfTrips;
    }

    public List<LatLon> getPoints() {
        return points;
    }

    public Status getStatus() {
        return status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Type getType() {
        return type;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return x + " -" + y;
    }
}