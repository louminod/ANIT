package test;

import algorithms.Algorithmer;
import models.kripke.Graph;
import models.kripke.State;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AlgorithmerTests {
    @Test

    public void testMarking1(){
        Graph graph = new Graph("resources/k1.json");
        List<State> result = Algorithmer.marking(graph, "b");
        List<State> expected = graph.getStates();

        assert (result == expected);

    }
}
