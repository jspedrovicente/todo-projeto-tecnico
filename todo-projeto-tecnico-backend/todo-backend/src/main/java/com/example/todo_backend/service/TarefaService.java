package com.example.todo_backend.service;

import com.example.todo_backend.enums.SituacaoEnum;
import com.example.todo_backend.model.Tarefa;
import com.example.todo_backend.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }


    @Transactional
    public Tarefa criarTarefa(Tarefa tarefa) {
        tarefa.setId(null);
        tarefa.setSituacao(SituacaoEnum.ABERTA);
        return tarefaRepository.save(tarefa);
    }

    public Optional<Tarefa> buscarPorId(UUID id) {
        return tarefaRepository.findById(id);
    }

    public Page<Tarefa> listarComFiltro(String nome, String prioridade, String situacao, int pagina, int tamanho, String ordenarPor, String direcao) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.Direction.fromString(direcao), ordenarPor);
        Specification<Tarefa> spec = Specification.where(null);

        System.out.println("Filtering by:");
        System.out.println("Nome: " + nome);
        System.out.println("Prioridade: " + prioridade);
        System.out.println("Situacao: " + situacao);

        if (nome != null && !nome.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%")
            );
        }

        if (prioridade != null && !prioridade.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("prioridade").as(String.class)), prioridade.toLowerCase())
            );
        }

        if (situacao != null && !situacao.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("situacao").as(String.class)), situacao.toLowerCase())
            );
        }

        return tarefaRepository.findAll(spec, pageable);
    }

    @Transactional
    public Tarefa concluirTarefa(UUID id){
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()){
            Tarefa tarefaAtualizada = tarefa.get();
            tarefaAtualizada.setSituacao(SituacaoEnum.CONCLUIDA);
            return tarefaRepository.save(tarefaAtualizada);
        }
        return null;
    }

    @Transactional
    public Tarefa pendenteTarefa(UUID id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            Tarefa tarefaAtualizada = tarefa.get();
            tarefaAtualizada.setSituacao(SituacaoEnum.PENDENTE);
            return tarefaRepository.save(tarefaAtualizada);
        }
        return null;
    }

    @Transactional
    public boolean deletarTarefa(UUID id) {
        if (!tarefaRepository.existsById(id)) {
            return false;
        }
        tarefaRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Tarefa atualizarTarefa(UUID id, Tarefa tarefaAtualizada) {
        Optional<Tarefa> existente = tarefaRepository.findById(id);
        if (existente.isEmpty()) {
            return null; // Not found, can throw exception if needed
        }

        Tarefa tarefa = existente.get();
        tarefa.setNome(tarefaAtualizada.getNome());
        tarefa.setDescricao(tarefaAtualizada.getDescricao());
        tarefa.setPrioridade(tarefaAtualizada.getPrioridade());
        tarefa.setDataPrevistaConclusao(tarefaAtualizada.getDataPrevistaConclusao());

        return tarefaRepository.save(tarefa);
    }
}
