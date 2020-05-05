package com.server.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.agenda.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
