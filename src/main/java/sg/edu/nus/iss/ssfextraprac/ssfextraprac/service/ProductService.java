package sg.edu.nus.iss.ssfextraprac.ssfextraprac.service;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.constant.ConstantVar;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Product;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Task;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.repo.TaskRepo;

@Service
public class ProductService {

    @Autowired
    TaskRepo taskRepo;

    public void readFile(String fileName) throws IOException, ParseException {

        ClassPathResource resource = new ClassPathResource(fileName);
        InputStream is = resource.getInputStream();
        JsonReader reader = Json.createReader(is);
        JsonArray productsJsonArray = reader.readArray();

        List<Task> products = new ArrayList<>();

        
        for (int i = 0; i<productsJsonArray.size(); i++) {
            JsonObject individualProductJson = productsJsonArray.getJsonObject(i);

            //need make the id a string
            taskRepo.setHash(ConstantVar.redisKeyProduct, String.valueOf(individualProductJson.getInt("id")), individualProductJson.toString());

               
        }

        
    }

    public List<Product> getAllProducts() {
      
        List<Object> objectList = taskRepo.getAllValuesFromHash(ConstantVar.redisKeyProduct);
        List<Product> products = new ArrayList<>();

        for (Object data : objectList) {
            String dataJsonString = (String) data;
            InputStream is = new ByteArrayInputStream(dataJsonString.getBytes());
            JsonReader reader = Json.createReader(is);
            JsonObject dataJson = reader.readObject();
            Product product = new Product();

            Integer id = dataJson.getInt("id");
            String title = dataJson.getString("title");
            String description = dataJson.getString("description");
            Integer price = dataJson.getInt("price");
            Double discountPercentage = dataJson.getJsonNumber("discountPercentage").doubleValue();
            Double rating = dataJson.getJsonNumber("rating").doubleValue();
            Integer stock = dataJson.getInt("stock");
            String brand = dataJson.getString("brand");
            String category = dataJson.getString("category");
            Long dated = dataJson.getJsonNumber("dated").longValue();
            Integer buy = dataJson.getInt("buy");
            
            product.setId(id);
            product.setTitle(title);
            product.setDescription(description);
            product.setPrice(price);
            product.setDiscountPercentage(discountPercentage);
            product.setRating(rating);
            product.setStock(stock);
            product.setBrand(brand);
            product.setCategory(category);
            product.setDated(dated);
            product.setBuy(buy);
            products.add(product);
           

        }
        return products;
    
    }

    public void saveProduct(Product product) {
        
        // private Integer id;
        // private String title;
        // private String description;
        // private Integer price;
        // private Double discountPercentage;
        // private Double rating;
        // private Integer stock;
        // private String brand;
        // private String category;
        // private Long dated;
        // private Integer buy;
        
        JsonObject productJsonObject = Json.createObjectBuilder()
                                                .add("id",product.getId())
                                                .add("title",product.getTitle())
                                                .add("description",product.getDescription())
                                                .add("price",product.getPrice())
                                                .add("discountPercentage",product.getDiscountPercentage())
                                                .add("rating",product.getRating())
                                                .add("stock",product.getStock())
                                                .add("brand",product.getBrand())
                                                .add("category",product.getCategory())
                                                .add("dated",product.getDated())
                                                .add("buy",product.getBuy())
                                                .build();
        taskRepo.setHash(ConstantVar.redisKeyProduct, String.valueOf(product.getId()), productJsonObject.toString());
    }

    public Product getProductById(String id) {

        String IndividualProductJsonString = taskRepo.getValueFromHash(ConstantVar.redisKeyProduct, id);

        
        InputStream is = new ByteArrayInputStream(IndividualProductJsonString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject dataJson = reader.readObject();

        Product product = new Product();

       
        String title = dataJson.getString("title");
        String description = dataJson.getString("description");
        Integer price = dataJson.getInt("price");
        Double discountPercentage = dataJson.getJsonNumber("discountPercentage").doubleValue();
        Double rating = dataJson.getJsonNumber("rating").doubleValue();
        Integer stock = dataJson.getInt("stock");
        String brand = dataJson.getString("brand");
        String category = dataJson.getString("category");
        Long dated = dataJson.getJsonNumber("dated").longValue();
        Integer buy = dataJson.getInt("buy");
        
        product.setId(Integer.valueOf(id));
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setDiscountPercentage(discountPercentage);
        product.setRating(rating);
        product.setStock(stock);
        product.setBrand(brand);
        product.setCategory(category);
        product.setDated(dated);
        product.setBuy(buy);
        




        return product;



    }

    public Boolean isProductOut(String id) {
        Product product = this.getProductById(id);

        return product.getBuy()>=product.getStock();

        
    }
    public Boolean doesRedisKeyExist() {
        return taskRepo.hashExists(ConstantVar.redisKeyProduct);
    }
}
