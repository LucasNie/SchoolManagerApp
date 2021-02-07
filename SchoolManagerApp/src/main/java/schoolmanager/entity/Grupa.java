package schoolmanager.entity;

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="grupa")
public class Grupa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message="Description is required.")
	@Size(min=1, message="Description is required.")
	@Column(name="description")
	private String description;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="grupa_student", joinColumns=@JoinColumn(name="grupa_id"), inverseJoinColumns=@JoinColumn(name="student_id"))
	private Set<Student> students;
	
	public Grupa( ) {
		
	}

	public Grupa(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	//ADD STUDENT TO GRUPA
	public void addStudent(Student theStudent) {
		if(students == null) {
			students = new HashSet<Student>();
		}
		students.add(theStudent);
	}
	
	//REMOVE STUDENT FROM GRUPA
	public void removeStudent(Student theStudent) {
		if(students != null) {
			students.remove(theStudent);
		}
	}
	
	public String toString() {
		return "Grupa: " + this.description;
	}
	
}
