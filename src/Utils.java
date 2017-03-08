/**
 * Created by Elias on 3/4/2017.
 */
public class Utils {


    /* Procedure printToken prints a token
         * and its lexeme to the listing file
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
//                //Globals.writeListing(tokenString);
//
//                fprintf(listing,
//                        "reserved word: %s\n", tokenString);
//                break;
//            case ASSIGN:
//                fprintf(listing, ":=\n");
//                break;
//            case LT:
//                fprintf(listing, "<\n");
//                break;
//            case EQ:
//                fprintf(listing, "=\n");
//                break;
//            case LPAREN:
//                fprintf(listing, "(\n");
//                break;
//            case RPAREN:
//                fprintf(listing, ")\n");
//                break;
//            case SEMI:
//                fprintf(listing, ";\n");
//                break;
//            case PLUS:
//                fprintf(listing, "+\n");
//                break;
//            case MINUS:
//                fprintf(listing, "-\n");
//                break;
//            case TIMES:
//                fprintf(listing, "*\n");
//                break;
//            case OVER:
//                fprintf(listing, "/\n");
//                break;
//            case ENDFILE:
//                fprintf(listing, "EOF\n");
//                break;
//            case NUM:
//                fprintf(listing,
//                        "NUM, val= %s\n", tokenString);
//                break;
//            case ID:
//                fprintf(listing,
//                        "ID, name= %s\n", tokenString);
//                break;
//            case ERROR:
//                fprintf(listing,
//                        "ERROR: %s\n", tokenString);
//                break;
//            default: /* should never happen */
//                fprintf(listing, "Unknown token: %d\n", token);
        }
    }


}
