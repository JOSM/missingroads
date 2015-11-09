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
package org.openstreetmap.josm.plugins.missinggeo.argument;

import java.util.List;
import org.openstreetmap.josm.plugins.missinggeo.entity.Status;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;


/**
 * Defines a default search filter, that can be applied to every 'search' method call.
 *
 * @author Beata
 * @version $Revision: 57 $
 */
public class SearchFilter {

    private final Status status;
    private final List<Type> types;


    /**
     * Builds a new filter with the given status.
     *
     * @param status a {@code Status} object
     * @param type a {@code Type} object
     */
    public SearchFilter(final Status status, final List<Type> types) {
        this.status = status;
        this.types = types;
    }

    public Status getStatus() {
        return status;
    }

    public List<Type> getTypes() {
        return types;
    }
}