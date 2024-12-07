package sg.edu.nus.iss.ssfextraprac.ssfextraprac.model;

public class Product {
    

    private Integer id;
    private String title;
    private String description;
    private Integer price;
    private Double discountPercentage;
    private Double rating;
    private Integer stock;
    private String brand;
    private String category;
    private Long dated;
    private Integer buy;

    public Product(){}

    public Product(String brand, Integer buy, String category, Long dated, String description, Double discountPercentage, Integer id, Integer price, Double rating, Integer stock, String title) {
        this.brand = brand;
        this.buy = buy;
        this.category = category;
        this.dated = dated;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.id = id;
        this.price = price;
        this.rating = rating;
        this.stock = stock;
        this.title = title;
    }



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Double getDiscountPercentage() {
        return discountPercentage;
    }
    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Long getDated() {
        return dated;
    }
    public void setDated(Long dated) {
        this.dated = dated;
    }
    public Integer getBuy() {
        return buy;
    }
    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    
}
