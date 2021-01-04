package prjct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Passive object representing the prjct.Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	private List<Course> courses;
	private List<User> users;

	private static class SingletonHolder{
		private static Database instance = new Database();
	}

	//to prevent user from creating new prjct.Database
	private Database() {
		courses = new ArrayList<>();
		users = new ArrayList<>();
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
		try (BufferedReader br = new BufferedReader(new FileReader(coursesFilePath))) {
			int courseOrdNum = 0;
			for (String line; (line = br.readLine()) != null; ) {
				line = line.trim();
				int iend = line.indexOf('|');
				int cNum = Integer.parseInt(line.substring(0, iend));

				line = line.substring(iend + 1).trim();
				iend = line.indexOf('|');
				String cName = line.substring(0, iend);

				line = line.substring(iend + 2).trim();
				iend = line.indexOf(']');
				String[] strKdamim = line.substring(0, iend).split(",");
				int[] kdamim = new int[strKdamim.length];
				for (int i = 0; i < kdamim.length; i++)
					kdamim[i] = Integer.parseInt(strKdamim[i].trim());

				line = line.substring(iend + 2).trim();
				int maxStuds = Integer.parseInt(line);
				Course c = new Course(cNum, cName, kdamim, maxStuds, courseOrdNum);
				courseOrdNum++;
				courses.add(c);
			}
			// line is not visible here.
		} catch (Exception e) { return false; }

		return true;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public List<User> getUsers() {
		return users;
	}
}
