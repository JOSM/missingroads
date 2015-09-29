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

import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.josm.plugins.missinggeo.entity.Comment;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;


/**
 * Defines the body of the 'Comment' operation.
 *
 * @author Beata
 * @version $Revision: 8 $
 */
public class CommentRoot {

    private final String username;
    private final String text;
    private final String status;
    private final List<Tile> tiles;

    /**
     * Builds a new object from the given arguments.
     *
     * @param comment the {@code Comment} to be add
     * @param tiles the list of {@code Tile}s to be commented
     */
    public CommentRoot(final Comment comment, final List<Tile> tiles) {
        this.username = comment.getUsername();
        this.text = comment.getText();
        this.status = comment.getStatus() != null ? comment.getStatus().name() : null;
        this.tiles = new ArrayList<>();
        for (final Tile tile : tiles) {
            this.tiles.add(new Tile(tile.getX(), tile.getY()));
        }
    }


    public String getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public String getUsername() {
        return username;
    }
}