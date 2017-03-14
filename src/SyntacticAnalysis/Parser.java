package SyntacticAnalysis;

import javax.swing.tree.DefaultMutableTreeNode;
import LexicalAnalysis.*;
import Generic.*;
import LexicalAnalysis.TokenType;

public class Parser {

    /* holds current token */
    private TokenType token;

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public TokenType getToken() {
        return token;
    }

    public void setToken(TokenType token) {
        this.token = token;
    }

    private void syntaxError(String message) {
        Listing.getInstance().write(
                "\n>>> Syntax error at line" + Globals.lineno + ": " + message
        );
        Globals.Error = true;
    }

    /* TODO: pass in token as parameter to match()? */
    private void match(TokenType expectedToken) {
        if (token == expectedToken) {
            token = scanner.getToken();
        } else {
            syntaxError("unexpected token -> ");

        /* TODO: how can I associate a tokenString with a LexicalAnalysis.TokenType?
         * Should I create a LexicalAnalysis.TokenType class? */
            Utils.printToken(token, "replace with tokenString");
            Listing.getInstance().write("      ");
        }
    }

    /* Corresponds to function "statement()" in "PARSE.C". */
    private DefaultMutableTreeNode createStatementNodeFromToken() {
        DefaultMutableTreeNode tree = null;

        if (null == token) {
            token = TokenType.ERROR;
        }

        switch (token) {
            case IF:
                tree = new DefaultMutableTreeNode(new IfStatement(), true);
                break;
            case WHILE:
                tree = new DefaultMutableTreeNode(new WhileStatement(), true);
                break;
            case ID:
                tree = new DefaultMutableTreeNode(new AssignmentStatement(), true);
                break;
            case READ:
                tree = new DefaultMutableTreeNode(new ReadStatement(), true);
                break;
            case WRITE:
                tree = new DefaultMutableTreeNode(new WriteStatement(), true);
                break;
            default:
                syntaxError("unexpected token -> ");
                Utils.printToken(token, "replace with tokenString");
                token = scanner.getToken();
                break;
        }

        return tree;
    }

    /* 1. Creates a new Statement node based on this.token
    *  2. If the current node is a semicolon, its sets the sibling of the
    *       new node to be the next node after the semicolon.
    *
    */
    public DefaultMutableTreeNode stmtSequence() {
//        if (null == token) {
//            token = scanner.getToken();
//        }

        DefaultMutableTreeNode tree = createStatementNodeFromToken();
        DefaultMutableTreeNode p = tree;

        while ((token != TokenType.ENDFILE)
                && (token != TokenType.END)
                && (token != TokenType.ELSE)
                && (token != TokenType.UNTIL)) {

            DefaultMutableTreeNode q;
            match(TokenType.SEMI); /* set this.token to LexicalAnalysis.TokenType.SEMI. */
            q = createStatementNodeFromToken();

            if (q != null) {
                if (tree == null) {
                    tree = q;
                    p = q;
                }
                else /* now p cannot be NULL either */ {
                    //p->sibling = q;

                    if (p.getParent() == null) {
                        p.setParent(new DefaultMutableTreeNode());
                    }


                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) p.getParent();
                    parent.add(q);


                    p = q;
                }
            }

            System.out.println(Utils.tokenToString(token));
        }

        return tree;
    }

}
