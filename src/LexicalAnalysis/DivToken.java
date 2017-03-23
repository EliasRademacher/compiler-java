package LexicalAnalysis;

import SyntacticAnalysis.OperationExpression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class DivToken extends Token {

    public DivToken() {
    }

    @Override
    public Type getType() {
        return Type.DIV;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new OperationExpression(this);
    }


}
