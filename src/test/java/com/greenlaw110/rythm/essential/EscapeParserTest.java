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
package com.greenlaw110.rythm.essential;

import com.greenlaw110.rythm.TestBase;
import com.greenlaw110.rythm.conf.RythmConfigurationKey;
import com.greenlaw110.rythm.extension.ICodeType;
import org.junit.Test;

/**
 * Test escape
 */
public class EscapeParserTest extends TestBase {

    /**
     * Test default @escape()
     */
    @Test
    public void testDefault() {
        t = "@escape(){@p}";
        s = r(t, "<h1>abc</h1>");
        eq("&lt;h1&gt;abc&lt;/h1&gt;");
    }
    
    @Test
    public void testParam() {
        t = "@escape(\"html\"){@p}";
        s = r(t, "<h1>abc</h1>");
        eq("&lt;h1&gt;abc&lt;/h1&gt;");

        t = "@escape(\"json\"){@p}";
        s = r(t, "<h1>abc</h1>");
        eq("<h1>abc</h1>");

        t = "@escape(\"json\"){@p}";
        s = r(t, "\"foo\"");
        eq("\\\"foo\\\"");

        t = "@escape(\"javascript\"){@p}";
        s = r(t, "<h1>abc</h1>");
        eq("<h1>abc<\\/h1>");

        t = "@escape(\"javascript\"){@p}";
        s = r(t, "\"foo\"");
        eq("\\\"foo\\\"");

        t = "@escape(\"csv\"){@p}";
        s = r(t, "Someone's good, bad and ...");
        eq("\"Someone's good, bad and ...\"");
    }
    
    @Test
    public void testLineBreak() {
        t = "abc\n@escape(){\n123\n}\nxyz";
        s = r(t);
        eq("abc\n123\nxyz");
    }
    
    @Test
    public void testShortNotation() {
        t = "@escape()@1@";
        s = r(t, "<h1>h1</h1>");
        eq("&lt;h1&gt;h1&lt;/h1&gt;");
    }
    
    @Test
    public void testTransform() {
        System.getProperties().put(RythmConfigurationKey.DEFAULT_CODE_TYPE_IMPL.getKey(), ICodeType.DefImpl.HTML);
        t = "@args String foo;@foo.escape()";
        s = r(t, "<h1>abc</h1>");
        eq("&lt;h1&gt;abc&lt;/h1&gt;");
    }
    
    public static void main(String[] args) {
        run(EscapeParserTest.class);
    }
}
