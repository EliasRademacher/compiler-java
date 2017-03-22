package SemanticAnalysis;

import LexicalAnalysis.Token;

/**
 * Created by Markus Woltjer.
 */ /* http://stackoverflow.com/questions/4777622/creating-a-list-of-pairs-in-java */

public class  Pair<id, val> {//int symbol tree entry

    public Token.Type id;

    public String val;



    public Pair(Token.Type id, String val) {
        this.id = id;
        this.val = val;
    }

    public Token.Type getID() {
        return id;
    }

    public String getR() {
        return val;
    }

    public void setID(Token.Type id) {
        this.id = id;
    }

    public void setR(String val) {
        this.val = val;
    }
}
