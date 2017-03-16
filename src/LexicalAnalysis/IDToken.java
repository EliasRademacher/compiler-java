package LexicalAnalysis;

import SyntacticAnalysis.Expression;
import SyntacticAnalysis.IDExpression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class IDToken extends Token {
    public IDToken() {
    }

    @Override
    public Type getType() {
        return Type.ID;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new IDExpression(this);
    }
}
