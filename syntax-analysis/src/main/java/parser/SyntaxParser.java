package parser;

import grammar.Grammar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategy;

import java.lang.reflect.Constructor;

public class SyntaxParser {
    // Logger
    private Logger l = LogManager.getFormatterLogger(getClass());

    private ParseStrategy parseStrategy;

    public SyntaxParser(Grammar grammar, String parseStrategyClass) {

        l.info("Selecting strategy: " + parseStrategyClass);

        try {
            Class<?> parseClass = Class.forName(parseStrategyClass);
            Constructor<?> constructor = parseClass.getConstructor(Grammar.class);
            parseStrategy = (ParseStrategy) constructor.newInstance(grammar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get parse strategy
     * @return parse strategy
     */
    public ParseStrategy getParseStrategy() {
        return parseStrategy;
    }
}
