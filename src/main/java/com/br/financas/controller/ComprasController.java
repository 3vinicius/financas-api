package com.br.financas.controller;


import com.br.financas.dto.CompraClienteDTO;
import com.br.financas.model.Compra;
import com.br.financas.services.CompraService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import static com.br.financas.shareds.urls.compras.*;

@RestController
@AllArgsConstructor
@RequestMapping(Url_Compras)
public class ComprasController {


    private final CompraService compraService;

    @GetMapping("/all")
    public ResponseEntity<List<CompraClienteDTO>> buscarCompras(){
        return ResponseEntity.ok().body(compraService.buscarCompras());
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<Compra>> buscarComprasPorCliente(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok().body(compraService.buscarComprasPorCliente(id));
    }

    @GetMapping("/intervalo")
    public ResponseEntity<List<Compra>> buscarComprasIntervalo(@RequestParam(value = "periodoInicial") LocalDate periodoInicial,
                                                               @RequestParam(value = "periodoFinal") LocalDate periodoFinal){
        return ResponseEntity.ok().body(compraService.buscarComprasPorData(periodoInicial, periodoFinal));
    }

    @GetMapping()
    public ResponseEntity<Compra> buscarCompraPorId(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok().body(compraService.buscarCompraPorId(id));
    }

    @PostMapping()
    public ResponseEntity<Compra> cadastrarCompra(@RequestBody CompraClienteDTO compra){
        return ResponseEntity.ok().body(compraService.cadastrarCompra(compra.valor(),compra.descricao(),
                compra.idCliente(), compra.dataPrevPagamento(), compra.produto()));
    }

    @DeleteMapping()
    public void deletarCompra(@RequestParam(value = "id") Integer id){
        compraService.deletarCompra(id);
    }


    @PutMapping()
    public ResponseEntity<Compra> atualizarCompra(@RequestBody CompraClienteDTO compra){
        return ResponseEntity.ok().body(compraService.atualizarCompra(compra.idCompra(),compra.valor(),compra.descricao(),
                compra.idCliente(),compra.dataPrevPagamento(),compra.produto()));
    }


}
