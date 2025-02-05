package br.com.improving.carrinho;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
public class CarrinhoCompras {

    private String identificacaoCliente;
    private Collection<Item> itens;

    public CarrinhoCompras(String identificacaoCliente, Collection<Item> itens) {
        this.identificacaoCliente = identificacaoCliente;
        this.itens = itens;
    }

    public CarrinhoCompras(String identificacaoCliente) {
        this.identificacaoCliente = identificacaoCliente;
        this.itens = new ArrayList<>();
    }

    /**
     * Permite a adição de um novo item no carrinho de compras.
     *
     * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
     * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
     * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
     * o passado como parâmetro.
     *
     * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
     *
     * @param produto
     * @param valorUnitario
     * @param quantidade
     */
    public void adicionarItem(Produto produto, BigDecimal valorUnitario, int quantidade) {

        try{

                Optional<Item> ItemExiste = Optional.ofNullable(getItens().stream()
                        .filter(Items -> Items.getProduto().equals(produto))
                        .findFirst().orElse(null));

                if (ItemExiste.isPresent()) {
                    Item item = ItemExiste.get();
                    somaQuantidadeItem(item, quantidade);
                    if (!(item.getValorUnitario()).equals(valorUnitario)) {
                        alteraValorItem(item, valorUnitario);
                    }
                } else {
                    Item novoItem = new Item(produto, valorUnitario, quantidade);
                    itens.add(novoItem);
                }

        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }

    /**
     * Permite a remoção do item que representa este produto do carrinho de compras.
     *
     * @param produto
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(Produto produto) {

        return getItens().removeIf(item -> item.getProduto().equals(produto));

    }

     /**
     * Permite a remoção do item de acordo com a posição.
     * Essa posição deve ser determinada pela ordem de inclusão do produto na 
     * coleção, em que zero representa o primeiro item.
     *
     * @param posicaoItem
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(int posicaoItem) {
        ArrayList<Item> itens = (ArrayList<Item>) getItens();
        return getItens().removeIf(item -> item.equals(itens.get(posicaoItem)));
    }

    /**
     * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
     * de todos os itens que compõem o carrinho.
     *
     * @return BigDecimal
     */
    public BigDecimal getValorTotal() {
        return BigDecimal.valueOf(getItens().stream().mapToDouble(item -> item.getValorTotal().doubleValue()).sum());
    }

    /**
     * Retorna a lista de itens do carrinho de compras.
     *
     * @return itens
     */
    public Collection<Item> getItens() {
        return itens;
    }


    public void somaQuantidadeItem(Item item, int quantidade){
        item.somaQuantidadeItem(quantidade);
    }

    public void alteraValorItem(Item item, BigDecimal valorUnitario){
        item.alteraValorItem(valorUnitario);
    }

    public String getIdentificacaoCliente(){
        return this.identificacaoCliente;
    }



}