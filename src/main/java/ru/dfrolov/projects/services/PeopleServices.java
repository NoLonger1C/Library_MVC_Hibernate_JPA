package ru.dfrolov.projects.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfrolov.projects.models.Book;
import ru.dfrolov.projects.models.Person;
import ru.dfrolov.projects.repositories.PeopleRepository;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class PeopleServices {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleServices(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }


    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findByFioAndIdNot(String fio, int id){
        return peopleRepository.findByFioAndIdNot(fio, id);
    }

    public List<Book> findBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();
            for (Book book:books) {
                long differenceInMillis = new Date().getTime() - book.getTaken().getTime();
                long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
                if (differenceInDays>10){
                    book.setOverdue(true);
                }
            }
            return books;
        }else {
            return Collections.emptyList();
        }
    }

}
