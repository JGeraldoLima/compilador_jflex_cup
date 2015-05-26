import utils.SemanticManager;

public class Main {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                System.out.println("Program " + args[i]);
                LexicalAnalysisCalculator scanner = new LexicalAnalysisCalculator(
                        new java.io.FileReader(args[i]));
                parser p = new parser(scanner);
                System.out.println(p.parse() + "\n");
                SemanticManager.setInstance();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
