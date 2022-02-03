package test;

import algorithms.Algorithmer;
import models.kripke.Graph;
import models.kripke.State;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmerTests {

    @Test
    public void testMarkingA() {
        Graph graph = new Graph("resources/k1.json");

        List<State> resultA = Algorithmer.marking(graph, "a");
        List<State> expectedA = graph.getStates().subList(1, 2);

        assert (resultA.containsAll(expectedA));
        assert (resultA.size() == expectedA.size());
    }

    @Test
    public void testMarkingB() {
        Graph graph = new Graph("resources/k1.json");

        List<State> resultB = Algorithmer.marking(graph, "b");
        List<State> expectedB = graph.getStates();

        assert (resultB.containsAll(expectedB));
        assert (resultB.size() == expectedB.size());
    }

    @Test
    public void testAnd() {
        Graph graph = new Graph("resources/k1.json");

        List<State> result = Algorithmer.and(graph, "b", "!c");
        List<State> expected = graph.getStates().subList(1, 3);

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }

    @Test
    public void testOr() {
        Graph graph = new Graph("resources/k1.json");

        List<State> result = Algorithmer.or(graph, "a", "!c");
        List<State> expected = graph.getStates().subList(1, 3);

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }

    @Test
    public void testEX() {
        Graph graph = new Graph("resources/k2.json");

        List<State> result = Algorithmer.EX(graph, "a");
        List<State> expected = new ArrayList<>();
        expected.add(graph.getStates().get(0));
        expected.add(graph.getStates().get(4));
        expected.add(graph.getStates().get(5));
        expected.add(graph.getStates().get(6));
        expected.add(graph.getStates().get(7));

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }

    @Test
    public void testAX() {
        Graph graph = new Graph("resources/k2.json");

        List<State> result = Algorithmer.AX(graph, "a");
        List<State> expected = new ArrayList<>();
        expected.add(graph.getStates().get(4));
        expected.add(graph.getStates().get(5));
        expected.add(graph.getStates().get(6));
        expected.add(graph.getStates().get(7));

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }

    @Test
    public void testEUntil() {
        Graph graph = new Graph("resources/k3.json");

        List<State> result = Algorithmer.EU(graph, "a", "b");
        List<State> expected = new ArrayList<>();
        expected.add(graph.getStates().get(1));
        expected.add(graph.getStates().get(4));
        expected.add(graph.getStates().get(6));
        expected.add(graph.getStates().get(3));
        expected.add(graph.getStates().get(7));

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }


    @Test
    public void testAUntil() {
        Graph graph = new Graph("resources/k3.json");

        List<State> result = Algorithmer.AU(graph, "a", "b");
        List<State> expected = new ArrayList<>();
        expected.add(graph.getStates().get(4));
        expected.add(graph.getStates().get(6));

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }

    @Test
    public void testNot() {
        Graph graph = new Graph("resources/k2.json");
        List<State> result = Algorithmer.not(graph, "a");
        List<State> expected = new ArrayList<>();

        expected.add(graph.getStates().get(1));
        expected.add(graph.getStates().get(4));
        expected.add(graph.getStates().get(3));
        expected.add(graph.getStates().get(5));

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }

    @Test
    public void testEF() {
        Graph graph = new Graph("resources/k2.json");
        Algorithmer.not(graph, "b");

        List<State> result = Algorithmer.EF(graph, "not b");
        List<State> expected = new ArrayList<>();

        expected.add(graph.getStates().get(0));
        expected.add(graph.getStates().get(1));
        expected.add(graph.getStates().get(2));
        expected.add(graph.getStates().get(3));
        expected.add(graph.getStates().get(4));
        expected.add(graph.getStates().get(5));

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());

    }

    @Test
    public void testAG(){
        Graph graph = new Graph("resources/k4.json");

        List<State> result = Algorithmer.AG(graph, "a");
        List<State> expected = new ArrayList<>();
        expected.add(graph.getStates().get(1));
        expected.add(graph.getStates().get(3));
        expected.add(graph.getStates().get(4));
        expected.add(graph.getStates().get(5));
        expected.add(graph.getStates().get(6));

        assert (result.containsAll(expected));
        assert (result.size() == expected.size());
    }
}
