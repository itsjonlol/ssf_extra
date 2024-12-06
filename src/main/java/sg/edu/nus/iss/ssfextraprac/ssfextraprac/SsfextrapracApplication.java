package sg.edu.nus.iss.ssfextraprac.ssfextraprac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Task;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.service.DatabaseService;

@SpringBootApplication
public class SsfextrapracApplication  implements CommandLineRunner{
	@Autowired
	DatabaseService databaseService;
	public static void main(String[] args) {
		SpringApplication.run(SsfextrapracApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// List<Task> tasks = databaseService.readFile("todos.json");
		// for (Task task : tasks) {
		// 	databaseService.saveTask2(task);
		// }
		List<Task> tasks = databaseService.readFile2("todos.json");
		
	}

}
