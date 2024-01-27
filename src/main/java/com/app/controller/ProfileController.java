package com.app.controller;

import com.app.dto.AppResponse;
import com.app.dto.PasswordChangeDTO;
import com.app.service.UserService;
import com.app.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private final String PASSWORD_EDIT_PAGE = "profile/passwordChange";
    private final String REDIRECT_LOGIN_PAGE = "redirect:/login";
    private final String ACTIVE_MENU = "password-change";
    private final String ACTIVE_MENU_PROFILE_UPDATE = "profile-update";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/password-change", method = RequestMethod.GET)
    public String loadPasswordEditPage(Model model, HttpSession httpSession) {
        log.info("Entering loadPasswordEditPage() method");

        model.addAttribute("pageTitle", "Change Password");
        model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadPasswordEditPage() method");
        return PASSWORD_EDIT_PAGE;
    }

    @RequestMapping(value = "/password-change", method = RequestMethod.POST)
    public String changePassword(Model model, @ModelAttribute PasswordChangeDTO passwordChangeDTO, HttpSession httpSession,
                                      final RedirectAttributes redirectAttributes) {
        log.info("Entering changePassword() method");
        System.out.println("passwordChangeDTO : "+passwordChangeDTO);

        AppResponse appResponse = userService.changePassword(passwordChangeDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            httpSession.invalidate();
            log.info("Exiting changePassword() method");
            return REDIRECT_LOGIN_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("passwordChangeDTO", new PasswordChangeDTO());
            log.info("Exiting changePassword() method");
            return PASSWORD_EDIT_PAGE;
        }
    }

}
