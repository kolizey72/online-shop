package com.github.kolizey72.onlineshop.controller;

import com.github.kolizey72.onlineshop.entity.Product;
import com.github.kolizey72.onlineshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "products/show";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute Product product) {
        return "products/new";
    }

    @PostMapping
    public String create(@ModelAttribute Product product) {
        productService.create(product);
        return "redirect:/products";
    }

    @GetMapping("{id}/edit")
    public String editPage(@PathVariable long id, Model model){
        model.addAttribute("product", productService.findById(id));
        return "products/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable long id, @ModelAttribute Product product) {
        productService.update(id, product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
