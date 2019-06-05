package com.n26.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.n26.dataobject.StatisticsDTO;
import com.n26.dataobject.TransactionDTO;

@Service
public class TranscationServiceImpl implements TranscationService {

	private Hashtable<String, TransactionDTO> transcationMap = new Hashtable<String, TransactionDTO>();

	@Override
	public synchronized HttpStatus createTransactions(TransactionDTO transactionDTO) {
		try {
			// Validate amount
			new BigDecimal(transactionDTO.getAmount());
			//
			LocalDateTime currentDateTime = ZonedDateTime.now().toLocalDateTime();
			DateTimeFormatter formater = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
			ZonedDateTime zdt = ZonedDateTime.parse(transactionDTO.getTimestamp(), formater);
			LocalDateTime trnsDatTime = zdt.toLocalDateTime();
			//
			long seconds = ChronoUnit.SECONDS.between(trnsDatTime, currentDateTime);
			if (seconds < 0) {
				return HttpStatus.UNPROCESSABLE_ENTITY;
			}
			if (seconds >= 60) {
				return HttpStatus.NO_CONTENT;
			}
		} catch (Exception e) {
			return HttpStatus.UNPROCESSABLE_ENTITY;
		}
		//
		String random = UUID.randomUUID().toString();
		transcationMap.put(random, transactionDTO);
		//
		return HttpStatus.CREATED;
	}

	@Override
	public void dateleTransactions() {
		transcationMap.clear();
	}

	@Override
	public StatisticsDTO getStatistics() {
		StatisticsDTO statisticsDTO = new StatisticsDTO();
		BigDecimal sum = new BigDecimal("0.00");
		BigDecimal avg = new BigDecimal("0.00");
		BigDecimal max = new BigDecimal("0.00");
		BigDecimal min = new BigDecimal("0.00");

		ArrayList<BigDecimal> amountList = new ArrayList<BigDecimal>();
		TransactionDTO transactionDTO;
		int trnsSize = 0;
		for (String key : transcationMap.keySet()) {
			transactionDTO = (TransactionDTO) transcationMap.get(key);

			LocalDateTime currentDateTime = ZonedDateTime.now().toLocalDateTime();
			DateTimeFormatter formater = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
			ZonedDateTime zdt = ZonedDateTime.parse(transactionDTO.getTimestamp(), formater);
			LocalDateTime trnsDatTime = zdt.toLocalDateTime();
			//
			long seconds = ChronoUnit.SECONDS.between(trnsDatTime, currentDateTime);
			if (seconds >= 60) {
				continue;
			}

			BigDecimal amount = new BigDecimal(transactionDTO.getAmount());
			amountList.add(amount);
			sum = sum.add(amount);
			trnsSize++;
		}

		if (transcationMap != null && trnsSize > 0) {
			avg = sum.divide(new BigDecimal(trnsSize), 2, BigDecimal.ROUND_HALF_UP);
		}
		if (amountList != null && amountList.size() > 0) {
			max = Collections.max(amountList);
			min = Collections.min(amountList);
		}

		if (sum != null) {
			sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
			statisticsDTO.setSum(sum.toString());
		}
		if (avg != null) {
			avg = avg.setScale(2, BigDecimal.ROUND_HALF_UP);
			statisticsDTO.setAvg(avg.toString());
		}
		if (max != null) {
			max = max.setScale(2, BigDecimal.ROUND_HALF_UP);
			statisticsDTO.setMax(max.toString());
		}
		if (min != null) {
			min = min.setScale(2, BigDecimal.ROUND_HALF_UP);
			statisticsDTO.setMin(min.toString());
		}
		statisticsDTO.setCount(trnsSize);
		return statisticsDTO;
	}

}
