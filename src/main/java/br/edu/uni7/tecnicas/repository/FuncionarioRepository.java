package br.edu.uni7.tecnicas.repository;

import br.edu.uni7.tecnicas.entities.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
}
