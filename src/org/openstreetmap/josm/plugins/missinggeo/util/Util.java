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
package org.openstreetmap.josm.plugins.missinggeo.util;

import java.awt.Point;
import java.util.List;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.coor.EastNorth;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.missinggeo.argument.BoundingBox;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;


/**
 * Holds utility methods.
 *
 * @author Beata
 * @version $Revision: 24 $
 */
public final class Util {

    private static final int DEGREE_180 = 180;
    private static final int DEGREE_360 = 360;
    private static final int MIN_ZOOM = 0;
    private static final int MAX_ZOOM = 18;
    private static final int TILE_SIZE = 1024;


    /**
     * Builds a bounding box for the given map view.
     *
     * @param mapView the current {@code MapView}
     * @return
     */
    public static BoundingBox buildBBox(final MapView mapView) {
        final Bounds bounds =
                new Bounds(mapView.getLatLon(0, mapView.getHeight()), mapView.getLatLon(mapView.getWidth(), 0));
        return new BoundingBox(bounds.getMax().lat(), bounds.getMin().lat(), bounds.getMax().lon(),
                bounds.getMin().lon());
    }

    /**
     * Returns the tile corresponding to the given point.
     *
     * @param tiles a {@code Tile} a list of available tiles
     * @param point a {@code Point} represents the location where the user clicked on the map
     * @return a {@code Tile} object
     */
    public static Tile nearbyTile(final List<Tile> tiles, final Point point) {
        Tile result = null;
        final LatLon latLon = pointToLatLon(point);
        final int tileX = getTileX(latLon.getX());
        final int tileY = getTileY(latLon.getY());
        if (tiles != null) {
            for (final Tile tile : tiles) {
                if (tile.equals(new Tile(tileX, tileY))) {
                    result = tile;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Computes the bounding box corresponding to the given tile identifiers.
     *
     * @param x the tile's X identifier
     * @param y the tile's Y identifier
     * @return a {@code BoundingBox} object
     */
    public static BoundingBox tileToBoundingBox(final Integer x, final Integer y) {
        final double north = tile2lat(y);
        final double south = tile2lat(y + 1);
        final double west = tile2lon(x);
        final double east = tile2lon(x + 1);
        return new BoundingBox(north, south, east, west);
    }

    /**
     * Returns the current zoom level.
     *
     * @return an integer value
     */
    public static int zoom() {
        final Bounds bounds = Main.map.mapView.getRealBounds();
        return ((int) Math.min(MAX_ZOOM, Math.max(MIN_ZOOM,
                Math.round(Math.floor(Math.log(TILE_SIZE / bounds.asRect().height) / Math.log(2))))));
    }

    private static int getTileX(final double lon) {
        int tileX = (int) Math.floor((lon + DEGREE_180) / DEGREE_360 * (1 << MAX_ZOOM));
        if (tileX >= (1 << MAX_ZOOM)) {
            tileX = ((1 << MAX_ZOOM) - 1);
        }
        return tileX;
    }

    private static Integer getTileY(final double lat) {
        int tileY = (int) Math
                .floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2
                        * (1 << MAX_ZOOM));
        if (tileY >= (1 << MAX_ZOOM)) {
            tileY = ((1 << MAX_ZOOM) - 1);
        }
        return tileY;
    }

    private static LatLon pointToLatLon(final Point point) {
        final int width = Main.map.mapView.getWidth();
        final int height = Main.map.mapView.getHeight();
        final EastNorth center = Main.map.mapView.getCenter();
        final double scale = Main.map.mapView.getScale();
        final EastNorth eastNorth = new EastNorth(center.east() + (point.getX() - width / 2.0) * scale,
                center.north() - (point.getY() - height / 2.0) * scale);
        return Main.map.mapView.getProjection().eastNorth2latlon(eastNorth);
    }

    private static double tile2lat(final int y) {
        final double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, MAX_ZOOM);
        return Math.toDegrees(Math.atan(Math.sinh(n)));
    }

    private static double tile2lon(final int x) {
        return x / Math.pow(2.0, MAX_ZOOM) * DEGREE_360 - DEGREE_180;
    }

    private Util() {}
}