package es.eduardsanz.ejercicio2.HELPERS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import es.eduardsanz.ejercicio2.Configuracion;
import es.eduardsanz.ejercicio2.POJO.Producto;

/**
 * todo: Completar lo necesario para que el Controlador de la BD funcione
 */

public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private Dao<Producto, Integer> productosDao;

    public OrmHelper(Context context) {
        super(context, Configuracion.DB_NAME, null, Configuracion.DB_VERSION);  // Vars de configuración
    }

    // Crear tabla en base a la clase Producto
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Producto.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Lo mismo al actualizar (habrá que modificar la DB_VERSION en Configuracion)
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTable(connectionSource, Producto.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Return productosDao
    public Dao<Producto, Integer> getProductosDao() throws SQLException {
        if (productosDao == null)
            productosDao = this.getDao(Producto.class);
        return productosDao;
    }
}
