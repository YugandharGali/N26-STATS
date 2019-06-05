package com.n26.dataobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

	@JsonIgnore
	private Long id;

	@NotNull(message = "Amount can not be null!")
	private String amount;

	@NotNull(message = "Timestamp can not be null!")
	private String timestamp;

	@JsonProperty
	public Long getId() {
		return id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
