package com.rodriguesadmar.controlesistema.repository;

import com.rodriguesadmar.controlesistema.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpfOuCnpj(String cpfOuCnpj);
    boolean existsByEmail(String email);
}
