/** @author varungoel
 * Name: Varun Goel
 *ID: 109991128
 * email: varun.goel@stonybrook.edu
 * CSE 214 HW 5
 * Recitation Section: 7
 * Recitation TA: Anthony Musco
 * Grading TA: Zhichuang Sun
 */

/**
 * DirectoryTree class which implements a tree of DirectoryNode where each node can hold upto 10 children
 * @author varungoel
 *
 */
public class DirectoryTree {
	/**
	 * Represents the top of the directory tree
	 */
	DirectoryNode root;

	/**
	 * For the current working directory
	 */
	DirectoryNode cursor;

	/**
	 * Default constructor
	 * Initializes a DirectoryTree object with a single DirectoryNode named "root"
	 */
	public DirectoryTree(){
		DirectoryNode Root = new DirectoryNode("root");
		this.root = Root;
		cursor = Root;
	}

	/**
	 * Accessor method for root
	 * @return root of the tree
	 */
	public DirectoryNode getRoot(){
		return root;
	}

	/**
	 * Accessor method for cursor
	 * @return cursor
	 */
	public DirectoryNode getCursor(){
		return cursor;
	}

	/**
	 * Resets cursor and sets it to the root of the directory
	 */
	public void resetCursor(){
		cursor = root;
	}

	/**
	 * Changes the directory by pointing the cursor to the specified directory name
	 * @param Name
	 * @throws NotADirectoryException
	 */
	public void changeDirectory(String Name) throws NotADirectoryException{
		boolean found = false;

		for(int i = 0; i < 10; i++){
			if(cursor.childArray[i] != null){
				if(cursor.childArray[i].getName().equals(Name)){

					//if node exists but is a file, do this
					if(cursor.childArray[i].getisFile())
						throw new NotADirectoryException();

					else{
						//System.out.print("SHIFTED LEFT");
						cursor = cursor.childArray[i];
						found = true;
					}
				}
			}	
		}
		if(!found)
			System.out.println("Directory with this name doesn't exist.");
	}

	/**
	 * Helper method that moves cursor to specified file/directory. Similar to cd. But works for files also and will be used in the mv function
	 * @param Name
	 */
	public void moveCursor(String Name){
		for(int i = 0; i < 10; i++){
			if(cursor.childArray[i] != null){
				if(cursor.childArray[i].getName().equals(Name)){
					cursor = cursor.childArray[i];
				}
			}	
		}

	}

	/**
	 *Removes a child directory. Required for extra credit mv function
	 * @param Name
	 * @throws NotADirectoryException
	 */
	public void removeDirectory(String Name) throws NotADirectoryException{
		boolean found = false;

		for(int i = 0; i < 10; i++){
			if(cursor.childArray[i] == null)
				break;

			if(cursor.childArray[i].getName().equals(Name)){
				cursor.childArray[i] = null; //removes the directory by setting it to null
				found = true;
				break;
			}
		}
		if(!found)
			System.out.println();
	}

	/**
	 * Uses the toString method from DirectoryNode
	 * @return a String containing the path of directory names from the root node of the tree to the cursor, with each name separated by a forward slash
	 */
	public String presentWorkingDirectory(){
		return cursor.toString();
	}

	/**
	 * Method to print all the children of the directory
	 * @return String containing a space-separated list of names of all the child directories or files of the cursor
	 */
	public String listDirectory(){
		String toReturn = "";

		for(int i = 0; i < 10; i++){
			if(cursor.childArray[i] != null)
				toReturn = toReturn + cursor.childArray[i].getName() + " ";
		}

		return toReturn;	
	}

	/**
	 * Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
	 * Uses the helper method preOrder from DirectoryNode class
	 */
	public void printDirectoryTree(){
		cursor.preOrder(0);
	}

	/**
	 * Creates a directory with the indicated name and adds it to the children of the cursor node. 
	 * Uses the addChild function from DirectoryNode class
	 * @param name The name of the directory to add
	 * @throws IllegalArgumentException if name is invalid (does not contain spaces " " or forward slashes "/")
	 * @throws FullDirectoryException if the directory can't be added
	 */
	public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException{

		if(name.indexOf(" ") != -1 || name.indexOf("/") != -1)
			throw new IllegalArgumentException();

		DirectoryNode newNode = new DirectoryNode(name);

		try{
			cursor.addChild(newNode);
		}catch(FullDirectoryException ex){

		}
	}

	/**
	 * Creates a file with the indicated name and adds it to the children of the cursor node.
	 * @param name
	 * @throws IllegalArgumentException if file name is invalid
	 * @throws FullDirectoryException
	 */
	public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException{

		if(name.indexOf(" ") != -1 || name.indexOf("/") != -1)
			throw new IllegalArgumentException();

		DirectoryNode newFile = new DirectoryNode(name);
		newFile.setisFile(true); //set true to that the directory node is true

		try{
			cursor.addChild(newFile);
		}
		catch(FullDirectoryException ex){
		}
	}

	/**
	 * Sets cursor to parent (cd ..)
	 */
	public void cursorToParent(){
		if(cursor == root)
			System.out.println("Already at the root");
		else
			cursor = cursor.parent;
	}
}