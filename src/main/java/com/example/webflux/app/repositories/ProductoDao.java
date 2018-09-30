package com.example.webflux.app.repositories;

import com.example.webflux.app.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{

}
