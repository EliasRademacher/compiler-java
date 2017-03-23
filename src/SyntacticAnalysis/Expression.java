package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * An expression in is a combination of one or more
 * explicit values, constants, variables, operators, and functions.
 * An expression is an internal component of a statement.
 * "2+3", "y+6", and "4!=4" are examples of expressions.
 *
 *
 * Can be of type "OpK": (EQ, LT, PLUS, MINUS, TIMES, DIV);
 * "ConstK": (INT); or
 * "IdK": (ID)
 */
public class Expression extends ParseTreeElement {

    public Expression() {
        super();
    }

    public Expression(Token token) {
        super(token);
    }

    public Expression(Type type) {
        super();
        this.type = type;
    }

    public Expression(Token token, Type type) {
        super(token);
        this.type = type;
    }

    @Override
    public void setToken(Token token) {
        super.setToken(token);
    }
}
