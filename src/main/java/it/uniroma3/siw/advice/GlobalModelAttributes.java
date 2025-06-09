package it.uniroma3.siw.advice;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addUserInfoToModel(Model model,
                                   @AuthenticationPrincipal OAuth2User oauthUser,
                                   Principal principal) {
        String username = null;

        if (oauthUser != null) {
            username = oauthUser.getAttribute("name"); // o "login" per GitHub
        } else if (principal != null) {
            username = principal.getName();
        }

        if (username != null) {
            model.addAttribute("username", username);
        }
    }
}