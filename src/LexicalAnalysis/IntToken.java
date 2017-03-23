package LexicalAnalysis;

import SyntacticAnalysis.ConstExpression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class IntToken extends Token {

    public IntToken() {
    }

    public IntToken(int integerValue) {
        super(integerValue);
    }

    @Override
    public Type getType() {
        return Type.INT;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new ConstExpression(ParseTreeElement.Type.INTEGER);
    }


}
