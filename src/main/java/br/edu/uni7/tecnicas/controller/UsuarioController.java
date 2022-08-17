package br.edu.uni7.tecnicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @RequestMapping("/")
    @ResponseBody
    public List<String> listarUsuarios() {
        var usuarios = new ArrayList<String>();
        usuarios.add("João Victor");
        usuarios.add("Darison");
        usuarios.add("João Paulo");

        return usuarios;
    }


}
