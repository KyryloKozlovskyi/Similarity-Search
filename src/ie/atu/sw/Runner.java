package ie.atu.sw;

/**
 * @author Kyrylo Kozlovskyi G00425385
 * @version 1.0
 * @since JavaSE-17
 */

/**
 * Main Runner class to run the code.
 */
public class Runner {

	private static Menu mainMenu = new Menu(); // Instance of the Menu class to access the runMenu() method.

	/**
	 * runMenu() call.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		mainMenu.runMenu(); // runMenu() call. Runs the menu loop.
	}

}