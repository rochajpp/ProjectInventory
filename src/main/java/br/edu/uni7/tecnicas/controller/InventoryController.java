package br.edu.uni7.tecnicas.controller;

import br.edu.uni7.tecnicas.entities.Funcionario;
import br.edu.uni7.tecnicas.entities.Item;
import br.edu.uni7.tecnicas.repository.FuncionarioRepository;
import br.edu.uni7.tecnicas.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if(item.getFabricante() != "" && item.getModelo() != "" && item.getAnoDeFabricacao() != null) {
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
    public ResponseEntity atualizarItem(@RequestBody Item item){

        Item itemAtual = itemRepository.findById(item.getIdentificador()).get();

        if(item.getModelo() != itemAtual.getModelo() || item.getFabricante() != itemAtual.getFabricante() || item.getAnoDeFabricacao() != itemAtual.getAnoDeFabricacao()) {
            item.setUltimaAtualizacao(LocalDate.now());
            BeanUtils.copyProperties(item, itemAtual, "funcionario");
            itemRepository.save(itemAtual);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/itens", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteItem(@RequestBody Item item){
        item = itemRepository.findById(item.getIdentificador()).get();
        if(item.getFuncionario() != null) {
            Funcionario funcionario = funcionarioRepository.findById(Integer.parseInt(item.getFuncionario().substring(0, 3))).get();
            funcionario.rmItem(item.getIdentificador());
            funcionarioRepository.save(funcionario);
        }
        itemRepository.delete(itemRepository.findById(item.getIdentificador()).get());
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
        List<Item> itensFunc = funcionarioRepository.findById(funcionario.getMatricula()).get().getItens();
        return new ResponseEntity<List<Item>>(itensFunc, HttpStatus.OK);
    }

    @RequestMapping(value = "/funcionarios", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addFuncionario(@RequestBody Funcionario funcionario){
        if(funcionario.getNome() != "") {
            Integer matricula;

            while (true) {
                matricula = funcionario.gerarNovaMatricula();
                if (!funcionarioRepository.existsById(matricula)) {
                    break;
                }
            }
            funcionario.setMatricula(matricula);
            funcionarioRepository.save(funcionario);
            return new ResponseEntity(HttpStatus.CREATED);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/updateItemFunc", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity emprestarItem(@RequestBody Item item){

        Integer matricula = Integer.parseInt(item.getFuncionario());

        if(funcionarioRepository.existsById(matricula)) {
            Item itemAddFuncionario = itemRepository.findById(item.getIdentificador()).get();
            Funcionario funcionarioItem = funcionarioRepository.findById(matricula).get();
            Funcionario funcionario = funcionarioRepository.findById(matricula).get();


            item.setUltimaAtualizacao(LocalDate.now());

            funcionario.addItem(itemAddFuncionario);
            funcionario.setNome(funcionarioItem.getNome());
            item.setFuncionario(matricula + " - " + funcionarioItem.getNome());

            BeanUtils.copyProperties(item, itemAddFuncionario);
            BeanUtils.copyProperties(funcionario, funcionarioItem);


            itemRepository.save(itemAddFuncionario);
            funcionarioRepository.save(funcionarioItem);

            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/removeItemfuncionario", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity removerItemDeFuncionario(@RequestBody Item item){

        item = itemRepository.findById(item.getIdentificador()).get();


        Integer matricula = Integer.parseInt(itemRepository.findById(item.getIdentificador()).get().getFuncionario().substring(0, 3));

        Funcionario funcionario = funcionarioRepository.findById(matricula).get();
        funcionario.rmItem(item.getIdentificador());
        funcionarioRepository.save(funcionario);
        item.setFuncionario(null);
        itemRepository.save(item);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/funcionarios", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity atualizarFuncionario(@RequestBody Funcionario funcionario){
        Funcionario funcionarioAtual = funcionarioRepository.findById(funcionario.getMatricula()).get();
        BeanUtils.copyProperties(funcionario, funcionarioAtual, "matricula");

        funcionarioRepository.save(funcionarioAtual);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/funcionarios", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removerFuncionario(@RequestBody Funcionario funcionario){

        funcionario = funcionarioRepository.findById(funcionario.getMatricula()).get();
        if(funcionario.getItens().size() != 0) {
            for (int i = 0; i < funcionario.getItens().size(); i++) {
                Item item = funcionario.getItens().get(i);
                item.setFuncionario(null);
            }
        }
        funcionarioRepository.deleteById(funcionario.getMatricula());

        return new ResponseEntity(HttpStatus.OK);
    }

}
