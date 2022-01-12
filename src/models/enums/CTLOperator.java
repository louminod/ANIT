package models.enums;

public abstract class CTLOperator {
    public static final String[] OPERATORS = {"E", "F", "X", "/\\", "\\/", "A", "G", "!"};

    public static boolean isOperator(String element) {
        for (String operator : OPERATORS) {
            if (operator.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
