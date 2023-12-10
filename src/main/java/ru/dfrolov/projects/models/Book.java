package ru.dfrolov.projects.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 2,max = 100,message = "Должно быть от 2 до 100 символов")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 2,max = 100,message = "Должно быть от 2 до 100 символов")
    private String author;
    @Column(name = "createyear")
    @Min(value = 0, message = "Должно быть больше 0")
    private int createYear;

    @Column(name = "taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taken;

    @ManyToOne()
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person owner;

    @Transient
    private boolean isOverdue;

    public Book() {
    }

    public Book(String name, String author, int createYear) {
        this.name = name;
        this.author = author;
        this.createYear = createYear;
    }

    public Date getTaken() {
        return taken;
    }

    public void setTaken(Date taken) {
        this.taken = taken;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCreateYear() {
        return createYear;
    }

    public void setCreateYear(int createYear) {
        this.createYear = createYear;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }
}
