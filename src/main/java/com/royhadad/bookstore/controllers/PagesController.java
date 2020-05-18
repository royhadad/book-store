package com.royhadad.bookstore.controllers;

import java.util.List;

import com.royhadad.bookstore.entities.Book;
import com.royhadad.bookstore.entities.CartBook;
import com.royhadad.bookstore.repos.BookRepo;
import com.royhadad.bookstore.repos.CartRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PagesController {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private CartRepo cartRepo;

    private String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return "NO_ROLE";
        } else if (auth.isAuthenticated()) {
            return auth.getAuthorities().iterator().next().getAuthority();
        } else {
            return "NO_ROLE";
        }
    }

    private ModelAndView getIndexModelAndViewByRoleAndCurrentPath(String role, String currentPath) {
        if (!(role.equals("ROLE_USER") || role.equals("ROLE_ADMIN"))) {
            return new ModelAndView(currentPath.equals("/login") ? "login.html" : "redirect:/login");
        }

        ModelAndView view;
        List<Book> books = this.bookRepo.findAll();
        List<CartBook> cart = this.cartRepo.findAll();

        switch (role) {
            case "ROLE_USER":
                view = new ModelAndView(currentPath.equals("/books") ? "books.html" : "redirect:/books");
                view.addObject("books", books);
                view.addObject("cart", cart);
                view.addObject("cartSum", CartBook.getCartSum(cart));
                return view;
            case "ROLE_ADMIN":
                view = new ModelAndView(currentPath.equals("/admin") ? "admin.html" : "redirect:/admin");
                view.addObject("books", books);
                return view;
            default:
                return new ModelAndView("login.html");
        }
    }

    @GetMapping(path = "/*")
    public ModelAndView index() {
        return this.getIndexModelAndViewByRoleAndCurrentPath(this.getRole(), "/");
    }

    @GetMapping(path = "/admin")
    public ModelAndView admin() {
        return this.getIndexModelAndViewByRoleAndCurrentPath(this.getRole(), "/admin");
    }

    @GetMapping(path = "/books")
    public ModelAndView books() {
        return this.getIndexModelAndViewByRoleAndCurrentPath(this.getRole(), "/books");
    }

    @GetMapping(path = "/error")
    public String error() {
        return "error.html";
    }

    @GetMapping(path = "/login")
    public ModelAndView login() {
        return this.getIndexModelAndViewByRoleAndCurrentPath(this.getRole(), "/login");
    }
}