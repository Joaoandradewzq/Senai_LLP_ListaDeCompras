
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<ListaCompras> listas = new ArrayList<>();
        while (true) {
            System.out.println(".-------------------.");
            System.out.println("| Gestão de compras |");
            System.out.println("'-------------------'");
            System.out.println("Selecione a opção:");
            System.out.println("1. Nova lista");
            System.out.println("2. Fazer compras");
            System.out.println("3. Relatório");
            System.out.println("0. Sair");
            System.out.print("\n>> Opção: ");
            String opcao = sc.nextLine().trim();
            if (opcao.equals("1")) {
                criarNovaLista(sc, listas);
            } else if (opcao.equals("2")) {
                fazerCompras(sc, listas);
            } else if (opcao.equals("3")) {
                relatorio(sc, listas);
            } else if (opcao.equals("0")) {
                break;
            } else {
                System.out.println("Opção inválida!");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void criarNovaLista(Scanner sc, List<ListaCompras> listas) {
        LocalDate hoje = LocalDate.now();
        String padrao = "lista_" + hoje.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.print(">> Nova lista, informe o nome [" + padrao + "]: ");
        String nome = sc.nextLine().trim();
        if (nome.isEmpty()) {
            nome = padrao;
        }
        ListaCompras lista = new ListaCompras(nome);
        while (true) {
            System.out.println(">> ---Informe o item---------");
            System.out.print(">> Descrição: ");
            String descricao = sc.nextLine().trim();
            if (descricao.isEmpty()) {
                break;
            }
            String unidade;
            while (true) {
                System.out.print(">> Unidade (UN, CX, KG, LT): ");
                unidade = sc.nextLine().trim().toUpperCase();
                if (unidade.equals("UN") || unidade.equals("CX") || unidade.equals("KG") || unidade.equals("LT")) {
                    break;
                }
            }
            System.out.print(">> Quantidade: ");
            String qtdStr = sc.nextLine().trim();
            double quantidade = 0;
            try {
                quantidade = Double.parseDouble(qtdStr.replace(",", "."));
            } catch (Exception e) {
                quantidade = 0;
            }
            lista.adicionarItem(new ItemLista(descricao, unidade, quantidade));
        }
        listas.add(lista);
        System.out.println(">> ---Lista salva!---------");
    }

    private static void fazerCompras(Scanner sc, List<ListaCompras> listas) {
        if (listas.isEmpty()) {
            System.out.println("Nenhuma lista cadastrada.");
            return;
        }
        ListaCompras lista = escolherLista(sc, listas);
        if (lista == null) {
            return;
        }
        System.out.println(">> ---Fazer compras [" + lista.getNome() + "]---");
        Compra compra = new Compra(lista);
        List<ItemLista> itens = lista.getItens();
        int totalItens = itens.size();
        for (int i = 0; i < totalItens; i++) {
            ItemLista il = itens.get(i);
            System.out.println(">> (" + (i + 1) + "/" + totalItens + ") Produto " + il.getDescricao() + " " + il.getQuantidade() + " " + il.getUnidade());
            System.out.print(">> Quantidade [" + il.getQuantidade() + " " + il.getUnidade() + "]: ");
            String qtdStr = sc.nextLine().trim();
            double quantidade = il.getQuantidade();
            if (!qtdStr.isEmpty()) {
                try {
                    quantidade = Double.parseDouble(qtdStr.replace(",", "."));
                } catch (Exception e) {
                    quantidade = il.getQuantidade();
                }
            }
            System.out.print(">> Preço: ");
            String precoStr = sc.nextLine().trim();
            double preco = 0;
            if (!precoStr.isEmpty()) {
                try {
                    preco = Double.parseDouble(precoStr.replace(",", "."));
                } catch (Exception e) {
                    preco = 0;
                }
            }
            if (preco > 0) {
                ItemCompra ic = new ItemCompra(il.getDescricao(), il.getUnidade(), quantidade, preco);
                compra.adicionarItem(ic);
            }
        }
        lista.setCompra(compra);
        double total = compra.calcularTotal();
        System.out.println(">> ---Total------------------");
        System.out.printf(">> R$: %.2f%n", total);
    }

    private static void relatorio(Scanner sc, List<ListaCompras> listas) {
        if (listas.isEmpty()) {
            System.out.println("Nenhuma lista cadastrada.");
            return;
        }
        ListaCompras lista = escolherLista(sc, listas);
        if (lista == null) {
            return;
        }
        Compra compra = lista.getCompra();
        if (compra == null || compra.getItensComprados().isEmpty()) {
            System.out.println("Nenhuma compra registrada para esta lista.");
            return;
        }
        System.out.println(">> ---Relatório [" + lista.getNome() + "]---");
        System.out.println(">> Item, Descrição, Qtd, UN, Preço, Total");
        List<ItemCompra> itens = compra.getItensComprados();
        int idx = 1;
        double somaQtd = 0;
        double somaTotal = 0;
        for (ItemCompra ic : itens) {
            double totalItem = ic.getTotal();
            somaQtd += ic.getQuantidade();
            somaTotal += totalItem;
            System.out.printf(">> %d, %s, %.2f, %s, %.2f, %.2f%n", idx, ic.getDescricao(), ic.getQuantidade(), ic.getUnidade(), ic.getPrecoUnitario(), totalItem);
            idx++;
        }
        System.out.printf(">> 0, TOTAL, %.2f, UN, %.2f%n", somaQtd, somaTotal);
    }

    private static ListaCompras escolherLista(Scanner sc, List<ListaCompras> listas) {
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).getNome());
        }
        System.out.print("Selecione a lista: ");
        String input = sc.nextLine().trim();
        int indice;
        try {
            indice = Integer.parseInt(input) - 1;
        } catch (Exception e) {
            return null;
        }
        if (indice < 0 || indice >= listas.size()) {
            return null;
        }
        return listas.get(indice);
    }
}