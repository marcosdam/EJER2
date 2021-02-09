package es.eduardsanz.ejercicio2.POJO;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * todo: Conmpletar la clase producto con TODO lo encesario para que sea un objeto
 * de tipo ORMLite
 */

// Mapeado (notaciones)
@DatabaseTable(tableName = "productos")
public class Producto {

    // Primary Key
    @DatabaseField(generatedId = true, columnName = "id_producto")  // Autoincremental
    private int idProducto;

    @DatabaseField(canBeNull = false)
    private  String imagen;
    @DatabaseField(canBeNull = false)
    private String nombre;
    @DatabaseField(canBeNull = false, defaultValue = "0")
    private float precio;

    // Const vacío para ORMLite
    public Producto() {
    }

    public Producto(String imagen, String nombre, float precio) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
