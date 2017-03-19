package LexicalAnalysis;

import SyntacticAnalysis.DeclarationStatement;
import SyntacticAnalysis.OperationExpression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class EqToken extends Token {

    public EqToken() {
    }

    @Override
    public Type getType() {
        return Type.EQ;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new OperationExpression(this);
    }


}
