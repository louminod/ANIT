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

    public static List<State> and(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s: graph.getStates()) {
            if (s.getFormulae().contains(prop1) && s.getFormulae().contains(prop2)) {
                s.addFormulae(String.format("%s and %s", prop1, prop2));
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> or(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s: graph.getStates()) {
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

        for (State s: graph.getStates()) {
            done = false;
            for (State successors: s.getSuccessors()) {
                if (successors.getFormulae().contains(prop) && !done) {
                    s.addFormulae(String.format("EX %s", prop));
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

        for(State s: graph.getStates()) {
            one = false;
            all = true;
            for(State successors: s.getSuccessors()) {
                if (successors.getFormulae().contains(prop)) {
                    one = true;
                }
                else {
                    all = false;
                }
            }
            if (one && all) {
                result.add(s);
                s.addFormulae(String.format("AX %s", prop));
            }
        }

        return result;
    }
}
