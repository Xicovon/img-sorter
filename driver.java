package hentaiMachine;

import java.util.ArrayList;
import java.util.Scanner;

/* things to add:
 * store duplicate file prompts in an array to prompt user in order after loops have finished -- DONE --
 * use a primitive sorting algorithm to lower the time it takes to find a match -- DONE --
 * make a ui to launch program from, and change settings from.
*/
public class driver {

	
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		String userInput = null;
		String sourceDir = "C:\\Users\\Bowen\\Desktop\\Pictures";
		
		ArrayList<String> destDirArray = new ArrayList<String>();
		destDirArray.add("S:\\Pictures");
		destDirArray.add("S:\\Pictures\\resize");
		destDirArray.add("S:\\Pictures\\resize\\above4k");
		destDirArray.add("S:\\Pictures\\resize\\large");
		destDirArray.add("S:\\Pictures\\resize\\medium");
		destDirArray.add("S:\\Pictures\\resize\\small");
		destDirArray.add("S:\\Pictures\\resize\\tiny");
		destDirArray.add("S:\\Pictures\\resize\\temp");
		destDirArray.add("S:\\Pictures\\Hentai");
		destDirArray.add("S:\\Pictures\\Hentai\\Check and Resize");
		destDirArray.add("S:\\Pictures\\Hentai\\video files");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\0 - Pic");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\1 - Gif");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\1.5 - Webm");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\0 - Pic\\0 - Okay");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\0 - Pic\\1 - Good");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\0 - Pic\\2 - Great");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\0 - Pic\\3 - Zoom");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\0 - Pic\\resize");
		destDirArray.add("S:\\Pictures\\Hentai\\Pass\\0 - Pic\\temp");
		
		do {
			System.out.println("Duplicate / Sort / End: ");
			userInput = keyboard.next();
		
			if(userInput.toLowerCase().equals("duplicate")) {
				duplicates duplicateFinder = new duplicates(sourceDir, destDirArray);
				duplicateFinder.start();
				
			}else if (userInput.toLowerCase().equals("sort")) {
				//sorts files by resolution
				new ImageSizer();
			}
			
		}while(!userInput.toLowerCase().equals("end"));
		
		System.out.println("Terminating");
		
		keyboard.close();
	}	
}