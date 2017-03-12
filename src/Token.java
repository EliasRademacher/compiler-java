

public enum Token {
    /* book-keeping tokens */
    ENDFILE, ERROR,
    /* reserved words */
    IF, THEN, ELSE, END, WHILE, UNTIL, READ, WRITE,
    INT_TYPE, VOID,
    /* multicharacter tokens */
    ID, INT,
    /* special symbols */
    ASSIGN, EQ, LT, PLUS, MINUS, TIMES, OVER, LPAREN,
    RPAREN, LBRACE, RBRACE, LBRACE_CURLY, RBRACE_CURLY, SEMI
}
