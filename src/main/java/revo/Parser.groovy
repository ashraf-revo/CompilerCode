package revo

/**
 * Created by ashraf on 4/30/2016.
 */
class Parser {
    private List<Token> scan;
    private int i = -1;

    Parser(Lexer lexer) {
        scan = lexer.scan()
    }

    private Token move() {
        scan[++i]
    }

    private Token fuck() {
        scan[i + 1]
    }

    private void is(int t, String s) {
        println(s + "          " + fuck().tag)
        if (t != move().tag) throw new Error("ERROR")
    }

    void parse() {
        stmt(new Stmt())
    }

    void stmt(Stmt stmt) {
        is(Tag.OpenBrace, "1")                    //{
        while (fuck().tag != Tag.CloseBrace) {           //}
            decl(stmt)
            stmts(stmt)
        }

        is(Tag.CloseBrace, "2")                  //}
    }

    private void decl(Stmt stmt) {
        while (fuck().tokenType == TokenType.Type) {         //Num  Bool
            move()       //Num Bool
            move()       // name
            if (fuck().tokenType == TokenType.Assign) {          //=
                move()  //=
                move()  //Expr       it should be expr()
            }
            is(Tag.Semicolon, "3")
        }
    }

    private void stmts(Stmt st) {
        while (TokenType.Reserve == fuck().tokenType) { //If While
            Token token = move()
            is(Tag.OpenParenthesis, "4")       //(
            Expr expr = expr()                              //Expr
            is(Tag.CloseParenthesis, "5")      //)
            new IW(token: token, expr: expr, stmt: stmt(st))
        }
    }
//  5 6+8*2 True False 5+5  5+5>5+5
    Expr expr() {
        new Expr()
    }

    public static void main(String[] args) {
        new Parser(new LexerImpl("C:\\Users\\ashraf\\Desktop\\CompilerCode\\src\\main\\resources\\code")).parse()
    }
}
