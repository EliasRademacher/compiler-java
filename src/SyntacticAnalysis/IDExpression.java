package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * Created by Elias on 3/15/2017.
 */
public class IDExpression extends Expression {

    public IDExpression() {
        super();
    }

    public IDExpression(Token token) {
        super(token);
    }

    public IDExpression(Type type) {
        super(type);
    }

    public IDExpression(Token token, Type type) {
        super(token, type);
    }
}
