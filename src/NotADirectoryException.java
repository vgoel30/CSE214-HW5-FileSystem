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
 * Custom NotADirectoryException
 * @author varungoel
 *Thrown when node is a file
 */
public class NotADirectoryException extends Exception {

	public NotADirectoryException(){
		System.out.println("This isn't a directory. You can add or cd only to a directory");
	}
}

