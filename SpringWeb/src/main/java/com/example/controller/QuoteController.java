package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Quote;
import com.example.service.QuoteService;

@Controller
public class QuoteController {

	@Autowired
	private QuoteService quoteService;

	@RequestMapping("/quoteAdd")
	public ModelAndView getAddForm(@ModelAttribute("quote") Quote quote, BindingResult result) {
		// add stuff in this map to help render form
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<Integer> ratings = new ArrayList<Integer>(); 
		ratings.add(0);
		ratings.add(1);
		ratings.add(2);
		ratings.add(3);
		ratings.add(4);
		
		model.put("ratings", ratings);
        return new ModelAndView("QuoteAddForm", "model", model);  
	}
	
	@RequestMapping("/quoteSave")
	public ModelAndView saveQuote(@ModelAttribute("quote") Quote quote, BindingResult result) {
        quoteService.addQuote(quote);
        return new ModelAndView("redirect:/quoteList.html");  
	}
	
	@RequestMapping("/quoteList")
	public ModelAndView getQuoteList() {
        Map<String, Object> model = new HashMap<String, Object>();  
        model.put("quotes", quoteService.getQuotes());  
        return new ModelAndView("QuoteList", model);  
	}
}
