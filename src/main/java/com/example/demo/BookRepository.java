package com.example.demo;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> findByAuthor(String author);

    @Query("select distinct author from Book")
    public List<String> getAllAuthors();

    @Modifying
    @Transactional
    @Query("update Book set author=:newName where author=:oldName")
    public void updateAuthorName(String oldName, String newName);

    @Modifying
    @Transactional
    @Query("update Book set author=?2 where author=?1")
    public void updateAuthorName2(@Param("oldName")String oldName, @Param("newName") String newName);

    @Query(value = "select new com.example.demo.StatUtilDTO(author, count(title))"+ "from Book group by author")
    public List<StatUtilDTO> getAuthorsStats();

    @Query("select distinct b.author from Book b order by b.author asc")
    public List<String> findAllAuthorsAlphabetically();


}
