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
        List<Object> parsedFormula = CTLParser.parseStringFormula("a/\\b");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='/\\'}, a, b]", result.toString());
    }

    @Test
    public void testFormula2() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("EXa");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='E'}, [Operator{operator='X'}, a]]", result.toString());
    }

    @Test
    public void testFormula3() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("%b");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='%'}, b]", result.toString());
    }

    @Test
    public void testFormula4() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("(a/\\b)\\/c");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='\\/'}, [Operator{operator='/\\'}, a, b], c]", result.toString());
    }

    @Test
    public void testFormula5() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("c\\/(a/\\b)");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='\\/'}, c, [Operator{operator='/\\'}, a, b]]", result.toString());
    }

    @Test
    public void testFormula6() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("(a/\\b)\\/(c/\\d)");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='\\/'}, [Operator{operator='/\\'}, a, b], [Operator{operator='/\\'}, c, d]]", result.toString());
    }

    @Test
    public void testFormula7() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("EaUb");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='E'}, [Operator{operator='U'}, a, b]]", result.toString());
    }

    @Test
    public void testFormula8() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("EaU(b/\\c)");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='E'}, [Operator{operator='U'}, a, [Operator{operator='/\\'}, b, c]]]", result.toString());
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
