package sg.edu.nus.iss.ssfextraprac.ssfextraprac.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.constant.ConstantVar;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Task;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.repo.TaskRepo;

@Service
public class DatabaseService {
    
    @Autowired
    TaskRepo taskRepo;

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
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/YYYY");
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
    
    public List<Task> getAllTasks() {
        List<Object> objectList = taskRepo.getAllValuesFromHash(ConstantVar.redisKey);
        List<Task> tasks = new ArrayList<>();

        for (Object data : objectList) {
            String[] rawData = ((String) data).split(",");
            String id = rawData[0];
            String name = rawData[1];
            String description = rawData[2];
            String dueDateString = rawData[3];

            Long dueDateLong = Long.valueOf(dueDateString);
            Date dueDate = new Date(dueDateLong);

            
            String priority = rawData[4];
            String status = rawData[5];

            Date createdAt = new Date(Long.valueOf(rawData[6]));
            Date updatedAt = new Date(Long.valueOf(rawData[7]));
            Task task = new Task();
            task.setId(id);
            task.setName(name);
            task.setDescription(description);
            task.setDueDate(dueDate);
            task.setPriority(priority);
            task.setStatus(status);
            task.setCreatedAt(createdAt);
            task.setUpdatedAt(updatedAt);
            tasks.add(task);
        }
        return tasks;
        


        // Epoch milliseconds (from your example)
        // Long epochDob = 186067500000L; // Replace with your epoch value
        
        // // Convert epoch to Date
        // Date date = new Date(epochDob);

        // // Format the Date to a readable string
        // SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS zzz");
        // String formattedDate = sdf.format(date);

        // long millisecondsSinceEpoch = System.currentTimeMillis();
        // long secondsSinceEpoch = Instant.now().getEpochSecond();



    }
    
    public List<Task> filterTaskByStatus(String status) {
        List<Task> tasks = this.getAllTasks();
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                filteredTasks.add(task);
            }
        }
        if (!status.equals("all")) {
            return filteredTasks;

        }
        return tasks;
        
    }

    public void saveTask(Task task) {
       
        taskRepo.setHash(ConstantVar.redisKey, task.getId(), task.toString());
    }

    public Boolean deleteTask(String id) {
        return taskRepo.deleteKeyFromHash(ConstantVar.redisKey, id);
    }


    public Task getTaskById(String id) {
        String rawIndividualUserData = taskRepo.getValueFromHash(ConstantVar.redisKey, id);

        String[] rawData = rawIndividualUserData.split(",");

    
        String name = rawData[1];
        String description = rawData[2];
        String dueDateString = rawData[3];

        Long dueDateLong = Long.valueOf(dueDateString);
        Date dueDate = new Date(dueDateLong);

        
        String priority = rawData[4];
        String status = rawData[5];

        Date createdAt = new Date(Long.valueOf(rawData[6]));
        Date updatedAt = new Date(Long.valueOf(rawData[7]));
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setStatus(status);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);
        return task;
    }
}
