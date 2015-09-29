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
package org.openstreetmap.josm.plugins.missinggeo.gui;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;


/**
 * Custom radio button group, that allows to unselect all the radio buttons.
 *
 * @author Beata
 * @version $Revision: 8 $
 */
public class RadioButtonGroup extends ButtonGroup {

    private static final long serialVersionUID = -8577359639633515341L;


    @Override
    public void setSelected(final ButtonModel model, final boolean selected) {
        if (selected) {
            super.setSelected(model, selected);
        } else {
            clearSelection();
        }
    }
}