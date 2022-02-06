import algorithms.Algorithmer;
import models.Operator;
import models.kripke.Graph;
import models.kripke.State;
import tools.CTLParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("resources/k2.json");

        String input = "(a/\\b)\\/c";
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
