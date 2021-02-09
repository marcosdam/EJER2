package es.eduardsanz.ejercicio2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.ArrayList;

import es.eduardsanz.ejercicio2.ADAPTERS.RecyclerViewAdapter;
import es.eduardsanz.ejercicio2.HELPERS.OrmHelper;
import es.eduardsanz.ejercicio2.POJO.Producto;
import es.eduardsanz.ejercicio2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Binding
    private ActivityMainBinding binding;

    // Helper, Adapter, DAO
    private OrmHelper helper;
    private RecyclerView recyclerView;
    private ArrayList<Producto> listaProductos;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Dao<Producto, Integer> daoProductos;
    private int resource = R.layout.elemento_recycler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = findViewById(R.id.recyclerView);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProducto().show();
            }
        });

        // ArrayList y Helper
        listaProductos = new ArrayList<>();
        helper = OpenHelperManager.getHelper(this, OrmHelper.class);
        try {
            daoProductos = helper.getProductosDao();
            listaProductos.addAll(daoProductos.queryForAll());  // Rellenar la lista con elems de la BD
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // LayoutManager, Adapter y Recycler
        adapter = new RecyclerViewAdapter(listaProductos, resource, this, daoProductos);
        layoutManager = new LinearLayoutManager(this);
        binding.contenedor.recyclerView.setHasFixedSize(true);
        binding.contenedor.recyclerView.setAdapter(adapter);
        binding.contenedor.recyclerView.setLayoutManager(layoutManager);

        refrescarVentana();
    }

    /**
     * Método que crea un diálogo para insertar un nuevo producto en la base de datos
     * y se refresque la ventana principal mostrando el nuevo producto
     * @return
     */
    private AlertDialog createProducto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alert = LayoutInflater.from(this).inflate(R.layout.new_prodcuto, null);

        final EditText txtImagenProducto = alert.findViewById(R.id.txtImagenProducto);
        final EditText txtNombreProducto = alert.findViewById(R.id.txtNombreProducto);
        final EditText txtPrecioProducto = alert.findViewById(R.id.txtPrecioProducto);



        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!txtImagenProducto.getText().toString().isEmpty()
                        && !txtNombreProducto.getText().toString().isEmpty()
                        && !txtPrecioProducto.getText().toString().isEmpty()){
                    // Crearlo
                    Producto p = new Producto(txtImagenProducto.getText().toString(),
                            txtNombreProducto.getText().toString(),
                            Float.parseFloat(txtPrecioProducto.getText().toString()));
                    // Añadirlo al DAO y a la lista
                    try {
                        daoProductos.create(p);
                        p.setIdProducto(daoProductos.extractId(p)); // Darle ID
                        listaProductos.add(p);  // Añadirlo a la lista
                        refrescarVentana();
                    } catch (SQLException throwables) {
                        Toast.makeText(MainActivity.this, "ERROR EN LA BD", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(alert);

        return builder.create();
    }

    /**
     * Método encargado de refrescar la Actividad para mostrar los nuevos productos
     */
    private void refrescarVentana(){
        adapter.notifyDataSetChanged();
    }

}
