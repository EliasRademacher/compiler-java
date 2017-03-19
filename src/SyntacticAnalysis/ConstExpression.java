package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * Created by Elias on 3/15/2017.
 */
public class ConstExpression extends Expression {


    public ConstExpression() {
        super();
    }

    public ConstExpression(Token token) {
        super(token);
    }

    public ConstExpression(Type type) {
        super(type);
    }

    public ConstExpression(Token token, Type type) {
        super(token, type);
    }

    @Override
    public void setToken(Token token) {
        super.setToken(token);
    }
}
