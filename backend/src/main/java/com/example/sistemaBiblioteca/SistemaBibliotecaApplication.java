package com.example.sistemaBiblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SistemaBibliotecaApplication extends SpringBootServletInitializer {

   public static void main(String[] args) {
       SpringApplication.run(SistemaBibliotecaApplication.class, args);
   }
/*    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SistemaBibliotecaApplication.class);
   }*/


}
