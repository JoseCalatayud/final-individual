package es.santander.ascender.calatayud;

import java.util.HashMap;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
       
        Producto producto1 = new Producto(1, "Camiseta", "Camiseta de algodón", 19.99f, 100);
        Producto producto2 = new Producto(2, "Zapatos", "Zapatos deportivos", 49.99f, 50);
        Producto producto3 = new Producto(3, "Gorra", "Gorra de béisbol", 15.50f, 200);        

        HashMap<Integer, Producto> productos=new HashMap<>();

        productos.put(producto1.getId(), producto1);
        productos.put(producto2.getId(), producto2);
        productos.put(producto3.getId(), producto3);
        Tienda tienda = new Tienda(productos);

        HashMap <Integer, Integer> productosVendidos = new HashMap<>();
        productosVendidos.put(1,20);
        productosVendidos.put(3,20);
        productosVendidos.put(2,20);

       // tienda.venderProductos(productosVendidos);
        System.out.println(tienda.verStockProducto(1));
        System.out.println(tienda.verStockProducto(2));
        System.out.println(tienda.verStockProducto(3));

    }
}
