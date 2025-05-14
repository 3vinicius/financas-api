package com.br.financas.controller;

import com.br.financas.dto.DateValorGraphDTO;
import com.br.financas.services.GraphsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;

import static com.br.financas.shareds.urls.Graphs;

@RestController
@AllArgsConstructor
@RequestMapping(value = Graphs, produces = "application/json")
public class GraphsController {

    private final GraphsService graphsService;

    @GetMapping()
    public ResponseEntity<HashMap<String, DateValorGraphDTO>> buscarPagamentos(){
        return ResponseEntity.ok().body(graphsService.buscarDados());
    }

}
