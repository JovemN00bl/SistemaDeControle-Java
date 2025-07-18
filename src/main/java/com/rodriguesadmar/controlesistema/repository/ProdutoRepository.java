package com.rodriguesadmar.controlesistema.repository;


import com.rodriguesadmar.controlesistema.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
