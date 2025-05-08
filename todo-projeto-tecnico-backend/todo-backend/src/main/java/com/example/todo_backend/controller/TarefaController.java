package com.example.todo_backend.controller;


import com.example.todo_backend.model.Tarefa;
import com.example.todo_backend.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.criarTarefa(tarefa);
        return ResponseEntity.ok(novaTarefa);
    }

    @GetMapping
    public Page<Tarefa> ListarComFiltro(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "") String prioridade,
            @RequestParam(defaultValue = "") String situacao,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(defaultValue = "nome") String ordenarPor,
            @RequestParam(defaultValue = "DESC") String direcao
    ){
        return tarefaService.listarComFiltro(nome, prioridade, situacao, pagina, tamanho, ordenarPor, direcao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable UUID id) {
        Optional<Tarefa> tarefa = tarefaService.buscarPorId(id);
        return tarefa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> tarefaConcluida(@PathVariable UUID id) {
        Tarefa tarefa = tarefaService.concluirTarefa(id);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/pendente")
    public ResponseEntity<Tarefa> tarefaPendente(@PathVariable UUID id) {
        Tarefa tarefa = tarefaService.pendenteTarefa(id);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable UUID id) {
        boolean deleted = tarefaService.deletarTarefa(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable UUID id, @Valid @RequestBody Tarefa tarefaAtualizada) {
        Tarefa tarefa = tarefaService.atualizarTarefa(id, tarefaAtualizada);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }


}
