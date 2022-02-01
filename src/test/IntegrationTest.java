package test;

import algorithms.Algorithmer;
import models.kripke.Graph;
import org.junit.jupiter.api.Test;
import tools.CTLParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {
    @Test
    public void testFormula1() throws Exception {
        Graph graph = new Graph("resources/k2.json");

        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("%(a\\/(a/\\b))"));

        System.out.println(parsedFormula);

        assertTrue(Algorithmer.run(graph, parsedFormula).isEmpty());
    }

    @Test
    public void testFormula2() throws Exception {
        Graph graph = new Graph("resources/k2.json");

        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("%a\\/b"));

        assertTrue(Algorithmer.run(graph, parsedFormula).isEmpty());
    }

    @Test
    public void testFormula3() throws Exception {
        Graph graph = new Graph("resources/k2.json");

        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("EX!a"));

        assertTrue(!Algorithmer.run(graph, parsedFormula).isEmpty());
    }

    @Test
    public void testFormula4() throws Exception {
        Graph graph = new Graph("resources/k2.json");

        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("EaUb"));

        assertTrue(!Algorithmer.run(graph, parsedFormula).isEmpty());
    }

    @Test
    public void testFormula5() throws Exception {
        Graph graph = new Graph("resources/k2.json");

        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("AaUb"));

        assertTrue(!Algorithmer.run(graph, parsedFormula).isEmpty());
    }
}
