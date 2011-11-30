package com.greenlaw110.rythm.internal.parser.build_in;

import com.greenlaw110.rythm.internal.Keyword;
import com.greenlaw110.rythm.internal.parser.BlockCodeToken;
import com.greenlaw110.rythm.internal.parser.ParserBase;
import com.greenlaw110.rythm.internal.parser.PatternStr;
import com.greenlaw110.rythm.spi.IContext;
import com.greenlaw110.rythm.spi.IParser;
import com.greenlaw110.rythm.util.TextBuilder;
import com.stevesoft.pat.Regex;

public class IfParser extends KeywordParserFactory {

    @Override
    public IParser create(final IContext ctx) {
        return new ParserBase(ctx) {
            @Override
            public TextBuilder go() {
                Regex r = reg(dialect());
                if (!r.search(remain())) return null;
                String s = r.stringMatched(1);
                ctx.step(s.length());
                s = r.stringMatched(2);
                if (!s.endsWith("{")) s = s + " {";
                return new BlockCodeToken(s, ctx);
            }
        };
    }

    @Override
    public Keyword keyword() {
        return Keyword.IF;
    }
    
    @Override
    protected String patternStr() {
        //return "(%s(%s\\s+\\(.*\\)(\\s*\\{)?)).*";
        return "(%s(%s\\s*" + PatternStr.Expression + "(\\s*\\{)?)).*";
    }
    
    public static void main(String[] args) {
        String p = String.format(new IfParser().patternStr(), "@", "if");
        System.out.println(p);
        
        Regex r = new Regex(p);
        String s = "@if (item.getChange() < 0.0) \n\t<td class=\"minus\">@item.getChange()</td>";
        if (r.search(s)) {
            System.out.println(r.stringMatched(1));
            System.out.println(r.stringMatched(2));
            System.out.println(r.stringMatched(3));
        }
    }

}
