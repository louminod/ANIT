package test;

import algorithms.Algorithmer;
import models.kripke.Graph;
import org.junit.jupiter.api.Test;
import tools.CTLParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {
    @Test
    public void testFormula1() {
        Graph graph = new Graph("resources/k2.json");

        List<Object> formula = CTLParser.parseStringFormula("%(a\\/(a/\\b))");
        assertTrue(CTLParser.checkFormulaValidity(formula));

        List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);
        assertTrue(!Algorithmer.run(graph, parsedFormula).isEmpty());
    }
}
