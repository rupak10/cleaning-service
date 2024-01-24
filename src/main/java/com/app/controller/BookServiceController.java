package com.app.controller;


import com.app.dto.AppResponse;
import com.app.dto.BookingDTO;
import com.app.service.BookingService;
import com.app.service.UserService;
import com.app.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/book-service")
public class BookServiceController {
    private final Logger log = LoggerFactory.getLogger(BookServiceController.class);
    private final String ADD_PAGE = "book-service/add";
    private final String EDIT_PAGE = "book-service/edit";
    private final String LIST_PAGE = "book-service/list";
    private final String REDIRECT_TO_LIST_PAGE = "redirect:/book-service";
    private final String ACTIVE_MENU = "book-service";

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String loadListPage(Model model, HttpSession httpSession) {
        log.info("Entering loadListPage() method");
        model.addAttribute("pageTitle", "Booking List");

        //model.addAttribute("bookingList", calorieService.getCalorieInfoList(CommonUtil.getUserFromSession(httpSession)));
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadListPage() method");
        return LIST_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String loadAddPage(Model model, HttpSession httpSession) {
        log.info("Entering loadAddPage() method");

        model.addAttribute("pageTitle", "Booking Add");
        model.addAttribute("bookingDTO", new BookingDTO());

        model.addAttribute("cleanerList", userService.getActiveCleanerList());
        model.addAttribute("cleaningTypeList", bookingService.getCleaningTypeList());
        model.addAttribute("serviceTypeList", bookingService.getServiceTypeList());

        model.addAttribute("am", ACTIVE_MENU);
        log.info("Exiting loadAddPage() method");
        return ADD_PAGE;
    }

    /*
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCalorie(Model model, HttpSession httpSession, @ModelAttribute CalorieDTO calorieDTO,
                             final RedirectAttributes redirectAttributes) {
        log.info("Entering addCalorie() method");
        log.info("calorieDTO request:"+calorieDTO);

        AppResponse appResponse = calorieService.addCalorie(calorieDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            log.info("Exiting addCalorie() method");
            return REDIRECT_CALORIE_LIST_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("calorie", calorieDTO);
            log.info("Exiting addCalorie() method");
            return CALORIE_ADD_PAGE;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String loadCalorieEditPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        log.info("Entering loadCalorieEditPage() method");
        log.info("Calorie ID : "+id);

        CalorieDTO calorieDTO = calorieService.fetchCalorieById(id);

        model.addAttribute("pageTitle", "Edit Calorie");
        model.addAttribute("calorie", calorieDTO);
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadCalorieEditPage() method");
        return CALORIE_EDIT_PAGE;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editCalorie(Model model, @ModelAttribute CalorieDTO calorieDTO, HttpSession httpSession,
                              final RedirectAttributes redirectAttributes) {
        log.info("Entering editCalorie() method");

        AppResponse appResponse = calorieService.updateCalorieInfo(calorieDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            log.info("Exiting editCalorie() method");
            return REDIRECT_CALORIE_LIST_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("calorie", calorieDTO);
            log.info("Exiting editCalorie() method");
            return CALORIE_EDIT_PAGE;
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteCalorieInfo(@PathVariable("id") Long id, HttpSession httpSession, final RedirectAttributes redirectAttributes) {
        log.info("Entering deleteCalorieInfo() method");
        log.info("Calorie ID : "+id);

        AppResponse appResponse = calorieService.deleteCalorieInfo(id);
        redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());

        log.info("Exiting deleteCalorieInfo() method");
        return REDIRECT_CALORIE_LIST_PAGE;
    }
     */
}
