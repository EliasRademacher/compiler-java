package Generic;

import LexicalAnalysis.Token;

/**
 * Created by Elias on 3/4/2017.
 */
public class Utils {

    static public String tokenToString(Token.TokenType token) {
        String stringValue = null;

        switch (token) {
            case IF:
            case THEN:
            case ELSE:
            case END:
            case WHILE:
            case UNTIL:
            case READ:
            case WRITE:
                stringValue = "WRITE";
                break;
            case INT_TYPE:
                stringValue = "int";
                break;
            case ASSIGN:
                stringValue = ":=\n";
                break;
            case LT:
                stringValue = "<\n";
                break;
            case EQ:
                stringValue = "=\n";
                break;
            case LPAREN:
                stringValue = "(\n";
                break;
            case RPAREN:
                stringValue = ")\n";
                break;
            case SEMI:
                stringValue = ";\n";
                break;
            case PLUS:
                stringValue = "+\n";
                break;
            case MINUS:
                stringValue = "-\n";
                break;
            case TIMES:
                stringValue = "*\n";
                break;
            case OVER:
                stringValue = "/\n";
                break;
            case ENDFILE:
                stringValue = "EOF\n";
                break;
            case INT:
                stringValue = "INT, val = %s\n";
                break;
            case ID:
                stringValue = "ID, name= %s\n";
                break;
            case ERROR:
                stringValue = "ERROR: %s\n";
                break;
            default: /* should never happen */
                stringValue = "Unknown token: %s\n";
        }

        return stringValue;
    }


    /* Procedure printToken prints a token
     * and its lexeme to the listing file.
     */
    static public void printToken(Token.TokenType token, final String tokenString) {
        switch (token) {
            case IF:
            case THEN:
            case ELSE:
            case END:
            case WHILE:
            case UNTIL:
            case READ:
            case WRITE:
                Listing.getInstance().write(tokenString);
                break;
            case INT_TYPE:
                Listing.getInstance().write("int");
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
            case INT:
                Listing.getInstance().write("INT, val= %s\n" + tokenString);
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
