package schoolmanager.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="subject")
public class Subject {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message="Title is required.")
	@Size(min=1, message="Title is required.")
	@Column(name="title")
	private String title;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="subject", cascade=CascadeType.ALL)
	private Set<Grade> grades;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="subject_student", joinColumns=@JoinColumn(name="subject_id"), inverseJoinColumns=@JoinColumn(name="student_id"))
	private Set<Student> students;
	
	@Transient
	private List<Grade> gradesSorted;
	
	public Subject() {
		
	}
	
	public Subject(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	
	public String toString() {
		return "SUBJECT #" +this.id + " " + this.title;
	}
	
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	///////////// MANY TO MANY STUDENTS
	
	//ADD STUDENT TO SUBJECT
	public void addStudent(Student theStudent) {
		if(students == null) {
			students = new HashSet<Student>();
		}
		students.add(theStudent);
	}
	
	//REMOVE STUDENT FROM SUBJECT
	public void removeStudent(Student theStudent) {
		if(students != null) {
			students.remove(theStudent);
		}
	}
	
	///////////// ONE TO MANY GRADE

	public void addGrade(Grade theGrade) {
		if(grades == null) {
			grades = new HashSet<Grade>();
		}
		grades.add(theGrade);
	}
	
	public void removeGrade(Grade theGrade) {
		if(grades != null) {
			grades.remove(theGrade);
		}
	}
	
	public List<Grade> getGradesSorted() {
		List<Grade> theGradesSorted = new ArrayList(this.getGrades());
		theGradesSorted = theGradesSorted.stream().sorted((s1, s2) -> s1.getComment().compareTo(s2.getComment())).collect(Collectors.toList());
		return theGradesSorted;
	}
	
}
