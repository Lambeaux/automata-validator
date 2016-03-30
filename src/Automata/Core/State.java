package Automata.Core;

import java.util.List;
import java.util.stream.Collectors;

import com.sun.istack.internal.NotNull;

/**
 * For this specific implementation of an automata, we will rely on a linked network of
 * {@link State} objects. These represent a node in the graph if the automata was being represented
 * visually. They contain a subset of all the possible relations that can occur within this
 * automata and are <b>only</b> the relations connected to this node. The logic to retrieve a set
 * of appropriate relations for legitimately transforming the automata lives here, as it should.
 * This keeps the logic of the actual automata, in this case a {@link Automata.PushdownAutomata},
 * as clean as possible.
 */
public class State {
    /**
     * The terminal symbol that represents sigma (lowercase greek letter 'e') in standard
     * automata notation; it represents the idea of a wildcard operator.
     */
    public static final char CONST_SIGMA = '*';

    /**
     * The terminal symbol representing NULL with regard to an automata's stack (if applicable).
     */
    public static final char CONST_NULL = '#';

    private String name;

    private boolean isAcceptingState;

    private List<Relation> localRelations;

    public State(@NotNull String name, boolean isAcceptingState,
            @NotNull List<Relation> localRelations) {
        this.name = name;
        this.isAcceptingState = isAcceptingState;
        this.localRelations = localRelations;
    }

    public String getName() {
        return name;
    }

    public boolean isAcceptingState() {
        return isAcceptingState;
    }

    /**
     * Return a list of relations that can legally be applied based on the provided configuration
     * of the automata. Note that if our automata calling this code is deterministic, the returned
     * list should <b>never</b> have more than one element within it.
     *
     * @param nextInput   the symbol currently being passed to the automata.
     * @param stackSymbol the symbol currently at the top of the stack.
     * @return a list of relations that should be used to transform the automata for
     * each non-deterministic path.
     */
    public List<Relation> getAppropriateRelations(char nextInput, char stackSymbol) {
        return localRelations.stream()
                .filter(relation -> isRelationAppropriate(relation, nextInput, stackSymbol))
                .collect(Collectors.toList());
    }

    //  Isolate the conditions that make a relation a viable option.
    //  It was easier to write the code to detect an invalid relation and simply negate it.
    private boolean isRelationAppropriate(Relation relation, char nextInput, char stackSymbol) {
        return !((nextInput != relation.getCurrentInputSymbol() && nextInput != CONST_SIGMA) || (
                stackSymbol != relation.getCurrentStackSymbol() && stackSymbol != CONST_SIGMA));
    }
}