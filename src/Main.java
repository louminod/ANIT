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

        /*
        System.out.println("%a\\/b -> " + CTLParser.parseCTLFormula(CTLParser.parseStringFormula("%a\\/b")));
        System.out.println("EX!a -> " + CTLParser.parseCTLFormula(CTLParser.parseStringFormula("EX!a")));
        System.out.println("FX(a\\/b) -> " + CTLParser.parseCTLFormula(CTLParser.parseStringFormula("FX(a\\/b)")));
        System.out.println("%(a\\/(b/\\c)) -> " + CTLParser.parseCTLFormula(CTLParser.parseStringFormula("%(a\\/(b/\\c))")));
        */

        String input = "EX!a";
        //List<Object> formula = CTLParser.parseStringFormula("%a\\/b");
        List<Object> formula = CTLParser.parseStringFormula(input);

        if (CTLParser.checkFormulaValidity(formula)) {
            List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);
            System.out.println(input + " -> " + parsedFormula);

            System.out.printf("RESULT -> %b", !Algorithmer.run(graph, parsedFormula).isEmpty());
        } else {
            System.out.println("Formula is not valid");
        }

    }
}
