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

        List<Object> formula = CTLParser.parseStringFormula("%(a\\/(a/\\b))");

        if (CTLParser.checkFormulaValidity(formula)) {
            List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);
            //System.out.print(parsedFormula + " -> \n");

            //System.out.println(parsedFormula);

            System.out.printf("RESULT -> %b", !run(graph, parsedFormula).isEmpty());
        } else {
            System.out.println("Formula is not valid");
        }
    }

    private static List<State> run(Graph graph, List<Object> formula) {

        Operator operator = (Operator) formula.get(0);

        Object first = null;
        if (formula.get(1) instanceof ArrayList) {
            first = !run(graph, (List<Object>) formula.get(1)).isEmpty();
        } else {
            first = (String) formula.get(1);
        }

        Object second = null;
        if (formula.size() == 3) {
            if (formula.get(2) instanceof ArrayList) {
                second = !run(graph, (List<Object>) formula.get(2)).isEmpty();
            } else {
                second = (String) formula.get(2);
            }
        }

        System.out.printf("operator -> %s | first -> %s | second -> %s\n", operator.getOperator(), first, second);

        List<State> result = new ArrayList<>();
        switch (operator.getOperator()) {
            case "/\\":
                if (first instanceof Boolean && second instanceof Boolean) {
                    if ((Boolean) first && (Boolean) second) {
                        result.add(new State("result"));
                    }
                } else if (first instanceof Boolean) {
                    if ((Boolean) first && !Algorithmer.marking(graph, (String) second).isEmpty()) {
                        result.add(new State("result"));
                    }
                } else if (second instanceof Boolean) {
                    if (!Algorithmer.marking(graph, (String) first).isEmpty() && (Boolean) second) {
                        result.add(new State("result"));
                    }
                } else {
                    result = Algorithmer.and(graph, (String) first, (String) second);
                }
            case "\\/":
                if (first instanceof Boolean && second instanceof Boolean) {
                    if ((Boolean) first || (Boolean) second) {
                        result.add(new State("result"));
                    }
                } else if (first instanceof Boolean) {
                    if ((Boolean) first || !Algorithmer.marking(graph, (String) second).isEmpty()) {
                        result.add(new State("result"));
                    }
                } else if (second instanceof Boolean) {
                    if (!Algorithmer.marking(graph, (String) first).isEmpty() || (Boolean) second) {
                        result.add(new State("result"));
                    }
                } else {
                    result = Algorithmer.or(graph, (String) first, (String) second);
                }
            case "!":
                if (first instanceof Boolean) {
                    if (!(Boolean) first) {
                        result.add(new State("result"));
                    }
                } else {
                    if (!Algorithmer.marking(graph, (String) first).isEmpty()) {
                        result.add(new State("result"));
                    }
                }
        }
        return result;
    }
}
