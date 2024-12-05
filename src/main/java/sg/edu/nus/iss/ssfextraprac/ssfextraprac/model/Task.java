package sg.edu.nus.iss.ssfextraprac.ssfextraprac.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Task {
    
    private String id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z\s]+$",message = "Name cannot contain numbers")
    @Size(min = 10, max = 50, message = "Name must be inbetween 10 and 50 characters")
    private String name;

    @Size(max = 255 ,message = "Max 255 characters")
    private String description;

    @FutureOrPresent(message ="Due today or in the future only")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @NotEmpty(message = "Priority cannot be empty")
    private String priority;

    @NotEmpty(message = "Status cannot be empty")
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    public Task() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());// when it is first created, it is also first updated
        
        
    }
    
    public Task(String id,
            @NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name cannot contain numbers") @Size(min = 10, max = 50, message = "Name must be inbetween 5 and 25 characters") String name,
            @Size(max = 255, message = "Max 255 characters") String description,
            @FutureOrPresent(message = "Due today or in the future only") Date dueDate,
            @NotEmpty(message = "Priority cannot be empty") String priority,
            @NotEmpty(message = "Status cannot be empty") String status, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        
    }


    public Task(String id,
            @NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name cannot contain numbers") @Size(min = 10, max = 50, message = "Name must be inbetween 5 and 25 characters") String name,
            @Size(max = 255, message = "Max 255 characters") String description,
            @FutureOrPresent(message = "Due today or in the future only") Date dueDate,
            @NotEmpty(message = "Priority cannot be empty") String priority,
            @NotEmpty(message = "Status cannot be empty") String status, Date createdAt, Date updatedAt) {
        
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());
    }




    @Override
    public String toString() {
        return id + "," + name + "," + description + "," + dueDate.getTime()
                + "," + priority + "," + status + "," + createdAt.getTime() + ","
                + updatedAt.getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    
}

