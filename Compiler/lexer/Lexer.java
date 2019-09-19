package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import syntax.Syntax;

public class Lexer {

    String[] keyWord = {"program", "end.", "var", "byte", "const", "begin", "end", "if",
        "then", "else", "while", "do", "and", "or", "not"};
    String[] separator = {"(", ")", ";", ":", ",", ":="};
    String[] operator = {"+", "-", "*", "/", "<", ">", "="};
    static ArrayList<Lex> chainLexeme = new ArrayList<Lex>();
    Map<Object, String> map = new HashMap<>();
    Map<String, String> mapStr = new HashMap<>();
    //Путь файла
    private String filePath;
    //Текущая лексема
    private String currentLexema;
    //Номер строки лексемы
    private int numberLine = 0;

    //Конструктор 
    public Lexer(String filePath) {
        this.filePath = filePath;
        readFile();
    }

    //Читать файл
    public void readFile() {
        File fileInput = new File(filePath);
        try {
            //Чтение файла
            FileInputStream fstream = new FileInputStream(fileInput);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Если файл существует
            if (fileInput.exists()) {
                //Пока не конец строки
                while ((strLine = br.readLine()) != null) {
                    findLexem(strLine);
                    numberLine++;
                }
                br.close();
                System.out.println(String.format("%-25s %-25s", "Лексема", "Тип"));
            } else {
                System.out.println("Файла не найден");
            }
        } catch (IOException e) {
        }
    }

    //Искать значение в перечислении Token
    private void findLexem(String timeLexema) {
        String type, idType;
        Pattern pattern = Pattern.compile("(?:\\d+)|(?:[*+-/]+)|(?:[a-zA-Z][a-zA-Z0-9_.]*)|([:=]{2})|(?:[<>=]+)|(?:[();:,]+)");
        Matcher matcher = pattern.matcher(timeLexema);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            currentLexema = timeLexema.substring(start, end);
            type = currentType(currentLexema);
            if (type.equals("Идентификатор")) {
                if (currentLexema.matches("\\d+")) {
                    idType = "Константа";
                } else {
                    idType = "Переменная";
                }
                map.put(new Key(currentLexema), currentLexema);
                currentLexema = Integer.toString(new Key(currentLexema).hashCode());
                chainLexeme.add(new Lex(currentLexema, type, start, numberLine, idType));
            } else {
                chainLexeme.add(new Lex(currentLexema, type, start, numberLine));
            }
        }
    }

    //Вывести на экран лексемы
    public void printLexema() {
        for (int i = 0; i < chainLexeme.size(); i++) {
            System.out.println(String.format("%-25s | %-25s", chainLexeme.get(i).getLexem(), chainLexeme.get(i).getLexType()));
        }
    }

    //Если не ключевое слово
    public boolean idWord() {
        for (String keyWord1 : keyWord) {
            if (currentLexema.equals(keyWord1)) {
                return true;
            }
        }
        return false;
    }

    //Получить тип лексемы
    public String currentType(String nameLex) {
        String type = "";
        if (idWord()) {
            type = "Ключевое слово";
        } else {
            type = "Идентификатор";
        }
        for (String separator1 : separator) {
            if (currentLexema.equals(separator1)) {
                type = "Разделитель";
            }
        }
        for (String operator1 : operator) {
            if (currentLexema.equals(operator1)) {
                type = "Оператор";
            }
        }
        return type;
    }

    //Запустить синтаксический разбор
    public void runSyntax() {
        chainLexeme.add(new Lex("#", "Конец", -1, -1));
        //String strSearch="49";
        //System.out.println("Есть значение? " + map.get(strSearch));
        //Отправить в синтаксический анализатор
        Syntax syn = new Syntax();
        for (int i = 0; i < chainLexeme.size(); i++) {
            syn.getLexeme(chainLexeme.get(i));
        }
        syn.syntAE();

        String empt = "";
        for (Map.Entry entry : map.entrySet()) {
            empt = (String) entry.getValue();
            Key keys = new Key(empt);
            mapStr.put(Integer.toString(keys.hashCode()), empt);
        }
        if (syn.error == true) {
            syn.buildTree(mapStr);
        }
    }
}
