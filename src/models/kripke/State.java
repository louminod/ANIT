package models.kripke;

import java.util.ArrayList;
import java.util.List;

public class State {
    private String name;
    private List<String> propositions;
    private List<State> predecessors;
    private List<State> successors;

    public State(String name){
        this.name = name;
        this.propositions = new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
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
}
