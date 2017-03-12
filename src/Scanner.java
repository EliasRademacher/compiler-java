
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import java.lang.Character;


public class Scanner {

    private FileReader fileReader;

    /* States in scanner DFA */
    public enum State {
        START, INEQU, INCOMMENT, INNUM, INID, DONE
    }

    /* The maximum size of a token */
    public final int MAXTOKENLEN = 40;

    /* The length of the input buffer for source code lines */
    public final int BUFLEN = 256;

    /* lexeme of identifier or reserved word */
    char tokenChars[] = new char[MAXTOKENLEN];

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

    public Scanner(FileReader fileReader) {

        this.fileReader = fileReader;

        reservedWords = new HashMap<>();
        reservedWords.put("if", Token.IF);
        reservedWords.put("then", Token.THEN);
        reservedWords.put("else", Token.ELSE);
        reservedWords.put("end", Token.END);
        reservedWords.put("while", Token.WHILE);
        reservedWords.put("until", Token.UNTIL);
        reservedWords.put("read", Token.READ);
        reservedWords.put("write", Token.WRITE);
        reservedWords.put("int", Token.INT_TYPE);
        reservedWords.put("void", Token.VOID);
    }

    /* getNextChar fetches the next non-blank character from
    lineBuf, reading in a new line if lineBuf is exhausted */
    public int getNextChar() throws FileNotFoundException {

        if (linepos >= bufsize - 1) {
            /* All the characters in lineBuf have been read (or lineBuf has not been loaded yet),
            so load the next line in the file into lineBuf*/

            Globals.lineno++;

            try {
                    /* FileReader.read() returns the number of characters read,
                    or -1 if the end of the stream has been reached */
                if (this.fileReader.read(lineBuf, 0, BUFLEN - 1) > 0) {
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
    public Token getToken() throws FileNotFoundException {

        /* index for storing into tokenChars */
        int tokenCharsIndex = 0;

        /* holds current token to be returned */
        Token currentToken = null;

        /* current state - always begins at START */
        State state = State.START;

        /* flag to indicate save to tokenChars */
        boolean save;

        while (state != State.DONE) {
            char c = (char) getNextChar();
            save = true;

            switch (state) {
                case START:
                    if (Character.isDigit(c)) {
                        state = State.INNUM;
                    } else if (Character.isLetter(c)) {
                        state = State.INID;
                    } else if (c == '=') { /* TODO: have "==" be the equals operator. */
                        state = State.INEQU;
                    } else if ((c == ' ') || (c == '\t') || (c == '\n') || (c == '\r')) {
                        save = false;
                    } else if (c == '#') {
                        save = false;
                        state = State.INCOMMENT;
                    } else {
                        state = State.DONE;
                        switch (c) {
                            case (char) Globals.EOF:
                                save = false;
                                currentToken = Token.ENDFILE;
                                break;
                            case '=': /* TODO: case is unreachable. */
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
                            case '[':
                                currentToken = Token.LBRACE;
                                break;
                            case ']':
                                currentToken = Token.RBRACE;
                                break;
                            case '{':
                                currentToken = Token.LBRACE_CURLY;
                                break;
                            case '}':
                                currentToken = Token.RBRACE_CURLY;
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
                case INEQU:
                    state = State.DONE;
                    if (c == '=') {
                        currentToken = Token.EQ;
                    }

                    else if (Character.isWhitespace(c)) {
                        currentToken = Token.ASSIGN;
                    }

                    else { /* backup in the input */
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

            if ((save) && (tokenCharsIndex <= MAXTOKENLEN)) {
                tokenChars[tokenCharsIndex++] = c;
            }

            if (state == State.DONE) {
                tokenChars[tokenCharsIndex] = '\0';

                if (currentToken == Token.ID) {
                    String tokenKey = new String(tokenChars, 0, tokenCharsIndex);
                    tokenKey.substring(0, tokenCharsIndex);
                    if (reservedWords.containsKey(tokenKey)) {
                        currentToken = reservedWords.get(tokenKey);
                    }
                }
            }
        }

        if (Globals.traceScan) {
            Listing.getInstance().write("\t: " + Globals.lineno);
            Utils.printToken(currentToken, new String(tokenChars));
        }

        return currentToken;
    }


}
