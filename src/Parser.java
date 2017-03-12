import javax.swing.tree.DefaultMutableTreeNode;

public class Parser {

    /* holds current token */
    private Token token;

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    private void syntaxError(String message) {
        Listing.getInstance().write(
                "\n>>> Syntax error at line" + Globals.lineno + ": " + message
        );
        Globals.Error = true;
    }

    /* TODO: pass in token as parameter to match()? */
    private void match(Token expectedToken) {
        if (token == expectedToken) {
            token = scanner.getToken();
        } else {
            syntaxError("unexpected token -> ");

        /* TODO: how can I associate a tokenString with a Token?
         * Should I create a Token class? */
            Utils.printToken(token, "replace with tokenString");
            Listing.getInstance().write("      ");
        }
    }

    /* Corresponds to function "statement()" in "PARSE.C". */
    private DefaultMutableTreeNode createStatementNodeFromToken() {
        DefaultMutableTreeNode tree = null;
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

    /* 1. Creates a new Statement node from this.token
    *  2. Sets the sibling of this new node to be a node of type Token.SEMI
    *
    */
    public DefaultMutableTreeNode stmtSequence() {
        DefaultMutableTreeNode tree = createStatementNodeFromToken();
        DefaultMutableTreeNode p = tree;

        while ((token != Token.ENDFILE)
                && (token != Token.END)
                && (token != Token.ELSE)
                && (token != Token.UNTIL)) {

            DefaultMutableTreeNode q;
            match(Token.SEMI); /* set this.token to Token.SEMI. */
            q = createStatementNodeFromToken();

            if (q != null) {
                if (tree == null) {
                    tree = q;
                    p = q;
                }
                else /* now p cannot be NULL either */ {
                    //p->sibling = q;
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) p.getParent();
                    parent.add(q);
                    p = q;
                }
            }
        }

        return tree;
    }

}
