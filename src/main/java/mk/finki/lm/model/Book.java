package mk.finki.lm.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;


    private String name;

    private String description;

    private LocalDate publicationDate;

    //edna kniga moze da pripagja na povekje temi i vo edna tema moze da ima povekje knigi
    @ManyToMany
    @JoinTable(
            name = "book_topics",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "topics_id"))
    private List<Topic> topics;


    private String author;

    @Column(name = "image")
    @Lob
    private String imageBase64;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doc_id",referencedColumnName = "id")
    private Doc doc;


    @Column(name = "rating", columnDefinition = "int default 0" )
    private int rating;

    public Book(){}

    public Book(String name, String description, LocalDate publicationDate, List<Topic> topics, String author) {
        this.name = name;
        this.description = description;
        this.publicationDate = publicationDate;
        this.topics = topics;
        this.author = author;
    }



    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
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

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
