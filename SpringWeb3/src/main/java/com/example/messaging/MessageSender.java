package com.example.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonObject;

import org.apache.activemq.ScheduledMessage;
import org.apache.log4j.Logger;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.MANDATORY)
public class MessageSender {
	private static final Logger log = Logger.getLogger(MessageSender.class);
	
	private JmsTemplate jmsTemplate;
	private JmsTemplate jmsTopicTemplate;
	private String quoteTopicDestName;
	private String preQuoteQueueDestName;
	private String postQuoteQueueDestName;

	public void sendPreQuotePersistMessage(String quote, Integer rating) {
		final String theQuote = String.format("PRE: %s", quote);
		log.info(String.format("MESSAGE: pre-quote message send: '%s', rate: %d", quote, rating));
		jmsTemplate.send(this.preQuoteQueueDestName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage();
				msg.setText(theQuote);
				return msg;
			}
		});
	}

	public void sendPostQuotePersistMessage(String quote, Integer rating) {
		final String theQuote = String.format("POST: %s", quote);
		log.info(String.format("MESSAGE: post-quote message send: '%s', rate: %d", quote, rating));
		jmsTemplate.send(this.postQuoteQueueDestName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage();
				msg.setText(theQuote);
				return msg;
			}
		});
	}
	
	public void sendNewQuoteTopicMessage(Integer id, String quote, Integer rating) {
		log.info(String.format("MESSAGE: Topic message send: '%s' for id: %d, rate: %d, sending to dest: '%s'", quote, id, rating, this.quoteTopicDestName));
		final JsonObject json = Json.createObjectBuilder().add("id", id).add("quote", quote).build();
		jmsTopicTemplate.send(this.quoteTopicDestName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage();
				msg.setText(json.toString());
				// ActiveMQ specific - delay handling the message for 2 seconds
				// http://activemq.apache.org/delay-and-schedule-message-delivery.html
				// you must enable this in <activemq_root>/conf/activemq.xml, add: 
				//     schedulerSupport="true"
				// to the <broker> element
				msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 2000l);
				return msg;
			}
		});
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public JmsTemplate getJmsTopicTemplate() {
		return jmsTopicTemplate;
	}

	public void setJmsTopicTemplate(JmsTemplate jmsTopicTemplate) {
		this.jmsTopicTemplate = jmsTopicTemplate;
	}

	public String getQuoteTopicDestName() {
		return quoteTopicDestName;
	}

	public void setQuoteTopicDestName(String quoteTopicDestName) {
		this.quoteTopicDestName = quoteTopicDestName;
	}

	public String getPreQuoteQueueDestName() {
		return preQuoteQueueDestName;
	}

	public void setPreQuoteQueueDestName(String preQuoteQueueDestName) {
		this.preQuoteQueueDestName = preQuoteQueueDestName;
	}

	public String getPostQuoteQueueDestName() {
		return postQuoteQueueDestName;
	}

	public void setPostQuoteQueueDestName(String postQuoteQueueDestName) {
		this.postQuoteQueueDestName = postQuoteQueueDestName;
	}

}
