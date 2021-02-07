package schoolmanager.controller;

import java.util.ArrayList;
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

import schoolmanager.entity.Student;
import schoolmanager.entity.Subject;
import schoolmanager.service.GradeService;
import schoolmanager.service.StudentService;
import schoolmanager.service.SubjectService;

@Controller
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	SubjectService subjectService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	GradeService gradeService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/showSubjects")
	public String showSubjects(Model theModel) {
		
		Set<Subject> theSubjects = subjectService.getSubjects();
		theModel.addAttribute("subjects", sortSubjectsByTitle(theSubjects));
		
		return "subject-list";
	}
	
	@GetMapping("/subjectFormAdd")
	public String addSubject(Model theModel) {
		
		Subject theSubject = new Subject();
		theModel.addAttribute("subject", theSubject);
		
		return "subject-form";
		
	}
	
	@PostMapping("/saveSubject")
	public String saveSubject(@Valid @ModelAttribute("subject") Subject theSubject, BindingResult theBindingResult) {
		
		if(theBindingResult.hasErrors()) {
			return "subject-form";
		}
		subjectService.saveSubject(theSubject);
		return "redirect:/subject/showSubjects";
		
	}
	
	@GetMapping("/subjectUpdateForm")
	public String updateSubjectForm(@RequestParam("subjectId") int theSubjectId, Model theModel) {
		
		Subject theSubject = subjectService.getSubject(theSubjectId);
		if(theSubject==null) {
			return "redirect:/subject/showSubjects";
		}
		theModel.addAttribute("subject", theSubject);
		return "subject-update-form";
	}
	
	@PostMapping("/updateSubject")
	public String updateSubject(@Valid @ModelAttribute("subject") Subject theSubject, BindingResult theBindingResult) {
		
		if(theBindingResult.hasErrors()) {
			return "subject-update-form";
		}
		
		subjectService.updateSubject(theSubject);
		return "redirect:/subject/showSubjects";
		
	}
	
	@GetMapping("/showSubjectDetails")
	public String showSubjectDetails(@RequestParam("subjectId") int theSubjectId, Model theModel) {
			
		Subject theSubject = subjectService.getSubject(theSubjectId);
		
		if(theSubject == null) {
			return "resource-not-exist";
		}
		
		Set<Student> theStudents = theSubject.getStudents();
		Set<Student> theStudentsAll = studentService.getStudents();
		
		theModel.addAttribute("subject", theSubject);
		theModel.addAttribute("students", sortStudentsByLastName(theStudents));
		theModel.addAttribute("studentsAll", sortStudentsByLastName(theStudentsAll));
		
		return "subject-details";
		
	}
	
	@GetMapping("/deleteSubject")
	public String deleteSubject(@RequestParam("subjectId") int theSubjectId) {
	
		subjectService.deleteSubject(theSubjectId);
		return "redirect:/subject/showSubjects";
		
	}
	
	@GetMapping("/removeStudentFromSubject")
	public String removeStudentFromSubject(@RequestParam("subjectId") int theSubjectId, @RequestParam("studentId") int theStudentId) {
		
		subjectService.removeStudentFromSubject(theStudentId, theSubjectId);
		return "redirect:/subject/showSubjectDetails?subjectId=" + theSubjectId;
		
	}
	
	// S O R T I N G
	
	private List<Subject> sortSubjectsByTitle(Set<Subject> subjectsToSort) {
		
		List<Subject> theSubjectsSorted = new ArrayList(subjectsToSort);
		theSubjectsSorted = theSubjectsSorted.stream().sorted((s1, s2) -> s1.getTitle().compareTo(s2.getTitle())).collect(Collectors.toList());
		return theSubjectsSorted;
	}
	
	private List<Student> sortStudentsByLastName(Set<Student> studentsToSort) {
		List<Student> theStudentsSorted = new ArrayList(studentsToSort);
		theStudentsSorted = theStudentsSorted.stream().sorted((s1, s2) -> s1.getLastName().compareTo(s2.getLastName())).collect(Collectors.toList());
		return theStudentsSorted;
	}
	
}
