package br.edu.uni7.tecnicas.controller;

import br.edu.uni7.tecnicas.entities.Item;
import br.edu.uni7.tecnicas.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    private ItemRepository repository;

    @RequestMapping(value = "/itens", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Item> listItens(){
        return repository.findAll();
    }
}
