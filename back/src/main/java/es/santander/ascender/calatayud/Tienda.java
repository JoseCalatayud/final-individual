package es.santander.ascender.calatayud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Tienda {

    private HashMap<Integer, Producto> productos;
    private List <Carrito> carritos;

    

    public Tienda(HashMap<Integer, Producto> productos) {
        this.productos = productos;
        this.carritos = new ArrayList<>();

    }

    public void crearCarrito(int id) {
        carritos.add(new Carrito(id));
    }

    public List<Producto> listarProductos()  {
        List<Producto> listaProductos = productos.entrySet().stream()
                .map((p) -> p.getValue())
                .collect(Collectors.toList());
        return listaProductos;
    }

    public Producto listarPorId(int id) throws NullPointerException{

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

    public void venderProductos(int idCarrito) throws NullPointerException {
        for (Integer producto : carritos.get(idCarrito).getContenido().keySet()) {
            if (productos.get(producto).getCantidad() == 0) {
                System.out.println("Sin stock");
            }
        }
        this.carritos.get(idCarrito).vaciarCarrito();;
    }

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    public void llenarCarrito(int idCarrito, int cantidad, int idProducto) {
        if (verStockProducto(idProducto) < cantidad) {
            System.out.println(
                    "Cantidad insuficiente. Como maximo puede coger " + verStockProducto(idProducto) + " unidades");
        } else {
            carritos.get(idCarrito).añadirProducto(idProducto, cantidad);
            productos.get(idProducto).setCantidad(verStockProducto(idProducto) - cantidad);
        }

    }

    public void vaciarCarrito(int idCarrito) {

        carritos.get(idCarrito).getContenido().entrySet().stream()
                .forEach(entry -> {
                    añadirStock(entry.getKey(), entry.getValue());
                });
                carritos.get(idCarrito).vaciarCarrito();;
    }

    public float calcularPrecioCompra(int idCarrito) {

        return this.carritos.get(idCarrito).getContenido().entrySet().stream()
                .map(entry -> (productos.get(entry.getKey()).getPrecio()) * entry.getValue())
                .reduce(0f, (a, b) -> a + b);

    }

    public Carrito getCarrito(int idCarrito) {
        return carritos.get(idCarrito);
    }

}
