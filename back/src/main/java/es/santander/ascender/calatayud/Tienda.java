package es.santander.ascender.calatayud;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Tienda {

    private HashMap<Integer, Producto> productos;

    public Tienda(HashMap<Integer, Producto> productos) {
        this.productos = productos;
    }

    public List<Producto> listarProducto() throws NullPointerException {
        List<Producto> listaProductos = productos.entrySet().stream()
                .map((p) -> p.getValue())
                .collect(Collectors.toList());
        return listaProductos;
    }

    public List<Producto> listarPorTipoProducto(String tipo) {

        List<Producto> listaProductos = productos.entrySet().stream()
                .filter((p) -> (p.getValue().getNombre().equals(tipo)))
                .map((p) -> p.getValue())
                .collect(Collectors.toList());
        return listaProductos;

    }
    public List<Producto> listarPorId(int id) {

        List<Producto> listaProductos = productos.entrySet().stream()
                .filter((p) -> (p.getValue().getId() == id))
                .map((p) -> p.getValue())
                .collect(Collectors.toList());
        return listaProductos;

    }
    public int verStockProducto (int id){
        int stock = productos.get(id).getCantidad();
        return stock;
    }

    
    public void venderProductos (HashMap <Integer, Integer> productosVendidos) { 
        for (Integer producto : productosVendidos.keySet()) {
            if (productos.get(producto).getCantidad()==0){
                System.out.println("Sin stock");
            }else {
                int cantidad = productos.get(producto).getCantidad();
                productos.get(producto).setCantidad(cantidad-productosVendidos.get(producto));
                
            }
        }
        
    }



}
