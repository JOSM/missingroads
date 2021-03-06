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
 * Defines the search filter that can be applied when searching for clusters.
 *
 * @author Beata
 * @version $Revision: 57 $
 */
public class ClusterFilter extends SearchFilter {

    public static final ClusterFilter DEFAULT = new ClusterFilter(Status.OPEN, null, null);

    private final Integer numberOfPoints;


    /**
     * Builds a new filter with the given arguments.
     *
     * @param status a {@code Status} object represents the status characterizing the cluster
     * @param status a {@code Type} object represents the type characterizing the cluster
     * @param numberOfPoints the number of points belonging to the cluster
     */
    public ClusterFilter(final Status status, final List<Type> types, final Integer numberOfPoints) {
        super(status, types);
        this.numberOfPoints = numberOfPoints;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }
}