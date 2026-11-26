package com.leozorzi.apispring.controller;

import com.leozorzi.apispring.model.Cliente;
import com.leozorzi.apispring.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository){
            this.clienteRepository = clienteRepository;
    }
    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }
    //retorna o id dele e o nome
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id){
        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(
            @PathVariable Long id,
            @RequestBody Cliente clienteAtualizado){

        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        Cliente clienteExistente = clienteRepository.findById(id).orElse(null);

        if(clienteExistente != null){
            clienteExistente.setNome(clienteAtualizado.getNome());
            Cliente clienteAtualizadoNoBanco = clienteRepository.save(clienteExistente);
            return ResponseEntity.ok(clienteAtualizadoNoBanco);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    public Cliente listarPorId(@PathVariable Long id){
            return clienteRepository.findById(id).orElse(null);
    }
}
