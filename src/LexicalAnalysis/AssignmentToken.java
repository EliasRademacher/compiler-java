package LexicalAnalysis;

import SyntacticAnalysis.AssignmentStatement;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class AssignmentToken extends Token {

    public AssignmentToken() {
    }

    @Override
    public Type getType() {
        return Type.ASSIGN;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new AssignmentStatement();
    }


}
