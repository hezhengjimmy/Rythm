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
package com.greenlaw110.rythm.internal.parser.build_in;

import com.greenlaw110.rythm.Sandbox;
import com.greenlaw110.rythm.exception.ParseException;
import com.greenlaw110.rythm.internal.ICaretParserFactory;
import com.greenlaw110.rythm.internal.IContext;
import com.greenlaw110.rythm.internal.IDialect;
import com.greenlaw110.rythm.internal.IParserFactory;
import com.greenlaw110.rythm.logger.ILogger;
import com.greenlaw110.rythm.logger.Logger;
import com.stevesoft.pat.Regex;

public abstract class CaretParserFactoryBase implements ICaretParserFactory {

    protected ILogger logger = Logger.get(IParserFactory.class);

    public String getCaret(IDialect dialect) {
        return dialect.a();
    }

    public static void raiseParseException(IContext ctx, String msg, Object... args) {
        throw new ParseException(ctx.getEngine(), ctx.getTemplateClass(), ctx.currentLine(), msg, args);
    }

    public static void checkRestrictedClass(IContext ctx, String s) {
        if (com.greenlaw110.rythm.Rythm.insideSandbox()) {
            String s0 = Sandbox.hasAccessToRestrictedClasses(ctx.getEngine(), s);
            if (null != s0) {
                raiseParseException(ctx, "Access to restricted class [%s] is blocked in sandbox mode", s0);
            }
        }
    }

    // -- for testing purpose
    public static void p(int i, Regex r) {
        if (0 == i) {
            System.out.println(i + ": " + r.stringMatched());
        } else {
            System.out.println(i + ": " + r.stringMatched(i));
        }
    }

    public static void p(String s, Regex r) {
        if (r.search(s)) p(r);
    }

    public static void p(String s, Regex r, int max) {
        if (r.search(s)) p(r, max);
    }

    public static void p(Regex r, int max) {
        for (int i = 0; i < max; ++i) {
            p(i, r);
        }
    }

    public static void p(Regex r) {
        p(r, 6);
    }
}
