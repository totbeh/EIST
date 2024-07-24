package de.tum.in.ase.eist;

import java.util.List;

public class Lecturer extends Person {

	private List<Course> courses;
	private int employeeId;

	public Lecturer(String firstName, String lastName, String birthDate) {
		super(firstName, lastName, birthDate);
	}

	@Override
	public void printPersonalInformation() {
		System.out.println("Lecturer: " + getFirstName() + " " + getLastName() + " " + getBirthDate() + " " + employeeId);
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
