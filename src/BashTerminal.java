/** @author varungoel
 * Name: Varun Goel
 *ID: 109991128
 * email: varun.goel@stonybrook.edu
 * CSE 214 HW 5
 * Recitation Section: 7
 * Recitation TA: Anthony Musco
 * Grading TA: Zhichuang Sun
 */

import java.util.*;

/**
 * Class that implements a bash terminal
 * @author varungoel
 */
public class BashTerminal {



	public static void main(String[] args) {

		DirectoryTree tree = new DirectoryTree();

		Scanner s = new Scanner(System.in);

		System.out.print("[vgoel@ELIZA]: $ ");
		String input = s.nextLine();

		while(!input.equals("exit")){

			if(input.equalsIgnoreCase("pwd")){
				System.out.println(tree.presentWorkingDirectory());
			}

			else if(input.equalsIgnoreCase("ls")){
				System.out.println(tree.listDirectory());
			}

			else if(input.equalsIgnoreCase("ls -R")){
				tree.printDirectoryTree();
			}

			//for the different cases of cd

			else if(input.indexOf("cd") != -1){


				//changes directory to root (cd /)
				if(input.matches("cd[\\s]*/")){
					tree.resetCursor();
				}

				//extra credit. sets cursor to parent
				else if(input.equals("cd ..")){
					tree.cursorToParent();
				}

				//extra credit involving absolute paths
				else if(input.indexOf("/") != -1){
					String[] tokens = (input).split("/");

					tree.resetCursor();
					for(int i = 1; i < tokens.length; i++){
						try {
							tree.changeDirectory(tokens[i]);
						} catch (NotADirectoryException e) {
							tree.resetCursor();
						}
					}
				}

				// conventional cd (only for direct children)
				else{
					String toSplit = input;
					int spaces = 0;
					for(int i = 0; i < toSplit.length(); i++){
						if(toSplit.charAt(i) ==  ' ')
							spaces++;
					}

					String[] tokens = (toSplit).split(" ");
					String name = tokens[spaces];

					try {
						tree.changeDirectory(name);
					} catch (NotADirectoryException e) {
					}
				}	
			}

			else if(input.indexOf("mkdir") != -1){
				String name = input.substring(6);

				try {
					tree.makeDirectory(name);
				} catch (IllegalArgumentException ex) {
					System.out.println("Invalid  name");				
				} catch (FullDirectoryException ex){

				}
			}

			else if(input.indexOf("touch") != -1){
				String toSplit = input;
				int spaces = 0;
				boolean charStart = false;
				boolean invalid = false;
				for(int i = 6; i < toSplit.length(); i++){
					if(toSplit.charAt(i) ==  ' ' && charStart){
						invalid = true;
						System.out.println("Invalid name");
						break;
					}
					if(toSplit.charAt(i) ==  ' ')
						spaces++;
					else
						charStart = true;
				}

				//if file name is not invalid
				if(!invalid){
					String[] tokens = (toSplit.substring(6)).split(" ");
					String name = tokens[spaces];

					try {
						tree.makeFile(name);
					} catch (IllegalArgumentException ex) {
						System.out.println("Invalid  name");				
					} catch (FullDirectoryException ex){
					}
				}
			}

			//extra credit find function
			else if(input.indexOf("find") != -1){
				String param = input.substring(5);
				tree.getRoot().find(param);
			}

			else if(input.indexOf("mv ") != -1){
				String[] tokens = (input).split(" ");
				if(tokens.length >= 3){
					String[] path1 = tokens[1].split("/"); //source
					String[] path2 = tokens[2].split("/"); //destination



					if(path1.length > 1 && path2.length >= 1){
						tree.resetCursor();

						for(int a = 1; a < path1.length; a++){
							tree.moveCursor(path1[a]);
						}

						DirectoryNode toMove = tree.getCursor();

						//System.out.println(toMove);

						tree.cursorToParent();
						try {
							tree.removeDirectory(path1[path1.length - 1]);
						} catch (NotADirectoryException e1) {
						}
						toMove.setParentNull();

						tree.resetCursor();
						for(int b = 1; b < path2.length; b++){
							try {
								tree.changeDirectory(path2[b]);
							} catch (NotADirectoryException e) {
							}}
						
						//this completes the moving part
						try {
							tree.cursor.addChild(toMove);
						} catch (FullDirectoryException e) {

						}

						tree.resetCursor();
					}}
			}
			else{
				System.out.println("Invalid command");
			}

			System.out.print("[vgoel@ELIZA]: $ ");
			input = s.nextLine();
		}
		System.out.println("EXITING"); 
		s.close();

	}

}
