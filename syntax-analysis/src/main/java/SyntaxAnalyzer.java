import jvm.LexicalArgs;

public class SyntaxAnalyzer {

    // Lexical analyzer component
    private LexicalAnalyzer lexicalAnalyzer;

    public static void main(String[] args) {
        new SyntaxAnalyzer(new LexicalAnalyzer(System.getProperty(LexicalArgs.MACHINE), System.getProperty(LexicalArgs.CONFIG)));
    }

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }
}