// generated with ast extension for cup
// version 0.8
// 15/0/2022 17:30:5


package rs.ac.bg.etf.pp1.ast;

public class ClassNameExtends extends ClassName {

    private String className;
    private ExtendsHelper ExtendsHelper;

    public ClassNameExtends (String className, ExtendsHelper ExtendsHelper) {
        this.className=className;
        this.ExtendsHelper=ExtendsHelper;
        if(ExtendsHelper!=null) ExtendsHelper.setParent(this);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
    }

    public ExtendsHelper getExtendsHelper() {
        return ExtendsHelper;
    }

    public void setExtendsHelper(ExtendsHelper ExtendsHelper) {
        this.ExtendsHelper=ExtendsHelper;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendsHelper!=null) ExtendsHelper.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsHelper!=null) ExtendsHelper.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsHelper!=null) ExtendsHelper.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassNameExtends(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        if(ExtendsHelper!=null)
            buffer.append(ExtendsHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassNameExtends]");
        return buffer.toString();
    }
}
