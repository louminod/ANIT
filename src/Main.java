import tools.CTLParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Object> parsedFormula = CTLParser.parseStringFormula("!(Q3\\/(Q1/\\Q2))");
        // List<Object> parsedFormula = CTLParser.parseStringFormula("EqXr");
        // System.out.println(parsedFormula);
        System.out.println(CTLParser.parseCTLFormula(parsedFormula));
    }
}
