package syntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lexer.Key;
import lexer.Lex;
import lexer.Lexer;

public class Syntax {

    String[][] matrixPred = {
        /*program*/{"", "=", "<", "", "<", "", "", "<", "", "", "", "", "<", "", "", "", "", "", "", "", "", "", "", "<", "", ""},
        /*end.*/ {"", "", "", ">", "", ">", ">", "", ">", "", "", "", "", ">", "", "", "", ">", ">", ">", ">", "", ">", ">", ">", ">"},
        /*begin*/ {"", "", "<", "=", "<", "", "", "<", "", "", "", "", "<", "", "", "", "", "", "", "", "", "", "", "<", "", ""},
        /*end*/ {"", "", "", ">", "", ">", ">", "", ">", "", "", "", ">", ">", "", "", "", ">", ">", ">", ">", "", ">", ">", ">", ""},
        /*if*/ {"", "", "", "", "", "=", "", "", "", "<", "<", "<", "", "", "<", "<", "<", "<", "<", "<", "<", "<", "", "<", "<", ""},
        /*then*/ {"", "", "<", "", "<", "", "=", "<", "", ">", ">", ">", ">", "", "", ">", ">", ">", ">", ">", ">", "", ">", "<", ">", ""},
        /*else*/ {"", "", "<", ">", "<", ">", ">", "<", ">", "", "", "", ">", ">", "", "", "", ">", ">", ">", ">", "", ">", "<", ">", ""},
        /*while*/ {"", "", "", "", "", "", "", "", "=", "<", "<", "<", "<", "", "<", "<", "<", "<", "<", "<", "<", "<", "", "<", "<", ""},
        /*do*/ {"", "", "<", "", "<", "", "", "<", "", ">", ">", ">", ">", "", ">", ">", ">", ">", ">", ">", ">", "", ">", "<", ">", ""},
        /*and*/ {"", "", "", "", "", ">", "", "", ">", ">", ">", "<", "", "<", "<", "<", "<", "<", "<", "<", "<", "<", ">", "<", "<", ""},
        /*or*/ {"", "", "", "", "", ">", "", "", ">", ">", ">", "<", "", "<", "<", "<", "<", "<", "<", "<", "<", "<", ">", "<", "<", ""},
        /*not*/ {"", "", "", "", "", ">", "", "", ">", "", "", "<", "", "<", "<", "<", "<", "<", "<", "<", "<", "<", "", "<", "<", ""},
        /*;*/ {"", ">", "<", ">", "<", ">", ">", "<", ">", "", "", "", ">", ">", "", "", "", ">", ">", ">", ">", "", ">", "<", ">", ""},
        /*:=*/ {"", "", "", "", "", "", ">", "", "", "", "", "", ">", "", "", "", "", "<", "<", "<", "<", "<", "", "<", "<", ""},
        /*=*/ {"", "", "", "", "", ">", "", "", ">", "", "", "", "", "", "", "", "", "<", "<", "<", "<", "<", ">", "<", "<", ""},
        /*<*/ {"", "", "", "", "", ">", "", "", ">", "", "", "", "", "", "", "", "", "<", "<", "<", "<", "<", ">", "<", "<", ""},
        /*>*/ {"", "", "", "", "", ">", "", "", ">", "", "", "", "", "", "", "", "", "<", "<", "<", "<", "<", ">", "<", "<", ""},
        /*+*/ {"", "", "", "", "", "", ">", "", "", "", "", "", ">", "", "", "", "", ">", ">", ">", ">", "<", ">", "<", "<", ""},
        /*-*/ {"", "", "", "", "", "", ">", "", "", "", "", "", ">", "", "", "", "", ">", ">", ">", ">", "<", ">", "<", "<", ""},
        /*"*"*/ {"", "", "", "", "", "", ">", "", "", "", "", "", ">", "", "", "", "", ">", ">", ">", ">", "<", ">", "<", "<", ""},
        /*/*/ {"", "", "", "", "", "", ">", "", "", "", "", "", ">", "", "", "", "", ">", ">", ">", ">", "<", ">", "<", "<", ""},
        /*(*/ {"", "", "", "", "", "", "", "", "", "<", "<", "<", "", "", "<", "<", "<", "<", "<", "<", "<", "<", "=", "<", "<", ""},
        /*)*/ {"", "", "", "", "", ">", "", "", ">", ">", ">", ">", "", "", ">", ">", ">", ">", ">", ">", ">", "", ">", ">", ">", ""},
        /*a*/ {"", "", "", "", "", ">", ">", "", ">", ">", ">", ">", ">", "=", ">", ">", ">", ">", ">", ">", ">", "", ">", ">", ">", ""},
        /*c*/ {"", "", "", "", "", ">", ">", "", ">", "", "", "", ">", "", "<", "<", "<", "<", "<", "<", "<", "<", ">", "<", "<", ""},
        /*$*/ {"<", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},};
    String[] rule = {"program S end.", " S ", "S ; S", "S ;", "if S then S else S", "if S then S",
        "begin S end", "while S do S", "a := S", "S or S", "S and S", "not S",
        "S < S", "S > S", "S = S", "( S )", "S - S", "S + S", "S * S", "S / S", "a", "c"};
    List<String> stack = new LinkedList<>();
    ArrayList<Node> nodeTree = new ArrayList<>();
    ArrayList<Lex> chain = new ArrayList<>();
    public int index;
    public String elementStack = "";
    public String elementChain = "";
    public boolean error = true;
    public String numberRule = "";
    public int intStack = 0;
    public String checkRule = "";
    //Индексы для матрицы предшествования
    public int indexChainMatrix = 0;
    public int indexStackMatrix = 0;
    public String variableName = "";
    public String constName = "";
    Map<Object, String> maps = new HashMap<>();
    //Node root;

