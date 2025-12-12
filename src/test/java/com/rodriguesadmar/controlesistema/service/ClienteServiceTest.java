package com.rodriguesadmar.controlesistema.service;

import com.rodriguesadmar.controlesistema.model.Cliente;
import com.rodriguesadmar.controlesistema.repository.ClienteRepository;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository  clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve salvar o cliente com sucesso.")
    void deveSalvarClienteComSucesso() {
        Cliente cliente = CriarClienteTest();

        Mockito.when(clienteRepository.existsByCpfOuCnpj(cliente.getCpfOuCnpj())).thenReturn(false);
        Mockito.when(clienteRepository.existsByEmail(cliente.getEmail())).thenReturn(false);
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente salvo = clienteService.save(cliente);

        Assertions.assertNotNull(salvo);
        Mockito.verify(clienteRepository, Mockito.times(1)).save(cliente);
    }

    @Test
    @DisplayName("Não deve salvar cliente com cpf duplicado.")
    void naoDeveSalvarClienteComCpfDuplicado() {

        Cliente cliente = CriarClienteTest();

        Mockito.when(clienteRepository.existsByCpfOuCnpj(cliente.getCpfOuCnpj())).thenReturn(true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clienteService.save(cliente);
        });

        Assertions.assertEquals("CPF/CNPJ já cadastrado no sistema.", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(cliente);


    }




    private Cliente CriarClienteTest() {
        return new Cliente(null, "Iara", "11122233344", "Iaraa@email.com", "99999999",
                "20000000", "Rua A", "10", "Centro", "Cidade", "SP");

    }

}
