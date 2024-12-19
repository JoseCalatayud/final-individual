package es.santander.ascender.calatayud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;

public class Tienda {

    private HashMap<Integer, Producto> productos;
    private List<Carrito> carritos;

    public Tienda(HashMap<Integer, Producto> productos) {
        this.productos = productos;
        this.carritos = new ArrayList<>();

    }

    public void crearCarrito(int id) {
        carritos.add(new Carrito(id));
    }

    public List<Producto> listarProductos() {
        List<Producto> listaProductos = productos.entrySet().stream()
                .map((p) -> p.getValue())
                .collect(Collectors.toList());
        return listaProductos;
    }

    public Producto listarPorId(int id) throws Exception {

        return productos.entrySet().stream()
                .filter((p) -> (p.getValue().getId() == id))
                .map((p) -> p.getValue())
                .findFirst()
                .orElseThrow(() -> new Exception("Producto con ID " + id + " no encontrado."));

    }

    public void añadirNuevoProducto(Producto producto) {
        productos.put(producto.getId(), producto);
    }

    public void añadirStock(int id, int cantidad) throws Exception  {
        
        if (productos.containsKey(id)) {
            productos.get(id).setCantidad(verStockProducto(id) + cantidad);
        } else {
            System.out.println("Error, primero hay que dar de alta el producto");
        }

    }

    public int verStockProducto(int id) throws Exception {
        if (!productos.containsKey(id) || productos.isEmpty()||productos.get(id)==null) {
            throw new Exception("Producto con ID " + id + " no encontrado.");
        }
        int stock = productos.get(id).getCantidad();
        return stock;
    }

    public void venderProductos(int idCarrito) throws Exception {
        if (carritos.get(idCarrito).getContenido().isEmpty()) {
            throw new Exception("El carrito esta vacio");
        }
        for (Integer producto : carritos.get(idCarrito).getContenido().keySet()) {
            if (productos.get(producto).getCantidad() == 0) {
                System.out.println("Sin stock");
            }
        }
        this.carritos.get(idCarrito).vaciarCarrito();
        ;
    }

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    public void llenarCarrito(int idCarrito, int cantidad, int idProducto) throws Exception {
        if (verStockProducto(idProducto) < cantidad) {
            System.out.println(
                    "Cantidad insuficiente. Como maximo puede coger " + verStockProducto(idProducto) + " unidades");
        } else {
            carritos.get(idCarrito).añadirProducto(idProducto, cantidad);
            productos.get(idProducto).setCantidad(verStockProducto(idProducto) - cantidad);
        }

    }

    public void vaciarCarrito(int idCarrito) throws Exception {
        if (carritos.get(idCarrito) == null || carritos.get(idCarrito).getContenido().isEmpty()) {
            throw new Exception("El carrito esta no exite");

        }
        carritos.get(idCarrito).getContenido().entrySet().stream()
                .forEach(entry -> {
                    try {
                        añadirStock(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        carritos.get(idCarrito).vaciarCarrito();
        
    }

    public float calcularPrecioCompra(int idCarrito) throws Exception {
        if (carritos.get(idCarrito) == null) {
            throw new Exception("El carrito no existe");
            
        }
        return carritos.get(idCarrito).getContenido().entrySet().stream()
                .map(entry -> (productos.get(entry.getKey()).getPrecio()) * entry.getValue())
                .reduce(0f, (a, b) -> a + b);

    }

    public Carrito getCarrito(int idCarrito) throws Exception {
        if (carritos.get(idCarrito) == null) {
            throw new Exception("El carrito no existe");
        }
        return carritos.get(idCarrito);
    }

}
