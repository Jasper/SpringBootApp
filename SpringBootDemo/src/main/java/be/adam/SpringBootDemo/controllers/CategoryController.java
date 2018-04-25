package be.adam.SpringBootDemo.controllers;

import be.adam.SpringBootDemo.models.Category;
import be.adam.SpringBootDemo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Jasper on 22/04/2018.
 */
@RestController
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryController() {
    }

    @RequestMapping("/categories")
    @ResponseBody
    public Iterable<Category> categories() {
        return categoryRepository.findAll();
    }

    @RequestMapping("/categories/{categoryid}")
    @ResponseBody
    public Category category(@PathVariable("categoryid") String categoryId)
    {
        try {
            Optional<Category> cat = categoryRepository.findById(Long.valueOf(categoryId));

            if(cat.isPresent()) {
                return cat.get();
            }

            return null;
        }
        catch(Exception ex) {

        }

        return null;
    }
}
