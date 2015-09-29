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

import org.openstreetmap.josm.data.coor.LatLon;


/**
 * Defines the attributes of the 'Cluster' business entity.
 *
 * @author Beata
 * @version $Revision: 13 $
 */
public class Cluster implements Comparable<Cluster> {

    private final LatLon point;
    private final Integer numberOfPoints;


    /**
     * Builds a new object with the given arguments.
     *
     * @param point a {@code LatLon} representing the cluster center
     * @param numberOfPoints the numnber of points belonging to the cluster
     */
    public Cluster(final LatLon point, final Integer numberOfPoints) {
        this.point = point;
        this.numberOfPoints = numberOfPoints;
    }


    @Override
    public int compareTo(final Cluster object) {
        return numberOfPoints.compareTo(object.getNumberOfPoints());
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }


    public LatLon getPoint() {
        return point;
    }
}