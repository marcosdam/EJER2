package es.eduardsanz.ejercicio2.ADAPTERS;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.eduardsanz.ejercicio2.Configuracion;
import es.eduardsanz.ejercicio2.POJO.Producto;
import es.eduardsanz.ejercicio2.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Elemento> {

    // Variables necesarias
    private List<Producto> objects;
    private int resource;
    private Context context;
    private Dao<Producto, Integer> daoProductos;

    // Const
    public RecyclerViewAdapter(List<Producto> objects, int resource, Context context, Dao<Producto, Integer> daoProductos) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.daoProductos = daoProductos;
    }

    @NonNull
    @Override
    public Elemento onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View productoItem = LayoutInflater.from(context).inflate(resource, null);
        productoItem.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new Elemento(productoItem);
    }

    @Override
    public void onBindViewHolder(@NonNull Elemento elemento, int i) {
        Producto producto = objects.get(i);
        elemento.imgElemento.setImageURI(Uri.parse(producto.getImagen()));
        elemento.lblNombre.setText(producto.getNombre());
        elemento.lblPrecio.setText(String.valueOf(producto.getPrecio()));

        // Imagen
        Picasso.get()
                .load(Configuracion.URL_BASE + objects.get(i).getImagen())
                .error(R.drawable.imagenotavailable)
                .placeholder(R.drawable.imagenotavailable)
                .into(elemento.imgElemento);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class Elemento extends RecyclerView.ViewHolder{
        ImageView imgElemento;
        TextView lblNombre, lblPrecio;
        View card;

        public Elemento(@NonNull View itemView) {
            super(itemView);
            imgElemento = itemView.findViewById(R.id.imgElemento);
            lblNombre = itemView.findViewById(R.id.lblNombreElemento);
            lblPrecio = itemView.findViewById(R.id.lblPrecioElemento);
            card = itemView;
        }
    }
}
