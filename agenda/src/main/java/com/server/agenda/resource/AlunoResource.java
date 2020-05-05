package com.server.agenda.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.agenda.event.RecursoCriadoEvent;
import com.server.agenda.model.Aluno;
import com.server.agenda.repository.AlunoRepository;
import com.server.agenda.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoResource {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private AlunoService alunoService;

	@GetMapping
	public List<Aluno> listar() {
		return alunoRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Aluno> criar(@Valid @RequestBody Aluno aluno, HttpServletResponse response) {
		Aluno alunoSalva = alunoRepository.save(aluno);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, alunoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Aluno> buscarPeloCodigo(@PathVariable Long codigo) {
	    Optional<Aluno> aluno = this.alunoRepository.findById(codigo);
	    return aluno.isPresent() ? 
	            ResponseEntity.ok(aluno.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
	    Aluno aluno = new Aluno();
	    aluno.setCodigo(codigo);
	    this.alunoRepository.delete(aluno);
	}
	@PutMapping("/{codigo}")
	public ResponseEntity<Aluno> atualizar(@PathVariable Long codigo, @Valid @RequestBody Aluno aluno) {
		Aluno alunoSalva = alunoService.atualizar(codigo, aluno);
		return ResponseEntity.ok(alunoSalva);
	}

}
