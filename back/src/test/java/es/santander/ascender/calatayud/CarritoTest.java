package es.santander.ascender.calatayud;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarritoTest {
    private Carrito carrito;
    private Producto producto1;
    private Producto producto2;
    private Producto producto3;
    private Producto producto4;
    
    @BeforeEach
    void setUp() {
        carrito = new Carrito(1);
        producto1 = new Producto(1, "Camiseta", "Camiseta de algodón", 19.99f, 100);
        producto2 = new Producto(2, "Zapatos", "Zapatos deportivos", 49.99f, 50);
        producto3 = new Producto(3, "Gorra", "Gorra de béisbol", 15.50f, 200);
        producto4 = new Producto(4, "Pantalon", "Pantalon con Botonesl", 25.50f, 175);

    }

    

    @Test
    void testGetContenido() {
        carrito.añadirProducto(producto1.getId(), 30);
        carrito.añadirProducto(producto2.getId(), 40);
        carrito.añadirProducto(producto3.getId(), 50);
        carrito.añadirProducto(producto4.getId(),30);
        assertTrue(carrito.getContenido().get(1)==30);
        assertTrue(carrito.getContenido().get(2)==40);
        assertTrue(carrito.getContenido().get(3)==50);
        assertTrue(carrito.getContenido().size()==4);
        
    }
   

    @Test
    void testVaciarCarrito() {
        carrito.añadirProducto(producto1.getId(), 30);
        carrito.añadirProducto(producto2.getId(), 40);
        carrito.añadirProducto(producto3.getId(), 50);
        assertTrue(carrito.getContenido().get(1)==30);
        assertTrue(carrito.getContenido().get(2)==40);
        assertTrue(carrito.getContenido().get(3)==50);
        carrito.vaciarCarrito();
        assertTrue(carrito.getContenido().size()==0);

    }
}
