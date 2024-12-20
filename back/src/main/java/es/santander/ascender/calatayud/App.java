package es.santander.ascender.calatayud;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Producto producto1 = new Producto(1, "Camiseta", "Camiseta de algodón", 19.99f, 100);
        Producto producto2 = new Producto(2, "Zapatos", "Zapatos deportivos", 49.99f, 50);
        Producto producto3 = new Producto(3, "Gorra", "Gorra de béisbol", 15.50f, 200);
        Producto producto4 = new Producto(4, "Pantalones", "Pantalones vaqueros", 39.99f, 75);
        Producto producto5 = new Producto(5, "Chaqueta", "Chaqueta impermeable", 59.99f, 30);
        Producto producto6 = new Producto(6, "Mochila", "Mochila escolar", 29.99f, 150);
        Producto producto7 = new Producto(7, "Reloj", "Reloj digital deportivo", 89.99f, 40);
        Producto producto8 = new Producto(8, "Bufanda", "Bufanda de lana", 12.99f, 120);

        HashMap<Integer, Producto> productos = new HashMap<>();

        productos.put(producto1.getId(), producto1);
        productos.put(producto2.getId(), producto2);
        productos.put(producto3.getId(), producto3);
        productos.put(producto4.getId(), producto4);
        productos.put(producto5.getId(), producto5);
        productos.put(producto6.getId(), producto6);
        productos.put(producto7.getId(), producto7);
        productos.put(producto8.getId(), producto8);

        Tienda tienda = new Tienda(productos);

        int opcion = 1;
        while (opcion != 0) {
            System.out.println("1. Listar productos");
            System.out.println("2. Listar producto por id");
            System.out.println("3. Añadir nuevo producto");
            System.out.println("4. Añadir stock");
            System.out.println("5. Crear carrito");
            System.out.println("6. Añadir producto al carrito");
            System.out.println("7. Vaciar carrito");
            System.out.println("8. Vender productos");
            System.out.println("0. Salir");

            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    for (Producto producto : tienda.listarProductos()) {
                        System.out.println(producto.getNombre());
                    }
                    break;
                case 2:
                    System.out.println("Introduce el id del producto");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println(tienda.listarPorId(id).getNombre());
                    break;
                case 3:
                    System.out.println("Introduce los datos del producto");
                    System.out.println("Id:");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Nombre:");
                    String nombre = sc.nextLine();
                    System.out.println("Descripción:");
                    String descripcion = sc.nextLine();
                    System.out.println("Precio:");
                    float precio = sc.nextFloat();
                    System.out.println("Cantidad:");
                    int cantidad = sc.nextInt();
                    Producto producto = new Producto(id, nombre, descripcion, precio, cantidad);
                    tienda.añadirNuevoProducto(producto);
                    break;
                case 4:
                    System.out.println("Introduce el id del producto");
                    id = sc.nextInt();
                    System.out.println("Ahora tenemos " + tienda.verStockProducto(id));
                    System.out.println("Introduce la cantidad a añadir");
                    cantidad = sc.nextInt();
                    tienda.añadirStock(id, cantidad);
                    System.out.println("Ahora tenemos " + tienda.verStockProducto(id));
                    break;
                case 5:
                    tienda.crearCarrito(3);
                    break;
                case 6:
                    System.out.println("Introduce el id del producto");
                    id = sc.nextInt();
                    System.out.println("Introduce la cantidad a añadir");
                    cantidad = sc.nextInt();
                    System.out.println("Introduce el id del carrito");
                    int idCarrito = 3;
                    tienda.llenarCarrito(idCarrito, cantidad, id);

                    break;
                case 7:
                    tienda.vaciarCarrito(3);
                    break;
                case 8:
                    tienda.venderProductos(3);
                    break;
                case 0:
                    sc.close();
                    System.out.println("Hasta luego");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

        }
    }
}
