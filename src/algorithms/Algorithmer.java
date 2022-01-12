package algorithms;

import models.kripke.Graph;
import models.kripke.State;

import java.util.ArrayList;
import java.util.List;

public class Algorithmer {

    public static List<State> marking(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        for (State s: graph.getStates()) {
            if (s.getFormulae().contains(prop)) {
                result.add(s);
            }
        }

        return result;
    }

    public static void and(Graph graph, String prop1, String prop2) {

        for (State s: graph.getStates()) {
            if (s.getFormulae().contains(prop1) && s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s and %s", prop1, prop2));
            }
        }
    }

    public static void or(Graph graph, String prop1, String prop2) {

        for (State s: graph.getStates()) {
            if (s.getFormulae().contains(prop1) || s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s or %s", prop1, prop2));
            }
        }
    }
}
