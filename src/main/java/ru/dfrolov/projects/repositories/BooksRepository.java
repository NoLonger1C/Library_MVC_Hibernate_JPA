package ru.dfrolov.projects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.ObjectError;
import ru.dfrolov.projects.models.Book;
import ru.dfrolov.projects.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {

    public List<Book> findByNameStartingWith(String name);

}
