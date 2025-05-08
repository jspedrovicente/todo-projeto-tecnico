package com.example.todo_backend.controller;


import com.example.todo_backend.model.Tarefa;
import com.example.todo_backend.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("tarefas")
@Tag(name = "Tarefas", description = "Operações relacionadas às tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa no sistema")
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.criarTarefa(tarefa);
        return ResponseEntity.ok(novaTarefa);
    }

    @Operation(summary = "Listar tarefas com filtro", description = "Lista tarefas com base em filtros (nome, prioridade, situação) e paginação")
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

    @Operation(summary = "Buscar tarefa por ID", description = "Recupera uma tarefa pelo seu ID único")
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable UUID id) {
        Optional<Tarefa> tarefa = tarefaService.buscarPorId(id);
        return tarefa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Concluir tarefa", description = "Marca uma tarefa como concluída")
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Tarefa> tarefaConcluida(@PathVariable UUID id) {
        Tarefa tarefa = tarefaService.concluirTarefa(id);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Marcar tarefa como pendente", description = "Marca uma tarefa como pendente")
    @PatchMapping("/{id}/pendente")
    public ResponseEntity<Tarefa> tarefaPendente(@PathVariable UUID id) {
        Tarefa tarefa = tarefaService.pendenteTarefa(id);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deletar tarefa", description = "Deleta uma tarefa do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable UUID id) {
        boolean deleted = tarefaService.deletarTarefa(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente")
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable UUID id, @Valid @RequestBody Tarefa tarefaAtualizada) {
        Tarefa tarefa = tarefaService.atualizarTarefa(id, tarefaAtualizada);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }


}
