import algorithms.Algorithmer;
import models.Operator;
import models.kripke.Graph;
import models.kripke.State;
import tools.CTLParser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("resources/k2.json");

        List<Object> formula = CTLParser.parseStringFormula("%a\\/b");
        // List<Object> formula = CTLParser.parseStringFormula("%(a\\/(b/\\c))");

        if (CTLParser.checkFormulaValidity(formula)) {
            List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);
            System.out.println("parsed formula -> " + parsedFormula);

            //System.out.println(parsedFormula);

            System.out.printf("RESULT -> %b", !Algorithmer.run(graph, parsedFormula).isEmpty());
        } else {
            System.out.println("Formula is not valid");
        }
    }


}
