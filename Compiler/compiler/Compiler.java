package compiler;

import lexer.Lexer;

import java.util.Scanner;

public class Compiler {


    public static void main(String[] args) {
        //Ввод имени файла
        /*Scanner in = new Scanner(System.in);
        System.out.print("Имя входного файла: ");
        String sInputFile= in.nextLine();
        System.out.print("Имя результирующего файла: ");
        String sResultFile= in.nextLine();*/

        //Таблица лексем
        Lexer lexer = new Lexer("D:/User/Desktop/1.txt"); //sInputFile
        lexer.printLexema();
        System.out.println("Таблица лексем и идентификаторов построена");
        //Запустить синтаксический анализ
        lexer.runSyntax();

    }
}
