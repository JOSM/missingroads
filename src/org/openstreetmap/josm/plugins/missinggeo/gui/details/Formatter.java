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
package org.openstreetmap.josm.plugins.missinggeo.gui.details;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import org.openstreetmap.josm.plugins.missinggeo.entity.Comment;


/**
 * Utility class, formats custom objects.
 *
 * @author Beata
 * @version $Revision: 8 $
 */
final class Formatter {

    private static final String TSTP = "yyyy-MM-dd HH:mm:ss";
    private static final Long UNIX_TSTP = 1000L;

    /**
     * Formats the given collection of {@code Comment}s using html tags.
     *
     * @param comments a collection of {@code Comment}s
     * @return a string containing the given {@code Comment}s
     */
    static String formatComments(final Collection<Comment> comments) {
        final StringBuilder sb = new StringBuilder("<html><body><font size='3' face='times new roman'>");
        for (final Comment comment : comments) {
            sb.append(("<b>"));
            sb.append(formatTimestamp(comment.getTimestamp()));
            sb.append(", ").append(comment.getUsername());
            sb.append("</b><br>");
            if (comment.getStatus() != null) {
                sb.append("changed status to ");
                sb.append(comment.getStatus());
                sb.append("<br>").append("with ");
            } else {
                sb.append("added ");
            }
            sb.append("comment: ").append(comment.getText());
            sb.append("<br>");
        }
        sb.append("</font></body></html>");
        return sb.toString();
    }

    /**
     * Formats the given tile identifiers.
     *
     * @param x the X identifier
     * @param y the Y identifier
     * @return a string containing the given identifiers
     */
    static String formatTileIdentifier(final Integer x, final Integer y) {
        return x + " - " + y;
    }

    /**
     * Formats the given timestamp in 'yyyy-MM-dd HH:mm:ss' format.
     *
     * @param timestamp a {@code Long} value
     * @return a string containing the timestamp
     */
    static String formatTimestamp(final Long timestamp) {
        final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(TSTP);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        return timestamp != null ? dateTimeFormat.format(new Date(timestamp * UNIX_TSTP)) : "";
    }

    private Formatter() {}
}