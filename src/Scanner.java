import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;



/**
 * Created by Elias on 3/4/2017.
 */
public class Scanner {

    /* States in scanner DFA */
    public enum StateType {
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
    private HashMap<String, TokenType> reservedWords;

    public Scanner() {
        reservedWords = new HashMap<>();
        reservedWords.put("if", TokenType.IF);
        reservedWords.put("then", TokenType.THEN);
        reservedWords.put("else", TokenType.ELSE);
        reservedWords.put("end", TokenType.END);
        reservedWords.put("repeat", TokenType.REPEAT);
        reservedWords.put("until", TokenType.UNTIL);
        reservedWords.put("read", TokenType.READ);
        reservedWords.put("write", TokenType.WRITE);
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
                    if (Globals.EchoSource) {
                        /* TODO: print the output to a file "listing". */
                        System.out.println(Globals.lineno + ": " + new String(lineBuf));
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




}
