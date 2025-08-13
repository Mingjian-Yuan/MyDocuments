package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class Publication {
	@XmlID
	@XmlElement(name="id",required = true)
	private String id;
	@XmlElement(name="title",required = true)
	private String title;
	@XmlAttribute(name="yearPublished",required = true)
	private int yearPublished;
	@XmlAttribute(name="type",required = true)
	private PublicationType type;

	@XmlElement(name="author",required = true)
	private List<Author> authors = new LinkedList<>();

	public Publication() {
		super();
	}

	public PublicationType getType() {
		return type;
	}

	public void setType(PublicationType type) {
		this.type = type;
	}

	public int getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return String.format(
				"[%s] The author(s) %s published %s as a %s in %d", id,
				getAuthorNames(), title, type, yearPublished);
	}

	private String getAuthorNames() {
		StringJoiner result = new StringJoiner(", ");
		for (int i = 0; i < authors.size(); i++) {
			result.add(authors.get(i).getName());
		}
		return result.toString();
	}

	public List<Author> getAuthorById (List<String> authorId){
		List<Author> authors = new ArrayList<>();
		for(String id : authorId){
			Author foundAuthor = null;
			for(Author author:this.authors){
				if(author.getId().equals(id)){
					foundAuthor = author;
					break;
				}
			}
			if (foundAuthor != null) {
				authors.add(foundAuthor);
			} else {
				Author author1 = new Author();
				author1.setId(id);
				authors.add(author1);
			}
		}
		return authors;
}
}
