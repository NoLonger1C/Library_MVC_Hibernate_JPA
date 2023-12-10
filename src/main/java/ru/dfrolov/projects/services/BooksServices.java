package ru.dfrolov.projects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfrolov.projects.models.Book;
import ru.dfrolov.projects.models.Person;
import ru.dfrolov.projects.repositories.BooksRepository;
import ru.dfrolov.projects.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class BooksServices {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksServices(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public Page<Book> findAll(Pageable pageable){
        return booksRepository.findAll(pageable);
    }

    public List<Book> findAll(Sort sort){
        return booksRepository.findAll(sort);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    public Book findById(int id){
        return booksRepository.findById(id).orElse(null);
    }

    public Person findOnwerById(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book bookById = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookById.getOwner());
        updatedBook.setTaken(bookById.getTaken());
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void addToPerson(int id, Person person){
        Optional<Book> book = booksRepository.findById(id);
        book.ifPresent(value -> {
            value.setOwner(person);
            value.setTaken(new Date());
        });
    }

    @Transactional
    public void releaseOfPerson(int id){
        Optional<Book> book = booksRepository.findById(id);
        book.ifPresent(value -> {
            value.setOwner(null);
            value.setTaken(null);
        });
    }

    public List<Book> findBooksByNameStartingWith(String name){
        return booksRepository.findByNameStartingWith(name);
    }

}
