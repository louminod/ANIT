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

        String input = "%%a";
        List<Object> formula = CTLParser.parseStringFormula(input);

        System.out.println(formula);

        if (CTLParser.checkFormulaValidity(formula)) {
            List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);
            System.out.println(input + " -> " + parsedFormula);

            try {
                String result = Algorithmer.run(graph, parsedFormula);
                System.out.println(graph.getInitial().getFormulae());

                System.out.println("RESULT -> " + graph.getInitial().getFormulae().contains(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Formula is not valid");
        }

    }
}
