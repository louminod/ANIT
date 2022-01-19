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

        for (int i = 0; i < formula.size(); i++) {
            Object element = formula.get(i);

            if ("(".equals(element)) {
                List<Object> subFormula = new ArrayList<>();
                int counter = -1;
                for (int j = i; j < formula.size(); j++) {
                    element = formula.get(j);
                    if ("(".equals(element)) {
                        if (counter == 0) {
                            subFormula.add(element);
                        }
                        counter++;
                    } else if (")".equals(element)) {
                        if (counter == 0) {
                            stack.add(parseCTLFormula(subFormula));
                            return stack;
                        } else {
                            subFormula.add(element);
                            counter--;
                        }
                    } else {
                        subFormula.add(element);
                    }
                }
            } else if (element instanceof Operator) {

                int operatorIndex = i;
                boolean stop = false;
                do {
                    i++;
                    if (i < formula.size()) {
                        element = formula.get(i);
                        if (element instanceof String && element.equals("(")) {
                            stop = true;
                        } else if (element instanceof Operator) {
                            operatorIndex = i;
                        }
                    } else {
                        stop = true;
                    }
                } while (!stop);

                i = operatorIndex;
                element = formula.get(i);

                if ("/\\".equals(((Operator) element).getOperator()) || "\\/".equals(((Operator) element).getOperator())) {
                    if (!stack.isEmpty()) {
                        stack.remove(0);
                    }
                    stack.add(element);
                    List<Object> sub1 = new ArrayList<>();
                    List<Object> sub2 = new ArrayList<>();
                    sub1.add(parseCTLFormula(formula.subList(0, i)));
                    stack.addAll(sub1);
                    sub2.add(parseCTLFormula(formula.subList(i + 1, formula.size())));
                    stack.addAll(sub2);
                } else {
                    stack.add(element);
                    stack.addAll(parseCTLFormula(formula.subList(i + 1, formula.size())));
                }

                return stack;
            } else if (element instanceof String) {
                stack.add(element);
            }
        }

        return stack;
    }
}

