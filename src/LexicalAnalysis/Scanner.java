package LexicalAnalysis;

import Generic.*;

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
    private final int MAXTOKENLEN = 40;

    /* The length of the input buffer for source code lines */
    private final int BUFLEN = 256;

    /* lexeme of identifier or reserved word */
    private char tokenChars[] = new char[MAXTOKENLEN];

    /* holds the current line */
    private char lineBuf[] = new char[BUFLEN];

    /* current position in LineBuf */
    private int linepos = 0;

    /* current size of buffer string */
    private int bufsize = 0;

    /* corrects ungetNextChar behavior on EOF */
    private boolean EOF_flag = false;

    /* lookup table of reserved words */
    private HashMap<String, Token.Type> reservedWords;

    public Scanner(FileReader fileReader) {

        this.fileReader = fileReader;

        reservedWords = new HashMap<>();
        reservedWords.put("if", Token.Type.IF);
        reservedWords.put("then", Token.Type.THEN);
        reservedWords.put("else", Token.Type.ELSE);
        reservedWords.put("end", Token.Type.END);
        reservedWords.put("while", Token.Type.WHILE);
        reservedWords.put("until", Token.Type.UNTIL);
        reservedWords.put("read", Token.Type.READ);
        reservedWords.put("write", Token.Type.WRITE);
        reservedWords.put("int", Token.Type.INT_TYPE);
        reservedWords.put("void", Token.Type.VOID);
    }

    /* getNextChar fetches the next non-blank character from
    lineBuf, reading in a new line if lineBuf is exhausted */
    public int getNextChar() {

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
    public Token getToken() {

        /* index for storing into tokenChars */
        int tokenCharsIndex = 0;

        /* holds current token to be returned */
        Token currentToken = new Token();

        /* current state - always begins at START */
        State state = State.START;

        /* flag to indicate save to tokenChars */
        boolean save;

        while (state != State.DONE) {
            int cAsInt = getNextChar();


            char c = (char) cAsInt;
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
                                currentToken = new EndFileToken();
                                break;
                            case '=': /* TODO: case is unreachable. */
                                currentToken = new EqToken();
                                break;
                            case '<':
                                currentToken = new LessThanToken();
                                break;
                            case '+':
                                currentToken = new PlusToken();
                                break;
                            case '-':
                                currentToken = new MinusToken();
                                break;
                            case '*':
                                currentToken = new TimesToken();
                                break;
                            case '/':
                                currentToken = new OverToken();
                                break;
                            case '(':
                                currentToken = new LeftParenToken();
                                break;
                            case ')':
                                currentToken = new RightParenToken();
                                break;
                            case '[':
                                currentToken = new LeftBraceToken();
                                break;
                            case ']':
                                currentToken = new RightBraceToken();
                                break;
                            case '{':
                                currentToken = new LeftBraceCurlyToken();
                                break;
                            case '}':
                                currentToken = new RightBraceCurlyToken();
                                break;
                            case ';':
                                currentToken = new SemicolonToken();
                                break;
                            default:
                                currentToken = new ErrorToken();
                                break;
                        }
                    }
                    break;
                case INCOMMENT:
                    save = false;
                    if (c == (char) Globals.EOF) {
                        state = State.DONE;
                        currentToken = new EndFileToken();
                    } else if (c == '}') state = State.START;
                    break;
                case INEQU:
                    state = State.DONE;
                    if (c == '=') {
                        currentToken = new EqToken();
                    } else if (Character.isWhitespace(c)) {
                        currentToken = new AssignmentToken();
                    } else { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        currentToken = new ErrorToken();
                    }
                    break;
                case INNUM:
                    if (!Character.isDigit(c)) { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        state = State.DONE;
                        currentToken = new IntToken();
                    }
                    break;
                case INID:
                    if (!Character.isLetter(c)) { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        state = State.DONE;
                        /* TODO: why does the line below cause tests to fail? */
//                        currentToken = new IDToken();

                        currentToken.setType(Token.Type.ID);
                    }
                    break;
                case DONE:
                default: /* should never happen */
                    Listing.getInstance().write("LexicalAnalysis.Scanner Bug: state=" + state.name());
                    state = State.DONE;
                    currentToken = new ErrorToken();
                    break;
            }

            if ((save) && (tokenCharsIndex <= MAXTOKENLEN)) {
                tokenChars[tokenCharsIndex++] = c;
            }

            if (state == State.DONE) {
                tokenChars[tokenCharsIndex] = '\0';

                if (currentToken.getType() == Token.Type.ID) {
                    String tokenKey = new String(tokenChars, 0, tokenCharsIndex);
                    tokenKey.substring(0, tokenCharsIndex);
                    if (reservedWords.containsKey(tokenKey)) {


                        /* TODO: Create a new method which can create a new token
                            based on a token type? */
                        Token.Type type = reservedWords.get(tokenKey);

                        if (type == Token.Type.INT_TYPE) {
                            currentToken = new IntTypeToken();
                        } else {
                            /* TODO: Create token of the desired type
                                rather than just setting the type property. */
                            currentToken.setType(type);
                        }
                    }

                    else  {
                        currentToken = new IDToken();
                        currentToken.setName(tokenKey);
                    }
                }
            }
        }

        if (Globals.traceScan) {
            Listing.getInstance().write("\t: " + Globals.lineno);
            Utils.printToken(currentToken.getType(), new String(tokenChars));
        }

        return currentToken;
    }


}
