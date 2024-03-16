package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

/**
 * @author tcarrillo
 * 
 */
public class ProjectsApp {
	
	
//use a Scanner to obtain input from a user from the Java console.
	
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	
	
// code that holds the list of operations.
// private instance variable named "operations"	
// The type is List<String>.	
	
	//@formatter:off 
	private List<String> operations = List.of(
			"1) Add a project",	
			"2) List projects",
			"3) Select a project"
			);
	//formatter:on
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
// method that processes the menu. 
		
		new ProjectsApp().processUserSelections();
		
		
	}
	
//This method displays the menu selections, gets a selection from the user, and then acts on the selection.
	
	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
			try {
			  int selection = getUserSelection();
			  
// method to collect project details and save them in the project table. 
			  
			 switch (selection) {
			 	case -1:
			 		done = exitMenu();
			 		break;
			 		
			 	case 1:
			 		createProject();
			 		break;
			 	
			 	case 2:
			 		listProjects();
			 		break;
			 		
			 	case 3:
			 		selectProject();
			 		break;		
			 		
			 	default:
			 		System.out.println("\n" + selection + " is not a valid selection.Try again.");
			 }
			  
			}
			catch (Exception e) {
				System.out.println("\nError: " + e + " Try again.");
				
			}
		}
		
	}
	
    private void selectProject() {
    	listProjects();
    	Integer projectId = getIntInput("Enter a project ID to select a project");
    	
    	curProject = null;
    	
    	curProject = projectService.fetchProjectById(projectId);
    	
	
}

	private void listProjects() {
	List<Project> projects = projectService.fetchAllProjects();
	
	System.out.println("\nProjects:");
	
	projects.forEach(project -> System.out.println(" " + project.getProjectId() + ": " + project.getProjectName()));
	
}

// method to gather the project details from the user.
	
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer dificulty = getIntInput("Enter the project difficulty(1-5)");
		String notes = getStringInput("Enter project notes");
		
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(dificulty);
		project.setNotes(notes);
		
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
		
		
		
	}
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a decimal number.");
			
		}
	}
	
	
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}
	
// This method will print the operations and then accept user input as an Integer.
	
	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		
		return Objects.isNull(input) ? -1 : input;
	}
	
// This method accepts input from the user and converts it to an Integer, which may be null.
	
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
			
		}	
	}
	
// the method that really prints the prompt and gets the input from the user.
	
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();	
	}
	
// This method does just what it says, it prints each available selection on a separate line in the console.
	
	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		
		operations.forEach(line -> System.out.println(" " + line));
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
		
	}

}
