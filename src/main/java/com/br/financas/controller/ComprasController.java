package com.br.financas.controller;


import com.br.financas.dto.CompraDto;
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

    @GetMapping()
    public ResponseEntity<List<Compra>> buscarCompras(){
        return ResponseEntity.ok().body(compraService.buscarCompras());
    }

    @GetMapping("/compras_cliente")
    public ResponseEntity<List<Compra>> buscarComprasPorCliente(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok().body(compraService.buscarComprasPorCliente(id));
    }

    @GetMapping("/buscar_intervalor")
    public ResponseEntity<List<Compra>> buscarComprasIntervalo(@RequestParam(value = "periodoInicial") LocalDate periodoInicial,
                                                               @RequestParam(value = "periodoFinal") LocalDate periodoFinal){
        return ResponseEntity.ok().body(compraService.buscarComprasPorData(periodoInicial, periodoFinal));
    }

    @GetMapping()
    public ResponseEntity<Compra> buscarCompraPorId(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok().body(compraService.buscarCompraPorId(id));
    }

    @PostMapping()
    public ResponseEntity<Compra> cadastrarCompra(@RequestBody CompraDto compra){
        return ResponseEntity.ok().body(compraService.cadastrarCompra(compra.valor(),compra.descricao(),
                compra.idCliente(), compra.dataPrevPagamento(), compra.produto()));
    }

    @DeleteMapping()
    public void deletarCompra(@RequestParam(value = "id") Integer id){
        compraService.deletarCompra(id);
    }

    @PutMapping()
    public ResponseEntity<Compra> atualizarCompra(@RequestBody CompraDto compra){
        return ResponseEntity.ok().body(compraService.atualizarCompra(compra.id(),compra.valor(),compra.descricao(),
                compra.idCliente(),compra.dataPrevPagamento(),compra.produto()));
    }


}
