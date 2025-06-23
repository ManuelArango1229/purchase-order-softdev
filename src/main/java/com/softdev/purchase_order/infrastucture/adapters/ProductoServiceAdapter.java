package com.softdev.purchase_order.infrastucture.adapters;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.softdev.purchase_order.domain.repositories.ProductoServicePort;

/**
 * Adaptador para la interfaz de servicio de productos.
 * Implementa la interfaz ProductoServicePort y utiliza WebClient para realizar llamadas HTTP.
 */
@Component
public class ProductoServiceAdapter implements ProductoServicePort {

    /**
     * Cliente WebClient para realizar llamadas HTTP.
     */
    private final WebClient webClient;
    /**
     * URL base del servicio de productos.
     */
    private final String productoServiceUrl;

    /**
     * Constructor que inicializa el adaptador con el cliente WebClient.
     *
     * @param webClientBuilder Constructor de WebClient.
     */
    public ProductoServiceAdapter(final WebClient.Builder webClientBuilder) {
        this.productoServiceUrl = "lb://product-service/producto";
        this.webClient = webClientBuilder.baseUrl(productoServiceUrl).build();
    }

    /**
     * Verifica si un producto existe en el servicio de productos.
     *
     * @param nombreProducto Nombre del producto a verificar.
     * @return true si el producto existe, false en caso contrario.
     */
    @Override
    public boolean existeProducto(final String nombreProducto) {
        ExisteResponse response = webClient.get()
                .uri("/existe/{nombre}", nombreProducto)
                .retrieve()
                .bodyToMono(ExisteResponse.class)
                .block();

        return response != null && response.isExiste();
    }

