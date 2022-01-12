package test;

import models.Operator;
import org.junit.jupiter.api.Test;
import tools.CTLParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CTLFormulaTests {
    @Test
    public void testFormula1() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("!(Q3\\/(Q1/\\Q2))");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals(Operator.class, result.get(0).getClass());
   }
}
