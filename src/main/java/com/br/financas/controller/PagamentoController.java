package com.br.financas.controller;


import com.br.financas.dto.PagamentoDto;
import com.br.financas.model.Pagamento;
import com.br.financas.services.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.br.financas.shareds.urls.pagamentos.*;

@RestController
@AllArgsConstructor
@RequestMapping(Url_Pagamentos)
public class PagamentoController {


    private final PagamentoService PagamentoService;

    @GetMapping()
    public ResponseEntity<List<Pagamento>> buscarPagamentos(){
        return ResponseEntity.ok().body(PagamentoService.buscarPagamentos());
    }

    @GetMapping("/Pagamentos_cliente")
    public ResponseEntity<List<Pagamento>> buscarPagamentosPorCliente(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok().body(PagamentoService.buscarPagamentosPorCliente(id));
    }

    @GetMapping("/buscar_intervalor")
    public ResponseEntity<List<Pagamento>> buscarPagamentosIntervalo(@RequestParam(value = "periodoInicial") LocalDate periodoInicial,
                                                               @RequestParam(value = "periodoFinal") LocalDate periodoFinal){
        return ResponseEntity.ok().body(PagamentoService.buscarPagamentosPorData(periodoInicial, periodoFinal));
    }

    @GetMapping()
    public ResponseEntity<Pagamento> buscarPagamentoPorId(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok().body(PagamentoService.buscarPagamentoPorId(id));
    }

    @PostMapping()
    public ResponseEntity<Pagamento> cadastrarPagamento(@RequestBody PagamentoDto Pagamento){
        return ResponseEntity.ok().body(PagamentoService.cadastrarPagamento(Pagamento.valor(),Pagamento.descricao(),
                Pagamento.idCliente()));
    }

    @DeleteMapping()
    public void deletarPagamento(@RequestParam(value = "id") Integer id){
        PagamentoService.deletarPagamento(id);
    }

    @PutMapping()
    public ResponseEntity<Pagamento> atualizarPagamento(@RequestBody PagamentoDto Pagamento){
        return ResponseEntity.ok().body(PagamentoService.atualizarPagamento(Pagamento.id(),Pagamento.valor(),Pagamento.descricao(),
                Pagamento.idCliente()));
    }
}
