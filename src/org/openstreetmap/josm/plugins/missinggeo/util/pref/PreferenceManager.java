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

import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.StructUtils;
import org.openstreetmap.josm.plugins.missinggeo.argument.ClusterFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.SearchFilter;
import org.openstreetmap.josm.plugins.missinggeo.argument.TileFilter;
import org.openstreetmap.josm.plugins.missinggeo.entity.Status;
import org.openstreetmap.josm.plugins.missinggeo.entity.Type;


/**
 * Utility class, manages save and load (put & get) operations of the preference variables. The preference variables are
 * saved into a global preference file. Preference variables are static variables which can be accessed from any plugin
 * class. Values saved in this global file, can be accessed also after a JOSM restart.
 *
 * @author Beata
 * @version $Revision: 58 $
 */
public final class PreferenceManager {

    private static final PreferenceManager UNIQUE_INSTANCE = new PreferenceManager();


    public static PreferenceManager getInstance() {
        return UNIQUE_INSTANCE;
    }

    private PreferenceManager() {}

    /**
     * Loads the 'error suppress' flag,
     *
     * @return a boolean value
     */
    public boolean loadErrorSupressFlag() {
        return Main.pref.getBoolean(Keys.ERROR_SUPPRESS);
    }

    /**
     * Loads the user's OSM username.
     *
     * @return a {@code String} object
     */
    public String loadOsmUsername() {
        final String username = Main.pref.get(Keys.OSM_USERNAME);
        return username == null ? "" : username;
    }

    /**
     * Loads the search filters based on the given type.
     *
     * @param filterType specifies the filter type
     * @return a {@code SearchFilter} object
     */
    public <T extends SearchFilter> SearchFilter loadSearchFilter(final Class<T> filterType) {
        final Status status = loadStatusFilter();
        final List<TypeEntry> entries = StructUtils.getListOfStructs(Main.pref, Keys.TYPE, TypeEntry.class);
        List<Type> types = null;
        if (entries != null && !entries.isEmpty()) {
            types = new ArrayList<>();
            for (final TypeEntry entry : entries) {
                types.add(Type.valueOf(entry.getName()));
            }
        }
        SearchFilter filter = null;
        if (filterType.isAssignableFrom(TileFilter.class)) {
            final Integer numberOfTrips = loadIntValue(Keys.NO_TRIPS);
            filter = new TileFilter(status, types, numberOfTrips);
        } else {
            // cluster filter
            final Integer numberOfPoints = loadIntValue(Keys.NO_POINTS);
            filter = new ClusterFilter(status, types, numberOfPoints);
        }
        return filter;
    }

    /**
     * Loads the currently set status filter.
     *
     * @return a {@code Status}
     */
    public Status loadStatusFilter() {
        final String statusStr = Main.pref.get(Keys.STATUS);
        return statusStr != null && !statusStr.isEmpty() ? Status.valueOf(statusStr) : null;
    }

    /**
     * Saves the 'error suppress' flag.
     *
     * @param value a boolean value
     */
    public void saveErrorSupressFlag(final boolean value) {
        Main.pref.putBoolean(Keys.ERROR_SUPPRESS, value);
    }

    /**
     * Saves the 'filters changed' flag.
     *
     * @param changed a boolean value
     */
    public void saveFiltersChangedFlag(final boolean changed) {
        Main.pref.put(Keys.FILTERS_CHANGED, "");
        Main.pref.put(Keys.FILTERS_CHANGED, "" + changed);
    }

    /**
     * Saves the user's OSM username.
     *
     * @param username a {@code String} value
     */
    public void saveOsmUsername(final String username) {
        Main.pref.put(Keys.OSM_USERNAME, username);
    }

    /**
     * Saves the search filter.
     *
     * @param filter a {@code SearchFilter} object
     */
    public void saveSearchFilter(final SearchFilter filter) {
        if (filter != null) {
            // status
            final String status = filter.getStatus() != null ? filter.getStatus().name() : "";
            Main.pref.put(Keys.STATUS, status);

            // type
            final List<TypeEntry> entries = new ArrayList<TypeEntry>();
            if (filter.getTypes() != null) {
                for (final Type type : filter.getTypes()) {
                    entries.add(new TypeEntry(type));
                }
            }
            StructUtils.putListOfStructs(Main.pref, Keys.TYPE, entries, TypeEntry.class);

            if (filter instanceof TileFilter) {
                // tile filter
                final TileFilter tileFilter = (TileFilter) filter;
                final String numberOfTrips =
                        tileFilter.getNumberOfTrips() != null ? tileFilter.getNumberOfTrips().toString() : "";
                        Main.pref.put(Keys.NO_TRIPS, numberOfTrips);
            } else {
                // cluster filter
                final ClusterFilter clusterFilter = (ClusterFilter) filter;
                final String numberOfPoints =
                        clusterFilter.getNumberOfPoints() != null ? clusterFilter.getNumberOfPoints().toString() : "";
                        Main.pref.put(Keys.NO_POINTS, numberOfPoints);
            }
        }
    }

    private Integer loadIntValue(final String key) {
        String valueStr = Main.pref.get(key);
        valueStr = valueStr.trim();
        return (valueStr != null && !valueStr.isEmpty()) ? Integer.valueOf(valueStr) : null;
    }
}