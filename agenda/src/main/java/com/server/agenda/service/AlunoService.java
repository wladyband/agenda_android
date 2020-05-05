package com.server.agenda.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.server.agenda.model.Aluno;
import com.server.agenda.repository.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;

	public Aluno atualizar(Long codigo, Aluno pessoa) {

		  Aluno alunoSalva = this.alunoRepository.findById(codigo)
		      .orElseThrow(() -> new EmptyResultDataAccessException(1));

		  BeanUtils.copyProperties(pessoa, alunoSalva, "codigo");

		  return this.alunoRepository.save(alunoSalva);
		}

}
