package LexicalAnalysis;

import SyntacticAnalysis.ParseTreeElement;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Elias on 3/15/2017.
 */
public class LeftBraceToken extends Token {
    public LeftBraceToken() {
    }

    @Override
    public Type getType() {
        return Type.LBRACE;
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
