package edu.epam.fop.presentation.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import edu.epam.fop.model.FormData;

@Controller
public class FormController {

  private static final Logger log = LoggerFactory.getLogger(FormController.class);

  private static final String STUDENT_ATTR = "student";

  @GetMapping({"/", "saveStudent"})
  public ModelAndView showSaveStudentForm(@ModelAttribute(STUDENT_ATTR) FormData student) {
   return new ModelAndView("formTemplate");
  }

  @PostMapping("saveStudent")
  public ModelAndView saveStudent(@ModelAttribute(STUDENT_ATTR) FormData student) {
    log.debug("Student submited by form {}", student);
     return new ModelAndView("resultTemplate");
  }

}
