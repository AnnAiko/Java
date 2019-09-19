package lexer;

public class Key {

    String key;
    int hash;

    public Key(String key) {
        this.key = key;
    }

    /*используется для получения хэш кода*/
    @Override
    public int hashCode() {
        hash = (int) key.charAt(0);
        return hash;
    }
    //Проверка двух объектов на равество
    @Override
    public boolean equals(Object obj) {
        return key.equals(((Key) obj).key);
    }
}
