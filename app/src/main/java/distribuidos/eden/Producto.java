package distribuidos.eden;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id, cantidad;
    private String nombreProducto,descripcion;
    private double precio;

    public Producto() {
    }

    public Producto(int id, int cantidad, String nombreProducto, String descripcion, double precio) {
        this.id = id;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
