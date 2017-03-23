package LexicalAnalysis;

import SyntacticAnalysis.DeclarationStatement;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class IntTypeToken extends Token {

    public IntTypeToken() {
    }

    @Override
    public Type getType() {
        return Type.TYPE_SPECIFIER;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return new DeclarationStatement(this);
    }


}
