package es.santander.ascender.calatayud;

public class Carrito {
    private int id;
    private int idProducto;
    private int cantidad;


    public Carrito() {

    }


    public Carrito(int id, int idProducto, int cantidad) {
        this.id = id;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getIdProducto() {
        return idProducto;
    }


    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }


    public int getCantidad() {
        return cantidad;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    

    

    
}
