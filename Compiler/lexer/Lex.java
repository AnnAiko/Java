package lexer;

public class Lex {
    //Сама лексема
    private String Lexem;
    //Тип лексемы
    private String LexType;
    //Позиция
    private int iPos;
    //Строка
    private int iStr;
    //Если тип идентификатор то переменная или константа
    private String idType;

    Lex(String lex, String type, Integer ip, Integer is) {
        this.Lexem = lex;
        this.LexType = type;
        this.iPos = ip;
        this.iStr = is;
    }
    Lex(String lex, String type, Integer ip, Integer is, String id) {
        this.Lexem = lex;
        this.LexType = type;
        this.iPos = ip;
        this.iStr = is;
        this.idType = id;
    }
    public String getLexem() {
        return Lexem;
    }

    public String getLexType() {
        return LexType;
    }

    public int getiPos() {
        return iPos;
    }

    public int getiStr() {
        return iStr;
    }
    public String getidType() {
        return idType;
    }
}
