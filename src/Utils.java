/**
 * Created by Elias on 3/4/2017.
 */
public class Utils {


    /* Procedure printToken prints a token
     * and its lexeme to the listing file.
     */
    static void printToken(Token token, final String tokenString) {
        switch (token) {
            case IF:
            case THEN:
            case ELSE:
            case END:
            case REPEAT:
            case UNTIL:
            case READ:
            case WRITE:
                Listing.getInstance().write(tokenString);
                break;
            case ASSIGN:
                Listing.getInstance().write(":=\n");
                break;
            case LT:
                Listing.getInstance().write("<\n");
                break;
            case EQ:
                Listing.getInstance().write("=\n");
                break;
            case LPAREN:
                Listing.getInstance().write("(\n");
                break;
            case RPAREN:
                Listing.getInstance().write(")\n");
                break;
            case SEMI:
                Listing.getInstance().write(";\n");
                break;
            case PLUS:
                Listing.getInstance().write("+\n");
                break;
            case MINUS:
                Listing.getInstance().write("-\n");
                break;
            case TIMES:
                Listing.getInstance().write("*\n");
                break;
            case OVER:
                Listing.getInstance().write("/\n");
                break;
            case ENDFILE:
                Listing.getInstance().write("EOF\n");
                break;
            case NUM:
                Listing.getInstance().write("NUM, val= %s\n" + tokenString);
                break;
            case ID:
                Listing.getInstance().write("ID, name= %s\n" + tokenString);
                break;
            case ERROR:
                Listing.getInstance().write("ERROR: %s\n" + tokenString);
                break;
            default: /* should never happen */
                Listing.getInstance().write("Unknown token: \n" + token);
        }
    }


}
