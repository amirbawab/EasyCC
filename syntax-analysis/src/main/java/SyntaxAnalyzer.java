import grammar.Grammar;
import jvm.LexicalArgs;
import jvm.SyntaxArgs;

public class SyntaxAnalyzer {

    // Lexical analyzer component
    private LexicalAnalyzer lexicalAnalyzer;
    private Grammar grammar;

    public static void main(String[] args) {
        new SyntaxAnalyzer(new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG)), System.getProperty(SyntaxArgs.GRAMMAR));
    }

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer, String grammarPath) {
        this.lexicalAnalyzer = lexicalAnalyzer;
        grammar = new Grammar(grammarPath);
    }

    /**
     * Get parsed grammar
     * @return grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }
}