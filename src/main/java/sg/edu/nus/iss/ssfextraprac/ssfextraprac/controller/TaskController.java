package sg.edu.nus.iss.ssfextraprac.ssfextraprac.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Task;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.service.DatabaseService;





@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired 
    DatabaseService databaseService;

    @GetMapping("/listing")
    public String showListing(Model model,HttpSession session,RedirectAttributes redirectAttributes) {
        List<Task> tasks = databaseService.getAllTasks();
        model.addAttribute("tasks",tasks);
        
        if (session.getAttribute("username") == null) {
            return "invalid";
        }
        model.addAttribute("httpsession",(String) session.getAttribute("username"));

        return "listing";
    }

    @PostMapping("/listing/filter")
    public String filterTaskByStatus(@RequestParam(required=false,name="filteredstatus",defaultValue="all") String status
    ,Model model,HttpSession session) {
        System.out.println(status);
        List<Task> filteredTasks = databaseService.filterTaskByStatus(status);
        
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("currentstatus",status);
        String username = (String) session.getAttribute("username");
        model.addAttribute("httpsession", username);

//         The filterTaskByStatus method is invoked, where:
// It fetches the filtered tasks using databaseService.filterTaskByStatus(status).
// It updates the "tasks" model attribute with the filtered list.
// It also adds the "currentstatus" attribute to remember the selected filter for the dropdown.
// Rendering the Filtered Tasks:

// The listing.html template will render the updated "tasks" list because the POST handler immediately returns the listing view with the filtered data.
        //just like how you update the error message
        return "listing"; //cannot redirect here because it will go back to the original
    }
    
    @GetMapping("/register")
    public String registerTask(Model model) {
        Task task = new Task();
        model.addAttribute("task",task);
        return "register";
    }
    @PostMapping("/register/newtask")
    public String postRegister(@Valid @ModelAttribute("task") Task task,BindingResult result,Model model) {
        
        if (result.hasErrors()) {
            return "register";
        }
        databaseService.saveTask(task);
        return "redirect:/tasks/listing";
    }

    @GetMapping("/delete/{taskid}")
    public String deleteUser(@PathVariable("taskid") String id) {
        
        databaseService.deleteTask(id);
        return "redirect:/tasks/listing";

    }

    @GetMapping("/update/{taskid}")
    public String updateUser(@PathVariable("taskid") String id ,Model model) {
        Task task  = databaseService.getTaskById(id);
        model.addAttribute("task",task);


        return "registerupdate";
    }

    @PostMapping("/update")
    public String postUpdate(@Valid @ModelAttribute("task") Task task,BindingResult result,Model model) {
        
        if (result.hasErrors()) {
            return "registerupdate";
        }
        task.setUpdatedAt(new Date(System.currentTimeMillis()));
        databaseService.updateTask(task);

        // databaseService.saveTask(task);
        return "redirect:/tasks/listing";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    
    

    
    
    
    
}
