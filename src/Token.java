

public enum Token {
    /* book-keeping tokens */
    ENDFILE, ERROR,
    /* reserved words */
    IF, THEN, ELSE, END, REPEAT, UNTIL, READ, WRITE,
    /* multicharacter tokens */
    ID, INT, INT_TYPE,
    /* special symbols */
    ASSIGN, EQ, LT, PLUS, MINUS, TIMES, OVER, LPAREN, RPAREN, SEMI
}
