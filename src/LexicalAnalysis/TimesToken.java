package LexicalAnalysis;

import SyntacticAnalysis.OperationExpression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class TimesToken extends Token {

    public TimesToken() {
    }

    @Override
    public Type getType() {
        return Type.TIMES;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new OperationExpression(this);
    }


}
