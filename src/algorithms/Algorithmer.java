package algorithms;

import models.kripke.Graph;
import models.kripke.State;

import java.util.ArrayList;
import java.util.List;

public class Algorithmer {

    public List<Boolean> marking(Graph graph, String prop) {
        List<Boolean> result = new ArrayList<>();



        return result;
    }

    public List<Boolean> not(Graph graph, String prop) {
        List<Boolean> result = new ArrayList<>();

        for (State s: graph.getStates()) {
            if (s.getFormulae().contains(prop)) {
                s.addFormulae(String.format("not %s", prop));
                result.add(true);
            }
            else {
                result.add(false);
            }
        }
        return result;
    }

    public List<Boolean> and(Graph graph, String prop1, String prop2) {
        List<Boolean> result = new ArrayList<>();

        for (State s: graph.getStates()) {
            if (s.getFormulae().contains(prop1) && s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s and %s", prop1, prop2));
                result.add(true);
            }
            else {
                result.add(false);
            }
        }
        return result;
    }

    public List<Boolean> or(Graph graph, String prop1, String prop2) {
        List<Boolean> result = new ArrayList<>();

        for (State s: graph.getStates()) {
            if (s.getFormulae().contains(prop1) || s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s or %s", prop1, prop2));
                result.add(true);
            }
            else {
                result.add(false);
            }
        }
        return result;
    }
}
