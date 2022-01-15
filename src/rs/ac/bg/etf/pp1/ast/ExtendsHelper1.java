// generated with ast extension for cup
// version 0.8
// 15/0/2022 17:30:5


package rs.ac.bg.etf.pp1.ast;

public class ExtendsHelper1 extends ExtendsHelper {

    private String extensionTypeName;

    public ExtendsHelper1 (String extensionTypeName) {
        this.extensionTypeName=extensionTypeName;
    }

    public String getExtensionTypeName() {
        return extensionTypeName;
    }

    public void setExtensionTypeName(String extensionTypeName) {
        this.extensionTypeName=extensionTypeName;
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
        buffer.append("ExtendsHelper1(\n");

        buffer.append(" "+tab+extensionTypeName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExtendsHelper1]");
        return buffer.toString();
    }
}
