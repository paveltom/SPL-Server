package prjct;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Passive object representing the prjct.Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	/*
	*structure that holds courses info: id, name, [kdamim], currNumOfStuds ,maxStudents
	* structure for registered students and admins: username, password, list of courseIds
	*
	 */

	private static class SingletonHolder{
		private static Database instance = new Database();
	}

	//to prevent user from creating new prjct.Database
	private Database() {
		// TODO: implement
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the prjct.Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		// TODO: implement
		//read each line from the file in the path
		// each line = create instance of a course considering line information

		try(BufferedReader br = new BufferedReader(new FileReader(coursesFilePath))) {
			for(String line; (line = br.readLine()) != null; ) {
				/* from line get:
				* course id
				* course name
				* course kdamim
				* course maxStudents
				 */
			}
			// line is not visible here.
		} catch (Exception e) {}

		return false;
	}


}
