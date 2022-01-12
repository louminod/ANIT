package test;

import models.kripke.Graph;
import models.kripke.State;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KripkeTests {

    @Test
    public void initialStateIsS0() {
        Graph graph = new Graph("resources/k1.json");
        assertEquals("S0", graph.getInitial().getName());
    }

    @Test
    public void propositionsInS1AreCorrect() {
        Graph graph = new Graph("resources/k1.json");
        List<String> props = graph.getStates().get(1).getPropositions();
        StringBuilder prop = new StringBuilder();
        for (String p : props) {
            prop.append(p);
        }
        assertEquals("ab!c", String.valueOf(prop));
    }

    @Test
    public void predecessorsOfS2AreS0S1() {
        Graph graph = new Graph("resources/k1.json");
        List<State> predecessors = graph.getStates().get(2).getPredecessors();
        StringBuilder pred = new StringBuilder();
        for (State p : predecessors) {
            pred.append(p.getName());
        }
        assertEquals("S0S1", String.valueOf(pred));
    }

    @Test
    public void successorsOfS2IsS0() {
        Graph graph = new Graph("resources/k1.json");
        List<State> successors = graph.getStates().get(2).getSuccessors();
        StringBuilder succ = new StringBuilder();
        for (State p : successors) {
            succ.append(p.getName());
        }
        assertEquals("S0", String.valueOf(succ));
    }
}
