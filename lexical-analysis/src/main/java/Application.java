import Utils.StringUtilsPlus;
import parser.Parser;

public class Application {
    public static void main(String[] args) {
        Parser parser = new Parser();
        System.out.println(StringUtilsPlus.ObjectToJson(parser));
    }
}
