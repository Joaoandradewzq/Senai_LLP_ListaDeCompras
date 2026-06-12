

import java.util.ArrayList;
import java.util.List;

public class ListaCompras {
    private String nome;
    private List<ItemLista> itens;
    private Compra compra;

    public ListaCompras(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemLista item) {
        itens.add(item);
    }

    public String getNome() {
        return nome;
    }

    public List<ItemLista> getItens() {
        return itens;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}