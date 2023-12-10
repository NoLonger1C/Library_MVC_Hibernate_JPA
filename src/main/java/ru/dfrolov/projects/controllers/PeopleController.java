package ru.dfrolov.projects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dfrolov.projects.dao.PersonDAO;
import ru.dfrolov.projects.models.Person;
import ru.dfrolov.projects.repositories.BooksRepository;
import ru.dfrolov.projects.repositories.PeopleRepository;
import ru.dfrolov.projects.services.BooksServices;
import ru.dfrolov.projects.services.PeopleServices;
import ru.dfrolov.projects.util.PersonValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleServices peopleServices;
    private final BooksServices booksServices;
    private PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleServices peopleServices, BooksServices booksServices, PersonValidator personValidator) {
        this.peopleServices = peopleServices;
        this.booksServices = booksServices;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleServices.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person")Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("person")Person person,
                         BindingResult bindingResult){

        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        peopleServices.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person",peopleServices.findById(id));
        model.addAttribute("books",peopleServices.findBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person",peopleServices.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@Valid @ModelAttribute("person")Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleServices.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        peopleServices.delete(id);
        return "redirect:/people";
    }

}