    public Syntax() {
        stack.add("$");
        index = 0;
    }

    //Получить цепочку
    public void getLexeme(Lex lex) {
        chain.add(lex);
    }

    public void syntAE() {
        int checkStack = 0;
        boolean terminalStack = true;
        while (error) {
            while (terminalStack) {
                //Проход элементов стека
                //Идем в обратную форму
                for (int i = stack.size() - 1; i >= 0; i--) {
                    //Получаем элемент из стека
                    elementStack = stack.get(i);
                    //Получить индекс в матрице предшествования
                    checkStack = codeMatrix(elementStack);
                    intStack = i;
                    //Проверка является ли элемент терминальным 
                    //Если не -1 то терминал
                    if (checkStack != -1) {
                        terminalStack = false;
                        indexStackMatrix = checkStack;
                        break;
                    }
                }
            }
            terminalStack = true;
            //Взять первый элемент в цепочке 
            elementChain = (String) chain.get(index).getLexem();
            if (elementChain.matches("\\d+")) {
                //Если встретил переменную или константу
                if (chain.get(index).getidType().equals("Переменная")) {
                    elementChain = "a";
                    //if (!chain.get(index-1).getLexem().equals("program"))
                    variableName = variableName + chain.get(index).getLexem() + " ";
                } else if (chain.get(index).getidType().equals("Константа")) {
                    elementChain = "c";
                    constName = constName + chain.get(index).getLexem() + " ";
                }
            }

            //Получить индекс тек элемента
            indexChainMatrix = codeMatrix(elementChain);

            //Проверка завершения алгоритма
            if (elementChain == "#" && elementStack == "$") {
                System.out.println("Последовательность правил: " + numberRule);
                System.out.println("Алгоритм завершен");
                //error = false;
                //buildTree(numberRule);
                return;
            }
            //Получить значение на пересечении 
            String crossingValue = matrixPred[indexStackMatrix][indexChainMatrix];

            /*System.out.println("elementChain   =" + elementChain + " " + indexChainMatrix);
            System.out.println("elementStack   =" + elementStack + " " + indexStackMatrix);
            System.out.println("crossingValue   =" + crossingValue);*/
            //Завершить алгоритм
            if (crossingValue == "") {
                /*if (chain.get(index).getLexType().equals("Ключевое слово")) {
                        System.out.println("Некорректное ключевое слово. Строка: " + chain.get(index).getiStr() + ". Позиция: " + chain.get(index).getiPos());
                } else {*/
                System.out.println("Синтаксическая ошибка. Строка: " + chain.get(index).getiStr() + ". Позиция: " + chain.get(index).getiPos());
                //}
                error = false;
                return;
            }

            //Алгоритм сдвиг
            if (crossingValue == "=" || crossingValue == "<") {
                stack.add(elementChain);
                index++;
            }
            //Алгоритм свертка
            if (crossingValue == ">") {
                convolution();
            }
            /*System.out.println("ВЫвод:");
            for (int i = 0; i < stack.size(); i++) {
                System.out.println(stack.get(i));
            }*/
        }
    }

