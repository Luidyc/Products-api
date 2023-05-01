package br.myself.productms.controller;


import br.myself.productms.dto.ProductDTO;
import br.myself.productms.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService service;
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO request) {
        String log = String.valueOf(request);
        LOGGER.info("Tentando criar um produto"+log);
        Optional<ProductDTO> response = service.create(request);
        if(response.isPresent()) {
            LOGGER.info("Produto criado");
            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                //new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //Metodologia em forma de Java 8 ou 11///
        // return response.map(productDTO -> new ResponseEntity<>(response.get(), HttpStatus.CREATED))
        //                .orElse(ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
        public ResponseEntity<ProductDTO> getById(@PathVariable("id") Long id) {
            Optional<ProductDTO> response = service.getById(id);
            return response.map(ProductDTO -> ResponseEntity.ok(response.get()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
            /*  return response.map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); */
        }
    @PutMapping("/{id}")
        public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id,
                                                 @RequestBody @Valid ProductDTO request) {
        Optional<ProductDTO> response = service.update(id, request);
            if(response.isPresent()) {
                return ResponseEntity.ok(response.get());
            }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
        public  ResponseEntity<Void> inactive(@PathVariable("id") Long id){
        boolean inactive = service.inactive(id);
        return inactive
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
