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

import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.BOTH_COLOR;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.CLUSTER_COLOR;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.CLUSTER_RADIUS;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.INVALID_COLOR;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.NORMAL_COMPOSITE;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.OPEN_COLOR;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.PARKING_COLOR;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.POS_RADIUS;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.ROAD_COLOR;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.SOLVED_COLOR;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.TILE_COMPOSITE;
import static org.openstreetmap.josm.plugins.missinggeo.gui.layer.Constants.TILE_SEL_COMPOSITE;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.missinggeo.argument.BoundingBox;
import org.openstreetmap.josm.plugins.missinggeo.entity.Cluster;
import org.openstreetmap.josm.plugins.missinggeo.entity.Status;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;
import org.openstreetmap.josm.plugins.missinggeo.util.Util;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.ClusterConfig;


/**
 * Handles the drawing operations of the layer data.
 *
 * @author Beata
 * @version $Revision: 24 $
 */
class PaintHandler {

    private Ellipse2D.Double buildCircle(final Graphics2D g2D, final MapView mv, final LatLon latLon, final Color color,
            final double radius) {
        final Point2D point = mv.getPoint2D(latLon);
        final Ellipse2D.Double circle = new Ellipse2D.Double(point.getX(), point.getY(), radius, radius);
        g2D.setColor(color);
        g2D.fill(circle);
        return circle;
    }

    private GeneralPath buildPath(final Graphics2D g2D, final MapView mv, final Tile tile) {
        final BoundingBox bbox = Util.tileToBoundingBox(tile.getX(), tile.getY());
        final Point northEast = mv.getPoint(new LatLon(bbox.getNorth(), bbox.getEast()));
        final Point northWest = mv.getPoint(new LatLon(bbox.getSouth(), bbox.getWest()));
        final GeneralPath path = new GeneralPath();
        path.moveTo(northEast.getX(), northEast.getY());
        path.lineTo(northWest.getX(), northEast.getY());
        path.lineTo(northWest.getX(), northWest.getY());
        path.lineTo(northEast.getX(), northWest.getY());
        path.lineTo(northEast.getX(), northEast.getY());
        return path;
    }

    private Double clusterRadius(final SortedMap<Integer, Double> map, final Integer value) {
        Double radius = null;
        if (map.size() > 1) {
            for (final Integer key : map.keySet()) {
                if (value <= key) {
                    radius = map.get(key);
                    break;
                }
            }
        } else {
            radius = map.get(value);
        }
        return radius != null ? radius : CLUSTER_RADIUS;
    }

    private void drawPoints(final Graphics2D g2D, final MapView mv, final List<LatLon> points, final Color color) {
        for (final LatLon latLon : points) {
            final Ellipse2D.Double circle = buildCircle(g2D, mv, latLon, color, POS_RADIUS);
            g2D.draw(circle);
        }
    }

    private void drawTile(final Graphics2D g2D, final MapView mv, final Tile tile, final boolean selected) {
        final Color color = tile.getStatus() == Status.OPEN ? OPEN_COLOR
                : (tile.getStatus() == Status.SOLVED ? SOLVED_COLOR : INVALID_COLOR);
        g2D.setColor(color);
        final GeneralPath path = buildPath(g2D, mv, tile);
        g2D.draw(path);
        final Composite composite = selected ? TILE_SEL_COMPOSITE : TILE_COMPOSITE;
        g2D.setComposite(composite);
        g2D.fill(path);
    }

    private SortedMap<Integer, Double> generateClusterRadiusMap(final int zoom, final List<Cluster> clusters) {
        SortedMap<Integer, Double> map;
        final int max = clusters.get(clusters.size() - 1).getNumberOfPoints();
        final List<Double> radiusList = ClusterConfig.getInstance().getRadiusList(zoom);
        final int count = ClusterConfig.getInstance().getCount();
        if (clusters.size() > 1) {
            final int x = max / count;
            map = new TreeMap<>();
            int i;
            for (i = 1; i <= count - 1; i++) {
                map.put(i * x, radiusList.get(i - 1));
            }
            map.put(max, radiusList.get(i - 1));
        } else {
            map = new TreeMap<>();
            map.put(max, radiusList.get(count - 1));
        }
        return map;
    }

    /**
     * Draws a list of {@code Cluster}s to the map. A cluster is represented by a circle on the map. The circle's radius
     * depends on the zoom level and number of points belonging to the cluster.
     *
     * @param g2D a {@code Graphics2D} used to drawing to the map
     * @param mv the current {@code MapView}
     * @param zoom the current zoom level
     * @param clusters a list of {@code Cluster}s
     */
    void drawClusters(final Graphics2D g2D, final MapView mv, final int zoom, final List<Cluster> clusters) {
        final SortedMap<Integer, Double> clusterRadiusMap = generateClusterRadiusMap(zoom, clusters);
        for (final Cluster cluster : clusters) {
            final Double radius = clusterRadius(clusterRadiusMap, cluster.getNumberOfPoints());
            g2D.setComposite(TILE_SEL_COMPOSITE);
            final Ellipse2D.Double circle = buildCircle(g2D, mv, cluster.getPoint(), CLUSTER_COLOR, radius);
            g2D.draw(circle);
        }
        g2D.setComposite(NORMAL_COMPOSITE);
    }

    /**
     * Draws a list {@code Tile}s to the map. The tile is represented by a rectangle colored based on the tile's status.
     * The points belonging to a tile are colored based on the tile's type
     *
     * @param g2D a {@code Graphics2D} used to drawing to the map
     * @param mv the current {@code MapView}
     * @param tiles a list of {@code Tile}s to be drawn
     * @param selTiles a list of selected {@code Tile}s
     */
    void drawTiles(final Graphics2D g2D, final MapView mv, final List<Tile> tiles, final List<Tile> selTiles) {
        for (final Tile tile : tiles) {
            final boolean selected = selTiles.contains(tile);
            drawTile(g2D, mv, tile, selected);
            g2D.setComposite(NORMAL_COMPOSITE);
            final Color color = tile.getType() == Type.ROAD ? ROAD_COLOR
                    : (tile.getType() == Type.PARKING ? PARKING_COLOR : BOTH_COLOR);
            drawPoints(g2D, mv, tile.getPoints(), color);
        }
    }
}