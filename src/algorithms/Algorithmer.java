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
                graph.addFormulae(s, String.format("not %s", prop));
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> and(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (s.getFormulae().contains(prop1) && s.getFormulae().contains(prop2)) {
                graph.addFormulae(s, String.format("%s and %s", prop1, prop2));
                result.add(s);
            }
        }

        return result;
    }

    public static List<State> or(Graph graph, String prop1, String prop2) {
        List<State> result = new ArrayList<>();

        for (State s : graph.getStates()) {
            if (s.getFormulae().contains(prop1) || s.getFormulae().contains(prop2)) {
                graph.addFormulae(s, String.format("%s or %s", prop1, prop2));
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
                    graph.addFormulae(s, String.format("E X %s", prop));
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
                graph.addFormulae(s, String.format("A X %s", prop));
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
            graph.addFormulae(s, String.format("E %s U %s", prop1, prop2));
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
            graph.addFormulae(state, String.format("A %s U %s", prop1, prop2));
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
            graph.addFormulae(state, String.format("E F %s", prop));
            for (State predecessor : state.getPredecessors()) {
                if (!L.contains(predecessor) && !result.contains(predecessor)) {
                    L.add(predecessor);
                }
            }
        }

        return result;
    }

    public static List<State> AF(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        return result;
    }

    public static List<State> AG(Graph graph, String prop) {
        not(graph, prop);
        EF(graph, "not " + prop);
        List<State> result = not(graph, "E F not " + prop);

        for (State r: result) {
            for (State s: graph.getStates()) {
                if (s.equals(r)) {
                    graph.addFormulae(s, String.format("A G %s", prop));
                }
            }
        }
        return result;
    }

    public static List<State> EG(Graph graph, String prop) {
        List<State> result = new ArrayList<>();

        return result;
    }

    public static List<State> run(Graph graph, List<Object> formula) throws Exception {
        //System.out.println("formula to run : " + formula);
        List<State> result = new ArrayList<>();

        Operator operator = (Operator) formula.get(0);

        if (formula.size() == 2) {
            formula = (List<Object>) formula.get(1);

            Operator nextOperator = (Operator) formula.get(0);

            Object first;
            Object second;
            switch (operator.getOperator()) {
                case "%":
                    first = getElement(graph, formula.get(1));
                    if (first instanceof Boolean) {
                        if (!(Boolean) first) {
                            result.add(new State("result"));
                        }
                    } else if (first instanceof String) {
                        result = Algorithmer.not(graph, (String) first);
                    } else {
                        throw new Exception("first type not handled : " + first.getClass());
                    }
                    break;
                case "E":
                    switch (nextOperator.getOperator()) {
                        case "X":
                            first = getElement(graph, formula.get(1));
                            if (first instanceof Boolean) {
                                result.add(new State("exist"));
                            } else if (first instanceof String) {
                                result = Algorithmer.EX(graph, (String) first);
                            } else {
                                throw new Exception("first type not handled : " + first.getClass());
                            }
                            break;
                        case "F":
                            first = getElement(graph, formula.get(1));
                            if (first instanceof Boolean) {
                                result.add(new State("exist"));
                            } else if (first instanceof String) {
                                result = Algorithmer.EF(graph, (String) first);
                            } else {
                                throw new Exception("first type not handled : " + first.getClass());
                            }
                            break;
                        case "U":
                            first = getElement(graph, formula.get(1));
                            second = getElement(graph, formula.get(2));
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
                            } else if (first instanceof String && second instanceof String) {
                                result = Algorithmer.EU(graph, (String) first, (String) second);
                            } else {
                                if (!(first instanceof String)) {
                                    throw new Exception("first type not handled : " + first.getClass());
                                } else if (!(second instanceof String)) {
                                    throw new Exception("second type not handled : " + second.getClass());
                                }
                            }
                            break;
                        default:
                            throw new Exception("Operator not handled : " + operator.getOperator());
                    }
                    break;
                case "A":
                    switch (nextOperator.getOperator()) {
                        case "X":
                            first = getElement(graph, formula.get(1));
                            if (first instanceof Boolean) {
                                result.add(new State("exist"));
                            } else if (first instanceof String) {
                                result = Algorithmer.AX(graph, (String) first);
                            } else {
                                throw new Exception("first type not handled : " + first.getClass());
                            }
                            break;
                        case "F":
                            first = getElement(graph, formula.get(1));
                            if (first instanceof Boolean) {
                                result.add(new State("exist"));
                            } else if (first instanceof String) {
                                result = Algorithmer.AF(graph, (String) first);
                            } else {
                                throw new Exception("first type not handled : " + first.getClass());
                            }
                            break;
                        case "U":
                            first = getElement(graph, formula.get(1));
                            second = getElement(graph, formula.get(2));
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
                            } else if (first instanceof String && second instanceof String) {
                                result = Algorithmer.AU(graph, (String) first, (String) second);
                            } else {
                                if (!(first instanceof String)) {
                                    throw new Exception("first type not handled : " + first.getClass());
                                } else if (!(second instanceof String)) {
                                    throw new Exception("second type not handled : " + second.getClass());
                                }
                            }
                            break;
                        default:
                            throw new Exception("Operator not handled : " + operator.getOperator());
                    }
                    break;
                default:
                    throw new Exception("Operator not handled : " + operator.getOperator());
            }

        } else if (formula.size() == 3) {
            Object first = getElement(graph, formula.get(1));
            Object second = getElement(graph, formula.get(2));

            if (operator.getOperator().equals("/\\")) {
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
            } else if (operator.getOperator().equals("\\/")) {
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
            } else {
                throw new Exception("Operator not handled : " + operator.getOperator());
            }

        } else {
            throw new Exception("Formula size seems incorrect : " + formula.size());
        }

        return result;
    }

    private static Object getElement(Graph graph, Object element) throws Exception {
        if (element instanceof ArrayList) {
            if (((ArrayList<?>) element).size() == 1) {
                return ((ArrayList<?>) element).get(0);
            } else {
                return !run(graph, (List<Object>) element).isEmpty();
            }
        }
        return null;
    }
}
