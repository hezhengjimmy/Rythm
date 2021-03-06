/* 
 * Copyright (C) 2013 The Rythm Engine project
 * Gelin Luo <greenlaw110(at)gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.greenlaw110.rythm.utils;

import java.util.Arrays;

/**
 * Escape
 */
public enum Escape {
    /**
     * Indicate raw escape scheme, i.e. no other escape scheme should apply
     */
    RAW,
    /**
     * CSV escape scheme
     */
    CSV {
        @Override
        protected RawData apply_(String s) {
            return com.greenlaw110.rythm.utils.S.escapeCsv(s);
        }
    },
    /**
     * HTML escape scheme
     */
    HTML {
        @Override
        protected RawData apply_(String s) {
            return com.greenlaw110.rythm.utils.S.escapeHtml(s);
        }
    },
    /**
     * javascript escape scheme
     */
    JS {
        @Override
        protected RawData apply_(String s) {
            return com.greenlaw110.rythm.utils.S.escapeJavaScript(s);
        }
    },
    /**
     * JSON escape scheme
     */
    JSON {
        @Override
        protected RawData apply_(String s) {
            return com.greenlaw110.rythm.utils.S.escapeJson(s);
        }
    },
    /**
     * XML escape scheme
     */
    XML {
        @Override
        protected RawData apply_(String s) {
            return com.greenlaw110.rythm.utils.S.escapeXml(s);
        }
    };

    /**
     * Apply this escape scheme to the object's string representation
     * @param o
     * @return
     */
    public RawData apply(Object o) {
        if (null == o) return RawData.NULL;
        String s = o.toString();
        return apply_(s);
    }

    protected RawData apply_(String s) {
        return new RawData(s);
    }

    private static String[] sa_ = null;

    public static String[] stringValues() {
        if (null == sa_) {
            Escape[] ea = values();
            String[] sa = new String[ea.length];
            for (int i = 0; i < ea.length; ++i) {
                sa[i] = ea[i].toString();
            }
            Arrays.sort(sa);
            sa_ = sa;
        }
        return sa_.clone();
    }
}
