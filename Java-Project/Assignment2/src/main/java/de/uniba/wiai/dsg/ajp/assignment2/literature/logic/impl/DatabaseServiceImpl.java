package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import java.io.File;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.ValidationHelper;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.MainServiceImpl;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;

public class DatabaseServiceImpl implements DatabaseService {
	private Database db ;


	public DatabaseServiceImpl(Database db){
		this.db=db;
	}
	public void setDb(Database db){
		this.db=db;
	}
	public Database getDb(){return db;}

	@Override
	public Database addPublication(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id)
			throws LiteratureDatabaseException {

		if (title == null || title.isEmpty()) {
			throw new LiteratureDatabaseException("Title cannot be null or empty.");
		}

		if (yearPublished < 0) {
			throw new LiteratureDatabaseException("Year published cannot be negative.");
		}

		if (type == null) {
			throw new LiteratureDatabaseException("Publication type cannot be null.");
		}

		if (authorIDs == null || authorIDs.isEmpty()) {
			throw new LiteratureDatabaseException("Author IDs cannot be null or empty.");
		}

		if (id == null || id.isEmpty()) {
			throw new LiteratureDatabaseException("Publication ID cannot be null or empty.");
		}


		for (Publication publication : db.getPublications()) {
			if (publication.getId().equals(id)) {
				throw new LiteratureDatabaseException("Publication with id " + id + " already exists.");
			}
		}

		if (!ValidationHelper.isId(id)) {
			throw new LiteratureDatabaseException("Invalid ID format: " + id);
		}


		List<Publication> publications= getPublications();
		Publication newPublication = new Publication();
		newPublication.setTitle(title);
		newPublication.setYearPublished(yearPublished);
		newPublication.setType(type);
		newPublication.setId(id);
		newPublication.setAuthors(newPublication.getAuthorById(authorIDs));

		publications.add(newPublication);
		Database db = new Database();
		db.setPublications(publications);
		return db;
	}





	@Override
	public Database removePublicationByID(String id) throws LiteratureDatabaseException {
		if (!ValidationHelper.isId(id)) {
			throw new LiteratureDatabaseException("Invalid ID: " + id);
		}

		List<Publication> publications=getPublications();
		Publication toRemove = null;
		for(Publication publication :publications){
			if(publication.getId().equals(id)){
				toRemove = publication;
				break;
			}
		}

		if(toRemove != null) {
			publications.remove(toRemove);
		} else {
			throw new LiteratureDatabaseException("Publication with ID " + id + " not found.");
		}
		Database db = new Database();
		db.setPublications(publications);
		return db;
	}

	@Override
	public Database removeAuthorByID(String id) throws LiteratureDatabaseException {
		if (!ValidationHelper.isId(id)) {
			throw new LiteratureDatabaseException("Invalid ID: " + id);
		}

		List<Author> authors = getAuthors();
		Author toRemove = null;
		for(Author author:authors){
			if(author.getId().equals(id)){
				toRemove = author;
				break;
			}
		}
		if(toRemove != null) {
			authors.remove(toRemove);
		} else {
			throw new LiteratureDatabaseException("Author with ID " + id + " not found.");
		}
		Database db = new Database();
		db.setAuthors(authors);
		return db;

	}

	@Override
	public Database addAuthor(String name, String email, String id) throws LiteratureDatabaseException {
		List<Author> authors= getAuthors();
		for(Author author:authors){
			if(author.getId().equals(id)){
				throw new LiteratureDatabaseException("Author with ID " + id + " already exists.");
			}
		}

		if (!ValidationHelper.isId(id)) {
			throw new LiteratureDatabaseException("Invalid ID format: " + id);
		}
		if (!ValidationHelper.isEmail(email)) {
			throw new LiteratureDatabaseException("Invalid email format: " + email);
		}

		Author newAuthor= new Author();
		newAuthor.setName(name);
		newAuthor.setEmail(email);
		newAuthor.setId(id);
		authors.add(newAuthor);
		Database db = new Database();
		db.setAuthors(authors);

		return db;
	}

	@Override
	public List<Publication> getPublications() {
		List<Publication> publications=null;
		try{MainServiceImpl msi = new MainServiceImpl();
		DatabaseServiceImpl data=(DatabaseServiceImpl)msi.load("database.xml");
		publications=data.getDb().getPublications();}
		catch (LiteratureDatabaseException e){
			System.out.println("Error while getting publications");
		}
		return publications;
	}

	@Override
	public List<Author> getAuthors() {
		List<Author> authors=null;
		try{MainServiceImpl msi = new MainServiceImpl();
			DatabaseServiceImpl data=(DatabaseServiceImpl)msi.load("database.xml");
			authors=data.getDb().getAuthors();}
		catch (LiteratureDatabaseException e){
			System.out.println("Error while getting authors");
		}
		return authors;
	}

	@Override
	public Database clear() {
		List<Author> authors= getAuthors();
		for(Author author:authors){
			authors.remove(author);
		}

		List<Publication> publications=getPublications();
		for(Publication publication:publications){
			publications.remove(publication);
		}
		Database db = new Database();
		db.setAuthors(authors);
		db.setPublications(publications);
		return db;

	}

	@Override
	public void printXMLToConsole() throws LiteratureDatabaseException {

		try {
			JAXBContext context = JAXBContext.newInstance(Database.class);
			Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();
			ms.marshal(db, writer);
			String xmlString = writer.toString();
			System.out.println(xmlString);
		} catch (JAXBException e) {
			throw new LiteratureDatabaseException("Cannot print database to console", e);
		}
	}

	@Override
	public void saveXMLToFile(String path) throws LiteratureDatabaseException {
		try {
			JAXBContext context = JAXBContext.newInstance(Database.class);
			Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(db, new File("database.xml"));
		} catch (JAXBException e) {
			throw new LiteratureDatabaseException("Cannot save database to file " + path, e);
		}
	}


}
