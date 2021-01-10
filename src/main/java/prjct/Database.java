package prjct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Passive object representing the prjct.Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	private BlockingQueue<Course> courses;
	private BlockingQueue<User> users;
	private BlockingQueue<User> loggedInUsers;
	private Object loginKey;
	private Object regKey;
	private Object getUserBy;
//	private BlockingQueue<String> msgs;
//	private BlockingQueue<String> defaultMsgs;
//	private BlockingQueue<String> errorMsgs;

	private static class SingletonHolder{
		private static Database instance = new Database();
	}

	//to prevent user from creating new prjct.Database
	private Database() {
		courses = new LinkedBlockingQueue<>();
		users = new LinkedBlockingQueue<>();
		loggedInUsers = new LinkedBlockingQueue<>();
		loginKey = new Object();
		regKey = new Object();
		getUserBy = new Object();
//		msgs = new LinkedBlockingQueue<>();
//		defaultMsgs = new LinkedBlockingQueue<>();
//		errorMsgs = new LinkedBlockingQueue<>();
		initialize("./Courses.txt");
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.instance;
	}

	public boolean logMeIn(User toAdd) {
		synchronized (loginKey) {
			if (toAdd == null || loggedInUsers.contains(toAdd))
				return false;
			loggedInUsers.add(toAdd);
			return true;
		}
	}

//	public void addMsg (String msg) {
//		try {
//			this.msgs.put(msg);
//		} catch (InterruptedException exception) {
//			exception.printStackTrace();
//		}
//	}
//
//	public void addDefMsg (String msg) {
//		try {
//			this.defaultMsgs.put(msg);
//		} catch (InterruptedException exception) {
//			exception.printStackTrace();
//		}
//	}
//
//	public void addErrMsg (String msg) {
//		try {
//			this.errorMsgs.put(msg);
//		} catch (InterruptedException exception) {
//			exception.printStackTrace();
//		}
//	}

	public boolean logMeOut(User toRemove) {
		synchronized (loginKey) {
			if (toRemove == null || !loggedInUsers.contains(toRemove))
				return false;
			loggedInUsers.remove(toRemove);
			return true;
		}
	}

	public boolean addUser(User u){
		synchronized (regKey) {
			if (users.stream().noneMatch(us -> us.getUsername().equals(u.getUsername()))) {
				try {
					users.put(u);
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
				return true;
			}
			return false;
		}
	}

	public User getUserByUsername(String username) {
		synchronized (getUserBy) {
			return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
		}
	}

	public Course getCourseByNum(int courseNum) {
		return courses.stream().filter(c -> c.getNum() == courseNum).findFirst().orElse(null);
	}

	public BlockingQueue<Course> getCourses() {
		return courses;
	}

	public BlockingQueue<User> getUsers() {
		return users;
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
				if(line.equals("")) continue;
				int iend = line.indexOf('|');
				int cNum = Integer.parseInt(line.substring(0, iend));

				line = line.substring(iend + 1).trim();
				iend = line.indexOf('|');
				String cName = line.substring(0, iend);

				line = line.substring(iend + 2).trim();
				iend = line.indexOf(']');
				String[] strKdamim = line.substring(0, iend).split(",");
				int tempLength = strKdamim.length;
				if (strKdamim.length == 1 && strKdamim[0].equals(""))
					tempLength = 0;
				int[] kdamim = new int[tempLength];
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

}
