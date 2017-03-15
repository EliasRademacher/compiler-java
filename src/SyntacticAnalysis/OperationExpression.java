package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * Created by Elias on 3/15/2017.
 */
public class OperationExpression extends Expression {
    public OperationExpression(Type type) {
        super(type);
    }

    public OperationExpression(Token token, Type type) {
        super(token, type);
    }
}
