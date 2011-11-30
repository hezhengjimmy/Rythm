package com.greenlaw110.rythm.internal.dialect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.greenlaw110.rythm.internal.parser.build_in.KeywordParserFactory;
import com.greenlaw110.rythm.spi.IContext;
import com.greenlaw110.rythm.spi.IDialect;
import com.greenlaw110.rythm.spi.IParser;
import com.greenlaw110.rythm.spi.IParserFactory;

public abstract class DialectBase implements IDialect {
    
    public DialectBase() {
        registerBuildInParsers();
    }
    
    private List<IParserFactory> freeParsers = new ArrayList<IParserFactory>();
    @Override
    public void registerParserFactory(IParserFactory parser) {
        if (parser instanceof KeywordParserFactory) {
            KeywordParserFactory kp = (KeywordParserFactory)parser;
            keywords.put(kp.keyword().toString(), kp);
        } else {
            if (!freeParsers.contains(parser)) freeParsers.add(parser);
        }
    }

    private final Map<String, KeywordParserFactory> keywords = new HashMap<String,KeywordParserFactory>();
    private void registerBuildInParsers() {
        for (Class<?> c: buildInParserClasses()) {
            if (!Modifier.isAbstract(c.getModifiers())) {
                @SuppressWarnings("unchecked")
                Class<? extends IParserFactory> c0 = (Class<? extends IParserFactory>)c;
                try {
                    Constructor<? extends IParserFactory> ct = c0.getConstructor();
                    ct.setAccessible(true);
                    IParserFactory f = ct.newInstance();
                    registerParserFactory(f);
                } catch (Exception e) {
                    if (e instanceof RuntimeException) throw (RuntimeException) e;
                    else throw new RuntimeException(e);
                }
            }
        }
    }
    
    public IParser createBuildInParser(String keyword, IContext context) {
        KeywordParserFactory f = keywords.get(keyword);
        return null == f ? null : f.create(context);
    }
    
    public Iterable<IParserFactory> freeParsers() {
        return new Iterable<IParserFactory>() {
            final List<IParserFactory> fs = new ArrayList<IParserFactory>(freeParsers);
            @Override
            public Iterator<IParserFactory> iterator() {
                return new Iterator<IParserFactory>() {
                    
                    private int cursor = 0;

                    @Override
                    public boolean hasNext() {
                        return cursor < fs.size();
                    }

                    @Override
                    public IParserFactory next() {
                        return fs.get(cursor++);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                };
            }
        };
    }
    
    protected abstract Class<?>[] buildInParserClasses();
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof IDialect) {
            return getClass().equals(o.getClass());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return id().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s Dialect", id());
    }
}
