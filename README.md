# ANIT
> ANIT is a system designed to perform model checking. \
> Model checking is a powerful formal verification approach. Given a formal model of a system and a temporal formula expressing a correctness property, the model checking technique allows to check whether the system satisfies the formula. \
> It is based on an exhaustive exploration of the reachable state space of the system. Thus, it suffers from the well known state explosion problem. \
> In this project, we aim to implement a CTL model checker. We represent the system with a **Kripke structure** : a graph where the nodes/states are labeled with a subset of satisfied atomic propositions.

## CTL Formula grammar
To perfom verification of CTL Formula, you have to use this grammar : 

- E → ∃
- F → F
- X → X
- /\ → ∧
- \\/ → ∨
- A → ∀
- G → G
- % → ¬

For example, if the user want to verify is the CTL formula **φ → ∀aUb** , you will have to type : **AaUb** . \
Spaces don't matter, they will be removed by the parser.


## Get started
In this first version, there is not user interface to interact with.
You’re gonna have to put your hands in the mud.

So, start by cloning the project :

```
git clone https://github.com/louminod/ANIT
```

Then, open the *Main.java* file.
It should look like this :

```java
public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("resources/k2.json");

        String input = "(a/\\b)\\/c";
        System.out.println("Evaluating " + input);
        List<Object> formula = CTLParser.parseStringFormula(input);

        if (CTLParser.checkFormulaValidity(formula)) {
            List<Object> parsedFormula = CTLParser.parseCTLFormula(formula);

            try {
                String result = Algorithmer.run(graph, parsedFormula);

                System.out.println("RESULT -> " + graph.getInitial().getFormulae().contains(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Formula is not valid");
        }

    }
}
```

ANIT contains pre-loaded kripke structure in **resources**. \
You can perfom a CTL Formula verification on one of these.

By default, the **k2** is loaded. You can change it if you want to.

Next, you have to change the value in the *input* by your own formula, with the correct grammar.
```java 
String input = "(a/\\b)\\/c";
```

Finally, run the program a see the result.