package ru.dfrolov.projects.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dfrolov.projects.dao.BookDAO;
import ru.dfrolov.projects.dao.PersonDAO;
import ru.dfrolov.projects.models.Book;
import ru.dfrolov.projects.models.Person;
import ru.dfrolov.projects.services.BooksServices;
import ru.dfrolov.projects.services.PeopleServices;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final PeopleServices peopleServices;
    private final BooksServices booksServices;

    @Autowired
    public BookController(PeopleServices peopleServices, BooksServices booksServices) {
        this.peopleServices = peopleServices;
        this.booksServices = booksServices;
    }

    @GetMapping
    public String index(@RequestParam(value = "page",required = false) Integer page,
                        @RequestParam(value = "books_per_page",required = false) Integer bookPerPage,
                        @RequestParam(value = "sort_by_year",required = false) boolean sortByYear,
                        Model model){
        if (page!=null && bookPerPage!=null && sortByYear){
            Pageable pageable = PageRequest.of(page,bookPerPage, Sort.by("createYear"));
            model.addAttribute("books", booksServices.findAll(pageable).getContent());
        } else if (page!=null && bookPerPage!=null) {
            Pageable pageable = PageRequest.of(page,bookPerPage);
            model.addAttribute("books", booksServices.findAll(pageable).getContent());
        } else if (sortByYear) {
            model.addAttribute("books", booksServices.findAll(Sort.by("createYear")));
        } else {
            model.addAttribute("books",booksServices.findAll());
        }
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book){
        return "books/new";
    }

    @PostMapping()
    public String createBook(@Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/new";
        }
        booksServices.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model
                            ,@ModelAttribute("person")Person person){

        model.addAttribute("book",booksServices.findById(id));
        model.addAttribute("people",peopleServices.findAll());
        model.addAttribute("owner", booksServices.findOnwerById(id));
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksServices.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult,
                             @PathVariable("id") int id){

        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        booksServices.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        booksServices.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/addToPerson")
    public String addToPerson(@PathVariable("id") int id,
                              @ModelAttribute("person")Person person){
        booksServices.addToPerson(id,person);
        return "redirect:/books/"+id;
    }

    @PatchMapping("{id}/ReleaseOfPerson")
    public String releaseOfPerson(@PathVariable("id") int id) {
        booksServices.releaseOfPerson(id);
        return "redirect:/books/"+id;
    }

    @GetMapping("/search")
    public String searchBook(){
        return "books/search";
    }

    @PostMapping("/search")
    public String findBooksByName(Model model,
                                  @RequestParam(value = "name",required = false) String name){

            if (!name.equals("")){
                model.addAttribute("books", booksServices.findBooksByNameStartingWith(name));
            }else {
                model.addAttribute("books", Collections.emptyList());
            }

        return "books/search";
    }
}
