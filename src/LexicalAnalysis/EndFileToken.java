package LexicalAnalysis;

import SyntacticAnalysis.DeclarationStatement;
import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class EndFileToken extends Token {

    public EndFileToken() {
    }

    @Override
    public Type getType() {
        return Type.ENDFILE;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return null;
    }


}
