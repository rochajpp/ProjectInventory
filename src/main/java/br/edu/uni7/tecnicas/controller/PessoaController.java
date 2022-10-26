package br.edu.uni7.tecnicas.controller;

import br.edu.uni7.tecnicas.entities.Pessoa;
import br.edu.uni7.tecnicas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @RequestMapping(value = "/pessoa", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Pessoa> listarPessoas() {
        return repository.findAll();
    }

    @RequestMapping(value = "/pessoa", method = RequestMethod.POST)
    @ResponseBody
    public void cadastrarPessoa(@RequestBody Pessoa pessoa) {
        repository.save(pessoa);
    }

}
