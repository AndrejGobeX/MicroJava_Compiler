// generated with ast extension for cup
// version 0.8
// 20/0/2022 17:46:30


package rs.ac.bg.etf.pp1.ast;

public class CBegin implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public CBegin () {
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CBegin(\n");

        buffer.append(tab);
        buffer.append(") [CBegin]");
        return buffer.toString();
    }
}
