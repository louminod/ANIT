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
                if ("/\\".equals(((Operator) element).getOperator()) || "\\/".equals(((Operator) element).getOperator())) {
                    stack.remove(0);
                    stack.add(element);
                    stack.addAll(parseCTLFormula(formula.subList(0, i)));
                    stack.addAll(parseCTLFormula(formula.subList(i + 1, formula.size())));
                } else {
                    stack.add(element);
                    stack.addAll(parseCTLFormula(formula.subList(i + 1, formula.size())));
                }

                return stack;
            } else {
                stack.add(element);
            }
        }

        return stack;
    }
}

