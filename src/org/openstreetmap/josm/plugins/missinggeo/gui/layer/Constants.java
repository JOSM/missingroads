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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;


/**
 *
 * @author Beata
 * @version $Revision: 52 $
 */
final class Constants {

    /* composite constants */
    static final Composite NORMAL_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F);
    static final Composite TILE_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50F);
    static final Composite TILE_SEL_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80F);

    /* cluster color */
    static final Color CLUSTER_COLOR = new Color(176, 23, 31);

    /* tile colors */
    static final Color OPEN_COLOR = new Color(70, 130, 180);
    static final Color SOLVED_COLOR = new Color(60, 179, 113);
    static final Color INVALID_COLOR = new Color(255, 99, 71);

    /* tile point colors */
    static final Color ROAD_COLOR = new Color(180, 82, 205);
    static final Color PARKING_COLOR = new Color(255, 255, 0);
    static final Color BOTH_COLOR = new Color(30, 30, 30);

    /* tile point radius */
    static final double POS_RADIUS = 5;

    /* cluster default radius */
    static final double CLUSTER_RADIUS = 50;

    private Constants() {}
}