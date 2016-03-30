package Automata;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import Automata.Core.Relation;
import Automata.Core.State;

/**
 * Container class that represents a fully defined automata, in this case, a
 * {@link PushdownAutomata}. It holds a comprehensive {@link Map} object of all the states
 * that make up the automata, which can be accessed using the name of the state. The currentState
 * is used for traversing the graph and is pointed to the initialState at the start of a call
 * to recognize(String), which handles any clean up needed between recognition tasks.
 */
public class PushdownAutomata {
    private Map<String, State> states;

    private State initialState;

    private State currentState;

    private Stack<Character> stack;

    public PushdownAutomata(Map<String, State> states, State initialState) {
        this.states = states;
        this.initialState = initialState;
        stack = new Stack<>();
    }

    public boolean recognize(String input) {
        reset();

        if (input.isEmpty()) {
            return currentState.isAcceptingState();
        }

        List<Relation> relations = currentState.getAppropriateRelations(input.toCharArray()[0],
                stack.peek());

        if (relations.isEmpty()) {
            throw new IllegalStateException(
                    ">> Automata broken: Could not find any appropriate relations <<");
        }

        transition(relations.get(0));
        return recognize(input.substring(1));

        //  TODO: Add support for non-determinism
    }

    private void transition(Relation relation) {
        if (!currentState.getName()
                .equals(relation.getNextStateName())) {
            currentState = states.get(relation.getNextStateName());
        }

        if (relation.getNextStackSymbol() == State.CONST_SIGMA) {
            stack.pop();
        } else if (relation.getNextStackSymbol() != State.CONST_NULL) {
            stack.push(relation.getNextStackSymbol());
        }
    }

    private void reset() {
        currentState = initialState;
        stack.clear();
        stack.push(State.CONST_NULL);
    }
}
