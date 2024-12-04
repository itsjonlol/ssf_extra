package sg.edu.nus.iss.ssfextraprac.ssfextraprac.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Task;

@Service
public class DatabaseService {
    

    public List<Task> readFile(String fileName) throws IOException, ParseException {

        ClassPathResource resource = new ClassPathResource(fileName);
        InputStream is = resource.getInputStream();
        JsonReader reader = Json.createReader(is);
        JsonArray tasksJsonArray = reader.readArray();

        List<Task> tasks = new ArrayList<>();
        

        for (int i = 0; i<tasksJsonArray.size(); i++) {
            JsonObject individualTaskJson = tasksJsonArray.getJsonObject(i);
            Task task = new Task();
            String id = individualTaskJson.getString("id");
            String name = individualTaskJson.getString("name");
            String description = individualTaskJson.getString("description");
            String due_date = individualTaskJson.getString("due_date");
            String priority_level = individualTaskJson.getString("priority_level");
            String status = individualTaskJson.getString("status");
            String created_at = individualTaskJson.getString("created_at");
            String updated_at = individualTaskJson.getString("updated_at");
            
            task.setId(id);
            task.setName(name);
            task.setDescription(description);
            SimpleDateFormat sdf = new SimpleDateFormat("DDD, MM/dd/YYYY");
            Date dueDate = sdf.parse(due_date);
            task.setDueDate(dueDate);
            task.setPriority(priority_level);
            task.setStatus(status);
            Date created_at_date = sdf.parse(created_at);
            Date updated_at_date = sdf.parse(updated_at);
            task.setCreatedAt(created_at_date);
            task.setUpdatedAt(updated_at_date);
            tasks.add(task);
            
            // events.add(event);
            
        }

        return tasks;
        
    }
}
