package test;

import algorithms.Algorithmer;
import models.kripke.Graph;
import models.kripke.State;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AlgorithmerTests {

    @Test
    public void testMarkingA(){
        Graph graph = new Graph("resources/k1.json");

        List<State> resultA = Algorithmer.marking(graph, "a");
        List<State> expectedA = graph.getStates().subList(1, 2);

        assert (resultA.containsAll(expectedA));
    }

    @Test
    public void testMarkingB(){
        Graph graph = new Graph("resources/k1.json");

        List<State> resultB = Algorithmer.marking(graph, "b");
        List<State> expectedB = graph.getStates();

        assert (resultB.containsAll(expectedB));
    }

    @Test
    public void testAnd(){
        Graph graph = new Graph("resources/k1.json");

        List<State> result = Algorithmer.and(graph, "b", "!c");
        List<State> expected = graph.getStates().subList(1,3);

        assert (result.containsAll(expected));
    }

    @Test
    public void testOr(){
        Graph graph = new Graph("resources/k1.json");

        List<State> result = Algorithmer.or(graph, "a", "!c");
        List<State> expected = graph.getStates().subList(1,3);

        assert (result.containsAll(expected));
    }
}
