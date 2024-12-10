package sg.edu.nus.iss.ssfextraprac.ssfextraprac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Task;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.service.DatabaseService;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.service.ProductService;

@SpringBootApplication
public class SsfextrapracApplication  implements CommandLineRunner{
	@Autowired
	DatabaseService databaseService;

	@Autowired
	ProductService productService;
	public static void main(String[] args) {
		SpringApplication.run(SsfextrapracApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// List<Task> tasks = databaseService.readFile("todos.json");
		// for (Task task : tasks) {
		// 	databaseService.saveTask2(task);
		// }
		//for task example
		List<Task> tasks = databaseService.readFile2("data/todos.json");
		//if want to put in resources/data
		// List<Task> tasks = databaseService.readFile2("data/todos.json");

		//for product example
		//i only want to read the file once. so if you delete the rediskey, and restart the program. starts back from zero.
		if (!productService.doesRedisKeyExist()) {
			productService.readFile("products.json");

		}
		

		
	}

}
