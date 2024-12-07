package sg.edu.nus.iss.ssfextraprac.ssfextraprac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.nus.iss.ssfextraprac.ssfextraprac.model.Product;
import sg.edu.nus.iss.ssfextraprac.ssfextraprac.service.ProductService;



@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/listing")
    public String showProductsListing(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products",products);
        
        return "productslisting";
    }

    @GetMapping("/buy/{productid}")
    public String buyProduct(@PathVariable("productid") String id,Model model,RedirectAttributes redirectAttributes) {

        Product product = productService.getProductById(id);
        model.addAttribute("product",product);
        

        
        if (productService.isProductOut(id)) {
            model.addAttribute("productout",true);
            // model.addAttribute("product",product);
            return "productout";
        }
        

        product.setBuy(product.getBuy()+1);
        productService.saveProduct(product);
       
        return "productpage";

        //if using redirectattributes
        // redirectAttributes.addFlashAttribute("product",product);
        // return "redirect:/products/bought";
        
        //if want to see on the spot update #1 method
        // return "redirect:/products/listing"; //

        //if want to see on the spot update #2 method
        // List<Product> products = productService.getAllProducts();
        // model.addAttribute("products",products);
        // return "productslisting";
    }
    
    //if you would like to use redirect instead of returning productpage directly.
    // @GetMapping("/bought")
    // public String getBoughtProduct(@ModelAttribute("product")Product product,Model model) {
    //     model.addAttribute("product",product);
    //     return "productpage";
    // }
    
    
    
    
}
