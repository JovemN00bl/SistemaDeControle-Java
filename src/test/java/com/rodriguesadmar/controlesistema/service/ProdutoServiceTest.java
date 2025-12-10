package com.rodriguesadmar.controlesistema.service;

import com.rodriguesadmar.controlesistema.model.Produto;
import com.rodriguesadmar.controlesistema.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {


    @Mock
    private ProdutoRepository  produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve salvar um produto com sucesso")
    void deveSalvarProdutoComSucesso() {
        Produto produto = new Produto(1L, "Coca-Cola", new BigDecimal("5.00"),
                new BigDecimal("10.00"), 50, "112345678");

        Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

        Produto produtoSalvo = produtoService.save(produto);

        Assertions.assertNotNull(produtoSalvo);
        Assertions.assertEquals("Coca-Cola", produtoSalvo.getNome());
        Assertions.assertEquals(1L, produtoSalvo.getId());

        Mockito.verify(produtoRepository, Mockito.times(1)).save(produto);

    }

    @Test
    @DisplayName("Deve retornar uma lista de produtos")
    void deveRetornarListaDeProdutos() {
        Produto p1 = new Produto(1L, "Prod A",  BigDecimal.ONE, BigDecimal.TEN, 10, "111");
        Produto p2 = new Produto(2L, "Prod B",  BigDecimal.ONE, BigDecimal.TEN, 20, "222");
        List<Produto> listaMock = Arrays.asList(p1, p2);

        Mockito.when(produtoRepository.findAll()).thenReturn(listaMock);

        List<Produto> list = produtoRepository.findAll();

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("Prod A", list.get(0).getNome());

    }

    @Test
    @DisplayName("Deve excluir um produto")
    void deveExcluirProduto() {

        Long idParaExcluir = 1L;
        produtoService.deleteById(idParaExcluir);
        Mockito.verify(produtoRepository, Mockito.times(1)).deleteById(idParaExcluir);
    }

    @Test
    @DisplayName("Deve dar baixa no estoque com sucesso")
    void deveDarBaixaNoEstoqueComSucesso() {
        Produto produto = new Produto(1L, "Coca-Cola-Zero", new BigDecimal("5.00"),
                new BigDecimal("10.00"), 5, "1123456789");

        Mockito.when(produtoService.findById(1L)).thenReturn(Optional.of(produto));

        produtoService.darBaixaNoEstoque(1L, 4);

        Assertions.assertEquals(1, produto.getQuantidadeEstoque());

        Mockito.verify(produtoRepository, Mockito.times(1)).save(produto);

    }

    @Test
    @DisplayName("Deve lancar um erro quando o estoque for insuficiente")
    void deveLancarErrorQuandoEstoqueInsuficiente() {
        Produto produto = new Produto(1L, "Coca-Cola-Zero", new BigDecimal("5.00"),
                new BigDecimal("10.00"), 5, "1123456789");

        Mockito.when(produtoService.findById(1L)).thenReturn(Optional.of(produto));

        Assertions.assertThrows(RuntimeException.class, () -> {
            produtoService.darBaixaNoEstoque(1L, 10);
        });

        Mockito.verify(produtoRepository, Mockito.never()).save(Mockito.any());
    }




}
