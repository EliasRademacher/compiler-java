package LexicalAnalysis;

import SyntacticAnalysis.OperationExpression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class LessThanToken extends Token {

    public LessThanToken() {
    }

    @Override
    public Type getType() {
        return Type.LT;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new OperationExpression(this);
    }


}
