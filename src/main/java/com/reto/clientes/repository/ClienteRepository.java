package com.reto.clientes.repository;

import com.reto.clientes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;



public interface ClienteRepository extends JpaRepository<Cliente, UUID>{

}
