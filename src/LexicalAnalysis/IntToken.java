package LexicalAnalysis;

import SyntacticAnalysis.AssignmentStatement;
import SyntacticAnalysis.ConstExpression;
import SyntacticAnalysis.Expression;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class IntToken extends Token {

    public IntToken() {
    }

    @Override
    public Type getType() {
        return Type.INT;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new ConstExpression(Expression.Type.INTEGER);
    }


}
