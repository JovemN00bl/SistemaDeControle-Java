package com.rodriguesadmar.controlesistema.repository;


import com.rodriguesadmar.controlesistema.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    long countByQuantidadeEstoqueLessThan(Integer quantidadeMinima) ;
    List<Produto> findByQuantidadeEstoqueLessThan(Integer quantidadeMinima);


    long count();
}
