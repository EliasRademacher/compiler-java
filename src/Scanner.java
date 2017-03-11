import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import java.lang.Character;


/**
 * Created by Elias on 3/4/2017.
 */
public class Scanner {

    /* States in scanner DFA */
    public enum State {
        START, INASSIGN, INCOMMENT, INNUM, INID, DONE
    }

    /* The maximum size of a token */
    public final int MAXTOKENLEN = 40;

    /* The length of the input buffer for source code lines */
    public final int BUFLEN = 256;

    /* lexeme of identifier or reserved word */
    char tokenString[] = new char[MAXTOKENLEN];

    /* holds the current line */
    private char lineBuf[] = new char[BUFLEN];

    /* current position in LineBuf */
    private int linepos = 0;

    /* current size of buffer string */
    private int bufsize = 0;

    /* corrects ungetNextChar behavior on EOF */
    private boolean EOF_flag = false;

    /* lookup table of reserved words */
    private HashMap<String, Token> reservedWords;

    public Scanner() {
        reservedWords = new HashMap<>();
        reservedWords.put("if", Token.IF);
        reservedWords.put("then", Token.THEN);
        reservedWords.put("else", Token.ELSE);
        reservedWords.put("end", Token.END);
        reservedWords.put("repeat", Token.REPEAT);
        reservedWords.put("until", Token.UNTIL);
        reservedWords.put("read", Token.READ);
        reservedWords.put("write", Token.WRITE);
    }

    /* getNextChar fetches the next non-blank character from
    lineBuf, reading in a new line if lineBuf is exhausted */
    public int getNextChar(FileReader fileReader) throws FileNotFoundException {

        if (linepos >= bufsize - 1) {
            /* All the characters in lineBuf have been read (or lineBuf has not been loaded yet),
            so load the next line in the file into lineBuf*/

            Globals.lineno++;

            try {
                    /* FileReader.read() returns the number of characters read,
                    or -1 if the end of the stream has been reached */
                if (fileReader.read(lineBuf, 0, BUFLEN - 1) > 0) {
                    if (Globals.echoSource) {
                        Listing.getInstance().write(Globals.lineno + ": " + new String(lineBuf));
                    }
                    bufsize = lineBuf.length;
                    linepos = 0;
                    return lineBuf[linepos++];
                } else {
                    EOF_flag = true;
                    return Globals.EOF;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lineBuf[linepos++];
    }

    /* ungetNextChar backtracks one character in lineBuf */
    private void ungetNextChar() {
        if (!EOF_flag) linepos--;
    }


    /* The primary function of the scanner: returns the
     * next token in source file
     */
    public Token getToken(FileReader fileReader) throws FileNotFoundException {

        /* index for storing into tokenString */
        int tokenStringIndex = 0;

        /* holds current token to be returned */
        Token currentToken = null;

        /* current state - always begins at START */
        State state = State.START;

        /* flag to indicate save to tokenString */
        boolean save;

        while (state != State.DONE) {
            char c = (char) getNextChar(fileReader);
            save = true;

            switch (state) {
                case START:
                    if (Character.isDigit(c)) {
                        state = State.INNUM;
                    } else if (Character.isLetter(c)) {
                        state = State.INID;
                    } else if (c == ':') {
                        state = State.INASSIGN;
                    } else if ((c == ' ') || (c == '\t') || (c == '\n')) {
                        save = false;
                    } else if (c == '{') {
                        save = false;
                        state = State.INCOMMENT;
                    } else {
                        state = State.DONE;
                        switch (c) {
                            case (char) Globals.EOF:
                                save = false;
                                currentToken = Token.ENDFILE;
                                break;
                            case '=':
                                currentToken = Token.EQ;
                                break;
                            case '<':
                                currentToken = Token.LT;
                                break;
                            case '+':
                                currentToken = Token.PLUS;
                                break;
                            case '-':
                                currentToken = Token.MINUS;
                                break;
                            case '*':
                                currentToken = Token.TIMES;
                                break;
                            case '/':
                                currentToken = Token.OVER;
                                break;
                            case '(':
                                currentToken = Token.LPAREN;
                                break;
                            case ')':
                                currentToken = Token.RPAREN;
                                break;
                            case ';':
                                currentToken = Token.SEMI;
                                break;
                            default:
                                currentToken = Token.ERROR;
                                break;
                        }
                    }
                    break;
                case INCOMMENT:
                    save = false;
                    if (c == (char) Globals.EOF) {
                        state = State.DONE;
                        currentToken = Token.ENDFILE;
                    } else if (c == '}') state = State.START;
                    break;
                case INASSIGN:
                    state = State.DONE;
                    if (c == '=') {
                        currentToken = Token.ASSIGN;
                    } else { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        currentToken = Token.ERROR;
                    }
                    break;
                case INNUM:
                    if (!Character.isDigit(c)) { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        state = State.DONE;
                        currentToken = Token.INT;
                    }
                    break;
                case INID:
                    if (!Character.isLetter(c)) { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        state = State.DONE;
                        currentToken = Token.ID;
                    }
                    break;
                case DONE:
                default: /* should never happen */
                    Listing.getInstance().write("Scanner Bug: state=" + state.name());
                    state = State.DONE;
                    currentToken = Token.ERROR;
                    break;
            }
            if ((save) && (tokenStringIndex <= MAXTOKENLEN)) {
                tokenString[tokenStringIndex++] = c;
            }
            if (state == State.DONE) {
                tokenString[tokenStringIndex] = '\0';
                if (currentToken == Token.ID) {
                    currentToken = reservedWords.get(new String(tokenString));
                }
            }
        }
        if (Globals.traceScan) {
            Listing.getInstance().write("\t: " + Globals.lineno);
            Utils.printToken(currentToken, new String(tokenString));
        }
        return currentToken;
    }


}
