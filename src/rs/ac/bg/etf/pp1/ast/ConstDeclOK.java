// generated with ast extension for cup
// version 0.8
// 19/0/2022 11:25:4


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclOK extends ConstDecl {

    private Type Type;
    private ConstAss ConstAss;
    private ConstAssList ConstAssList;

    public ConstDeclOK (Type Type, ConstAss ConstAss, ConstAssList ConstAssList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstAss=ConstAss;
        if(ConstAss!=null) ConstAss.setParent(this);
        this.ConstAssList=ConstAssList;
        if(ConstAssList!=null) ConstAssList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstAss getConstAss() {
        return ConstAss;
    }

    public void setConstAss(ConstAss ConstAss) {
        this.ConstAss=ConstAss;
    }

    public ConstAssList getConstAssList() {
        return ConstAssList;
    }

    public void setConstAssList(ConstAssList ConstAssList) {
        this.ConstAssList=ConstAssList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstAss!=null) ConstAss.accept(visitor);
        if(ConstAssList!=null) ConstAssList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstAss!=null) ConstAss.traverseTopDown(visitor);
        if(ConstAssList!=null) ConstAssList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstAss!=null) ConstAss.traverseBottomUp(visitor);
        if(ConstAssList!=null) ConstAssList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclOK(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAss!=null)
            buffer.append(ConstAss.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssList!=null)
            buffer.append(ConstAssList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclOK]");
        return buffer.toString();
    }
}
