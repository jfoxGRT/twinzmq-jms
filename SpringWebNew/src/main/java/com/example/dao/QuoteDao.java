package com.example.dao;


import java.util.List;

import com.example.model.Quote;

public interface QuoteDao {
	public void saveQuote(Quote quote);
	public List<Quote> getQuotes();
	public Quote getQuote(int id);
}
