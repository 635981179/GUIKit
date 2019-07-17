package domain;

public class EnterpriseScc {
    private String name;
    private String scc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScc() {
        return scc;
    }

    public void setScc(String scc) {
        this.scc = scc;
    }

    public EnterpriseScc(String name, String scc){
        this.name = name;
        this.scc = scc;
    }
}
