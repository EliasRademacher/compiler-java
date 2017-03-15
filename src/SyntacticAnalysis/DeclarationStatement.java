package SyntacticAnalysis;

import LexicalAnalysis.Token;


/**
 * A parse tree element for a statement that makes
 * a declaration, such as "int x;".
 */
public class DeclarationStatement extends Statement {

    public DeclarationStatement() {
        super();
    }

    public DeclarationStatement(Token token) {
        super(token);
    }
}
