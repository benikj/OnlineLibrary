package mk.finki.lm.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Topic {
    //topic ti e kako koja kategorija
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    @ManyToMany(mappedBy = "topics")
    private List<Book> books = new ArrayList<>();

    public Topic(){}

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Topic(String name, String description, List<Book> books) {
        this.name = name;
        this.description = description;
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
