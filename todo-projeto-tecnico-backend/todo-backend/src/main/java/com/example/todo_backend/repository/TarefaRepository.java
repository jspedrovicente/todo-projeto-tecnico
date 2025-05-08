package com.example.todo_backend.repository;

import com.example.todo_backend.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TarefaRepository extends JpaRepository<Tarefa, UUID>, JpaSpecificationExecutor<Tarefa> {
}
