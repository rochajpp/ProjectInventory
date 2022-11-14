package br.edu.uni7.tecnicas.controller;

import br.edu.uni7.tecnicas.entities.Item;
import br.edu.uni7.tecnicas.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hsqldb.Tokens.T;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    public ResponseEntity<List<Item>>listItems(){
        List<Item> itens = (List<Item>) repository.findAll();
        return new ResponseEntity<List<Item>>(itens, HttpStatus.OK);
    }
    @RequestMapping(value = "/itens", method = RequestMethod.PUT)
    @ResponseBody
    public Item atualizarItem(@RequestBody Item item){
        Item itemAtual = repository.findById(item.getIdentificador()).get();
        item.setUltimaAtualizacao(LocalDate.now());
        BeanUtils.copyProperties(item, itemAtual);
        return repository.save(itemAtual);
    }
    @RequestMapping(value = "/itens", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteItem(@RequestBody Item item){
        repository.delete(item);
        return new ResponseEntity(HttpStatus.OK);
    }
}
