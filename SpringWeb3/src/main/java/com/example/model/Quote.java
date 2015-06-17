package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="quote")
public class Quote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="quote_text")
	private String quoteText;

	@Column(name="rating")
	private Integer rating;
	
	@Column(name="acknowledged")
	private boolean acknowledged;

	public int getId() {
		return id;
}
public void setId(int id) {
	this.id = id;
}
public String getQuoteText() {
	return quoteText;
}
public void setQuoteText(String quoteText) {
	this.quoteText = quoteText;
}
public Integer getRating() {
	return rating;
}
public void setRating(Integer rating) {
	this.rating = rating;
}
public boolean isAcknowledged() {
	return acknowledged;
}
public void setAcknowledged(boolean acknowledged) {
	this.acknowledged = acknowledged;
}



}
