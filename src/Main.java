import models.kripke.Graph;
import tools.CTLParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello ANIT");

        List<Object> formula = CTLParser.parseStringFormula("!(a\\/b)");

        if (CTLParser.checkFormulaValidity(formula)) {
            List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);

            // INSERT CODE HERE
        } else {
            System.out.println("Formula is not valid");
        }
    }
}
