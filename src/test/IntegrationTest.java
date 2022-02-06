package test;

import algorithms.Algorithmer;
import models.kripke.Graph;
import org.junit.jupiter.api.Test;
import tools.CTLParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {
    Graph graph = new Graph("resources/k2.json");

    @Test
    public void testIntegration() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("a"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationAND() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("a/\\b"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationOR() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("a\\/b"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationNOT() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("%%a"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationEX() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("EXa"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationEF() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("EFa"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationEG() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("EGa"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertFalse(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationEU() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("EaUb"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationAX() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("AXa"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertFalse(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationAF() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("AFa"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationAG() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("AGa"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }

    @Test
    public void testIntegrationAU() throws Exception {
        List<Object> parsedFormula = CTLParser.parseCTLFormula(CTLParser.parseStringFormula("AaUb"));

        String result = Algorithmer.run(graph, parsedFormula);

        assertTrue(graph.getInitial().getFormulae().contains(result));
    }
}
