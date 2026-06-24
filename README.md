# Fundamentos Spring Boot - Practica 3 API REST

## Autor

Mateo Orellana

## Descripcion

Proyecto desarrollado con Spring Boot para practicar la construccion de una API REST usando controladores, DTOs, modelos y mappers. En esta practica se implementa un CRUD en memoria para usuarios y productos, sin servicios, repositorios JPA ni base de datos.

La aplicacion conserva los endpoints iniciales de estado y estudiantes, y agrega los recursos solicitados en la guia de la practica 3.

## Requisitos

* Java 21 o superior
* Gradle Wrapper
* Spring Boot
* Visual Studio Code

## Ejecucion

Desde la carpeta `fundamentos01`:

```bash
.\gradlew.bat bootRun
```

Una vez iniciado el proyecto, el servidor se ejecuta en:

```text
http://localhost:8080
```

## Cambios realizados

* Se implemento el recurso `users` con controlador, modelo, DTOs y mapper.
* Se corrigio el POST de usuarios para que el ID se genere automaticamente en el backend.
* Se implemento el recurso `products` replicando la estructura de usuarios.
* Se agregaron los 6 endpoints REST para productos: GET, GET por ID, POST, PUT, PATCH y DELETE.
* Se actualizo el informe con autor correcto y evidencias nuevas.
* Se ajusto `build.gradle` para compilar con el JDK disponible manteniendo compatibilidad Java 21.

## Estructura implementada

```text
src/main/java/ec/edu/ups/icc/fundamentos01/
+-- users/
|   +-- controllers/
|   |   +-- UsersController.java
|   +-- dto/
|   |   +-- CreateUserDto.java
|   |   +-- PartialUpdateUserDto.java
|   |   +-- UpdateUserDto.java
|   |   +-- UserResponseDto.java
|   +-- mappers/
|   |   +-- UserMapper.java
|   +-- models/
|       +-- UserModel.java
|
+-- products/
    +-- controllers/
    |   +-- ProductsController.java
    +-- dto/
    |   +-- CreateProductDto.java
    |   +-- PartialUpdateProductDto.java
    |   +-- ProductResponseDto.java
    |   +-- UpdateProductDto.java
    +-- mappers/
    |   +-- ProductMapper.java
    +-- models/
        +-- ProductModel.java
```

## Endpoints disponibles

### Estado y estudiantes

| Metodo | Ruta | Descripcion |
| ------ | ---- | ----------- |
| GET | `/api/status` | Verifica que el servicio este en ejecucion |
| GET | `/v1/students` | Lista estudiantes de prueba |

### Usuarios

| Metodo | Ruta | Descripcion |
| ------ | ---- | ----------- |
| GET | `/api/users` | Lista todos los usuarios |
| GET | `/api/users/{id}` | Obtiene un usuario por ID |
| POST | `/api/users` | Crea un usuario y genera el ID automaticamente |
| PUT | `/api/users/{id}` | Reemplaza completamente un usuario |
| PATCH | `/api/users/{id}` | Actualiza parcialmente un usuario |
| DELETE | `/api/users/{id}` | Elimina un usuario |

### Productos

| Metodo | Ruta | Descripcion |
| ------ | ---- | ----------- |
| GET | `/api/products` | Lista todos los productos |
| GET | `/api/products/{id}` | Obtiene un producto por ID |
| POST | `/api/products` | Crea un producto y genera el ID automaticamente |
| PUT | `/api/products/{id}` | Reemplaza completamente un producto |
| PATCH | `/api/products/{id}` | Actualiza parcialmente un producto |
| DELETE | `/api/products/{id}` | Elimina un producto |

## Ejemplos de uso

### Crear producto

```http
POST /api/products
Content-Type: application/json
```

```json
{
  "name": "Laptop Lenovo",
  "price": 850.50,
  "stock": 10
}
```

### Respuesta

```json
{
  "id": 1,
  "name": "Laptop Lenovo",
  "price": 850.5,
  "stock": 10,
  "createdAt": "2026-06-23T19:23:01.1188331"
}
```

## Validacion

Se ejecuto:

```bash
.\gradlew.bat test
```

Resultado:

```text
BUILD SUCCESSFUL
```

## Evidencias

### Evidencia 1: GET `/api/products` con 3 productos creados

![GET products](img/products-get-all.png)

### Evidencia 2: GET `/api/products/2` con producto existente

![GET product by id](img/products-get-one.png)

### Evidencia 3: DELETE `/api/products/2` eliminando un producto existente

![DELETE product existing](img/products-delete-existing.png)

### Evidencia 4: DELETE `/api/products/999` eliminando un producto que no existe

![DELETE product missing](img/products-delete-missing.png)

### Evidencia 5: POST `/api/users` generando ID automaticamente

![POST users generated id](img/users-post-id.png)
