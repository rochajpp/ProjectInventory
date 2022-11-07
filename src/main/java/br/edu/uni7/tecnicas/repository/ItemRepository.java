package br.edu.uni7.tecnicas.repository;

import br.edu.uni7.tecnicas.entities.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
}
