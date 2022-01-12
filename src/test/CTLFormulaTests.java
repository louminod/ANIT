package test;

import org.junit.jupiter.api.Test;
import tools.CTLParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CTLFormulaTests {
    @Test
    public void testFormula1() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("!(Q3\\/(Q1/\\Q2))");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='!'}, [Operator{operator='\\/'}, Q3, [Operator{operator='/\\'}, Q1, Q2]]]", result.toString());
    }

    @Test
    public void testFormula2() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("EXp");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='E'}, Operator{operator='X'}, p]", result.toString());
    }

    @Test
    public void testFormula3() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("EqXr");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals("[Operator{operator='E'}, q, Operator{operator='X'}, r]", result.toString());
    }
}
