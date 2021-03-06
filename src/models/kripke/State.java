package models.kripke;

import java.util.ArrayList;
import java.util.List;

public class State {
    private String name;
    private List<String> propositions;
    private List<State> predecessors;
    private List<State> successors;
    private List<String> formulae;
    private int degree;


    public State(String name){
        this.name = name;
        this.propositions = new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.formulae = new ArrayList<>();
    }

    public void addProposition(String expression){
        this.propositions.add(expression);
    }

    public void addPredecessor(State state){
        this.predecessors.add(state);
    }

    public void addSuccessor(State state){
        this.successors.add(state);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<String> propositions) {
        this.propositions = propositions;
    }

    public List<State> getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(List<State> predecessors) {
        this.predecessors = predecessors;
    }

    public List<State> getSuccessors() {
        return successors;
    }

    public void setSuccessors(List<State> successors) {
        this.successors = successors;
    }


    public List<String> getFormulae() {
        return formulae;
    }

    public void setFormulae(List<String> formulae) {
        this.formulae = formulae;
    }

    public void addFormulae(String formulae) { this.formulae.add(formulae); }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String displayPredecessors() {
        StringBuilder names = new StringBuilder();
        for (State state : this.predecessors){
            names.append(state.getName()).append(" ");
        }
        return String.valueOf(names);
    }

    public String displaySuccessors() {
        StringBuilder names = new StringBuilder();
        for (State state : this.successors){
            names.append(state.getName()).append(" ");
        }
        return String.valueOf(names);
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", propositions=" + propositions +
                ", formulae=" + formulae +
                ", predecessors=" + displayPredecessors() +
                ", successors=" + displaySuccessors() +
                '}';
    }

}
