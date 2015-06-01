package com.example.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Quote;


@Repository("quoteDao")
public class QuoteDaoImpl implements QuoteDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional(propagation=Propagation.MANDATORY)
	public void saveQuote(Quote quote) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(quote);
	}

	@SuppressWarnings("unchecked")
	public List<Quote> getQuotes() {
		List<Quote> qlist = this.sessionFactory.getCurrentSession().createCriteria(Quote.class).list();
		return qlist;
	}

	@SuppressWarnings("unchecked")
	public Quote getQuote(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Quote.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.setMaxResults(1);
		List<Quote> list = criteria.list();
		return (list.size() > 0) ? list.get(0) : null;
	}
}
