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

import static org.openstreetmap.josm.tools.I18n.tr;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URI;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.missinggeo.gui.GuiBuilder;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.Config;
import org.openstreetmap.josm.plugins.missinggeo.util.cnf.GuiConfig;
import org.openstreetmap.josm.tools.OpenBrowser;


/**
 * Builds a panel for displaying the feedback information.
 *
 * @author Beata
 * @version $Revision: 58 $
 */
class FeedbackPanel extends JPanel implements HyperlinkListener {

    private static final long serialVersionUID = -2101183058945962229L;
    private static final Dimension DIM = new Dimension(150, 100);


    /**
     * Builds a new panel.
     */
    FeedbackPanel() {
        setLayout(new BorderLayout());
        setName(GuiConfig.getInstance().getPnlFeedbackTitle());
        final JEditorPane txtHelp = new JEditorPane("text/html", "");
        txtHelp.setEditorKit(new HTMLEditorKit());
        txtHelp.setEditable(false);
        txtHelp.setText(GuiConfig.getInstance().getPnlFeedbackTxt());
        txtHelp.addHyperlinkListener(this);

        final JScrollPane cmpTile =
                GuiBuilder.buildScrollPane(GuiConfig.getInstance().getPnlTileTitle(), txtHelp, getBackground(), DIM);
        add(cmpTile, BorderLayout.CENTER);
    }

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            final String url = Config.getInstance().getFeedbackUrl();
            try {
                OpenBrowser.displayUrl(new URI(url));
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(Main.parent, tr(GuiConfig.getInstance().getTxtFeedbackUrl()),
                        tr(GuiConfig.getInstance().getErrorTitle()), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}