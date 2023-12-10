package ru.dfrolov.projects.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dfrolov.projects.dao.PersonDAO;
import ru.dfrolov.projects.models.Person;
import ru.dfrolov.projects.services.PeopleServices;

@Component
public class PersonValidator implements Validator {

    private final PeopleServices peopleServices;

    @Autowired
    public PersonValidator(PeopleServices peopleServices) {
        this.peopleServices = peopleServices;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleServices.findByFioAndIdNot(person.getFio(),person.getId()).isPresent()){
            errors.rejectValue("fio","","Человек с такими ФИО существует");
        }
    }
}
