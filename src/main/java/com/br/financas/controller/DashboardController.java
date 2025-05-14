package com.br.financas.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.br.financas.shareds.urls.Dashboard;
import static com.br.financas.shareds.urls.Graphs;

@RestController
@AllArgsConstructor
@RequestMapping(value = Dashboard, produces = "application/json")
public class DashboardController {

}
