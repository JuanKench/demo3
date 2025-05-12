package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Book> findBookbyId(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }

    @RequestMapping("/bok/{id}/delete")
    public List<Book> deleteBookById(@PathVariable long id) {
        bookRepository.deleteById(id);
        return bookRepository.findAll();
    }
}
