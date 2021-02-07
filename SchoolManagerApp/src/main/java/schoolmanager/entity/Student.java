package schoolmanager.entity;

import java.util.ArrayList;
import java.util.Comparator;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="student")
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message="Username is required.")
	@Size(min=1, message="Username is required.")
	@Pattern(regexp="^[a-z]{4}", message="Username should consist of four lowercase letters.")
	@Column(name="username")
	private String username;
	
	@NotNull(message="Password is required.")
	@Size(min=1, message="Password is required.")
	@Column(name="password")
	private String password;
	
	@NotNull(message="First Name is required.")
	@Size(min=1, message="First Name is required.")
	@Column(name="first_name")
	private String firstName;
	
	@NotNull(message="Last Name is required.")
	@Size(min=1, message="Last Name is required.")
	@Column(name="last_name")
	private String lastName;
	
	@NotNull(message="Email is required.")
	@Size(min=1, message="Email is required.")
	@Column(name="email")
	private String email;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="student", cascade=CascadeType.ALL)
	private Set<Grade> grades;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="grupa_student", joinColumns=@JoinColumn(name="student_id"), inverseJoinColumns=@JoinColumn(name="grupa_id"))
	private Set<Grupa> grupas;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="subject_student", joinColumns=@JoinColumn(name="student_id"), inverseJoinColumns=@JoinColumn(name="subject_id"))
	private Set<Subject> subjects;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="student_id")
	private List<Role> roles;
	
	@Transient
	private List<Grade> gradesSorted;
	
	@Transient
	private List<Subject> subjectsSorted;
	
	@Transient
	private List<Grupa> grupasSorted;
	
	public Student() {
		
	}

	public Student(String username, String password, String firstName, String lastName, String email) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
		for(Grade tempGrade : grades) {
			tempGrade.setStudent(this);
		}
	}

	public Set<Grupa> getGrupas() {
		return grupas;
	}

	public void setGrupas(Set<Grupa> grupas) {
		this.grupas = grupas;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	
	/////////////// ONE TO MANY GRADE

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
	
	public void addRole(Role theRole) {
		if(roles == null) {
			roles = new ArrayList<Role>();
		}
		roles.add(theRole);
	}
	
	////////////////////////////////
	
	@Override
	public String toString() {
		return "STUDENT #" + this.id + " " + this.firstName + " " + this.lastName + " " + this.email;
	}
	
	public void getDetails() {
		
		System.out.println("STUDENT #" + this.id + " " + this.firstName + " " + this.lastName + " " + this.email);
		System.out.println("GRUPY: ");
		for(Grupa tempGrupa : this.grupas) {
			System.out.println(tempGrupa.toString());
		}
		System.out.println("PRZEDMIOTY: ");
		for(Subject tempSubject : this.subjects) {
			System.out.println(tempSubject.toString());
		}
		for(Grade tempGrade : this.grades) {
			System.out.println(tempGrade.toString());
		}
		
	}
	
	public List<Grade> getGradesLessThan (int x) {
		List<Grade> gradesLessThan = new ArrayList<Grade>();
		for(Grade tempGrade : this.grades) {
			if(Integer.parseInt(tempGrade.getComment()) < x) {
				gradesLessThan.add(tempGrade);
			}
		}
		return gradesLessThan;
	}
	
	//GRADE SORTING
	
	public List<Grade> getGradesSorted() {
		List<Grade> theGradesSorted = new ArrayList(this.getGrades());
		theGradesSorted = theGradesSorted.stream().sorted(Comparator.comparingInt(Grade::getMark)).collect(Collectors.toList());
		return theGradesSorted;
	}
	
	//GRUPA SORTING
	
	public List<Grupa> getGrupasSorted() {
		List<Grupa> theGrupasSorted = new ArrayList(this.getGrupas());
		theGrupasSorted = theGrupasSorted.stream().sorted((s1, s2) -> s1.getDescription().compareTo(s2.getDescription())).collect(Collectors.toList());
		return theGrupasSorted;
	}
	
	//SUBJECT SORTING
	
	public List<Subject> getSubjectsSorted() {
		List<Subject> theSubjectsSorted = new ArrayList(this.getSubjects());
		theSubjectsSorted = theSubjectsSorted.stream().sorted((s1, s2) -> s1.getTitle().compareTo(s2.getTitle())).collect(Collectors.toList());
		return theSubjectsSorted;
	}
	
	//IS A TEACHER?
	public boolean isTeacher() {
		boolean isTeacher = false;
		for(Role tempRole : this.roles) {
			if(tempRole.getRole().equals("ROLE_TEACHER")) {
				isTeacher = true;
				return isTeacher;
			}
		}
		return isTeacher;
	}
	
	//IS A STUDENT?
	public boolean isStudent() {
		return !isTeacher();
	}
	
	//IS A ADMIN?
	public boolean isAdmin() {
		boolean isAdmin = false;
		for(Role tempRole : this.roles) {
			if(tempRole.getRole().equals("ROLE_ADMIN")) {
				isAdmin = true;
				return isAdmin;
			}
		}
		return isAdmin;
	}
	
}
