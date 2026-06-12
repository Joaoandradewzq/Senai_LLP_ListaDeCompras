public class ItemCompra {
    private String descricao;
    private String unidade;
    private double quantidade;
    private double precoUnitario;

    public ItemCompra(String descricao, String unidade, double quantidade, double precoUnitario) {
        this.descricao = descricao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
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
    public double getPrecoUnitario() {
        return precoUnitario;
    }
    public double getTotal() {
        return quantidade * precoUnitario;
    }
}
