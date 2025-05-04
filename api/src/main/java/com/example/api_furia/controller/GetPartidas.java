package com.example.api_furia.controller;

import com.example.api_furia.model.Partida;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/partidas")
public class GetPartidas {

    @GetMapping
    public List<Partida> getPartidas() {
        return List.of(
                new Partida("09/04/2025","PGL Bucharest 2025", "The MolgolZ","Derrota"),
                new Partida("08/04/2025","PGL Bucharest 2025", "Virtus.pro","Derrota"),
                new Partida("07/04/2025","PGL Bucharest 2025", "Complexity","Derrota"),
                new Partida("06/04/2025","PGL Bucharest 2025","Betclic","Vitória"),
                new Partida("10/05/2025","PGL Astana 2025","NAVI","Pendente")
        );
    }
}
