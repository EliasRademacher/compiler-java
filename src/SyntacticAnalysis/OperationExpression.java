package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * Can be of type EQ, LT, PLUS, MINUS, TIMES, DIV;
 */
public class OperationExpression extends Expression {

    public OperationExpression(Token token) {
        super(token);
    }
    public OperationExpression(Type type) {
        super(type);
    }

    public OperationExpression(Token token, Type type) {
        super(token, type);
    }
}
