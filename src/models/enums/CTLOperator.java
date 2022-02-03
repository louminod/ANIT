package models.enums;

public abstract class CTLOperator {
    public static final String[] OPERATORS = {"EX", "EF", "EU", "EG", "AX", "AF", "AU", "AG", "/\\", "\\/", "%"};

    public static boolean isOperator(String element) {
        for (String operator : OPERATORS) {
            if (operator.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
