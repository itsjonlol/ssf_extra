package sg.edu.nus.iss.ssfextraprac.ssfextraprac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // model.addAttribute("errorMessage", "You must be older than 10 to view the website");
        return "login";
    }
    @PostMapping("/login/enter")
    public String postLogin(@RequestParam(required=false,name="username") String username,
    @RequestParam(required=false,name="age") String age,HttpSession session,RedirectAttributes redirectAttributes
    ,Model model) {
        System.out.println(username);
        System.out.println(age);
        session.setAttribute("username", username);
        session.setAttribute("age", age);
        Boolean sessionExists = true;
        model.addAttribute("sessionexists",sessionExists);
        
        if (Integer.valueOf(age)<10) {
            session.removeAttribute("username");
            session.removeAttribute("age");
            model.addAttribute("errorMessage","You must be older than 10 to view the website");
            return "login";
        }
        return "redirect:/tasks/listing";
    }
    

    //@PostMapping("/login") // if want to use redirect
// public String postLogin(@RequestParam String username, RedirectAttributes redirectAttributes) {
//     if (username == null || username.isEmpty()) {
//         redirectAttributes.addFlashAttribute("errorMessage", "Username is required!");
//         return "redirect:/login"; // New request to /login
//     }
//     return "redirect:/dashboard";
// }

// @GetMapping("/login")
// public String showLoginPage(Model model) {
//     // errorMessage will be available here if it was set in RedirectAttributes
//     return "login";
// }
}
