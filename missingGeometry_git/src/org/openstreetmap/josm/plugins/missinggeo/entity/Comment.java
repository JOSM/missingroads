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


/**
 * Defines the attributes of the 'Comment' business entity.
 *
 * @author Beata
 * @version $Revision: 7 $
 */
public class Comment {

    private final String username;
    private final Long timestamp;
    private final String text;
    private final Status status;


    /**
     * Builds a new object with the given arguments.
     *
     * @param username the user's OSM name
     * @param timestamp the time when the comment was created.
     * @param text the comment's text
     * @param status the tile's status. This attribute is null if no status change was done.
     */
    public Comment(final String username, final Long timestamp, final String text, final Status status) {
        this.username = username;
        this.timestamp = timestamp;
        this.text = text;
        this.status = status;
    }


    public Status getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }
}