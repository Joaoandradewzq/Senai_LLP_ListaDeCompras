

public class ItemLista {
    private String descricao;
    private String unidade;
    private double quantidade;

    public ItemLista(String descricao, String unidade, double quantidade) {
        this.descricao = descricao;
        this.unidade = unidade;
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public double getQuantidade() {
        return quantidade;
    }
}