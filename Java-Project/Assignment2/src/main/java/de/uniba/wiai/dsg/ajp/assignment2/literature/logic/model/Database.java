package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.LinkedList;
import java.util.List;
@XmlRootElement
public class Database {
	@XmlElement(name="author",required = false)
	private List<Author> authors = new LinkedList<>();
	@XmlElement(name="publication",required = false)
	private List<Publication> publications = new LinkedList<>();

	public Database() {
		super();
	}

	public List<Author> getAuthors() {
		return authors;
	}


	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

}
