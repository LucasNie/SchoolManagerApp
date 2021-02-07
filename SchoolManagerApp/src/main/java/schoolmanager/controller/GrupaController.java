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

import schoolmanager.entity.Grupa;
import schoolmanager.entity.Student;
import schoolmanager.service.GrupaService;
import schoolmanager.service.StudentService;


@Controller
@RequestMapping("/group")
public class GrupaController {
	
	@Autowired
	GrupaService grupaService;
	
	@Autowired
	StudentService studentService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/showGroups")
	public String showGroups(Model theModel) {
		
		Set<Grupa> theGroups = grupaService.getGrupas();
		theModel.addAttribute("groups", sortGroupsByDescription(theGroups));
		
		return "group-list";
	}
	
	@GetMapping("/groupFormAdd")
	public String addGroup(Model theModel) {
		
		Grupa theGrupa = new Grupa();
		theModel.addAttribute("group", theGrupa);
		
		return "group-form";
		
	}
	
	@PostMapping("/saveGroup")
	public String saveGroup(@Valid @ModelAttribute("group") Grupa theGrupa, BindingResult theBindingResult) {
		
		if(theBindingResult.hasErrors()) {
			return "group-form";
		}
		grupaService.saveGrupa(theGrupa);
		return "redirect:/group/showGroups";
		
	}
	
	@GetMapping("/groupUpdateForm")
	public String updateGroupForm(@RequestParam("groupId") int theGroupId, Model theModel) {
		
		Grupa theGrupa = grupaService.getGrupa(theGroupId);
		if(theGrupa==null) {
			return "redirect:/group/showGroups";
		}
		theModel.addAttribute("group", theGrupa);
		return "group-update-form";
	}
	
	@PostMapping("/updateGroup")
	public String updateGroup(@Valid @ModelAttribute("group") Grupa theGrupa, BindingResult theBindingResult) {
		
		if(theBindingResult.hasErrors()) {
			return "group-update-form";
		}
		
		grupaService.updateGrupa(theGrupa);
		return "redirect:/group/showGroups";
		
	}
	
	@GetMapping("/deleteGroup")
	public String deleteGroup(@RequestParam("groupId") int theGroupId) {
		
		grupaService.deleteGrupa(theGroupId);
		return "redirect:/group/showGroups";
		
	}
	
	@GetMapping("/showGroupDetails")
	public String showGroupDetails(@RequestParam("groupId") int theGroupId, Model theModel) {
			
		Grupa theGroup = grupaService.getGrupa(theGroupId);
		
		if(theGroup == null) {
			return "resource-not-exist";
		}
		
		Set<Student> theStudents = theGroup.getStudents();
		Set<Student> theStudentsAll = studentService.getStudents();
		
		//TO-DO: LOADING STUDENTS OUT OF THE GROUP
		
		theModel.addAttribute("group", theGroup);
		theModel.addAttribute("students", sortStudentsByLastName(theStudents));
		theModel.addAttribute("studentsAll", sortStudentsByLastName(theStudentsAll));
		
		return "group-details";
		
	}
	
	@GetMapping("/removeStudentFromGroup")
	public String removeStudentFromGroup(@RequestParam("groupId") int theGroupId, @RequestParam("studentId") int theStudentId) {
		
		grupaService.removeStudentFromGrupa(theStudentId, theGroupId);
		return "redirect:/group/showGroupDetails?groupId=" + theGroupId;
		
	}
	
	private List<Grupa> sortGroupsByDescription(Set<Grupa> groupsToSort) {
		
		List<Grupa> theGroupsSorted = new ArrayList(groupsToSort);
		theGroupsSorted = theGroupsSorted.stream().sorted((s1, s2) -> s1.getDescription().compareTo(s2.getDescription())).collect(Collectors.toList());
		return theGroupsSorted;
	}
	
	private List<Student> sortStudentsByLastName(Set<Student> studentsToSort) {
		List<Student> theStudentsSorted = new ArrayList(studentsToSort);
		theStudentsSorted = theStudentsSorted.stream().sorted((s1, s2) -> s1.getLastName().compareTo(s2.getLastName())).collect(Collectors.toList());
		return theStudentsSorted;
	}

}
