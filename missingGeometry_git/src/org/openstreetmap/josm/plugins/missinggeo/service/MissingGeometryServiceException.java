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
 * Custom exception used by MissingGeometryService .
 *
 * @author Beata
 * @version $Revision: 6 $
 */
public class MissingGeometryServiceException extends Exception {

    private static final long serialVersionUID = 4601659074602462817L;

    /**
     * Builds a new exception.
     */
    public MissingGeometryServiceException() {}

    /**
     * Builds a new exception with the given message.
     *
     * @param msg the exception message
     */
    public MissingGeometryServiceException(final String msg) {
        super(msg);
    }

    /**
     * Builds a new exception with the given message and cause.
     *
     * @param msg the exception message
     * @param cause the exception cause
     */
    public MissingGeometryServiceException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Builds a new exception with the given cause.
     *
     * @param cause the exception cause
     */
    public MissingGeometryServiceException(final Throwable cause) {
        super(cause);
    }
}