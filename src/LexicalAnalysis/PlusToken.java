package LexicalAnalysis;

import SyntacticAnalysis.OperationExpression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class PlusToken extends Token {

    public PlusToken() {
    }

    @Override
    public Type getType() {
        return Type.PLUS;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new OperationExpression(this);
    }


}
