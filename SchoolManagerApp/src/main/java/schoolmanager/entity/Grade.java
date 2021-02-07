package schoolmanager.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="grade")
public class Grade {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="mark")
	private int mark;
	
	@Column(name="comment")
	private String comment;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="subject_id")
	private Subject subject;
	
	public Grade() {
		
	}

	public Grade(int mark, String comment) {
		this.mark = mark;
		this.comment = comment;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String toString() {
		return "[" + this.mark + " " + this.comment + " " + this.getSubject().getTitle() + "] ";
	}
	
	//ADDING GRADE AND BINDING IT TO STUDENT AND SUBJECT
	
	public void addGrade(Subject theSubject, Student theStudent) {
		
		this.setSubject(theSubject);
		this.setStudent(theStudent);
		theSubject.addGrade(this);
		theStudent.addGrade(this);
		
	}
	
	//REMOVING GRADE AND FIRSTLY DISCONNECTING IT FROM STUDENT AND SUBJECT
	public void removeGrade() {
		
		this.getSubject().removeGrade(this);
		this.getStudent().removeGrade(this);
		
		this.setSubject(null);
		this.setStudent(null);
		
	}
	
}
