package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinder;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinderException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class SimpleTokenFinder implements TokenFinder {


	public SimpleTokenFinder() {
		/*
		 * DO NOT REMOVE
		 *
		 * REQUIRED FOR GRADING
		 */
	}

	@Override
	public void search(final SearchTask task) throws TokenFinderException {

		// TODO: Implement here!
		String rootFolder = task.getRootFolder();
		String ignoreFile = task.getIgnoreFile();
		String fileExtension = task.getFileExtension();
		String token = task.getToken();
		String resultFile = task.getResultFile();

		Path inputpath = Path.of(rootFolder);
		Path ignorepath = Path.of(ignoreFile);
		Path outputpath = Path.of(resultFile);

        //read ignoreFile and take all lines into a list
		List<String> ignoredFiles = new ArrayList<>();

		try
			(BufferedReader reader = Files.newBufferedReader(ignorepath, StandardCharsets.UTF_8)){
			String line = reader.readLine();
			while (line != null) {
				line = reader.readLine();
				ignoredFiles.add(line);
			}
		} catch (IOException e) {
			throw new TokenFinderException();
		}


		//go through the whole folder and ignore the file and fold that should be ignored
		//make all paths from the files we need into a list of path
		List<Path> otherFiles = new ArrayList<>();

		try{
			walkTree(inputpath,ignoredFiles,otherFiles,outputpath);
		} catch (IOException e) {
			throw new TokenFinderException();
		}


		//pick all the files that have the right file extension
		List<Path> filesWithExtension = new ArrayList<>();

		for (Path path : otherFiles) {
			if (path.toString().endsWith(fileExtension)){
				filesWithExtension.add(path);
			}
		}
		System.out.println("The files with the right file extension:");
		System.out.println(filesWithExtension);

		//search in the file
		int totalTokenCount = 0;

		for (Path path : filesWithExtension) {
			int fileTokenCount = 0;
			try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
				String line;
				int lineNumber = 1;
				while ((line = reader.readLine()) != null) {
					int indexNumber = 0;
					while ((indexNumber = line.indexOf(token.trim(), indexNumber)) != -1) {
						//output every token we find
						writeTokenFound(path, lineNumber, indexNumber, line, token, outputpath);
						indexNumber += token.length();
						fileTokenCount++;
					}lineNumber++;
				}
				//output the count of one file
				writeTokenCount(path, token, outputpath, fileTokenCount);
			} catch (IOException e) {
				throw new TokenFinderException();
			}
			totalTokenCount += fileTokenCount;
		}

		//output the total count
		try(BufferedWriter writer = Files.newBufferedWriter(outputpath,StandardCharsets.UTF_8,StandardOpenOption.APPEND)){
			String totalInfo = "The project includes **" + token + "** " + totalTokenCount + " times.\n";
			writer.write(totalInfo);
			writer.newLine();
		}catch (IOException e) {
			throw new TokenFinderException();
		}

	}
	private void walkTree (Path path, List<String> ignoredFiles, List<Path> otherFiles,Path outputpath) throws IOException , TokenFinderException {

		try
			(DirectoryStream<Path> stream = Files.newDirectoryStream(path)){
			for (Path entry : stream) {
				String fileName = entry.getFileName().toString();
				String alsoIgnore = "ignore";
				if ((ignoredFiles.contains(fileName))||(fileName.equalsIgnoreCase(alsoIgnore))) {
					//output the ignored files or directory
					writeIgnoredFiles(entry,outputpath);
				} else {
					if(Files.isDirectory(entry)){
						walkTree(entry, ignoredFiles, otherFiles,outputpath);
					}else {
						otherFiles.add(entry);
					}
				}
			}
		} catch (IOException e) {
			throw new TokenFinderException();
		}
	}

	private void writeIgnoredFiles(Path path , Path outputpath) throws IOException , TokenFinderException{

	     try(BufferedWriter writer = Files.newBufferedWriter(outputpath,StandardCharsets.UTF_8,StandardOpenOption.APPEND)){
			 String outputInfo;
			 if (Files.isDirectory(path)) {
				 outputInfo = path + " directory was ignored.\n";
			 }else {
				 outputInfo = path + " file was ignored.\n";
			 }
			 writer.write(outputInfo);
			 writer.newLine();
		 } catch (IOException e) {
			 throw new TokenFinderException();
		 }
	}

	private void writeTokenFound (Path path,int lineNumber,int indexNumber,String line,String token,Path outputpath) throws IOException , TokenFinderException {
	     try{
			 BufferedWriter writer = Files.newBufferedWriter(outputpath,StandardCharsets.UTF_8,StandardOpenOption.APPEND );
			 StringBuilder textInfo = new StringBuilder(line);
			 textInfo.insert(indexNumber,"**");
			 textInfo.insert(indexNumber + 2 + token.length(),"**");
			 String tokenFoundInfo = path + ":" + lineNumber + "," + indexNumber + "> " + textInfo + "\n";
			 writer.write(tokenFoundInfo);
			 writer.newLine();
			 writer.close();
		 } catch (IOException e) {
			 throw new TokenFinderException();
		 }
    }

	private void writeTokenCount (Path path,String token,Path outputpath,int fileTokenCount) throws IOException , TokenFinderException {
	     try{
			 BufferedWriter writer = Files.newBufferedWriter(outputpath,StandardCharsets.UTF_8,StandardOpenOption.APPEND );
			 String tokenCountInfo = path + " includes **" + token + "** " + fileTokenCount + " times.\n";
			 writer.write(tokenCountInfo);
			 writer.newLine();
			 writer.close();
		 } catch (IOException e) {
			 throw new TokenFinderException();
		 }
	}
}