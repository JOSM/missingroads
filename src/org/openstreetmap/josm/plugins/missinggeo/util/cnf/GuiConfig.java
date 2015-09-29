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


/**
 * Utility class, holds GUI texts.
 *
 * @author Beata
 * @version $Revision: 46 $
 */
public final class GuiConfig extends BaseConfig {

    private static final String CONFIG_FILE = "missinggeo_gui.properties";
    private static final GuiConfig INSTANCE = new GuiConfig();


    public static GuiConfig getInstance() {
        return INSTANCE;
    }


    private final String pluginName;
    private final String pluginTooltip;

    /* buttons related text */
    private final String btnOk;
    private final String btnCancel;
    private final String btnReset;
    private final String btnFilterTooltip;
    private final String btnCommentTooltip;
    private final String btnOpenTooltip;
    private final String btnSolveTooltip;
    private final String btnInvalidTooltip;

    /* edit dialog window related texts */
    private final String dlgCommentTitle;
    private final String dlgSolveTitle;
    private final String dlgReopenTitle;
    private final String dlgInvalidTitle;

    /* filter dialog texts */
    private final String dlgFilterTitle;
    private final String dlgFilterLblStatus;
    private final String dlgFilterLblType;

    /* details panel related texts */
    private final String pnlTileTitle;
    private final String pnlHistoryTitle;
    private final String lblId;
    private final String lblTimestamp;
    private final String lblStatus;
    private final String lblType;

    /* commonly used texts */
    private final String lblTripCount;
    private final String lblPointCount;

    private final String warningTitle;
    private final String txtInvalidComment;
    private final String txtMissingUsername;
    private final String txtInvalidInteger;

    private final String pnlFeedbackTitle;
    private final String pnlFeedbackTxt;

    private final String errorTitle;
    private final String txtFeedbackUrl;

    private GuiConfig() {
        super(CONFIG_FILE);

        pluginName = readProperty("plugin.name");
        pluginTooltip = readProperty("plugin.tlt");
        btnOk = readProperty("btn.ok");
        btnCancel = readProperty("btn.cancel");
        btnReset = readProperty("btn.reset");
        btnFilterTooltip = readProperty("btn.filter.tlt");
        btnCommentTooltip = readProperty("btn.comment.tlt");
        btnOpenTooltip = readProperty("btn.open.tlt");
        btnSolveTooltip = readProperty("btn.solve.tlt");
        btnInvalidTooltip = readProperty("btn.invalid.tlt");
        dlgCommentTitle = readProperty("edit.dialog.comment.title");
        dlgSolveTitle = readProperty("edit.dialog.solve.title");
        dlgReopenTitle = readProperty("edit.dialog.reopen.title");
        dlgInvalidTitle = readProperty("edit.dialog.invalid.title");
        dlgFilterTitle = readProperty("dialog.filter.title");
        dlgFilterLblStatus = readProperty("dialog.filter.status.lbl");
        dlgFilterLblType = readProperty("dialog.filter.type.lbl");
        pnlTileTitle = readProperty("details.tile.title");
        pnlHistoryTitle = readProperty("details.history.title");
        lblId = readProperty("details.id.lbl");
        lblTimestamp = readProperty("details.time.lbl");
        lblStatus = readProperty("details.status.lbl");
        lblType = readProperty("details.type.lbl");
        lblTripCount = readProperty("lbl.tripCount");
        lblPointCount = readProperty("lbl.pointCount");
        warningTitle = readProperty("warning.title");
        txtInvalidComment = readProperty("warning.invalid.comment");
        txtMissingUsername = readProperty("warning.missing.username");
        txtInvalidInteger = readProperty("warning.invalid.integer");
        pnlFeedbackTitle = readProperty("feedback.title");
        pnlFeedbackTxt = readProperty("feedback.txt");
        errorTitle = readProperty("error.title");
        txtFeedbackUrl = readProperty("error.feedback");
    }


    public String getBtnCancel() {
        return btnCancel;
    }

    public String getBtnCommentTooltip() {
        return btnCommentTooltip;
    }

    public String getBtnFilterTooltip() {
        return btnFilterTooltip;
    }

    public String getBtnInvalidTooltip() {
        return btnInvalidTooltip;
    }

    public String getBtnOk() {
        return btnOk;
    }

    public String getBtnOpenTooltip() {
        return btnOpenTooltip;
    }

    public String getBtnReset() {
        return btnReset;
    }

    public String getBtnSolveTooltip() {
        return btnSolveTooltip;
    }

    public String getDlgCommentTitle() {
        return dlgCommentTitle;
    }

    public String getDlgFilterLblStatus() {
        return dlgFilterLblStatus;
    }

    public String getDlgFilterLblType() {
        return dlgFilterLblType;
    }

    public String getDlgFilterTitle() {
        return dlgFilterTitle;
    }

    public String getDlgInvalidTitle() {
        return dlgInvalidTitle;
    }

    public String getDlgReopenTitle() {
        return dlgReopenTitle;
    }

    public String getDlgSolveTitle() {
        return dlgSolveTitle;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getLblId() {
        return lblId;
    }

    public String getLblPointCount() {
        return lblPointCount;
    }

    public String getLblStatus() {
        return lblStatus;
    }

    public String getLblTimestamp() {
        return lblTimestamp;
    }

    public String getLblTripCount() {
        return lblTripCount;
    }

    public String getLblType() {
        return lblType;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginTooltip() {
        return pluginTooltip;
    }

    public String getPnlFeedbackTitle() {
        return pnlFeedbackTitle;
    }

    public String getPnlFeedbackTxt() {
        return pnlFeedbackTxt;
    }

    public String getPnlHistoryTitle() {
        return pnlHistoryTitle;
    }

    public String getPnlTileTitle() {
        return pnlTileTitle;
    }

    public String getTxtFeedbackUrl() {
        return txtFeedbackUrl;
    }

    public String getTxtInvalidComment() {
        return txtInvalidComment;
    }

    public String getTxtInvalidInteger() {
        return txtInvalidInteger;
    }

    public String getTxtMissingUsername() {
        return txtMissingUsername;
    }

    public String getWarningTitle() {
        return warningTitle;
    }
}