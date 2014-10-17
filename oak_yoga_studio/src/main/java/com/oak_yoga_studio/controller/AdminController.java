/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oak_yoga_studio.controller;

import com.oak_yoga_studio.domain.Course;
import com.oak_yoga_studio.domain.Credential;
import com.oak_yoga_studio.domain.Faculty;
import com.oak_yoga_studio.service.ICourseService;
import com.oak_yoga_studio.service.ICustomerService;
import com.oak_yoga_studio.service.IFacultyService;
import com.oak_yoga_studio.service.INotificationService;
import com.oak_yoga_studio.service.ISectionService;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Weldino
 */
@Controller
public class AdminController {
    
    @Resource
    private INotificationService notificationService;
    @Resource
    private ICourseService courseService;
    @Resource
    private ISectionService sectionService;
//    @Resource
//    private IProductService productServcie;
    @Resource
    private IFacultyService facultyServcie;
    @Resource
    private ICustomerService customerService;
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(){
        return "admin";
    }
    
    
    @RequestMapping(value = "/addCourse", method = RequestMethod.GET)
    public String addCourse(@ModelAttribute("course") Course course, HttpSession session) {
        //customer.setCredential((Credential) session.getAttribute("credential"));
        //System.out.println("hello signup");
        return "addCourse";
    }
    
    @RequestMapping(value="/addCourse", method=RequestMethod.POST)
    public String addCourse(@Valid Course course,BindingResult result,HttpSession session){
        String view="redirect:/index";
                if(!result.hasErrors()){
                    course.setActive(true);
                    courseService.addCourse(course);
                   
                }else{
                     view="addCourse";
                }
                return view;
    }
    
    @RequestMapping(value = "/viewCustomers", method = RequestMethod.GET)
    public String getAllCustomers(Model model,HttpSession session) {
        
       //TODO use session on login
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customersList";
    }
    
    @RequestMapping(value = "/customerDetail/{id}", method = RequestMethod.GET)
    public String getUserDetail(Model model, @PathVariable int id) {
        model.addAttribute("customerDetail", customerService.getCustomerById(id));
        return "customerDetail";
    }
    
    
  /**
   * ADD FACULTY
   * 
   * @param credential
   * @return 
   */  
    
    @RequestMapping(value = "/addFacultyCredential", method = RequestMethod.GET)
    public String addCredential(@ModelAttribute("credential") Credential credential) {
        credential.setRole("Please don't change");
        return "addFacultyCredential";
    }

    @RequestMapping(value = "/addFacultyCredential", method = RequestMethod.POST)
    public String addCredential(@Valid Credential credential, BindingResult result, HttpSession session) {
        String view = "redirect:/addFaculty";
        if (!result.hasErrors()) {
            //Faculty f = (Faculty) session.getAttribute("loggedUser");
            credential.setRole("ROLE_FACULTY");
            credential.setActive(true);
            session.setAttribute("credential", credential);
        } else {
            view = "addFacultyCredential";
        }
        return view;
    }
    
    
    @RequestMapping(value = "/addFaculty", method = RequestMethod.GET)
    public String addFaculty(@ModelAttribute("faculty") Faculty faculty, HttpSession session) {
        faculty.setCredential((Credential) session.getAttribute("credential"));
        
        System.out.println("hello addFaculty GET");
        return "addFaculty";
    }

    @RequestMapping(value = "/addFaculty", method = RequestMethod.POST)
    public String addFaculty(Faculty faculty, BindingResult result, HttpSession session, RedirectAttributes flashAttr, @RequestParam("file") MultipartFile file) {
        String view = "redirect:/admin";
        System.out.println("add faculty Add outside");

        if (!result.hasErrors()) {
            try {
                faculty.setProfilePicture(file.getBytes());
            } catch (IOException ex) {
                //Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            faculty.setActive(true);
            facultyServcie.addFaculty(faculty);
            session.removeAttribute("credential");
            flashAttr.addFlashAttribute("successful registered", "Faculty signed up succesfully. please  log in to proceed"); //           Customer c=(Customer) session.getAttribute("loggedCustomer");

        } else {
            for (FieldError err : result.getFieldErrors()) {
                System.out.println("Error:" + err.getField() + ":" + err.getDefaultMessage());
            }
            view = "addFaculty";
        }
        return view;
    }
    
    /**
     * VIEW FACULTIES
     */
    
    @RequestMapping(value = "/viewFaculties" , method = RequestMethod.GET)
    public String getAllFaculties(Model model,HttpSession session) {
       //TODO use session on login
        model.addAttribute("faculties", facultyServcie.getListOfFaculty());
        return "viewFaculties";
    }
    
    /**
     * ENABLE OR DISABLE FACULTY
     */
    
     @RequestMapping(value = "/faculties/{id}/{operation}", method = RequestMethod.GET)
    public String EnableDisable(@PathVariable("id") int facultyId, @PathVariable("operation") String operation) {

        Faculty f=facultyServcie.getFacultyById(facultyId);
        if ("enable".equalsIgnoreCase(operation)) {
            System.out.println("enabled");
            f.getCredential().setActive(true);
            //f.getCredential().setUserName(f.getCredential().getUserName());
        } else {
            System.out.println("disabled");
            f.getCredential().setActive(false);
        }
        facultyServcie.updateFaculty(facultyId,f);
        //userService.updateUserInfo(userId, u);
        return "redirect:/viewFaculties";
    }
}