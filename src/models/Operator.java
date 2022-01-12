package models;

public class Operator {
    private final String operator;

    public Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "operator='" + operator + '\'' +
                '}';
    }
}
