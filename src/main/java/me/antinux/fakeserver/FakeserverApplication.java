package me.antinux.fakeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.OutputStream;
import java.io.PrintStream;

@SpringBootApplication
public class FakeserverApplication {

    public static void main(String[] args) {
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }
        }));
        SpringApplication.run(FakeserverApplication.class, args);
    }

}
