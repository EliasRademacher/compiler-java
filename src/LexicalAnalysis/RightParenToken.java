package LexicalAnalysis;

import SyntacticAnalysis.ParseTreeElement;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Elias on 3/15/2017.
 */
public class RightParenToken extends Token {
    public RightParenToken() {
    }

    @Override
    public Type getType() {
        return Type.RPAREN;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return null;
    }

    @Override
    public DefaultMutableTreeNode toTreeNode() {
        return null;
    }
}
