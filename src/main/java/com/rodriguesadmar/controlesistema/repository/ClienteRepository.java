package com.rodriguesadmar.controlesistema.repository;

import com.rodriguesadmar.controlesistema.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpfOuCnpj(String cpfOuCnpj);
    boolean existsByEmail(String email);

    Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);
}
