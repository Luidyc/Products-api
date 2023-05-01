package br.myself.productms.service;

import br.myself.productms.controller.ProductController;
import br.myself.productms.dto.ProductDTO;
import br.myself.productms.model.Product;
import br.myself.productms.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductRepository repository;

    @Autowired // Utilizando ModelMapper;
    private ModelMapper mapper;
    @Override
    public Optional<ProductDTO> create(ProductDTO request) {
        // Passando o Objeto produto, e mapeando o request para as classes do objeto;
        Product product = mapper.map(request,Product.class);
        //Salvando o objeto no repositório;
        repository.saveAndFlush(product);
        //Criando um map dos produto da request, para ser devolvido como response;
        ProductDTO response = mapper.map(product,ProductDTO.class);
        return Optional.of(response);
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = repository.findAll();
        List<ProductDTO> responses = new ArrayList<>();
        for (Product product : products ) {
            ProductDTO response = mapper.map(product,ProductDTO.class);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public Optional<ProductDTO> getById(Long id) {
        Optional<Product> product = repository.findById(id);
        if(product.isPresent()) {
            Product productEntity = product.get();
            ProductDTO response = mapper.map(productEntity, ProductDTO.class);
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductDTO> update(Long id, ProductDTO request) {
        Optional<Product> product = repository.findById(id);
        if(product.isPresent()) {
            product.get().setDescription(request.getDescription());
            product.get().setPrice(request.getPrice());
            if(request.isAvailable() == true) product.get().setAvailable(request.isAvailable());
            repository.save(product.get());
            return Optional.of(mapper.map(product.get(),ProductDTO.class));
        }
        return Optional.empty();
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Override
    public boolean inactive(Long id) {
        // Meu metodo devolve o produto;
        Optional<Product> product = repository.findById(id);
        if(product.isPresent()) {
            product.get().setAvailable(false);
            LOGGER.info("Deu um true pro produto"+product);
            repository.save(product.get());
            return true;
        }
        LOGGER.info("Retornei falso");
        return false;
    }
}
