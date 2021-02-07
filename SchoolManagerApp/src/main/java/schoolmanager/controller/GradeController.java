package schoolmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import schoolmanager.entity.Grade;
import schoolmanager.service.GradeService;

@Controller
@RequestMapping("/grade")
public class GradeController {
	
	@Autowired
	GradeService gradeService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/gradeStudent")
	public String gradeStudent(@RequestParam ("studentIdToGrade") int theStudentId, 
								@RequestParam("gradeMark") int gradeMark, 
								@RequestParam("gradeComment") String gradeComment, 
								@RequestParam("subjectId") int theSubjectId) {
				
		gradeService.gradeStudent(theStudentId, gradeMark, gradeComment, theSubjectId);
		return "redirect:/subject/showSubjectDetails?subjectId=" + theSubjectId;
		
	}
	
	@GetMapping("/showGradeDetails")
	public String showGradeDetails(@RequestParam ("gradeId") int gradeId, @RequestParam ("fromStudent") boolean fromStudent, Model theModel) {
		
		Grade theGrade = gradeService.getGrade(gradeId);
		if(theGrade == null) {
			return "resource-not-exist";
		}
		
		theModel.addAttribute("grade", theGrade);
		theModel.addAttribute("fromStudent", fromStudent);
		return "grade-details";
	}
	
	@PostMapping("/deleteGrade")
	public String deleteGrade(@RequestParam ("gradeId") int gradeId, @RequestParam ("subjectId") int subjectId, @RequestParam ("studentId") int studentId, @RequestParam ("fromStudent") boolean fromStudent) {
		gradeService.deleteGrade(gradeId);
		
		//CHECKING THE SOURCE OF REQUEST
		if(fromStudent) {
			return "redirect:/student/showStudentDetails?studentId=" + studentId;
		} else {
			return "redirect:/subject/showSubjectDetails?subjectId=" + subjectId;
		}
	}
	
}
