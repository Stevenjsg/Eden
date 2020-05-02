package distribuidos.eden;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    private Context mContext;
    private List<Producto> listaproducto;
    public static final String KEY_UPDATE = "UPDATE";


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtnombrePro, txtDescrip, txtPrecio, txtCant, txtId;
        public ImageView menu;
        private CardView cardproducto;

        public ViewHolder(@NonNull final View vista) {
            super(vista);
            txtId = vista.findViewById(R.id.txt_id);
            txtnombrePro = (TextView) vista.findViewById(R.id.txt_nomProducto);
            txtDescrip = (TextView) vista.findViewById(R.id.txt_descripcion);
            txtPrecio = (TextView) vista.findViewById(R.id.txt_precio);
            txtCant = (TextView) vista.findViewById(R.id.txt_cantidad);
            cardproducto = (CardView) vista.findViewById(R.id.cardproducto);
            cardproducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Producto p = new Producto();
                    p.setId(Integer.valueOf(txtId.getText().toString()));
                    p.setNombreProducto(txtnombrePro.getText().toString());
                    p.setDescripcion(txtDescrip.getText().toString());
                    p.setCantidad(Integer.valueOf(txtCant.getText().toString()));
                    p.setPrecio(Double.valueOf(txtPrecio.getText().toString()));
                    Intent i = new Intent(vista.getContext(),SecondActivity.class);
                    i.putExtra(KEY_UPDATE, p);
                    vista.getContext().startActivity(i);
                }
            });
        }
    }


    public RecyclerViewAdaptador(List<Producto> listaproducto) {
        this.listaproducto = listaproducto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View vista = layoutInflater.inflate(R.layout.tarjeta, parent, false);
        this.mContext = parent.getContext();
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Producto pro = listaproducto.get(position);
        holder.txtId.setText(String.valueOf(pro.getId()));
        holder.txtnombrePro.setText(pro.getNombreProducto());
        holder.txtDescrip.setText(pro.getDescripcion());
        holder.txtCant.setText(String.valueOf(pro.getCantidad()));
        holder.txtPrecio.setText(String.valueOf(pro.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return listaproducto.size();
    }


}
