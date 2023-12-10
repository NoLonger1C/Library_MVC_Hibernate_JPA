package ru.dfrolov.projects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dfrolov.projects.models.Book;
import ru.dfrolov.projects.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

    public Optional<Person> findByFioAndIdNot(String fio,int id);

}
