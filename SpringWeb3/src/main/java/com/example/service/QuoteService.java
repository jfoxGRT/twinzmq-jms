package com.example.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.QuoteDao;
import com.example.messaging.MessageSender;
import com.example.model.Quote;


@Service
//Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class QuoteService {
	public final static String FAIL_BEFORE_PRE_MESSAGE_STRING = "FailBeforePreMsg";
	public final static String FAIL_AFTER_PRE_MESSAGE_STRING = "failAfterPreMsg";
	public final static String FAIL_AFTER_JDBC_INSERT_STRING = "failAfterJdbcInsert";
	public final static String FAIL_AFTER_POST_MESSAGE_STRING = "failAfterPostMsg";
	
	public final static String FAIL_PRE_ACK_STRING = "failPreAck";
	public final static String FAIL_POST_ACK_STRING = "failPostAck";
	
	private static final Logger log = Logger.getLogger(QuoteService.class);

	@Autowired
	QuoteDao quoteDao;
	
	@Autowired
	MessageSender messageSender;

	// Transactional(isolation=Isolation.REPEATABLE_READ, rollbackFor=Throwable.class, propagation=Propagation.REQUIRED)
	@Transactional(rollbackFor=Throwable.class, propagation=Propagation.REQUIRED)
	public void addQuote(Quote quote) {
		if(quote.getQuoteText().contains(FAIL_BEFORE_PRE_MESSAGE_STRING)) {
			throw new RuntimeException("FORCED ERROR: " + FAIL_BEFORE_PRE_MESSAGE_STRING + "included in quote.");
		}

		this.messageSender.sendPreQuotePersistMessage(quote.getQuoteText(), quote.getRating());

		if(quote.getQuoteText().contains(FAIL_AFTER_PRE_MESSAGE_STRING)) {
			throw new RuntimeException("FORCED ERROR: " + FAIL_AFTER_PRE_MESSAGE_STRING + "included in quote.");
		}

		this.quoteDao.saveQuote(quote);
		
		if(quote.getQuoteText().contains(FAIL_AFTER_JDBC_INSERT_STRING)) {
			throw new RuntimeException("FORCED ERROR: " + FAIL_AFTER_JDBC_INSERT_STRING + "included in quote.");
		}
		
		this.messageSender.sendPostQuotePersistMessage(quote.getQuoteText(), quote.getRating());
		this.messageSender.sendNewQuoteTopicMessage(quote.getId(), quote.getQuoteText(), quote.getRating());
		
		if(quote.getQuoteText().contains(FAIL_AFTER_POST_MESSAGE_STRING)) {
			throw new RuntimeException("FORCED ERROR: " + FAIL_AFTER_POST_MESSAGE_STRING + "included in quote.");
		}
		
	}
	
	@Transactional(rollbackFor=Throwable.class, propagation=Propagation.REQUIRED, readOnly=true)
	public List<Quote> getQuotes() {
		log.debug("Getting list of quotes");
		return quoteDao.getQuotes();
	}
	
	@Transactional(readOnly=true)
	public Quote getQuoteById(int id) {
		return quoteDao.getQuote(id);
	}

	@Transactional(readOnly=true)
	public void save(Quote q) {
		quoteDao.saveQuote(q);
	}
}
