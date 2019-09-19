/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

/*public class Node {

    //Значение 
    public String value;
    //Индекс родителя в правиле
    public int indexParent;
    //Уровень вложенности
    public int layer;
    //потомок
    public Node[] child;

    //Конструктор
    public Node(String value, Integer indexParent) {
        this.value = value;
        this.indexParent = indexParent;
    }

    public int getIndexParent() {
        return indexParent;
    }

}*/
public class Node {

    private String id;
    private String parent;
    private String text;
    private boolean children;

    public Node(String id, String parent, String text) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.children = true;
    }
}
