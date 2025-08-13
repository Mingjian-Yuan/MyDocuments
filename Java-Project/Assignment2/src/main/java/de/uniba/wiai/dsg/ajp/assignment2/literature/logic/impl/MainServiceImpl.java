package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.DatabaseServiceImpl;


public class MainServiceImpl implements MainService {

	/**
	 * Default constructor required for grading.
	 */
	public MainServiceImpl() {
		/*
		 * DO NOT REMOVE - REQUIRED FOR GRADING
		 * 
		 * YOU CAN EXTEND IT BELOW THIS COMMENT
		 */

	}

	@Override
	public void validate(String path) throws LiteratureDatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public DatabaseService load(String path) throws LiteratureDatabaseException {

		Database db = null;
		try {
			JAXBContext context = JAXBContext.newInstance(Author.class, Database.class, Publication.class);
			Unmarshaller um = context.createUnmarshaller();
			db = (Database) um.unmarshal(new File("database.xml"));

		}
		catch(jakarta.xml.bind.JAXBException e){
			System.out.println("Fehler getroffen");

		}
		DatabaseService database = new DatabaseServiceImpl(db);

		return database;
	}

	@Override
	public DatabaseService create() throws LiteratureDatabaseException {

		Database db = new Database();

		DatabaseService database = new DatabaseServiceImpl(db);

		return database;

	}

}