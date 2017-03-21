package SemanticAnalysis;

/**
 * Created by marku on 3/21/2017.
 */ /* http://stackoverflow.com/questions/4777622/creating-a-list-of-pairs-in-java */
public class  Pair<L, R> {
    private L l;
    private R r;

    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    public L getL() {
        return l;
    }

    public R getR() {
        return r;
    }

    public void setL(L l) {
        this.l = l;
    }

    public void setR(R r) {
        this.r = r;
    }
}
