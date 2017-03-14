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

//                        if (cAsInt < 0) {
//                            save = false;
//                            currentToken = Type.ENDFILE;
//                        }

                        switch (c) {
                            case (char) Globals.EOF:
                                save = false;
                                currentToken.setType(Token.Type.ENDFILE);
                                break;
                            case '=': /* TODO: case is unreachable. */
                                currentToken.setType(Token.Type.EQ);
                                break;
                            case '<':
                                currentToken.setType(Token.Type.LT);
                                break;
                            case '+':
                                currentToken.setType(Token.Type.PLUS);
                                break;
                            case '-':
                                currentToken.setType(Token.Type.MINUS);
                                break;
                            case '*':
                                currentToken.setType(Token.Type.TIMES);
                                break;
                            case '/':
                                currentToken.setType(Token.Type.OVER);
                                break;
                            case '(':
                                currentToken.setType(Token.Type.LPAREN);
                                break;
                            case ')':
                                currentToken.setType(Token.Type.RPAREN);
                                break;
                            case '[':
                                currentToken.setType(Token.Type.LBRACE);
                                break;
                            case ']':
                                currentToken.setType(Token.Type.RBRACE);
                                break;
                            case '{':
                                currentToken.setType(Token.Type.LBRACE_CURLY);
                                break;
                            case '}':
                                currentToken.setType(Token.Type.RBRACE_CURLY);
                                break;
                            case ';':
                                currentToken.setType(Token.Type.SEMI);
                                break;
                            default:
                                currentToken.setType(Token.Type.ERROR);
                                break;
                        }
                    }
                    break;
                case INCOMMENT:
                    save = false;
                    if (c == (char) Globals.EOF) {
                        state = State.DONE;
                        currentToken.setType(Token.Type.ENDFILE);
                    } else if (c == '}') state = State.START;
                    break;
                case INEQU:
                    state = State.DONE;
                    if (c == '=') {
                        currentToken.setType(Token.Type.EQ);
                    } else if (Character.isWhitespace(c)) {
                        currentToken.setType(Token.Type.ASSIGN);
                    } else { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        currentToken.setType(Token.Type.ERROR);
                    }
                    break;
                case INNUM:
                    if (!Character.isDigit(c)) { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        state = State.DONE;
                        currentToken.setType(Token.Type.INT);
                    }
                    break;
                case INID:
                    if (!Character.isLetter(c)) { /* backup in the input */
                        ungetNextChar();
                        save = false;
                        state = State.DONE;
                        currentToken.setType(Token.Type.ID);
                    }
                    break;
                case DONE:
                default: /* should never happen */
                    Listing.getInstance().write("LexicalAnalysis.Scanner Bug: state=" + state.name());
                    state = State.DONE;
                    currentToken.setType(Token.Type.ERROR);
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
                        currentToken.setType(reservedWords.get(tokenKey));
                        currentToken.setName("");
                    }

                    else  {
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
