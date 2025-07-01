package com.rodriguesadmar.controlesistema.service;

import com.rodriguesadmar.controlesistema.model.Produto;
import com.rodriguesadmar.controlesistema.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Produto> findById(Long  id){
        return produtoRepository.findById(id);
    }

    @Transactional
    public Produto save(Produto produto){
        return  produtoRepository.save(produto);
    }

    @Transactional
    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }

    @Transactional
    public Produto darBaixaNoEstoque(Long produtoId, Integer quantidade){
        Produto produto = findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new RuntimeException("Estoque insuficiente do produto: " + produto.getNome());
        }

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        return produtoRepository.save(produto);



    }


}
