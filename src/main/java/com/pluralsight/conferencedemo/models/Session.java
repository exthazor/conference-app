package com.pluralsight.conferencedemo.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;

@Entity(name="sessions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //preventing lazy loading and eager loading of the relational data. When you serialize a hibernate object, you do not want to serialize this because this will try to load in all of your relational data with SQL and it can cause problems.
public class Session{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long session_id;
	private String session_name;
	private String session_description;
	private Integer session_length;
	
	@ManyToMany
	@JoinTable(
			name = "session_speakers",
			joinColumns = @JoinColumn(name = "session_id"),
			inverseJoinColumns = @JoinColumn(name = "speaker_id")
			)
	
	private List<Speaker> speakers;
	
	public Long getSession_id() {
		return session_id;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public void setSession_id(Long session_id) {
		this.session_id = session_id;
	}

	public String getSession_name() {
		return session_name;
	}

	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}

	public String getSession_description() {
		return session_description;
	}

	public void setSession_description(String session_description) {
		this.session_description = session_description;
	}

	public Integer getSession_length() {
		return session_length;
	}

	public void setSession_length(Integer session_length) {
		this.session_length = session_length;
	}

	
	
	public Session() {
		
	}

}