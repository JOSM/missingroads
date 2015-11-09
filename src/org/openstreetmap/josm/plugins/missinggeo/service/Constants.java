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
package org.openstreetmap.josm.plugins.missinggeo.service;


/**
 *
 * @author Beata
 * @version $Revision: 70 $
 */
enum Constants {

    FORMAT,
    FORMAT_VAL() {

        @Override
        public String toString() {
            return "json";
        }
    },
    CLIENT,
    CLIENT_VAL() {

        @Override
        public String toString() {
            return "JOSM";
        }
    },
    SEARCH,
    NORTH,
    SOUTH,
    EAST,
    WEST,
    ZOOM,
    STATUS,
    TYPE,
    NO_TRIPS() {

        @Override
        public String toString() {
            return "numberOfTrips";
        }
    },
    NO_POINTS() {

        @Override
        public String toString() {
            return "numberOfPoints";
        }
    },
    TILE_X() {

        @Override
        public String toString() {
            return "tileX";
        }
    },
    TILE_Y() {

        @Override
        public String toString() {
            return "tileY";
        }
    },

    RETRIEVE_COMMENTS() {

        @Override
        public String toString() {
            return "retrieveComments";
        }
    },
    COMMENT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}