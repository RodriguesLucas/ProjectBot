package br.com.unisc.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisc.project.dtos.HistoryDto;
import br.com.unisc.project.service.HistoryService;

@RestController
@RequestMapping(value = "/history")
public class HistoryController {
	@Autowired
	private HistoryService historyService;

	@PostMapping
	public HistoryDto add(@RequestBody HistoryDto historyDto) {
		return historyService.add(historyDto);
	}
}
