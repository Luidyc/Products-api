## Product-ms
- Minha primeira API desenvolvida inicialmente para aprender conceitos de C R U D e aplicações RestFull.
- Feito em memória, com Banco H2 com injeção de algumas depedências como Lombrok e Model Mapper.
### Endpoints
- BaseURL: /products
- POST: create()
- GET: getAll()
- GET /{id} : getById()
- PUT /{id}: update()
- DELETE /{id}:  inactive()

## Model
```json
{
"id":1,
"name":"Iphone 13",
"description":"Celular de 2021",
"price": 5664.99,
"isAvailable":true
}
```

## Business Rules
- Só é possível a criação de produtos com preço positivo;
- Não é possível pesquisar produtos que não estão disponíveis;
- Não é possível inserir descrição com menos de 50 caracteres;
- Meu produto não pode ser inativado com Update, somente com Delete (Lógico) o mesmo pode ser reativado;