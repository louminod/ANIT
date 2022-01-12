package models.kripke;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<State> states;
    private State initial;

    public Graph(String filePath) {
        states = new ArrayList<>();
        readFile(filePath);
        findPredecessors();
        for (State state : this.states) {
            for (String prop : state.getPropositions()) {
                state.addFormulae(prop);
            }
        }
        /*
        for (State state : this.states) {
            System.out.println(state.toString());
        }
        */
    }

    private void readFile(String filePath) {
        try {
            String text = Files.readString(Paths.get(filePath));
            JSONObject obj = new JSONObject(text);
            String initial_name = obj.getString("initial");
            JSONArray states_names = obj.getJSONArray("states");
            for (int i = 0; i < states_names.length(); i++){
                String name = states_names.getJSONObject(i).getString("name");
                states.add(new State(name));
                if (name.equals(initial_name)){
                    this.initial = states.get(states.size()-1);
                }
            }
            JSONArray states_definitions = obj.getJSONArray("states");
            for (int i = 0; i < states_definitions.length(); i++){
                JSONObject state = states_definitions.getJSONObject(i);
                JSONArray propositions = state.getJSONArray("propositions");
                for (int j = 0; j < propositions.length(); j++){
                    this.states.get(i).addProposition(propositions.getString(j));
                }
                JSONArray successors = state.getJSONArray("successors");
                for (int j = 0; j < successors.length(); j++){
                    this.states.get(i).addSuccessor(findStateUsingName(successors.getString(j)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private State findStateUsingName(String name) {
        for(State state : this.states){
            if (state.getName().equals(name)){
                return state;
            }
        }
        return null;
    }

    private void findPredecessors() {
        for (State state : this.states) {
            for (State successor : state.getSuccessors()) {
                successor.addPredecessor(state);
            }
        }
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public State getInitial() {
        return initial;
    }

    public void setInitial(State initial) {
        this.initial = initial;
    }
}
