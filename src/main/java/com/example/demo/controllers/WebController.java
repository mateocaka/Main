package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public String getAllPersons(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("person", new Person());
        return "create";
    }

    @PostMapping("/create")
    public String createPerson(@ModelAttribute Person person) {
        personService.createPerson(person);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        personService.findById(id).ifPresent(person -> model.addAttribute("person", person));
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePerson(@PathVariable Long id, @ModelAttribute Person person) {
        personService.updatePerson(id, person);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return "redirect:/";
    }
}