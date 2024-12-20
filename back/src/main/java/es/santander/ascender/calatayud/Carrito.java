package es.santander.ascender.calatayud;

import java.util.HashMap;

public class Carrito {

    private int id;
    private HashMap<Integer, Integer> contenido;

    public Carrito (int id) {
        this.id = id;
        this.contenido = new HashMap<>();
    }

    public void setContenido(HashMap<Integer, Integer> contenido) {
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, Integer> getContenido() {
        return contenido;
    }   

    public void añadirProducto(int idProducto, int cantidad) {
        if (contenido.containsKey(idProducto)) {
            contenido.put(idProducto, contenido.get(idProducto) + cantidad);
        } else {
            contenido.put(idProducto, cantidad);
        }
    }

    public void vaciarCarrito() {
        contenido.clear();
    }
    

    

   
}
