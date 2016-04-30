package revo.Lex

import groovy.transform.Canonical

/**
 * Created by ashraf on 4/14/2016.
 */
@Canonical
class Token {
    String lexeme
    int tag
    TokenType tokenType

    Token(String lexeme, int tag, TokenType tokenType) {
        this.lexeme = lexeme
        this.tag = tag
        this.tokenType = tokenType
    }
}
