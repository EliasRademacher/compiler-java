package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * A statement is the smallest standalone element of an
 * imperative programming language that expresses some action to be carried out.
 *
 * Can be thought of as everything that can make up a line
 * (or several lines) of code.
 *
 * Can be of type IF, WHILE, WRITE, or ASSIGN.
 *
 */
public class Statement extends ParseTreeElement {

    public Statement() {
        super();
    }

    public Statement(Token token) {
        super(token);
    }
}
