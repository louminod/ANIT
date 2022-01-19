package algorithms;

import models.Operator;
import models.kripke.Graph;
import models.kripke.State;

import java.util.ArrayList;
import java.util.List;

public class Algorithmer {

    public static List<State> marking(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (s.getFormulae().contains(prop)) {
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> not(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (!s.getFormulae().contains(prop)) {
                s.addFormulae(String.format("not %s", prop));
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> and(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (s.getFormulae().contains(prop1) && s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s and %s", prop1, prop2));
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> or(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (s.getFormulae().contains(prop1) || s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s or %s", prop1, prop2));
                result.add(s);
            }
        }
        return result;
    }

    public static List<State> EX(Graph graph, String prop) {
        List<State> result = new ArrayList<>();
        boolean done;

        for (State s : graph.getStates()) {
            done = false;
            for (State successors : s.getSuccessors()) {
                if (successors.getFormulae().contains(prop) && !done) {
                    s.addFormulae(String.format("E X %s", prop));
                    result.add(s);
                    done = true;
                }
            }
        }

        return result;
    }

    public static List<State> AX(Graph graph, String prop) {
        List<State> result = new ArrayList<>();
        boolean one;
        boolean all;

        for (State s : graph.getStates()) {
            one = false;
            all = true;
            for (State successors : s.getSuccessors()) {
                if (successors.getFormulae().contains(prop)) {
                    one = true;
                } else {
                    all = false;
                }
            }
            if (one && all) {
                result.add(s);
                s.addFormulae(String.format("A X %s", prop));
            }
        }

        return result;
    }

    public static List<State> EUntil(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();
        List<State> L = marking(graph, prop2);
        List<State> seenBefore = marking(graph, prop2);
        State s;

        while (!L.isEmpty()) {
            s = L.get(0);
            L.remove(0);
            s.addFormulae(String.format("E %s U %s", prop1, prop2));
            result.add(s);
            for (State predecessor: s.getPredecessors()) {
                if (!seenBefore.contains(predecessor)) {
                    seenBefore.add(predecessor);
                    if (predecessor.getFormulae().contains(prop1)) {
                        L.add(predecessor);
                    }
                }
            }
        }

        return result;
    }

    public static List<State> AU(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        return result;
    }

    public static List<State> EF(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        return result;
    }

    public static List<State> AF(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        return result;
    }

    public static List<State> G(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        return result;
    }

    public static List<State> run(Graph graph, List<Object> formula) {
        System.out.println("formula : " + formula);

        Operator operator = (Operator) formula.get(0);

        Object first = null;
        if (formula.get(1) instanceof ArrayList && ((ArrayList) formula.get(1)).size() > 1) {
            first = !run(graph, (List<Object>) formula.get(1)).isEmpty();
        } else {
            if (formula.get(1) instanceof ArrayList) {
                first = ((ArrayList) formula.get(1)).get(0);
            } else {
                first = (String) formula.get(1);
            }

        }

        Object second = null;
        if (formula.size() == 3) {
            if (formula.get(2) instanceof ArrayList && ((ArrayList) formula.get(2)).size() > 1) {
                second = !run(graph, (List<Object>) formula.get(2)).isEmpty();
            } else {
                if (formula.get(2) instanceof ArrayList) {
                    second = ((ArrayList) formula.get(2)).get(0);
                } else {
                    second = (String) formula.get(2);
                }
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
            case "%":
                if (first instanceof Boolean) {
                    if (!(Boolean) first) {
                        result.add(new State("result"));
                    }
                } else {
                    result = Algorithmer.not(graph, (String) first);
                }
        }
        return result;
    }
}
