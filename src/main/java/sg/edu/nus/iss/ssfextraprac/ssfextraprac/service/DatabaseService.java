package sg.edu.nus.iss.ssfextraprac.ssfextraprac.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
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
    //refer to readFile2 function -> this is not adding the jsonobjects directly
    //initial tasklist gathered from reading file
    public List<Task> readFile(String fileName) throws IOException, ParseException {

        ClassPathResource resource = new ClassPathResource(fileName);
        InputStream is = resource.getInputStream();
        JsonReader reader = Json.createReader(is);
        JsonArray tasksJsonArray = reader.readArray();

        //method 2
        // File file = new File("./src/main/java/sg/edu/nus/iss/ssfextraprac" + fileName);
        // File file = new File("src/main/java/sg/edu/nus/iss/ssfextraprac" + fileName);
        // FileReader fr = new FileReader(file);
        // BufferedReader br = new BufferedReader(fr);
        // JsonReader reader = Json.createReader(br);
        // JsonArray tasksJsonArray = reader.readArray();

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
    //for when i want to save jsonobject directly after reading a file.
    //i created a new jsonobject to account for changing date from string to long
    //initial tasklist gathered from reading file
    public List<Task> readFile2(String fileName) throws IOException, ParseException {

        ClassPathResource resource = new ClassPathResource(fileName);
        InputStream is = resource.getInputStream();
        JsonReader reader = Json.createReader(is);
        JsonArray tasksJsonArray = reader.readArray();

        List<Task> tasks = new ArrayList<>();

        
        
        

        for (int i = 0; i<tasksJsonArray.size(); i++) {
            JsonObject individualTaskJson = tasksJsonArray.getJsonObject(i);
            String id = individualTaskJson.getString("id");
            String name = individualTaskJson.getString("name");
            String description = individualTaskJson.getString("description");
            String due_date = individualTaskJson.getString("due_date");
            String priority_level = individualTaskJson.getString("priority_level");
            String status = individualTaskJson.getString("status");
            String created_at = individualTaskJson.getString("created_at");
            String updated_at = individualTaskJson.getString("updated_at");

            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/YYYY");
            Date dueDate = sdf.parse(due_date);
            Date created_at_date = sdf.parse(created_at);
            Date updated_at_date = sdf.parse(updated_at);

            JsonObject individualTaskJsonFormatted = Json.createObjectBuilder()
                                                        .add("id",id)
                                                        .add("name",name)
                                                        .add("description",description)
                                                        .add("due_date",dueDate.getTime())
                                                        .add("priority_level",priority_level)
                                                        .add("status",status)
                                                        .add("created_at",created_at_date.getTime())
                                                        .add("updated_at",updated_at_date.getTime())
                                                        .build();


            taskRepo.setHash(ConstantVar.redisKey, individualTaskJson.getString("id"), individualTaskJsonFormatted.toString());

            Task task = new Task();
            
            
            task.setId(id);
            task.setName(name);
            task.setDescription(description);
            
            task.setDueDate(dueDate);
            task.setPriority(priority_level);
            task.setStatus(status);
            
            task.setCreatedAt(created_at_date);
            task.setUpdatedAt(updated_at_date);
            tasks.add(task);
            
            // events.add(event);
            
        }

        return tasks;
        
    }
    //saving to redis using task.toString(). refer to getAllTasks2()
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
    //convert jsonstringformat to json object -> extract values and make a task
    //i have a separate getAllTasks to pull the values from redis.
    public List<Task> getAllTasks2() {
        List<Object> objectList = taskRepo.getAllValuesFromHash(ConstantVar.redisKey);
        List<Task> tasks = new ArrayList<>();

        for (Object data : objectList) {
            String dataJsonString = (String) data;
            InputStream is = new ByteArrayInputStream(dataJsonString.getBytes());
            JsonReader reader = Json.createReader(is);
            JsonObject dataJson = reader.readObject();
            Task task = new Task();

            String id = dataJson.getString("id");
            String name = dataJson.getString("name");
            String description = dataJson.getString("description");
            Long dueDateLong = dataJson.getJsonNumber("due_date").longValue();
            String priority_level = dataJson.getString("priority_level");
            String status = dataJson.getString("status");
            Long createdAtLong = dataJson.getJsonNumber("created_at").longValue();
            Long updatedAtLong = dataJson.getJsonNumber("updated_at").longValue();
            
           
            Date dueDate = new Date(dueDateLong);
            Date createdAt = new Date(createdAtLong);
            Date updatedAt = new Date(updatedAtLong);

            task.setId(id);
            task.setName(name);
            task.setDescription(description);
            task.setDueDate(dueDate);
            task.setPriority(priority_level);
            task.setStatus(status);
            task.setCreatedAt(createdAt);
            task.setUpdatedAt(updatedAt);
            tasks.add(task);
            
        }
        return tasks;
    }
    
    public List<Task> filterTaskByStatus(String status) {
        List<Task> tasks = this.getAllTasks2();
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
    //converting task to string (task.tostring()) -> use saveTask2 instead
    public void saveTask(Task task) {
       
        taskRepo.setHash(ConstantVar.redisKey, task.getId(), task.toString());
    }
    //convert task to taskjsonobject
    //converting taskjsonobject to string
    public void saveTask2(Task task) {

        
        JsonObject taskJsonObject = Json.createObjectBuilder()
                                                .add("id",task.getId())
                                                .add("name",task.getName())
                                                .add("description",task.getDescription())
                                                .add("due_date",task.getDueDate().getTime())
                                                .add("priority_level",task.getPriority())
                                                .add("status",task.getStatus())
                                                .add("created_at",task.getCreatedAt().getTime())
                                                .add("updated_at",task.getUpdatedAt().getTime())
                                                .build();
        taskRepo.setHash(ConstantVar.redisKey, task.getId(), taskJsonObject.toString());
    }

    public Boolean deleteTask(String id) {
        return taskRepo.deleteKeyFromHash(ConstantVar.redisKey, id);
    }

    //task.tostring method -> refer to getTaskById2 instead
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
    //convert taskjsonstring to taskjsonobject to task
    public Task getTaskById2(String id) {
        String IndividualUserDataJsonString = taskRepo.getValueFromHash(ConstantVar.redisKey, id);

        
        InputStream is = new ByteArrayInputStream(IndividualUserDataJsonString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject dataJson = reader.readObject();

    
        String name = dataJson.getString("name");
        String description = dataJson.getString("description");
        Long dueDateLong = dataJson.getJsonNumber("due_date").longValue();
        String priority_level = dataJson.getString("priority_level");
        String status = dataJson.getString("status");
        Long createdAtLong = dataJson.getJsonNumber("created_at").longValue();
        Long updatedAtLong = dataJson.getJsonNumber("updated_at").longValue();

        Date dueDate = new Date(dueDateLong);
        Date createdAt = new Date(createdAtLong);
        Date updatedAt = new Date(updatedAtLong);
        

        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority_level);
        task.setStatus(status);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);
        return task;
    }

    

    // public void updateTask(Task task) {
    //     taskRepo.updateValue(ConstantVar.redisKey,task.getId(),task.toString() );
    // }
}
