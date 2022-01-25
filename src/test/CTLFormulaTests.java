package test;

import models.Operator;
import models.kripke.State;
import org.junit.jupiter.api.Test;
import tools.CTLParser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CTLFormulaTests {
    @Test
    public void testFormula1() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("%(Q3\\/(Q1/\\Q2))");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        System.out.println(result);

        assertEquals(Operator.class, result.get(0).getClass());
        assertEquals(ArrayList.class, result.get(1).getClass());
        assertEquals(Operator.class, ((ArrayList<Object>) result.get(1)).get(0).getClass());
        assertEquals("[Q3]", ((ArrayList<Object>) result.get(1)).get(1).toString());
        assertEquals(ArrayList.class, ((ArrayList<Object>) result.get(1)).get(2).getClass());
        assertEquals(Operator.class, ((ArrayList) ((ArrayList<Object>) result.get(1)).get(2)).get(0).getClass());
        assertEquals("[Q1]", ((ArrayList) ((ArrayList<Object>) result.get(1)).get(2)).get(1).toString());
        assertEquals("[Q2]", ((ArrayList) ((ArrayList<Object>) result.get(1)).get(2)).get(2).toString());
    }

    @Test
    public void testFormula2() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("a/\\b");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals(Operator.class, result.get(0).getClass());
        assertEquals("[a]", result.get(1).toString());
        assertEquals("[b]", result.get(2).toString());
    }

    @Test
    public void testFormula3() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("EXa");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals(Operator.class, result.get(0).getClass());
        assertEquals(ArrayList.class, result.get(1).getClass());
        assertEquals("[a]", ((ArrayList) result.get(1)).get(1).toString());
    }

    @Test
    public void testFormulaValidityIsNotCorrect() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("%(a\\/b");

        boolean valid = CTLParser.checkFormulaValidity(parsedFormula);

        assertFalse(valid);
    }

    @Test
    public void testFormulaValidityIsCorrect() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("%(a\\/b)");

        boolean valid = CTLParser.checkFormulaValidity(parsedFormula);

        assertTrue(valid);
    }
}
