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

import java.net.HttpURLConnection;
import java.util.List;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.missinggeo.argument.BoundingBox;
import org.openstreetmap.josm.plugins.missinggeo.argument.SearchFilter;
import org.openstreetmap.josm.plugins.missinggeo.entity.Comment;
import org.openstreetmap.josm.plugins.missinggeo.entity.DataSet;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;
import org.openstreetmap.josm.plugins.missinggeo.service.entity.CommentRoot;
import org.openstreetmap.josm.plugins.missinggeo.service.entity.Root;
import org.openstreetmap.josm.plugins.missinggeo.util.http.HttpConnector;
import org.openstreetmap.josm.plugins.missinggeo.util.http.HttpConnectorException;
import org.openstreetmap.josm.plugins.missinggeo.util.http.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;


/**
 * Executes the operations of the 'MissingGeometryService'.
 *
 * @author Beata
 * @version $Revision: 69 $
 */
public class MissingGeometryService {

    private final Gson gson;

    /**
     * Builds a new object.
     */
    public MissingGeometryService() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LatLon.class, new LatLonDeserializer());
        this.gson = builder.create();
    }

    /**
     * Adds a comment possibly with a status change to a given tiles.
     *
     * @param comment a {@code Comment} to be added
     * @param tiles a list of {@code Tile}s
     * @throws MissingGeometryServiceException
     */
    public void comment(final Comment comment, final List<Tile> tiles) throws MissingGeometryServiceException {
        final String url = new HttpQueryBuilder().build(Constants.COMMENT.toString());
        final CommentRoot commentRoot = new CommentRoot(comment, tiles);
        final String content = gson.toJson(commentRoot, CommentRoot.class);
        final Root root = executePost(url, content);
        verifyResponseStatus(root);
    }

    /**
     * Retrieves the comments of the given tile. The comments are ordered in descending order by timestamp.
     *
     * @param tileX the X identifier of the tile
     * @param tileY the Y identifier of the tile
     * @return
     * @throws MissingGeometryServiceException
     */
    public List<Comment> retrieveComments(final Integer tileX, final Integer tileY)
            throws MissingGeometryServiceException {
        final String url = new HttpQueryBuilder(tileX, tileY).build(Constants.RETRIEVE_COMMENTS.toString());
        final Root root = executeGet(url);
        verifyResponseStatus(root);
        return root.getComments();
    }

    /**
     * Searches for clusters/tiles in the current bounding box based on the given filters.
     *
     * @param bbox a {@code BoundingBox} defines the searching area
     * @param filter a {@code SearchFilter} defines the filters to be applied
     * @param zoom the current zoom level
     * @return a {@code DataSet} containing a list of cluster/tile
     * @throws MissingGeometryServiceException
     */
    public DataSet search(final BoundingBox bbox, final SearchFilter filter, final int zoom)
            throws MissingGeometryServiceException {
        final String url = new HttpQueryBuilder(bbox, filter, zoom).build(Constants.SEARCH.toString());
        final Root root = executeGet(url);
        verifyResponseStatus(root);
        return new DataSet(root.getClusters(), root.getTiles());
    }

    private Root buildRoot(final String response) throws MissingGeometryServiceException {
        Root root = null;
        if (response != null) {
            try {
                root = gson.fromJson(response, Root.class);
            } catch (final JsonSyntaxException e) {
                throw new MissingGeometryServiceException(e);
            }
        }
        return root;
    }

    private Root executeGet(final String url) throws MissingGeometryServiceException {
        String response = null;
        try {
            response = new HttpConnector(url, HttpMethod.GET).read();
        } catch (final HttpConnectorException e) {
            throw new MissingGeometryServiceException(e);
        }
        return buildRoot(response);
    }

    private Root executePost(final String url, final String content) throws MissingGeometryServiceException {
        String response = null;
        try {
            final HttpConnector connector = new HttpConnector(url, HttpMethod.POST);
            connector.write(content);
            response = connector.read();
        } catch (final HttpConnectorException e) {
            throw new MissingGeometryServiceException(e);
        }
        return buildRoot(response);
    }

    private void verifyResponseStatus(final Root root) throws MissingGeometryServiceException {
        if (root.getStatus() != null && root.getStatus().getHttpCode() != HttpURLConnection.HTTP_OK) {
            throw new MissingGeometryServiceException(root.getStatus().getApiMessage());
        }
    }
}