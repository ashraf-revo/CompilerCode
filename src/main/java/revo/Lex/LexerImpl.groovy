package revo.Lex

class LexerImpl implements Lexer {
    private Map<String, Token> TokenRepository = [:]
    private List<String> AllTokens = []

    private void Load() {
        TokenRepository["Num"] = new Token("Num", Tag.Num, TokenType.Type)
        TokenRepository["Bool"] = new Token("Bool", Tag.Bool, TokenType.Type)

        TokenRepository["True"] = new Token("True", Tag.True, TokenType.Value)
        TokenRepository["False"] = new Token("False", Tag.False, TokenType.Value)

        TokenRepository["While"] = new Token("While", Tag.While, TokenType.Reserve)
        TokenRepository["If"] = new Token("If", Tag.If, TokenType.Reserve)

        TokenRepository["&&"] = new Token("&&", Tag.And, TokenType.Logic)
        TokenRepository["||"] = new Token("||", Tag.Or, TokenType.Logic)

        TokenRepository["=="] = new Token("==", Tag.Eq, TokenType.Compare)
        TokenRepository["!="] = new Token("!=", Tag.NE, TokenType.Compare)
        TokenRepository["<"] = new Token("<", Tag.L, TokenType.Compare)
        TokenRepository["<="] = new Token("<=", Tag.Le, TokenType.Compare)
        TokenRepository[">"] = new Token(">", Tag.G, TokenType.Compare)
        TokenRepository[">="] = new Token(">=", Tag.Ge, TokenType.Compare)

        TokenRepository["{"] = new Token("{", Tag.OpenBrace, TokenType.Special)
        TokenRepository["}"] = new Token("}", Tag.CloseBrace, TokenType.Special)
        TokenRepository["("] = new Token("(", Tag.OpenParenthesis, TokenType.Special)
        TokenRepository[")"] = new Token(")", Tag.CloseParenthesis, TokenType.Special)
        TokenRepository[";"] = new Token(";", Tag.Semicolon, TokenType.Special)

        TokenRepository["="] = new Token("=", Tag.Assign, TokenType.Assign)


        TokenRepository["+"] = new Token("+", Tag.Plus, TokenType.Arithmetic)
        TokenRepository["-"] = new Token("-", Tag.Minus, TokenType.Arithmetic)
        TokenRepository["*"] = new Token("*", Tag.Mul, TokenType.Arithmetic)
        TokenRepository["/"] = new Token("/", Tag.Div, TokenType.Arithmetic)

    }

    LexerImpl(String Path) {
        String TextCODE = new File(Path).text
        List split = '+~-~*~/~&~|~.~,~;~(~)~{~}~[~]~<~>~=~!~'.split("~")
        List<String> AllTokens = replace(TextCODE, split, 0).split(/(~+)/).collect { it.trim() }.findAll { it }
        List<String> ConCat = "<=~>=~!=~==~&&~||".split("~")
        for (int i = 0; i < AllTokens.size(); i++) {
            if (AllTokens[i].length() == 1 && i + 1 < AllTokens.size() && AllTokens[i + 1].length() == 1 && (AllTokens[i] + AllTokens[i + 1] in ConCat)) {
                AllTokens[i] = AllTokens[i] + AllTokens[i + 1]
                AllTokens.remove(i-- + 1)
            }
        }
        this.AllTokens = AllTokens
        Load()
    }


    private static String replace(String text, List<String> split, int index) {
        if (split.size() == index) return text.replace(" ", "~")
        else return replace(text.replace(split[index], "~${split[index]}~"), split, index + 1)
    }

    @Override
    List<Token> scan() {
        AllTokens.collect {
            Token get = TokenRepository.get(it)
            get == null ? it ==~ /[1-9]*/ ? new Token(it, -1, TokenType.Value) : new Token(it, -1, TokenType.Id) : get
        }
    }
}