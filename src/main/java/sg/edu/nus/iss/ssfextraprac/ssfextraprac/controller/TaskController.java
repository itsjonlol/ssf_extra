package sg.edu.nus.iss.ssfextraprac.ssfextraprac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Task;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.service.DatabaseService;



@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired 
    DatabaseService databaseService;

    @GetMapping("/listing")
    public String showListing(Model model) {
        List<Task> tasks = databaseService.getAllTasks();
        model.addAttribute("tasks",tasks);

        return "listing";
    }

    @PostMapping("/listing/filter")
    public String filterTaskByStatus(@RequestParam(required=false,name="filteredstatus",defaultValue="all") String status
    ,Model model) {
        System.out.println(status);
        List<Task> filteredTasks = databaseService.filterTaskByStatus(status);
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("currentstatus",status);

//         The filterTaskByStatus method is invoked, where:
// It fetches the filtered tasks using databaseService.filterTaskByStatus(status).
// It updates the "tasks" model attribute with the filtered list.
// It also adds the "currentstatus" attribute to remember the selected filter for the dropdown.
// Rendering the Filtered Tasks:

// The listing.html template will render the updated "tasks" list because the POST handler immediately returns the listing view with the filtered data.
        
        return "listing"; //cannot redirect here because it will go back to the original
    }
    
    
    
}
