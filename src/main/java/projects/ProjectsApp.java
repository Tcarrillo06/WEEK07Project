/**
 * 
 */
package projects;

import projects.dao.DbConnection;

/**
 * @author tcarrillo
 * 
 */
public class ProjectsApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbConnection.getConnection();
	}

}
