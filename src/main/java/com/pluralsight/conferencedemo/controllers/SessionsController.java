package com.pluralsight.conferencedemo.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionsController {
	@Autowired
	private SessionRepository sessionRepository;
	
	@GetMapping  //tells which HTTP verb to use, which wil be a GET verb to call this endpoint
	public List <Session> list(){  //List endpoint that will return sessions when called
		return sessionRepository.findAll(); //queries all the sessions in the database and returns them as a list of sessions object
	}
	
	@GetMapping
	@RequestMapping("{id}") //in addition to the class request mapping, in this case class request mapping is api/v1/sessions, and the RequestMapping is adding an additional id to the url 
	public Session get(@PathVariable Long id) {
		return sessionRepository.getOne(id);
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED) to set the response to 201
	public Session create(@RequestBody final Session session) {
		return sessionRepository.saveAndFlush(session);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		//Also need to check repositories before deleting
		sessionRepository.deleteById(id);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Session update(@PathVariable Long id, @RequestBody Session session) {
		//because this is a PUT, we expect all attributes to be passed in. A PATCH would only need what 
		//TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
		Session existingSession = sessionRepository.getOne(id);
		BeanUtils.copyProperties(session, existingSession, "session_id"); //if you don't ignore session_id which is the primary key, you'll copy null onto the primary key and when when you go to update that on the database, that will cause an exception to occur.
		return sessionRepository.saveAndFlush(existingSession);
	}

}