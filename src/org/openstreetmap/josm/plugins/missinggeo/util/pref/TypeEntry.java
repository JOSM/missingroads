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
package org.openstreetmap.josm.plugins.missinggeo.util.pref;

import org.openstreetmap.josm.data.StructUtils.StructEntry;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;


/**
 * Preference entry corresponding to {@code Type}.
 *
 * @author Beata
 * @version $Revision: 61 $
 */
public class TypeEntry {

    // preference entities must be declared public, otherwise JOSM preference loaded does not work!

    @StructEntry
    private String name;

    public TypeEntry() {}

    public TypeEntry(final Type type) {
        this.name = type.name();
    }

    public String getName() {
        return name;
    }
}