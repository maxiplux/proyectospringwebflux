package com.example.webflux.app;

import com.example.webflux.app.models.documents.Producto;
import com.example.webflux.app.repositories.ProductoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    @Autowired
    private ProductoDao dao;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        mongoTemplate.dropCollection("productos").subscribe();  // se eliminan los anteriores

        Flux.just(new Producto("TV Panasonic Pantalla LCD", 456.89),
                new Producto("Sony Camara HD Digital", 177.89),
                new Producto("Apple iPod", 46.89),
                new Producto("Sony Notebook", 846.89),
                new Producto("Hewlett Packard Multifuncional", 200.89),
                new Producto("Bianchi Bicicleta", 70.89),
                new Producto("HP Notebook Omen 17", 2500.89),
                new Producto("Mica Cómoda 5 Cajones", 150.89),
                new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89)
        )
                .flatMap(producto -> {
                    producto.setCreateAt(new Date());
                    return dao.save(producto);
                })
                .subscribe(producto -> System.out.println("Insert: " + producto.getId() + " " + producto.getNombre()));

    }

}
