package de.uniba.wiai.dsg.ajp.assignment2.literature;


import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.MainServiceImpl;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;
import de.uniba.wiai.dsg.ajp.assignment2.literature.ui.ConsoleHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		ConsoleHelper consoleHelper = ConsoleHelper.build();
		MainService mainService = new MainServiceImpl();
		DatabaseService databaseService = null;

		try{
			boolean running = true;
			while(running){
				consoleHelper.getOut().println("(1) Load and Validate Literature Database");
				consoleHelper.getOut().println("(2) Create New Literature Database");
				consoleHelper.getOut().println("(0) Exit System");

				int option = consoleHelper.askIntegerInRange("Please enter an option: ",0,2);
				switch (option){
					case 1:
						String path = consoleHelper.askNonEmptyString("Input the path to the database file: ");
						try{
							databaseService = mainService.load(path);
							mainService.validate(path);
							System.out.println("Database loaded and validated successfully.");
						}catch (LiteratureDatabaseException e){
							System.out.println("Error while loading");
						}
						runSubMenu(consoleHelper,databaseService);
						break;
					case 2:
						try{
							databaseService = mainService.create();
						}catch (LiteratureDatabaseException e){
							System.out.println("Error while creating");
						}
						runSubMenu(consoleHelper,databaseService);
						break;
					case 3:
						running = false;
						break;
				}
			}
		} catch (IOException e){
			consoleHelper.getOut().println("Error:"+e.getMessage());
		}
	}

	private static void runSubMenu(ConsoleHelper consoleHelper,DatabaseService databaseService) throws IOException{
		boolean running = true;
		while (running){
			consoleHelper.getOut().println("(1) Add Author");
			consoleHelper.getOut().println("(2) Remove Author");
			consoleHelper.getOut().println("(3) Add Publication");
			consoleHelper.getOut().println("(4) Remove Publication");
			consoleHelper.getOut().println("(5) List Authors");
			consoleHelper.getOut().println("(6) List Publications");
			consoleHelper.getOut().println("(7) Print XML to Console");
			consoleHelper.getOut().println("(8) Save XML to File");
			consoleHelper.getOut().println("(0) Back to main menu/close without saving");

			int option = consoleHelper.askIntegerInRange("Please enter an option: ",0,8);
			switch (option){
				case 1:
					String name = consoleHelper.askNonEmptyString("Please enter the name of author: ");
					String email = consoleHelper.askNonEmptyString("Please enter the email of author: ");
					String id= consoleHelper.askNonEmptyString("Please enter the ID of author: ");
					try{
						databaseService.addAuthor(name,email,id);
					}catch (LiteratureDatabaseException e){
						System.out.println("Error while adding Author");
					}
					break;
				case 2:
					String id2 = consoleHelper.askNonEmptyString("Please enter the ID of the author you want to remove: ");
					try{
						databaseService.removeAuthorByID(id2);
					}catch (LiteratureDatabaseException e){
						System.out.println("Error while removing author");
					}
				case 3:
					String title = consoleHelper.askNonEmptyString("Please enter the title of the publication: ");
					int yearPublished = consoleHelper.askInteger("Please enter the year of publication: ");
					PublicationType type = PublicationType.valueOf(consoleHelper.askNonEmptyString("Please enter the type of the publication: "));
					List<String> authorIDs = new ArrayList<>();
					int numAuthors = consoleHelper.askInteger("Please enter the number of authors: ");
					for (int i = 0; i < numAuthors; i++) {
						authorIDs.add(consoleHelper.askNonEmptyString("Please enter author ID " + (i+1) + ": "));
					}
					String id3 = consoleHelper.askNonEmptyString("Please enter the ID of the publication: ");
					try{
						databaseService.addPublication(title, yearPublished, type, authorIDs, id3);
					}catch (LiteratureDatabaseException e){
						System.out.println("Error while adding publications");
					}
					break;
				case 4:
					String id4 = consoleHelper.askNonEmptyString("Please enter the ID of the publication you want to remove: ");
					try{
						databaseService.removePublicationByID(id4);
					}catch (LiteratureDatabaseException e){
						System.out.println("Error while removing publications");
					}
					break;
				case 5:
					List<Author> authors = databaseService.getAuthors();
					if (authors.isEmpty()) {
						consoleHelper.getOut().println("No authors found in the database.");
					} else {
						for (Author author : authors) {
							consoleHelper.getOut().println("ID: " + author.getId());
							consoleHelper.getOut().println("Name: " + author.getName());
						}
					}
					break;
				case 6:
					List<Publication> publications = databaseService.getPublications();
					if (publications.isEmpty()) {
						consoleHelper.getOut().println("No authors found in the database.");
					} else {
						for (Publication publication : publications) {
							consoleHelper.getOut().println("Name: " + publication.getTitle());
						}
					}
					break;
				case 7:
					try{
						databaseService.printXMLToConsole();
					}catch (LiteratureDatabaseException e){
						System.out.println("Error while printing XML");
					}
					break;
				case 8:
					String path = consoleHelper.askNonEmptyString("Please enter the path you want to save: ");
					try{
						databaseService.saveXMLToFile(path);
					}catch (LiteratureDatabaseException e){
						System.out.println("Error while saving XML");
					}
					break;
				case 9:
					running = false;
					break;
			}
		}
	}

}