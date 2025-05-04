package com.example.api_furia.controller;

import com.example.api_furia.model.Jogador;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/lineup")
public class GetLineup {

    @GetMapping
    public List<Jogador> getLineup(){
        return List.of(
                new Jogador("Kscerato", "Brasil", "Rifler Lurker"),
                new Jogador("Fallen","Brasil","Rifler"),
                new Jogador("Yuurih","Brasil","Rifler"),
                new Jogador("Yekindar","Letônia","Rifler Entry"),
                new Jogador("Molodoy","Cazaquistão","AWPer"));
    }
}
