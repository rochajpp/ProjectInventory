package br.edu.uni7.tecnicas.controller;

import br.edu.uni7.tecnicas.entities.Funcionario;
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

@RestController
public class InventoryController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @RequestMapping(value = "/itens", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addItem(@RequestBody Item item){
        if(item != null) {
            while (true) {
                Integer identificador = item.gerarNovoIdentificador();
                if (!itemRepository.existsById(identificador)) {
                    item.setIdentificador(identificador);
                    break;
                }
            }
            item.setDataDeInclusao(LocalDate.now());
            item.setUltimaAtualizacao(LocalDate.now());
            itemRepository.save(item);
            return new ResponseEntity(HttpStatus.CREATED);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/itens", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Item>>listItems(){
        List<Item> itens = (List<Item>) itemRepository.findAll();
        return new ResponseEntity<List<Item>>(itens, HttpStatus.OK);
    }
    @RequestMapping(value = "/itens", method = RequestMethod.PUT)
    @ResponseBody
    public Item atualizarItem(@RequestBody Item item){
        Item itemAtual = itemRepository.findById(item.getIdentificador()).get();
        item.setUltimaAtualizacao(LocalDate.now());
        BeanUtils.copyProperties(item, itemAtual);
        return itemRepository.save(itemAtual);
    }
    @RequestMapping(value = "/itens", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteItem(@RequestBody Item item){
        itemRepository.delete(item);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/funcionarios", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Funcionario>> listFuncionarios(){
        List<Funcionario> funcionarios = (List<Funcionario>) funcionarioRepository.findAll();
        return new ResponseEntity<List<Funcionario>>(funcionarios, HttpStatus.OK);
    }

    @RequestMapping(value = "/listItens", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Item>> listItensFuncionarios(@RequestBody Funcionario funcionario){
        List<Item> itensFunc = funcionario.getItens();
        return new ResponseEntity<List<Item>>(itensFunc, HttpStatus.OK);
    }

    @RequestMapping(value = "/funcionarios", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addFuncionario(@RequestBody Funcionario funcionario){

        Integer matricula;

        while(true) {
            matricula = funcionario.gerarNovaMatricula();
            if(!funcionarioRepository.existsById(matricula)){
               break;
            }
        }
        funcionario.setMatricula(matricula);
        funcionarioRepository.save(funcionario);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/updateItemFunc", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity emprestarItem(@RequestBody Item item){
        if(funcionarioRepository.existsById(item.getFuncionario().getMatricula())) {
            Item itemAddFuncionario = itemRepository.findById(item.getIdentificador()).get();
            Funcionario funcionarioItem = funcionarioRepository.findById(item.getFuncionario().getMatricula()).get();
            Funcionario funcionario = item.getFuncionario();


            item.setUltimaAtualizacao(LocalDate.now());

            funcionario.addItem(itemAddFuncionario);
            funcionario.setNome(funcionarioItem.getNome());


            BeanUtils.copyProperties(item, itemAddFuncionario);
            BeanUtils.copyProperties(funcionario, funcionarioItem);


            itemRepository.save(itemAddFuncionario);
            funcionarioRepository.save(funcionarioItem);







            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
