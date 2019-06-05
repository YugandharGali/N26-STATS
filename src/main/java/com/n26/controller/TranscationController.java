package com.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.dataobject.StatisticsDTO;
import com.n26.dataobject.TransactionDTO;
import com.n26.service.TranscationService;

@RestController
@RequestMapping("/")
public class TranscationController {

	private final TranscationService transcationService;

	@Autowired
	public TranscationController(final TranscationService transcationService) {
		this.transcationService = transcationService;
	}

	@PostMapping("/transactions")
	public ResponseEntity<String> createTransactions(@RequestBody TransactionDTO transactionDTO) {
		HttpStatus response = transcationService.createTransactions(transactionDTO);
		return new ResponseEntity<String>(response);	
	}

	@GetMapping("/statistics")
	public StatisticsDTO statistics() {
		return transcationService.getStatistics();
	}

	@DeleteMapping("/transactions")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void dateleTransactions() {
		transcationService.dateleTransactions();
	}

}
