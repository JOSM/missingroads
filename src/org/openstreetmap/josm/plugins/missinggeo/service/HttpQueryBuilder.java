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
package org.openstreetmap.josm.plugins.missinggeo.service;

import org.openstreetmap.josm.plugins.missinggeo.argument.BoundingBox;
import org.openstreetmap.josm.plugins.missinggeo.argument.ClusterFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.SearchFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.TileFilter;
import org.openstreetmap.josm.plugins.missinggeo.entity.Status;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.Config;
import org.openstreetmap.josm.plugins.missinggeo.util.http.HttpUtil;


/**
 * Utility class, builds HTTP queries.
 *
 * @author Beata
 * @version $Revision: 37 $
 */
class HttpQueryBuilder {

    private static final char QUESTIONM = '?';
    private static final char EQ = '=';
    private static final char AND = '&';

    private final StringBuilder query;

    /**
     * Builds an empty query builder.
     */
    HttpQueryBuilder() {
        query = new StringBuilder();
    }

    /**
     * Builds a new {@code HttpQueryBuilder} for building the search method request.
     *
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param filter a {@code SearchFilter} specifies the filters to be applied
     * @param zoom specifies the current zoom level
     */
    HttpQueryBuilder(final BoundingBox bbox, final SearchFilter filter, final int zoom) {
        this();

        addFormatFilter();
        addBoundingBoxFilter(bbox);
        addZoomFilter(zoom);

        if (filter != null) {
            addStatusFilter(filter.getStatus());
            addTypeFilter(filter.getType());
            if (filter instanceof TileFilter) {
                // filters for only tile view
                final TileFilter tileFilter = (TileFilter) filter;
                addNumberOfTrips(tileFilter.getNumberOfTrips());
            } else {
                // filters for only cluster view
                addNumberOfPointsFilter(((ClusterFilter) filter).getNumberOfPoints());
            }
        }
    }

    /**
     * Builds a new {@code HttpQueryBuilder} for building the retrieveComments request.
     *
     * @param tileX the tile's X identifier
     * @param tileY the tile's Y identifier
     */
    HttpQueryBuilder(final Integer tileX, final Integer tileY) {
        this();

        addFormatFilter();
        addTileIdFilter(tileX, tileY);
    }

    private void addBoundingBoxFilter(final BoundingBox bbox) {
        query.append(AND).append(Constants.NORTH.toString()).append(EQ).append(bbox.getNorth());
        query.append(AND).append(Constants.SOUTH.toString()).append(EQ).append(bbox.getSouth());
        query.append(AND).append(Constants.EAST.toString()).append(EQ).append(bbox.getEast());
        query.append(AND).append(Constants.WEST.toString()).append(EQ).append(bbox.getWest());
    }

    private void addFormatFilter() {
        query.append(Constants.FORMAT.toString()).append(EQ).append(Constants.FORMAT_VAL.toString());
    }

    private void addNumberOfPointsFilter(final Integer noPoints) {
        if (noPoints != null) {
            query.append(AND).append(Constants.NO_POINTS).append(EQ).append(noPoints);
        }
    }

    private void addNumberOfTrips(final Integer noTrips) {
        if (noTrips != null) {
            query.append(AND).append(Constants.NO_TRIPS).append(EQ).append(noTrips);
        }
    }

    private void addStatusFilter(final Status status) {
        if (status != null) {
            query.append(AND).append(Constants.STATUS.toString()).append(EQ).append(HttpUtil.utf8Encode(status.name()));
        }
    }

    private void addTileIdFilter(final Integer tileX, final Integer tileY) {
        query.append(AND).append(Constants.TILE_X.toString()).append(EQ).append(tileX);
        query.append(AND).append(Constants.TILE_Y.toString()).append(EQ).append(tileY);

    }

    private void addTypeFilter(final Type type) {
        if (type != null) {
            query.append(AND).append(Constants.TYPE.toString()).append(EQ).append(HttpUtil.utf8Encode(type.name()));
        }
    }

    private void addZoomFilter(final int zoom) {
        query.append(AND).append(Constants.ZOOM.toString()).append(EQ).append(zoom);
    }

    /**
     * Builds a new HTTP query for the specifies method with the currently set fields.
     *
     * @param method specifies a MissingGeometryService method
     * @return a {@code String} object
     */
    String build(final String method) {
        final StringBuilder url = new StringBuilder(Config.getInstance().getServiceUrl());
        url.append(method).append(QUESTIONM).append(query);
        return url.toString();
    }
}