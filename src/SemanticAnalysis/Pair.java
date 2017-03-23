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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (id != pair.id) return false;
        return val != null ? val.equals(pair.val) : pair.val == null;
    }
}



