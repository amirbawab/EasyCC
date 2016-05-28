package parser;

import grammar.Grammar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.strategy.ParseStrategy;

import java.lang.reflect.Constructor;

/**
 * Created by amir on 5/26/16.
 */
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
            l.error(e.getMessage());
        }
    }
}
