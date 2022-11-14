package br.edu.uni7.tecnicas.controller;

import br.edu.uni7.tecnicas.entities.Item;
import br.edu.uni7.tecnicas.repository.FuncionarioRepository;
import br.edu.uni7.tecnicas.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class InventoryController {
    @Autowired
    private ItemRepository Itemrepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @RequestMapping(value = "/itens", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addItem(@RequestBody Item item){
        if(item != null) {
            while (true) {
                Integer identificador = item.gerarNovoIdentificador();
                if (!Itemrepository.existsById(identificador)) {
                    item.setIdentificador(identificador);
                    break;
                }
            }
            item.setDataDeInclusao(LocalDate.now());
            item.setUltimaAtualizacao(LocalDate.now());
            Itemrepository.save(item);
            return new ResponseEntity(HttpStatus.CREATED);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/itens", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Item>>listItems(){
        List<Item> itens = (List<Item>) Itemrepository.findAll();
        return new ResponseEntity<List<Item>>(itens, HttpStatus.OK);
    }
    @RequestMapping(value = "/itens", method = RequestMethod.PUT)
    @ResponseBody
    public Item atualizarItem(@RequestBody Item item){
        Item itemAtual = Itemrepository.findById(item.getIdentificador()).get();
        item.setUltimaAtualizacao(LocalDate.now());
        BeanUtils.copyProperties(item, itemAtual);
        return Itemrepository.save(itemAtual);
    }
    @RequestMapping(value = "/itens", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteItem(@RequestBody Item item){
        Itemrepository.delete(item);
        return new ResponseEntity(HttpStatus.OK);
    }
}
