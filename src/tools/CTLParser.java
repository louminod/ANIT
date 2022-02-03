package tools;

import models.Operator;
import models.enums.CTLOperator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CTLParser {
    public static List<Object> parseStringFormula(String formula) {
        List<Object> result = new ArrayList<>();
        final char[] elements = formula.toCharArray();

        int index = 0;
        while (index < elements.length) {
            String element = String.valueOf(elements[index]);

            if (CTLOperator.isOperator(element)) {
                result.add(new Operator(element));
            } else if ("/".equals(element)) {
                result.add(new Operator("/\\"));
                index++;
            } else if ("\\".equals(element)) {
                result.add(new Operator("\\/"));
                index++;
            } else if ("(".equals(element) || ")".equals(element)) {
                result.add(element);
            } else {
                StringBuilder state = new StringBuilder(element);
                boolean stop = false;
                while (!stop && index < elements.length - 1) {
                    element = String.valueOf(elements[index + 1]);
                    if (!CTLOperator.isOperator(element) && !"/".equals(element) && !"\\".equals(element) && !"(".equals(element) && !")".equals(element)) {
                        state.append(element);
                        index++;
                        if (index == elements.length - 1) {
                            stop = true;
                        }
                    } else {
                        stop = true;
                    }
                }
                result.add(state.toString());
            }
            index++;
        }

        return result;
    }

    public static boolean checkFormulaValidity(List<Object> formula) {
        boolean valid = true;

        int counter = 0;
        for (Object element : formula) {
            if ("(".equals(element) || ")".equals(element)) {
                counter++;
            }
        }

        if (counter % 2 != 0) {
            valid = false;
        }

        return valid;
    }

    public static List<Object> parseCTLFormula(List<Object> formula) {
        // System.out.printf("Evaluating %s\n", formula);
        List<Object> stack = new ArrayList<>();

        Object element = formula.get(0);
        Object first = null;
        Object second = null;
        Operator operator = null;

        if (element instanceof String) {
            if ("(".equals(element)) {
                first = parseCTLFormula(getSubFormula(formula));
                List<Object> newFormula = new ArrayList<>();
                newFormula.add(first);
                newFormula.addAll(formula.subList(((ArrayList<?>) first).size() + 2, formula.size()));
                formula = newFormula;
            } else {
                first = element;
            }

            operator = (Operator) formula.get(1);

            if (formula.size() >= 3) {
                second = formula.get(2);

                if ("(".equals(second)) {
                    second = parseCTLFormula(getSubFormula(formula.subList(2, formula.size())));
                }
            }

            stack.add(operator);
            if (first != null) {
                stack.add(first);
            }
            if (second != null) {
                stack.add(second);
            }
        } else {
            operator = (Operator) element;
            second = formula.get(1);

            if (second instanceof Operator) {
                stack.add(operator);
                stack.add(parseCTLFormula(formula.subList(1, formula.size())));
            } else {
                if ("(".equals(second)) {
                    second = parseCTLFormula(getSubFormula(formula.subList(1, formula.size())));
                }

                if (formula.size() >= 4) {
                    Object third = formula.get(2);
                    Object fourth = formula.get(3);

                    if(third instanceof Operator && fourth instanceof String) {
                        stack.add(operator);
                        if (first != null) {
                            stack.add(first);
                        }
                        List<Object> sub = new ArrayList<>();
                        sub.add(third);
                        sub.add(second);
                        if ("(".equals(fourth)) {
                            fourth = parseCTLFormula(getSubFormula(formula.subList(3, formula.size())));
                        }
                        sub.add(fourth);
                        stack.add(sub);
                    }
                } else {
                    stack.add(operator);
                    if (first != null) {
                        stack.add(first);
                    }
                    if (second != null) {
                        stack.add(second);
                    }
                }
            }
        }

        // System.out.println("operator -> " + operator + " | first -> " + first + " | second -> " + second);

        return stack;
    }

    private static List<Object> getSubFormula(List<Object> formula) {
        List<Object> subFormula = new ArrayList<>();
        int counter = 0;
        for (int i = 1; i < formula.size(); i++) {
            Object element = formula.get(i);
            if ("(".equals(element)) {
                subFormula.add(element);
                counter++;
            } else if (")".equals(element)) {
                if (counter == 0) {
                    break;
                } else {
                    subFormula.add(element);
                    counter--;
                }
            } else {
                subFormula.add(element);
            }
        }
        return subFormula;
    }
}

