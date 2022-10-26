package br.edu.uni7.tecnicas.repository;

import br.edu.uni7.tecnicas.entities.Pessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Integer> {

}
