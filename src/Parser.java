import javax.swing.tree.DefaultMutableTreeNode;

public class Parser {

    /* holds current token */
    private Token token;

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
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

    private DefaultMutableTreeNode statement() {
//        TreeNode * t = NULL;
//        switch (token) {
//            case IF : t = if_stmt(); break;
//            case REPEAT : t = repeat_stmt(); break;
//            case ID : t = assign_stmt(); break;
//            case READ : t = read_stmt(); break;
//            case WRITE : t = write_stmt(); break;
//            default : syntaxError("unexpected token -> ");
//                printToken(token,tokenString);
//                token = getToken();
//                break;
//        } /* end case */
        return new DefaultMutableTreeNode(new ParseTreeElement(), true);//t;
    }


    private DefaultMutableTreeNode stmtSequence() {
        DefaultMutableTreeNode tree = statement();
        DefaultMutableTreeNode p = (DefaultMutableTreeNode) tree.clone();

        while ((token != Token.ENDFILE)
                && (token != Token.END)
                && (token != Token.ELSE)
                && (token != Token.UNTIL)) {

            DefaultMutableTreeNode q;
            match(Token.SEMI); /* set this.token to Token.SEMI. */
            q = statement();

            if (q != null) {
                if (tree == null) {
                    tree = q;
                    p = q;
                }
                else /* now p cannot be NULL either */ {
                    //p->sibling = q;
                    p = q;
                }
            }
        }

        return tree;
    }

}
