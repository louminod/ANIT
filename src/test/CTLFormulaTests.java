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

        assertEquals("[Operator{operator='%'}, [Operator{operator='\\/'}, [Q3], [Operator{operator='/\\'}, [Q1], [Q2]]]]", result.toString());
    }

    @Test
    public void testFormula2() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("a/\\b");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='/\\'}, [a], [b]]", result.toString());
    }

    @Test
    public void testFormula3() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("EXa");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='E'}, [Operator{operator='X'}, [a]]]", result.toString());
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
