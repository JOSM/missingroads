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
package org.openstreetmap.josm.plugins.missinggeo.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.openstreetmap.josm.plugins.missinggeo.entity.Type;

/**
 * Contains HTTP communication utility methods.
 *
 * @author Beata
 * @version $Revision: 58 $
 */
public final class HttpUtil {

    public static final String ENCODING = "utf-8";

    /**
     * Encodes the given list of types using {@code HttpUtil#ENCODING} encoding.
     *
     * @param types a list of types
     * @return the encoded parameters
     */
    public static String utf8Encode(final List<Type> types) {
        final StringBuilder param = new StringBuilder();
        for (final Type type : types) {
            param.append(type.name()).append(",");
        }
        return utf8Encode(param.substring(0, param.length() - 1));
    }

    /**
     * Encodes the given parameter using {@code HttpUtil#ENCODING} encoding.
     *
     * @param param the parameter to be encoded
     * @return the encoded parameter
     */
    public static String utf8Encode(final String content) {
        String encodedContent = null;
        try {
            encodedContent = URLEncoder.encode(content, ENCODING);
        } catch (final UnsupportedEncodingException ex) {
            /* should not appear since UTF-8 is a supported encoding */
        }
        return encodedContent;
    }

    private HttpUtil() {}
}