    /**
     * Función para acceder al token JWT.
     * @return el token JWT
     */
    private String obtenerToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            Jwt jwt = jwtAuthToken.getToken();
            return jwt.getTokenValue();
        }
        return null;
    }

    /**
     * Verifica si hay suficiente stock disponible para un producto.
     *
     * @param nombreProducto Nombre del producto a verificar.
     * @param cantidad       Cantidad solicitada.
     * @return true si hay suficiente stock, false en caso contrario.
     */
    @Override
    public boolean verificarStock(final String nombreProducto, final int cantidad) {
        StockResponse response = webClient.get()
                .uri("/stock/{nombre}", nombreProducto)
                .header("Authorization", "Bearer " + obtenerToken())
                .retrieve()
                .bodyToMono(StockResponse.class)
                .block();

        return response != null && response.getStock() >= cantidad;
    }


    /**
     * Actualiza el stock de un producto.
     *
     * @param nombreProducto Nombre del producto a actualizar.
     * @param cantidad       Cantidad a agregar o restar del stock.
     */
    @Override
    public void actualizarStock(final String nombreProducto, final int cantidad) {
        ActualizarStockRequest request = new ActualizarStockRequest(nombreProducto, cantidad);

        webClient.put()
            .uri("/stock/actualizar")
            .header("Authorization", "Bearer " + obtenerToken())
            .bodyValue(Map.of("nombre", nombreProducto, "cantidad", cantidad))
            .retrieve()
            .bodyToMono(Void.class)
            .block();


    }

    /**
     * Obtiene el precio de un producto.
     *
     * @param nombreProducto Nombre del producto a consultar.
     * @return Precio del producto.
     */
    @Override
    public BigDecimal obtenerPrecio(final String nombreProducto) {
        ProductoResponse response = webClient.get()
                .uri("/precio/{nombre}", nombreProducto)
                .header("Authorization", "Bearer " + obtenerToken())
                .retrieve()
                .bodyToMono(ProductoResponse.class)
                .block();

        return response != null ? response.getPrecio() : BigDecimal.ZERO;
    }

    /**
     * Clase interna para representar la respuesta del stock de un producto.
     */
    private static class StockResponse {
        /**
         * Cantidad de stock disponible del producto.
         */
        private int stock;
        /**
         * Constructor que inicializa la respuesta del stock.
         *
         * @return Cantidad de stock disponible.
         */
        public int getStock() {
            return stock;
        }
        /**
         * Establece la cantidad de stock disponible del producto.
         *
         * @param stockParam Cantidad de stock disponible.
         */
        public void setStock(final int stockParam) {
            this.stock = stockParam;
        }
    }


    /**
     * Clase interna para representar la solicitud de actualización de stock.
     */
    private static class ActualizarStockRequest {
        /**
         * Nombre del producto a actualizar.
         */
        private String nombreProducto;
        /**
         * Cantidad a agregar o restar del stock.
         */
        private int cantidad;

        /**
         * Constructor que inicializa la solicitud de actualización de stock.
         */
        ActualizarStockRequest() {
        }

        /**
         * Constructor que inicializa la solicitud de actualización de stock.
         *
         * @param nombreProductoParam Nombre del producto a actualizar.
         * @param cantidadParam       Cantidad a agregar o restar del stock.
         */
        ActualizarStockRequest(final String nombreProductoParam, final int cantidadParam) {
            this.nombreProducto = nombreProductoParam;
            this.cantidad = cantidadParam;
        }

        /**
         * Establece el nombre del producto a actualizar.
         *
         * @return Nombre del producto a actualizar.
         */
        public String getNombreProducto() {
            return nombreProducto;
        }

        /**
         * Establece el nombre del producto a actualizar.
         *
         * @param nombreProductoParam Nombre del producto a actualizar.
         */
        public void setNombreProducto(final String nombreProductoParam) {
            this.nombreProducto = nombreProductoParam;
        }

        /**
         * Establece la cantidad a agregar o restar del stock.
         *
         * @return Cantidad a agregar o restar del stock.
         */
        public int getCantidad() {
            return cantidad;
        }

        /**
         * Establece la cantidad a agregar o restar del stock.
         *
         * @param cantidadParam Cantidad a agregar o restar del stock.
         */
        public void setCantidad(final int cantidadParam) {
            this.cantidad = cantidadParam;
        }
    }


    /**
     * Clase interna para representar la respuesta del servicio de productos.
     */
    private static class ProductoResponse {
        /**
         * Nombre del producto.
         */
        private String nombre;
        /**
         * Precio del producto.
         */
        private BigDecimal precio;
        /**
         * Cantidad de stock disponible del producto.
         */
        private int stock;

        /**
         * Constructor que inicializa la respuesta del producto.
         *
         * @return Nombre del producto.
         */
        public String getNombre() {
            return nombre;
        }

        /**
         * Establece el nombre del producto.
         *
         * @param nombreParam Nombre del producto.
         */
        public void setNombre(final String nombreParam) {
            this.nombre = nombreParam;
        }

        /**
         * Establece el precio del producto.
         *
         * @return Precio del producto.
         */
        public BigDecimal getPrecio() {
            return precio;
        }

        /**
         * Establece el precio del producto.
         *
         * @param precioParam Precio del producto.
         */
        public void setPrecio(final BigDecimal precioParam) {
            this.precio = precioParam;
        }

        /**
         * Establece la cantidad de stock disponible del producto.
         *
         * @return Cantidad de stock disponible.
         */
        public int getStock() {
            return stock;
        }

        /**
         * Establece la cantidad de stock disponible del producto.
         *
         * @param stockParam Cantidad de stock disponible.
         */
        public void setStock(final int stockParam) {
            this.stock = stockParam;
        }
    }

    /**
     * Clase interna para representar la respuesta de existencia del producto.
     */
    private static class ExisteResponse {
        /**
         * Indica si el producto existe.
         */
        private boolean existe;

        /**
         * Constructor que inicializa la respuesta de existencia del producto.
         *
         * @return Indica si el producto existe.
         */
        public boolean isExiste() {
            return existe;
        }

        /**
         * Establece si el producto existe.
         *
         * @param existeParam Indica si el producto existe.
         */
        public void setExiste(final boolean existeParam) {
            this.existe = existeParam;
        }
}

}
