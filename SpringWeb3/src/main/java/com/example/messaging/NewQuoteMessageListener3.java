package com.example.messaging;

import com.example.model.Quote;
import com.example.service.QuoteService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


@Component
public class NewQuoteMessageListener3 implements MessageListener {
	private static final Logger log = Logger.getLogger(NewQuoteMessageListener.class);

	@Autowired
	QuoteService quoteService;


	@Transactional(rollbackFor=Throwable.class, propagation=Propagation.REQUIRES_NEW)
	public void onMessage(Message message) {
		log.debug("ONMESSAGE: received new quote message: " + message.toString());
		if(message instanceof TextMessage) {
			TextMessage textMsg = (TextMessage) message ;
			String theText;
			try {
				theText = textMsg.getText();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			JsonReader reader = Json.createReader(new StringReader(theText));
			JsonObject json = reader.readObject();
			log.debug("handling json: " + json);
			int id = json.getInt("id");
			String quoteStr = json.getString("quote");
			log.debug("id is: " + id + " and quote: '" + quoteStr + "'");
			//  lookup and update
			Quote quoteObj = quoteService.getQuoteById(id);
			if(quoteObj != null){
				try{
					log.debug("From CONSUMER 3, saving: " + quoteObj);
					quoteObj.setQuoteText("CONSUMER 3: " + quoteObj.getQuoteText());

					if(quoteObj.getQuoteText().contains(QuoteService.FAIL_PRE_ACK_STRING)) {
						throw new RuntimeException("FORCED ERROR: " + QuoteService.FAIL_PRE_ACK_STRING + "included in quote.");
					}
					quoteObj.setAcknowledged(true);
					quoteService.save(quoteObj);
					log.debug("saved");
					if(quoteObj.getQuoteText().contains(QuoteService.FAIL_POST_ACK_STRING)) {
						throw new RuntimeException("FORCED ERROR: " + QuoteService.FAIL_POST_ACK_STRING + "included in quote.");
					}
				}
				catch(RuntimeException re) {
					log.debug(re);
				}
			}

		} else {
			log.error("cannot handle message, expecting TextMessage but received " + message.getClass().getName());
		}
	}

}
