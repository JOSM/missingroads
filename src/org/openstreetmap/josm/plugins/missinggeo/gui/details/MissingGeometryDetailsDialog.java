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

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;
import org.openstreetmap.josm.plugins.missinggeo.entity.Comment;
import org.openstreetmap.josm.plugins.missinggeo.entity.Tile;
import org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder;
import org.openstreetmap.josm.plugins.missinggeo.observer.CommentObserver;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.IconConfig;
import org.openstreetmap.josm.tools.Shortcut;


/**
 * Defines the missing geometry right side dialog window.
 *
 * @author Beata
 * @version $Revision: 46 $
 */
public class MissingGeometryDetailsDialog extends ToggleDialog {

    private static final long serialVersionUID = -7078724920642966434L;
    /** the preferred dimension of the panel components */
    private static final Dimension DIM = new Dimension(150, 100);

    private static final int DLG_HEIGHT = 50;
    private static Shortcut shortcut = Shortcut.registerShortcut(GuiConfig.getInstance().getPluginName(),
            GuiConfig.getInstance().getPluginTooltip(), KeyEvent.VK_F3, Shortcut.CTRL);

    private final TilePanel pnlTile;
    private final CommentsPanel pnlComments;
    private final ButtonPanel pnlBtn;


    /**
     * Builds a new {@code MissingGeometryDetailsDialog} object.
     */
    public MissingGeometryDetailsDialog() {
        super(GuiConfig.getInstance().getPluginName(), IconConfig.getInstance().getShortcutIconName(),
                GuiConfig.getInstance().getPluginTooltip(), shortcut, DLG_HEIGHT);

        pnlTile = new TilePanel();
        final JScrollPane cmpTile =
                GuiBuilder.buildScrollPane(GuiConfig.getInstance().getPnlTileTitle(), pnlTile, getBackground(), DIM);
        pnlComments = new CommentsPanel();


        final JTabbedPane pnlDetails = GuiBuilder.buildTabbedPane(cmpTile, pnlComments, new FeedbackPanel());
        pnlBtn = new ButtonPanel();
        final JPanel pnlMain = GuiBuilder.buildBorderLayoutPanel(pnlDetails, pnlBtn);
        add(pnlMain);
    }

    /**
     * Registers the given observer to the corresponding components.
     *
     * @param commentObserver a {@code CommentObserver}
     */
    public void registerCommentObserver(final CommentObserver commentObserver) {
        pnlBtn.registerCommentObserver(commentObserver);
    }

    /**
     * Updates the UI with the selected tile's information, including the comments.
     *
     * @param tile a {@code Tile} represents the tile's basic information
     * @param comments a list of {@code Comment}s represents the tile's history
     */
    public void updateData(final Tile tile, final List<Comment> comments) {
        synchronized (this) {
            pnlTile.updateData(tile);
            pnlComments.updateData(comments);
            pnlBtn.setTile(tile);
            repaint();
        }
    }

    /**
     * Updates the UI with the selected tile's information.
     *
     * @param tile a {@code Tile} represents the tile's basic information
     */
    public void updateTileData(final Tile tile) {
        pnlTile.updateData(tile);
        pnlBtn.setTile(tile);
        repaint();
    }
}