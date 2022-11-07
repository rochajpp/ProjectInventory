package br.edu.uni7.tecnicas.controller;

import br.edu.uni7.tecnicas.entities.Item;
import br.edu.uni7.tecnicas.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
public class ItemController {
    @Autowired
    private ItemRepository repository;

    @RequestMapping(value = "/itens", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addItem(@RequestBody Item item){
        if(item != null) {
            while (true) {
                Integer identificador = item.gerarNovoIdentificador();
                if (!repository.existsById(identificador)) {
                    item.setIdentificador(identificador);
                    break;
                }
            }
            item.setDataDeInclusao(LocalDate.now());
            item.setUltimaAtualizacao(LocalDate.now());
            repository.save(item);
            return new ResponseEntity(HttpStatus.CREATED);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/itens", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Item> listItens(){
        return repository.findAll();
    }
}
