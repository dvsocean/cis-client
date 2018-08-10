package com.cisClient.cisclient;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

  @Bean
  private RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @GetMapping("/find/{id}")
  public String verifyProduct(@PathVariable("id") int id){
    Product pr = restTemplate().getForObject("http://localhost:5000/find/" + id, Product.class);
    return pr.getName();
  }

  @PostMapping("/describe")
  public Product locateAndDescribe(@RequestParam("name") String name){
    HttpEntity<Product> request = new HttpEntity<>(new Product());
    Product pr = restTemplate().postForObject("http://localhost:5000/describe", request, Product.class);
    return pr;
//    ResponseEntity<Product> res = restTemplate().exchange(RequestEntity.post(URI.create("http://localhost:5000/describe"))
//    .contentType(MediaType.APPLICATION_JSON).body(product), Product.class);
//    return res.getBody();
  }

  @PostMapping("getProduct")
  public Product verifyFirstProd(){
    HttpEntity<Product> req = new HttpEntity<>(new Product());
    Product pr = restTemplate().postForObject("http://localhost:5000/getProduct", req, Product.class);
    return pr;
  }
}
