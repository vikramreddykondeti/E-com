package com.intern.ecom_proj.controller;

import com.intern.ecom_proj.model.Product;
import com.intern.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getallProducts(){
       return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getproduc(@PathVariable int id){
        Product product= service.getbyProductId(id);
        if(product!=null)
            return new ResponseEntity<>(service.getbyProductId(id),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
    try{
        Product product1=service.addProduct(product,imageFile);
        return new ResponseEntity<>(product1,HttpStatus.OK);
    }
    catch (Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product= service.getbyProductId(productId);
        byte[] imageFile=product.getImageDate();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }
    @PutMapping("product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile imageFile) throws IOException {
        Product product1=null;
        try{ product1=service.updateProduct(id,product,imageFile);}
        catch (IOException e)
        {
            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
        }
        if(product1!=null){
             return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product=service.getbyProductId(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products=service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
