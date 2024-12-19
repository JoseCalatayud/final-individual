package es.santander.ascender.calatayud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TiendaTest {

    private Tienda tienda;
    private Producto producto1;
    private Producto producto2;
    private Producto producto3;
    private Producto producto4;

    @BeforeEach
    void setUp() {

        producto1 = new Producto(1, "Camiseta", "Camiseta de algodón", 19.99f, 100);
        producto2 = new Producto(2, "Zapatos", "Zapatos deportivos", 49.99f, 50);
        producto3 = new Producto(3, "Gorra", "Gorra de béisbol", 15.50f, 200);
        producto4 = new Producto(4, "Pantalon", "Pantalon con Botonesl", 25.50f, 175);
        HashMap<Integer, Producto> productosTienda = new HashMap<>();

        productosTienda.put(producto1.getId(), producto1);
        productosTienda.put(producto2.getId(), producto2);
        productosTienda.put(producto3.getId(), producto3);
        tienda = new Tienda(productosTienda);
        tienda.crearCarrito(1);
        tienda.crearCarrito(2);
    }

    @Test
    void testAñadirNuevoProducto() {

        tienda.añadirNuevoProducto(producto4);
        assertTrue(tienda.getProductos().get(producto4.getId()).equals(producto4));
    }

    @Test
    void testAñadirStock() throws Exception {
        int cantidadAnterior = producto1.getCantidad();
        int cantidadNueva = cantidadAnterior + 50;
        tienda.añadirStock(1, 50);
        assertTrue(cantidadNueva == producto1.getCantidad());
        
    }
    @Test
    void testListarPorId() throws Exception {
        Producto producto = producto1;
        assertEquals(producto, tienda.listarPorId(1));
        producto = producto3;
        assertEquals(producto, tienda.listarPorId(3));
        
    }

    @Test
    void testListarProductos() {
        try {
            List<Producto> listaEjemplo = new ArrayList<>();
            listaEjemplo.add(producto1);
            listaEjemplo.add(producto2);
            listaEjemplo.add(producto3);
            assertTrue(tienda.listarProductos().size() == 3);
            assertTrue(tienda.listarProductos().containsAll(listaEjemplo));
            assertFalse(tienda.listarProductos().contains(producto4));
            
        } catch (Exception e) {
            
        }
    }

    @Test
    void TestLlenarCarrito() throws Exception {
        tienda.llenarCarrito(0,30, producto1.getId());
        assertTrue(producto1.getCantidad() == (100 - 30));
        tienda.llenarCarrito(0,1000, producto2.getId());
        assertTrue(producto2.getCantidad() == 50);
        tienda.llenarCarrito(1,5, producto3.getId());

    }

    @Test
    void TestVaciarCarrito() throws Exception {
        tienda.llenarCarrito(0,30, producto1.getId());
        tienda.llenarCarrito(0,1000, producto2.getId());
        assertTrue(producto1.getCantidad() == (100 - 30));
        assertTrue(producto2.getCantidad() == 50);
        tienda.vaciarCarrito(0);
        assertTrue(producto1.getCantidad() == (100));
        assertTrue(producto2.getCantidad() == 50);

    }

    @Test
    void testVenderProductos() throws Exception {
        tienda.llenarCarrito(1,20, 1);
        tienda.llenarCarrito(1,49, 2);
        tienda.venderProductos(1);
        assertTrue(producto1.getCantidad() == (100 - 20));
        assertTrue(producto2.getCantidad() == (50 - 49));

    }

    @Test
    void testVerStockProducto() throws Exception {
        assertTrue(tienda.verStockProducto(1) == 100);
    }

    @Test
    void TestCalcularPrecioCompra() throws Exception {
        tienda.llenarCarrito(0,20, 1);
        tienda.llenarCarrito(0,49, 2);
        assertEquals((producto1.getPrecio()*20)+(producto2.getPrecio()*49),tienda.calcularPrecioCompra(0));
        
    }
}
