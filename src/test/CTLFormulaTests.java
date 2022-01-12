package test;

import models.Operator;
import models.kripke.State;
import org.junit.jupiter.api.Test;
import tools.CTLParser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CTLFormulaTests {
    @Test
    public void testFormula1() {
        List<Object> parsedFormula = CTLParser.parseStringFormula("!(Q3\\/(Q1/\\Q2))");

        List<Object> result = CTLParser.parseCTLFormula(parsedFormula);

        assertEquals(Operator.class, result.get(0).getClass());
        assertEquals(ArrayList.class, result.get(1).getClass());
        assertEquals(Operator.class, ((ArrayList<Object>)result.get(1)).get(0).getClass());
        assertEquals(State.class, ((ArrayList<Object>)result.get(1)).get(1).getClass());
        assertEquals(ArrayList.class, ((ArrayList<Object>)result.get(1)).get(2).getClass());
   }
}
