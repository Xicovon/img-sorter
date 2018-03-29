package hentaiMachine;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class duplicateFinder {
	private Scanner keyboard = new Scanner(System.in);
	private String srcFileSansJPG = "";
	private String dstFileSansJPG = "";
	private String userInput = "";
	private int deletedFileCount = 0;
	private ArrayList<String[]> matchList1 = new ArrayList<String[]>();
	private ArrayList<double[]> matchList2 = new ArrayList<double[]>();
	
	private File folderSource;
	private File[] listOfSourceFiles;
	private File folderDestination;
	private File[] listOfDestinationFiles;
	
	public duplicateFinder(String sourceDir) {
		createSrcArray(sourceDir);
	}
	
	public void findDuplicates(String sourceDir, ArrayList<String> destDirArray) {
		for (int i = 0; i < listOfSourceFiles.length; i++){
			String sourceFileName = listOfSourceFiles[i].getName();
			System.out.println("File " + i + "/" + listOfSourceFiles.length +": " +sourceFileName);
			
			if (listOfSourceFiles[i].isFile()) {
				//loops through destination directories passed from driver
				for (int i2 = 0; i2 < destDirArray.size(); i2++) {
					//creates destination file array
					createDstArray(destDirArray.get(i2));
					destinationFileLoop(sourceDir, sourceFileName, destDirArray.get(i2), i);
					
				}//second for loop
			}//if source file is a file
		}//first for loop
		
		presentMatchList();
		
		System.out.println("Files deleted: " + deletedFileCount);
	}
	
	//loops through destination files
	public void destinationFileLoop(String sourceDir, String sourceFileName, String destinationDir, int i) {
		for (int i3 = 0; i3 < listOfDestinationFiles.length; i3++) {
			String destinationFileName = listOfDestinationFiles[i3].getName();
			
			//find '.' if destination is a file
			if (listOfDestinationFiles[i3].isFile()) {
				
				//deletes files with the same name and file size
				if (compareFileNames(sourceFileName, destinationFileName)) {
					if (compareFileSizes(i, i3)) {
						deleteFile(sourceFileName, sourceDir);
						System.out.println("Source File Deleted");
						break;
						
					//if the name and size are not exactly equal output name and size of both files and prompt user for input
					}else {
						System.out.println("Match Found");
						appendMatchList(sourceFileName, sourceDir, destinationFileName, destinationDir, listOfSourceFiles[i].length(), listOfDestinationFiles[i3].length());
					}
				}
				
			}//if destination file is a file
		}//third for loop
	}
	
	public void presentMatchList() {
		for(int i = 0; i<matchList1.size(); i++) {
			boolean userInputIsAcceptable = true;
			
			String[] fileData = matchList1.get(i);
			double[] fileValues = matchList2.get(i);
			
			String sourceFileName = fileData[0];
			String sourceDir = fileData[1];
			String destinationFileName = fileData[2];
			String destinationDir = fileData[3];
			
			double srcBytes = fileValues[0];
			double dstBytes = fileValues[1];
			
			System.out.println("Match " + (i+1) + "/" + matchList1.size());
			System.out.println("Duplicate Found: \n" + "src: " + sourceFileName + " \ndst: " + destinationFileName);
			System.out.print("src file size: " + srcBytes + " bytes\ndst file size: " + dstBytes + " bytes\nActions: ");
			
			if(srcBytes == 0 || dstBytes == 0) {
				System.out.println("sourceBytes = 0 || destinationBytes = 0");
			}
			
			if(srcBytes != 0 && dstBytes != 0) {
				do {
					System.out.println("(deletesource / deletedestination / move / none)");
					userInput = keyboard.next();
					
					//check user input
					if (userInput.equals("deletesource")) {
						deleteFile(sourceFileName, sourceDir);
						System.out.println("Source File Deleted: " + sourceFileName);
						break;
					}else if (userInput.equals("move")) {
						moveFiles(sourceFileName, sourceDir, "S:\\srcjavatemp\\");
						moveFiles(destinationFileName, destinationDir, "S:\\dstjavatemp\\");
						break;
					}else if (userInput.equals("deletedestination")) {
						deleteFile(destinationFileName, destinationDir);
					    System.out.println("Destination File Deleted");
					    break;
					//skip file
					}else if (userInput.equals("none")) {
						System.out.println("No action taken");
						break;
					}else {
						userInputIsAcceptable = false;
					}
				}while(userInputIsAcceptable);
				System.out.println("\n**********");
			}
		}
	}
	
	
	//creates an array storing the 
	public void appendMatchList(String srcFileName, String srcDir, String dstFileName, String dstDir, double srcFileSize, double dstFileSize) {
		String[] match = {srcFileName, srcDir, dstFileName, dstDir};
		double[] values = {srcFileSize, dstFileSize};
		
		matchList1.add(match);
		matchList2.add(values);
	}

	//sets array values
	public void createSrcArray(String sourceDir) {
		folderSource = new File(sourceDir);
		listOfSourceFiles = folderSource.listFiles();
	}
	
	public void createDstArray(String destinationDir) {
		folderDestination = new File(destinationDir);
		listOfDestinationFiles = folderDestination.listFiles();
	}
	
	public boolean compareFileNames(String a, String b) {
		srcFileSansJPG = a.substring(0, a.lastIndexOf('.'));
		dstFileSansJPG = b.substring(0, b.lastIndexOf('.'));
		//terminates program if it cannot find the period
		if (a.indexOf('.')==-1||b.indexOf('.')==-1) {
			System.out.println("Fatal Error:\nThe method compareFileNames unable to find index of '.'\nTerminating");
			System.exit(0);
		}
		//compares the strings before the period, returns boolean
		if (srcFileSansJPG.equals(dstFileSansJPG)) {return true;}else {return false;}		
	}
	
	public boolean compareFileSizes(int A, int B) {
		//gets files sizes in bytes
		double srcbytes = listOfSourceFiles[A].length();
		double dstbytes = listOfDestinationFiles[B].length();
		//compares file sizes
		if (srcbytes == dstbytes) {return true;}else {return false;}	
	}
	
	public void deleteFile(String sourceFileName, String sourceDir) {
		Path path2 = Paths.get(sourceDir + "\\" + sourceFileName);
	    try {
	        Files.delete(path2);
	        deletedFileCount++;
	    } catch (NoSuchFileException x) {System.err.format("%s: no such" + " file or directory%n", path2);
	    } catch (DirectoryNotEmptyException x) {System.err.format("%s not empty%n", path2);
	    } catch (IOException x) {System.err.println(x);}
	}
	
	public void moveFiles(String sourceFileName, String sourceDir, String tempDir) {
		try{
			File afile =new File(sourceDir + "\\" + sourceFileName);
			if(afile.renameTo(new File(tempDir + "\\" + sourceFileName))){
				System.out.println("File moved to: " + tempDir +"\\"+ sourceFileName);
    		}else{System.out.println("File is failed to move!");}
    	}catch(Exception e){e.printStackTrace();}
	}
}