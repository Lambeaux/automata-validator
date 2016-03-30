package Automata.Core;

import com.sun.istack.internal.NotNull;

/**
 * Any automata is simply composed of a list of {@link Relation} objects that store the metadata
 * necessary to compute how that automata should be transformed. It is the responsibility of a
 * {@link State} to determine how and when. This object is just a declarative rule. It is the
 * basic building block of any automata.
 */
public class Relation {
    private String currentStateName;

    private char currentInputSymbol;

    private char currentStackSymbol;

    private String nextStateName;

    private char nextStackSymbol;

    public Relation(@NotNull String currentStateName, char currentInput, char currentStackSymbol,
            @NotNull String nextStateName, char nextStackSymbol) {
        if (currentStateName == null || nextStateName == null) {
            throw new IllegalArgumentException(">> Cannot have null constructor arguments <<");
        }
        this.currentStateName = currentStateName;
        this.currentInputSymbol = currentInput;
        this.currentStackSymbol = currentStackSymbol;
        this.nextStateName = nextStateName;
        this.nextStackSymbol = nextStackSymbol;
    }

    public String getCurrentStateName() {
        return currentStateName;
    }

    public char getCurrentInputSymbol() {
        return currentInputSymbol;
    }

    public char getCurrentStackSymbol() {
        return currentStackSymbol;
    }

    public String getNextStateName() {
        return nextStateName;
    }

    public char getNextStackSymbol() {
        return nextStackSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Relation relation = (Relation) o;

        return currentInputSymbol == relation.currentInputSymbol
                && currentStackSymbol == relation.currentStackSymbol
                && nextStackSymbol == relation.nextStackSymbol
                && currentStateName.equals(relation.currentStateName) && nextStateName.equals(
                relation.nextStateName);
    }

    @Override
    public int hashCode() {
        int result = currentStateName.hashCode();
        result = 31 * result + (int) currentInputSymbol;
        result = 31 * result + (int) currentStackSymbol;
        result = 31 * result + nextStateName.hashCode();
        result = 31 * result + (int) nextStackSymbol;
        return result;
    }
}
