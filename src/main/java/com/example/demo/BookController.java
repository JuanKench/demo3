package com.example.demo;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final BookRepository bookRepository;    // importerar bookRepository
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    BookController(BookRepository bookRepository) { // det fungerar för att spring gör massa grejer i backgrounder som jag inte förstår än
        this.bookRepository = bookRepository;
    }

    @RequestMapping("/bok")
    public List<Book> getBooks() {
        log.info("All books listed");
        log.error("All books returned in ERROR mess");
        log.warn("All books returned in WARN mess");
        log.debug("All books returned in DEBUG mess");
        return bookRepository.findAll();
    }

    @RequestMapping("/bok/add")
    public List<Book> addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return bookRepository.findAll();
    }

    @RequestMapping("/bok/{id}")
    public Book findBookbyId(@PathVariable long id) {
        log.info("All books found with id: " + id);
        return bookRepository.findById(id).get();
    }

    @RequestMapping("/bok/{author}")
    public List<Book> findBookbyAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }

    @RequestMapping("/bok/{id}/delete")
    public List<Book> deleteBookById(@PathVariable long id) {
        bookRepository.deleteById(id);
        return bookRepository.findAll();
    }

    @RequestMapping("/bok/deleteall")
    public ResponseEntity<String> deleteAllBooks(){
        bookRepository.deleteAll();
        return ResponseEntity.ok("books have been terminated");
    }

    @Bean           // it's MR
    public CommandLineRunner demo3(BookRepository bookRepository){
        return (args) ->{
            bookRepository.save(new Book("Sagan om Bagaren", "Tolkein"));
            bookRepository.save(new Book("fasdfas", "sdfacv"));
        };
    }

    @RequestMapping("/bok/getAuthors")
    public List<String> getAuthors(){
        return bookRepository.getAllAuthors();
    }

    @RequestMapping("/bok/updateAuthorName")
    public void updateAutorName(){
        bookRepository.updateAuthorName("Tolkein", "Johan");
    }

    @RequestMapping("/bok/updateAuthorName2")
    public void updateAuthorName2(){
        bookRepository.updateAuthorName2("Johan", "Tolkein");
    }

    @RequestMapping("bok/getStats")
    public List<StatUtilDTO> getStats(){
        return bookRepository.getAuthorsStats();
    }

    @RequestMapping("/bok/Alfa")
    public List<String> getAllAuthorsAlphabetically(){
        return bookRepository.findAllAuthorsAlphabetically();
    }
}