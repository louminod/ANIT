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
                s.addFormulae(String.format("%% %s", prop));
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> and(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (s.getFormulae().contains(prop1) && s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s /\\ %s", prop1, prop2));
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> or(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (s.getFormulae().contains(prop1) || s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s \\/ %s", prop1, prop2));
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
                    s.addFormulae(String.format("EX %s", prop));
                    result.add(s);
                    done = true;
                }
            }
        }

        return result;
    }

    public static List<State> AX(Graph graph, String prop) {
        List<State> result = new ArrayList<>();
        boolean validState;

        for (State s : graph.getStates()) {
            validState = true;
            for (State successors : s.getSuccessors()) {
                if (!successors.getFormulae().contains(prop)) {
                    validState = false;
                    break;
                }
            }
            if (validState) {
                result.add(s);
                s.addFormulae(String.format("AX %s", prop));
            }
        }

        return result;
    }

    public static List<State> EU(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();
        List<State> L = marking(graph, prop2);
        List<State> seenBefore = marking(graph, prop2);
        State s;

        while (!L.isEmpty()) {
            s = L.get(0);
            L.remove(0);
            s.addFormulae(String.format("EU %s %s", prop1, prop2));
            result.add(s);
            for (State predecessor : s.getPredecessors()) {
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
        List<State> listStateProp2 = marking(graph, prop2);

        for (State state : graph.getStates()) {
            state.setDegree(state.getSuccessors().size());
        }

        while (!listStateProp2.isEmpty()) {
            State state = listStateProp2.get(0);
            listStateProp2.remove(0);
            result.add(state);
            state.addFormulae(String.format("AU %s %s", prop1, prop2));
            for (State predecessor : state.getPredecessors()) {
                predecessor.setDegree(predecessor.getDegree() - 1);
                if (predecessor.getDegree() == 0 && predecessor.getFormulae().contains(prop1) && !result.contains(predecessor)) {
                    listStateProp2.add(predecessor);
                }
            }
        }

        return result;
    }

    public static List<State> EF(Graph graph, String prop) {
        List<State> result = new ArrayList<>();
        List<State> L = marking(graph, prop);

        while (!L.isEmpty()) {
            State state = L.get(0);
            L.remove(0);
            result.add(state);
            state.addFormulae(String.format("EF %s", prop));
            for (State predecessor : state.getPredecessors()) {
                if (!L.contains(predecessor) && !result.contains(predecessor)) {
                    L.add(predecessor);
                }
            }
        }

        return result;
    }

    public static List<State> AF(Graph graph, String prop) {
        not(graph, prop);
        EG(graph, "% " + prop);
        List<State> result = not(graph, "EG % " + prop);

        for (State r : result) {
            for (State s : graph.getStates()) {
                if (s.equals(r)) {
                    s.addFormulae(String.format("AF %s", prop));
                }
            }
        }

        return result;
    }

    public static List<State> AG(Graph graph, String prop) {
        not(graph, prop);
        EF(graph, "% " + prop);
        List<State> result = not(graph, "EF % " + prop);

        for (State r : result) {
            for (State s : graph.getStates()) {
                if (s.equals(r)) {
                    s.addFormulae(String.format("AG %s", prop));
                }
            }
        }
        return result;
    }

    public static List<State> EG(Graph graph, String prop) {
        List<State> result = new ArrayList<>();
        List<State> seenBefore;
        List<State> L;
        State s;
        boolean ok = true;
        for (State state : graph.getStates()) {
            if (state.getFormulae().contains(prop)) {
                L = state.getSuccessors();
                if (L.isEmpty() && !result.contains(state)) {
                    result.add(state);
                    state.addFormulae(String.format("EG %s", prop));
                }
                seenBefore = new ArrayList<>();
                while (!L.isEmpty()) {
                    s = L.get(0);
                    L.remove(0);
                    ok = true;
                    if (s.getFormulae().contains(prop)) {
                        seenBefore.add(s);
                        for (State successor : s.getSuccessors()) {
                            ok = false;
                            if (!seenBefore.contains(successor)) {
                                L.add(successor);
                            } else {
                                ok = true;
                            }
                        }
                        if (ok) {
                            if (!result.contains(state)) {
                                result.add(state);
                                state.addFormulae(String.format("EG %s", prop));
                            }

                        }
                    }
                }
            }

        }
        return result;
    }

    public static String run(Graph graph, List<Object> formula) throws Exception {

        String prop1 = null;
        String prop2 = null;

        if (formula.size() > 1) {
            Operator operator = (Operator) formula.get(0);

            for (int i = 1; i < formula.size(); i++) {
                Object element = formula.get(i);

                if (element instanceof ArrayList) {
                    element = run(graph, (List<Object>) element);
                    formula.set(i, element);
                }

                if (i == 1) {
                    prop1 = (String) element;
                } else {
                    prop2 = (String) element;
                }
            }

            switch (operator.getOperator()) {
                case "/\\":
                    Algorithmer.and(graph, prop1, prop2);
                    return String.format("%s %s %s", prop1, operator.getOperator(), prop2);
                case "\\/":
                    Algorithmer.or(graph, prop1, prop2);
                    return String.format("%s %s %s", prop1, operator.getOperator(), prop2);
                case "%":
                    Algorithmer.not(graph, prop1);
                    return String.format("%s %s", operator.getOperator(), prop1);
                case "EX":
                    Algorithmer.EX(graph, prop1);
                    return String.format("%s %s", operator.getOperator(), prop1);
                case "AX":
                    Algorithmer.AX(graph, prop1);
                    return String.format("%s %s", operator.getOperator(), prop1);
                case "EF":
                    Algorithmer.EF(graph, prop1);
                    return String.format("%s %s", operator.getOperator(), prop1);
                case "AF":
                    Algorithmer.AF(graph, prop1);
                    return String.format("%s %s", operator.getOperator(), prop1);
                case "EU":
                    Algorithmer.EU(graph, prop1, prop2);
                    return String.format("%s %s %s", operator.getOperator(), prop1, prop2);
                case "AU":
                    Algorithmer.AU(graph, prop1, prop2);
                    return String.format("%s %s %s", operator.getOperator(), prop1, prop2);
                case "EG":
                    Algorithmer.EG(graph, prop1);
                    return String.format("%s %s", operator.getOperator(), prop1);
                case "AG":
                    Algorithmer.AG(graph, prop1);
                    return String.format("%s %s", operator.getOperator(), prop1);
            }
        } else {
            return (String) formula.get(0);
        }

        return null;
    }
}
