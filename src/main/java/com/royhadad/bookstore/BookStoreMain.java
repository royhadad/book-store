package com.royhadad.bookstore;

import java.sql.DriverManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BookStoreMain {
	public static void main(String[] args) {
		System.out.println("starting...");
		ApplicationContext context = SpringApplication.run(BookStoreMain.class, args);

		Env env = context.getBean(Env.class);
		String url = env.getUrl();
		String username = env.getUsername();
		String password = env.getPassword();

		BookStoreMain.connectToDb(url, username, password);
	}

	private static void connectToDb(String url, String username, String password) {
		try {
			System.out.println("connecting to " + url + "...");
			DriverManager.getConnection(url, username, password);
			System.out.println("connected to DB successfully!");
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}