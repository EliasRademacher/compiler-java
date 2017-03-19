package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * Created by Elias on 3/11/2017.
 */
public class AssignmentStatement extends Statement {
    public AssignmentStatement() {
        super();
    }

    public AssignmentStatement(Token token) {
        super(token);
    }
}
