import algorithms.Algorithmer;
import models.kripke.Graph;
import tools.CTLParser;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("resources/k2.json");

        String input = "AX(b\\/(EF%c))";
        System.out.println("Evaluating " + input);
        List<Object> formula = CTLParser.parseStringFormula(input);

        if (CTLParser.checkFormulaValidity(formula)) {
            List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);

            try {
                String result = Algorithmer.run(graph, parsedFormula);

                System.out.println("RESULT -> " + graph.getInitial().getFormulae().contains(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Formula is not valid");
        }

    }
}
