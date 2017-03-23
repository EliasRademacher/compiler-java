package SemanticAnalysis;

import LexicalAnalysis.Token;

import java.util.ArrayList;

/**
 * Created by Markus Woltjer.
 */

public class SymTable {

    private ArrayList<Pair<Token.Type, String>> table = new ArrayList<Pair<Token.Type, String>>();

    public ArrayList<Pair<Token.Type, String>> getTable() {
        return table;
    }

    public void setTable(ArrayList<Pair<Token.Type,String>> toSet) {
        table = toSet;
    }

    public void addPair(Pair<Token.Type,String> toAdd) {
        table.add(toAdd);
    }

    public boolean containsPair(Pair<Token.Type,String> query) {
        if(table.contains(query)) {
            return true;
        }
        else return false;
    }

}
