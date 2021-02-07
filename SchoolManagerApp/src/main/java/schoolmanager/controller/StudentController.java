package schoolmanager.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import schoolmanager.entity.Grupa;
import schoolmanager.entity.Role;
import schoolmanager.entity.Student;
import schoolmanager.entity.Subject;
import schoolmanager.service.GrupaService;
import schoolmanager.service.StudentService;
import schoolmanager.service.SubjectService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	GrupaService grupaService;
	
	@Autowired
	SubjectService subjectService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
	
	@GetMapping("/showStudents")
	public String showStudents(Model theModel) {
		
		Set<Student> theStudents = studentService.getStudents();
		Set<Student> onlyStudents = getOnlyStudents(theStudents);
		Set<Student> onlyTeachers = getOnlyTeachers(theStudents);
		
		theModel.addAttribute("students", sortStudentsByLastName(onlyStudents));
		theModel.addAttribute("teachers", sortStudentsByLastName(onlyTeachers));
		//theModel.addAttribute("students", sortStudentsByLastName(theStudents));
				
		return "student-list";
	}
	
	@GetMapping("/studentFormAdd")
	public String addStudent(Model theModel) {
		
		Student theStudent = new Student();
		theModel.addAttribute("student", theStudent);
		
		return "student-form";
		
	}
	
	@PostMapping("/saveStudent")
	public String saveStudent(@Valid @ModelAttribute("student") Student theStudent, 
								BindingResult theBindingResult, 
								Model theModel, 
								@RequestParam (name="thisIsTeacher", defaultValue="false") boolean thisIsTeacher) {
		
		if(theBindingResult.hasErrors()) {
			return "student-form";
		}
		
		Student existing = studentService.findByUsername(theStudent.getUsername());
		if(existing != null) {
			theModel.addAttribute("student", theStudent);
			theModel.addAttribute("registrationErrorUsername", "User with this username is already in the database.");
			return "student-form";
		}
		
		existing = studentService.findByEmail(theStudent.getEmail());
		if(existing != null) {
			theModel.addAttribute("student", theStudent);
			theModel.addAttribute("registrationErrorEmail", "User with this email is already in the database.");
			return "student-form";
		}
		
		if(thisIsTeacher) {
			theStudent.addRole(new Role("ROLE_TEACHER"));
		}
		studentService.saveStudent(theStudent);
		return "redirect:/student/showStudents";
	}
	
	@GetMapping("/studentUpdateForm")
	public String updateStudentForm(@RequestParam("studentId") int theStudentId, Model theModel) {
		
		Student theStudent = studentService.getStudent(theStudentId);
		if(theStudent==null) {
			return "redirect:/student/showStudents";
		}
		//removing {noop} prefix from password
		theStudent.setPassword(theStudent.getPassword().substring(6));
		theModel.addAttribute("student", theStudent);
		theModel.addAttribute("originalEmail", theStudent.getEmail());
		
		return "student-update-form";
		
	}
	
	@PostMapping("/updateStudent")
	public String updateStudent(@Valid @ModelAttribute("student") Student theStudent, BindingResult theBindingResult, Model theModel, @RequestParam("originalEmail") String originalEmail) {
		
		if(theBindingResult.hasErrors()) {
			return "student-update-form";
		}
		
		Student existing = studentService.findByEmail(theStudent.getEmail());
		
		if(existing != null && !theStudent.getEmail().equals(originalEmail)) {
			
			theModel.addAttribute("originalEmail", originalEmail);
			theModel.addAttribute("student", theStudent);
			theModel.addAttribute("registrationErrorEmail", "User with this email is already in the database.");
			return "student-update-form";
			
		}
		
		studentService.updateStudent(theStudent);
		return "redirect:/student/showStudents";
	}
	
	@GetMapping("/deleteStudent")
	public String deleteStudent(@RequestParam("studentId") int theStudentId) {
		
		studentService.deleteStudent(theStudentId);
		return "redirect:/student/showStudents";
		
	}
	
	@GetMapping("/showStudentDetails")
	public String showStudentDetails(@RequestParam("studentId") int theStudentId, Model theModel) {
		
		Student theStudent = studentService.getStudent(theStudentId);
		if(theStudent == null) {
			return "resource-not-exist";
		}
		
		Set<Grupa> theGroups = theStudent.getGrupas();
		Set<Grupa> theGroupsAll = grupaService.getGrupas();
		
		Set<Subject> theSubjects = theStudent.getSubjects();
		Set<Subject> theSubjectsAll = subjectService.getSubjects();
		
		theModel.addAttribute("student", theStudent);
		theModel.addAttribute("groups", sortGroupsByDescription(theGroups));
		theModel.addAttribute("groupsAll", sortGroupsByDescription(theGroupsAll));
		
		theModel.addAttribute("subjects", sortSubjectsByTitle(theSubjects));
		theModel.addAttribute("subjectsAll", sortSubjectsByTitle(theSubjectsAll));
		
		return "student-details";
		
	}
	
	@GetMapping("/addStudentToGroup")
	public String addStudentToGroup(@RequestParam("groupChosen") int theGroupChosen, 
									@RequestParam("studentId") int theStudentId,
									@RequestParam("requestFromGroupDetails") boolean requestFromGroupDetails) {
		
		studentService.addToGrupa(theStudentId, theGroupChosen);
		if(requestFromGroupDetails) {
			return "redirect:/group/showGroupDetails?groupId=" + theGroupChosen;
		}
		return "redirect:/student/showStudentDetails?studentId=" + theStudentId;
		
	}
	
	@GetMapping("/addStudentToSubject")
	public String addStudentToSubject(@RequestParam("subjectChosen") int theSubjectChosen,
									  @RequestParam("studentId") int theStudentId,
									  @RequestParam("requestFromSubjectDetails") boolean requestFromSubjectDetails){
		
		studentService.addToSubject(theStudentId, theSubjectChosen);
		if(requestFromSubjectDetails) {
			return "redirect:/subject/showSubjectDetails?subjectId=" + theSubjectChosen;
		}
		return "redirect:/student/showStudentDetails?studentId=" + theStudentId;
		
	}
	
	//S O R T I N G
	
	private List<Student> sortStudentsByLastName(Set<Student> studentsToSort) {
		List<Student> theStudentsSorted = new ArrayList(studentsToSort);
		theStudentsSorted = theStudentsSorted.stream().sorted((s1, s2) -> s1.getLastName().compareTo(s2.getLastName())).collect(Collectors.toList());
		return theStudentsSorted;
	}
	
	private List<Grupa> sortGroupsByDescription(Set<Grupa> groupsToSort) {
		List<Grupa> theGroupsSorted = new ArrayList(groupsToSort);
		theGroupsSorted = theGroupsSorted.stream().sorted((s1, s2) -> s1.getDescription().compareTo(s2.getDescription())).collect(Collectors.toList());
		return theGroupsSorted;
	}
	
	private List<Subject> sortSubjectsByTitle(Set<Subject> subjectsToSort) {
		List<Subject> theSubjectsSorted = new ArrayList(subjectsToSort);
		theSubjectsSorted = theSubjectsSorted.stream().sorted((s1, s2) -> s1.getTitle().compareTo(s2.getTitle())).collect(Collectors.toList());
		return theSubjectsSorted;
	}
	
	private Set<Student> getOnlyStudents(Set<Student> studentsAll) {
		
		Set<Student> onlyStudents = new HashSet<Student>();
		for(Student tempStudent : studentsAll) {
			if(tempStudent.isStudent()) {
				onlyStudents.add(tempStudent);
			} 
		}
		return onlyStudents;
	}
	
	private Set<Student> getOnlyTeachers(Set<Student> studentsAll) {
		
		Set<Student> onlyTeachers = new HashSet<Student>();
		for(Student tempStudent : studentsAll) {
			if(tempStudent.isTeacher()) {
				onlyTeachers.add(tempStudent);
			} 
		}
		return onlyTeachers;
	}

}
