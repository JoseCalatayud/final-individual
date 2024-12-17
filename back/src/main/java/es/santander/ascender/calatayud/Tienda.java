package es.santander.ascender.calatayud;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Tienda {

    private HashMap<Integer, Producto> productos;
    private HashMap<Integer, Integer> carrito;

    public Tienda() {
    }

    public Tienda(HashMap<Integer, Producto> productos) {
        this.productos = productos;
        this.carrito = new HashMap<>();

    }

    public List<Producto> listarProductos() throws NullPointerException {
        List<Producto> listaProductos = productos.entrySet().stream()
                .map((p) -> p.getValue())
                .collect(Collectors.toList());
        return listaProductos;
    }

    public Producto listarPorId(int id) {

        return productos.entrySet().stream()
                .filter((p) -> (p.getValue().getId() == id))
                .map((p) -> p.getValue())
                .findFirst()
                .orElse(null);

    }

    public void añadirNuevoProducto(Producto producto) {
        productos.put(producto.getId(), producto);
    }

    public void añadirStock(int id, int cantidad) {
        if (productos.containsKey(id)) {
            productos.get(id).setCantidad(verStockProducto(id) + cantidad);
        } else {
            System.out.println("Error, primero hay que dar de alta el producto");
        }

    }

    public int verStockProducto(int id) {
        int stock = productos.get(id).getCantidad();
        return stock;
    }

    public void venderProductos() throws NullPointerException {
        for (Integer producto : this.carrito.values()) {
            if (productos.get(producto).getCantidad() == 0) {
                System.out.println("Sin stock");
            }
        }
        this.carrito.clear();
    }

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    public void llenarCarrito(int cantidad, int idProducto) {
        if (verStockProducto(idProducto) < cantidad) {
            System.out.println(
                    "Cantidad insuficiente. Como maximo puede coger " + verStockProducto(idProducto) + " unidades");
        } else {
            carrito.put(cantidad, idProducto);
            productos.get(idProducto).setCantidad(verStockProducto(idProducto) - cantidad);
        }

    }

    public void vaciarCarrito() {

        carrito.entrySet().stream()
                .forEach(entry -> {
                    añadirStock(entry.getValue(), entry.getKey());
                });
        carrito.clear();
    }

    public float calcularPrecioCompra() {

        return this.carrito.entrySet().stream()
                .map(entry -> (productos.get(entry.getValue()).getPrecio()) * entry.getKey())
                .reduce(0f, (a, b) -> a + b);

    }

    public HashMap<Integer, Integer> getCarrito() {
        return carrito;
    }

}
