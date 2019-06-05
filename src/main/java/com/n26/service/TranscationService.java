package com.n26.service;

import org.springframework.http.HttpStatus;

import com.n26.dataobject.StatisticsDTO;
import com.n26.dataobject.TransactionDTO;

public interface TranscationService {

	public HttpStatus createTransactions(TransactionDTO transactionDTO);

	public void dateleTransactions();

	public StatisticsDTO getStatistics();

}
