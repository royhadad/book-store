package com.royhadad.bookstore.repos;

import com.royhadad.bookstore.entities.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartBook, Long> {

}