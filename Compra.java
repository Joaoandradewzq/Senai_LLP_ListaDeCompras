import java.util.ArrayList;
import java.util.List;
public class Compra {
    private ListaCompras lista;
    private List<ItemCompra> itensComprados;



    
    public Compra(ListaCompras lista) {
        this.lista = lista;
        this.itensComprados = new ArrayList<>();
    }
    public void adicionarItem(ItemCompra item) {
        itensComprados.add(item);
    }
    public List<ItemCompra> getItensComprados() {
        return itensComprados;
    }
    public double calcularTotal() {
        double total = 0;
        for (ItemCompra item : itensComprados) {
            total += item.getTotal();
        }
        return total;
    }

    
    public double calcularQuantidadeTotal() {
        double totalQtd = 0;
        for (ItemCompra item : itensComprados) {
            totalQtd += item.getQuantidade();
        }
        return totalQtd;
        
    }
}
