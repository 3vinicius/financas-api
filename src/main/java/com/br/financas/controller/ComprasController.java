package com.br.financas.controller;


import com.br.financas.dto.CompraDto;
import com.br.financas.model.Compra;
import com.br.financas.services.CompraService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.br.financas.shareds.urls.compras.*;

@RestController
@AllArgsConstructor
@RequestMapping(Url_Compras)
public class ComprasController {


    private final CompraService compraService;

    @GetMapping("/todos")
    public ResponseEntity<List<Compra>> buscarCompras(){
        return ResponseEntity.ok().body(compraService.buscarCompras());
    }

    //@GetMapping("/buscar")

    @GetMapping("/id")
    public ResponseEntity<Compra> buscarCompraPorId(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok().body(compraService.buscarCompraPorId(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Compra> cadastrarCompra(@RequestBody CompraDto compra){
        return ResponseEntity.ok().body(compraService.cadastrarCompra(compra.valor(),compra.descricao(),
                compra.idCliente(), compra.dataPrevPagamento(), compra.produto()));
    }

    //@PostMapping("/atualizar")

    //@PostMapping("/deletar")



}
