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
package org.openstreetmap.josm.plugins.missinggeo.util.cnf;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.tools.ImageProvider;


/**
 * Utility class, holds icons and icon paths.
 *
 * @author Beata
 * @version $Revision: 13 $
 */
public final class IconConfig extends BaseConfig {

    private static final String CONFIG_FILE = "missinggeo_icon.properties";
    private static final IconConfig INSTANCE = new IconConfig();


    public static IconConfig getInstance() {
        return INSTANCE;
    }


    private final String shortcutIconName;
    private final Icon layerIcon;
    private final ImageIcon filterIcon;
    private final ImageIcon openIcon;
    private final ImageIcon commentIcon;
    private final ImageIcon solvedIcon;
    private final ImageIcon invalidIcon;


    private IconConfig() {
        super(CONFIG_FILE);

        shortcutIconName = readProperty("dialog.shortcut");
        layerIcon = ImageProvider.get(readProperty("layer.icon"));
        filterIcon = ImageProvider.get(readProperty("filter.icon"));
        openIcon = ImageProvider.get(readProperty("open.icon"));
        commentIcon = ImageProvider.get(readProperty("comment.icon"));
        solvedIcon = ImageProvider.get(readProperty("solved.icon"));
        invalidIcon = ImageProvider.get(readProperty("invalid.icon"));
    }

    public ImageIcon getCommentIcon() {
        return commentIcon;
    }

    public ImageIcon getFilterIcon() {
        return filterIcon;
    }

    public ImageIcon getInvalidIcon() {
        return invalidIcon;
    }

    public Icon getLayerIcon() {
        return layerIcon;
    }

    public ImageIcon getOpenIcon() {
        return openIcon;
    }

    public String getShortcutIconName() {
        return shortcutIconName;
    }

    public ImageIcon getSolvedIcon() {
        return solvedIcon;
    }
}