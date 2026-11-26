package com.leozorzi.apispring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.leozorzi.apispring.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}

