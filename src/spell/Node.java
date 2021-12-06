package spell;

public class Node implements INode{
    int value;
    INode [] children;


    public Node() {
        this.value = 0;
        this.children = new INode[27];
    }
    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void incrementValue() {
        value++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
