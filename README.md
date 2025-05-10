# SofDev Purchase Order

Este es el **microservicio de órdenes de compra** del ecosistema **SofDev**, desarrollado con **Spring Boot**. Su objetivo es gestionar la creación, actualización y consulta de órdenes de compra dentro de la plataforma, sirviendo como componente clave del sistema distribuido.

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 3.2.4**

## 📂 Estructura del Proyecto

```
sofdev-purchase_order/
├── src/main/java/com/sofdev/purchase_order
│   ├── SofdevPurchase-OrderApplication.java
│   └── controllers/
├── src/main/resources/
│   ├── application.yml
│   └── bootstrap.yml
├── pom.xml
└── README.md
```

````

## 🏗️ Instalación y Ejecución

### 1️⃣ Clonar el repositorio

```sh
git clone git@github.com:SofDev/sofdev-api-gateway.git
cd sofdev-api-gateway
````

### 2️⃣ Construir el proyecto con Maven

```sh
./mvnw clean install
```

### 3️⃣ Ejecutar la API Gateway

```sh
./mvnw spring-boot:run
```

## 📜 Licencia

Este proyecto está bajo la **Licencia MIT**. Puedes leer más en el archivo [LICENSE](LICENSE).