    //Свертка
    public void convolution() {
        boolean search = true;
        String timeFind = "";
        String checkRules = "";
        String ruleSearch = "";
        if (intStack != stack.size() - 1) {
            if (codeMatrix(stack.get(intStack + 1)) == -1) {
                checkRules = " " + checkRules + stack.get(intStack + 1);
                stack.remove(intStack + 1);
            }
        }
        checkRules = elementStack + checkRules;
        stack.remove(intStack);

        if (intStack != 0) {
            if (codeMatrix(stack.get(intStack - 1)) == -1) {
                checkRules = stack.get(intStack - 1) + " " + checkRules;
                stack.remove(intStack - 1);
            }
        }
        timeFind = find(elementStack, intStack, indexStackMatrix);

        ruleSearch = timeFind + checkRules;
        //System.out.println("Правило^" + ruleSearch);
        //Искать правило
        for (int tr = 0; tr < rule.length; tr++) {

            if (rule[tr].equals(ruleSearch)) {
                search = false;
                stack.add("S");
                //Номер правила 
                numberRule = numberRule + tr + " ";
                checkRule = "";
                return;
            }
        }

        //Если не нашли правило 
        if (search = true) {
            System.out.println("Правило не найдено");
            error = false;
        }
    }
    //Искать терминалы в стеки связанные отношением =.
    public String find(String terminal, Integer indexTerminal, Integer indexMatrix) {
        boolean firstElement = true;
        int indexStackCon = 0;
        String checkTer = "";
        int indexS = 0;
        String elementStackCon = "";
        for (int i = stack.size() - 1; i >= 0; i--) {
            //Получаем элемент из стека
            elementStackCon = stack.get(i);
            indexStackCon = codeMatrix(elementStackCon);
            //Проверка является ли элемент терминальным 
            //Если не -1 то терминал
            if (indexStackCon != -1) {
                //Самый верхний терминал в стеки
                if (firstElement == true && !elementStackCon.equals(terminal)) {
                    checkTer = elementStackCon;
                    indexS = i;
                    firstElement = false;
                    break;
                }
            }
        }
        /*System.out.println("Элемент "+ elementStackCon);
        System.out.println("Значение "+matrixPred[indexStackCon][indexMatrix] + " "+ indexStackCon +" "+indexS);*/
        if (matrixPred[indexStackCon][indexMatrix].equals("=")) {
            checkRule = checkTer + " " + checkRule;
            stack.remove(indexS);
            if (codeMatrix(stack.get(indexS - 1)) == -1) {
                checkRule = stack.get(indexS - 1) + " " + checkRule; //+ " "
                stack.remove(indexS - 1);
            }
            find(checkTer, indexS, indexStackCon);
        } else {
            return "";
        }
        return checkRule;
    }

    public void build(Integer indexParent, Integer layer) {

    }

    //Построить дерево
    public void buildTree(Map<String, String> map) {
        //Индекс родителя в правиле
        int indexParent = 0;
        //Уровень вложенности
        int layer = 0;
        //root=;
        String[] variableMatrix;
        String[] constMatrix;
        int inConst = 0, inVariable = 0;
        constMatrix = constName.split("\\s");
        variableMatrix = variableName.split("\\s");
        String[] fy;
        fy = numberRule.split("\\s");
        //nodeTree.add(new Node("S", 0));
        //Искать правило
        //String[] ruleTree;
        for (int j = fy.length - 1; j >= 0; j--) {
            for (int tr = 0; tr < rule.length; tr++) {
                if (tr == Integer.parseInt(fy[j])) {
/*ruleTree = rule[tr].split("\\s");
                    for (int t = 0; t < ruleTree.length; t--) {
                        if (codeMatrix(ruleTree[t]) == -1) {
                            nodeTree.add(new Node(ruleTree[t], t));
                        } else {
                            nodeTree.add(new Node(ruleTree[t], t));
                        }
                    }
                    indexParent++;*/        
                    if (rule[tr].equals("a")) {
                        System.out.println(map.get(variableMatrix[inVariable+1]));
                        inVariable++;
                    } else if (rule[tr].equals("c")) {
                        System.out.println(map.get(constMatrix[inConst]));
                        inConst++;
                    } else {

                        System.out.println(rule[tr]);
                    }
                }
            }
        }
        /*while (true) {
            //Перебрать номера правил
            for (int j = fy.length; j > 0; j++) {
                //
                //for (int tr = 0; tr < rule.length; tr++) {
                //if (tr == j) {
                //Взять правило
                ruleTree = rule[j];
                //break;
                ft = ruleTree.split("\\s");
                for (int ui = ft.length; ui > 0; ui++) {
                    //Добавляем вершины дерева
                    Node node = new Node(ft[ui]);
                    node.value = ft[ui];
                    //Вершина нетерминал
                    if (ft[ui] == "S") {
                        //сделать вершину текущий
                        Node current = root;
                        Node prev = null;
                    }
                    System.out.println(ft[ui] + "\t");
                }
            }
        }*/
    }

    //Код для матрица операторно предшествования
    public int codeMatrix(String lex) {
        int code;
        switch (lex) {
            case "program":
                code = 0;
                break;
            case "end.":
                code = 1;
                break;
            case "begin":
                code = 2;
                break;
            case "end":
                code = 3;
                break;

            case "if":
                code = 4;//4
                break;
            case "then":
                code = 5;//5
                break;
            case "else":
                code = 6;//6
                break;
            case "while":
                code = 7;
                break;

            case "do":
                code = 8;
                break;
            case "and":
                code = 9;//9
                break;
            case "or":
                code = 10;//10
                break;
            case "not"://not
                code = 11;//11
                break;

            case ";":
                code = 12;//12
                break;
            case ":=":
                code = 13;//13
                break;
            case "=":
                code = 14;
                break;
            case "<":
                code = 15;
                break;

            case ">":
                code = 16;
                break;
            case "+":
                code = 17;
                break;
            case "-":
                code = 18;
                break;
            case "*":
                code = 19;
                break;

            case "/":
                code = 20;
                break;
            case "(":
                code = 21;//21
                break;
            case ")":
                code = 22;//22
                break;
            case "a":
                code = 23;//23
                break;

            case "c":
                code = 24;
                break;
            case "$":
                code = 25;//25
                break;
            case "#":
                code = 25;//25
                break;
            default:
                return -1;
        }
        return code;
    }
}
