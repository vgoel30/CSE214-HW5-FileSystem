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
 * DirectoryNode class
 * Represents a node in the file tree with support of upto 10 children
 * @author varungoel
 */
public class DirectoryNode {

	/**
	 * Default constructor with no parameters
	 */
	public DirectoryNode(){

	}

	/**
	 * Additional constructor which sets the name of the directory node
	 * @param name
	 */
	public DirectoryNode(String name){
		this.name = name;
	}

	//instead of having 10 child references, maintain an array for the children directory nodes 
	public DirectoryNode[] childArray = new DirectoryNode[10];

	//helper variable that will be used for printing out the current working directory
	public DirectoryNode parent = null;

	/**
	 *Indicates the name of the node in the tree and should be a full string with no spaces, tabs, or any other whitespace
	 */
	private String name;

	/**
	 * boolean value should only be set to true if the node is a leaf, of the tree; i.e. files are not allowed to have children.
	 */
	private boolean isFile;

	/**
	 * Mutator method for name variable
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Sets parent to null. Will be useful in the remove directory method.
	 */
	public void setParentNull(){
		this.parent = null;
	}

	/**
	 * Accessor method for string variable
	 * @return String name of the node
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Setter method for isFile
	 * @param bool
	 */
	public void setisFile(boolean bool){
		isFile = bool;
	}

	/**
	 * Accessor method for boolean isFile
	 * @return boolean isFile
	 */
	public boolean getisFile(){
		return this.isFile;
	}

	

	/**
	 *Method to add a child to the current node.
	 * @param newNode: child to be added
	 * @throws FullDirectoryException: thrown if directory is full and child can't be added
	 * @throws NotADirectoryException: thrown if node is a file and not a directory
	 */
	public void addChild(DirectoryNode newNode) throws FullDirectoryException{

		int index = 0;
		boolean foundIndex = false;

		for(int i = 0; i < 10; i++){
			if(childArray[i] == null){
				foundIndex = true;
				index = i;
				break;
			}
		}

		if(foundIndex){
			childArray[index] = newNode;
			newNode.parent = this;
		}

		else
			throw new FullDirectoryException();

	}

	/**
	 * Overrides the toString method to print the node and it's path
	 * Will be used in the listDirectory method in DirectoryTree
	 */
	public String toString(){
		DirectoryNode pointer = this;
		String toReturn = "";

		String[] names = new String[100];
		int namesIndex = 0;

		//if the node is the root
		if(pointer.parent == null)
			return this.getName();

		while(pointer.parent != null){
			names[namesIndex++] = pointer.getName();
			pointer = pointer.parent;
		}
		toReturn = pointer.getName() + "/";

		for(int a = namesIndex-1; a >= 1; a--){
			toReturn = toReturn + names[a] + "/";
		}

		toReturn = toReturn + names[0];

		return toReturn;
	}

	/**
	 * Helper method for the printDirectoryTree() method in DirectoryTree
	 * @param indents
	 */
	public void preOrder(int indents){

		for(int i = 0; i < indents; i++){
			System.out.print("     ");
		}

		if(this.isFile)
			System.out.print("- "); //for files
		else
			System.out.print("|- "); //for directories

		System.out.println(this.getName());

		for(int i = 0; i < 10; i++){
			if(childArray[i] != null){
				childArray[i].preOrder(indents + 1);
			}
			
		}	
	}

	/**
	 * Find function to find a file/directory with specified name
	 * @param param name of the string
	 * @return boolean whether present or not
	 */
	public boolean find(String param){

		if(this.getName().equals(param)){
			System.out.println(this);
			return true;
		}

		for(int i = 0; i < 10; i++){
			if(childArray[i] != null){
				childArray[i].find(param);
			}
		}
		return false;
	}
}
