package distribuidos.eden;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import Interfaces.IcomunicaFragments;

public class SecondActivity extends AppCompatActivity implements IcomunicaFragments {
    public static final String KEY_ADD = "True";
    public static final String KEY_ACT = "UPDATE";
    private EditFragment ef = new EditFragment();
    private String op;
    private Producto p;
    private String url = "http://eden-floreria.000webhostapp.com/";
    //private String url = "http://192.168.1.117/AndroidService/";
   // private String url = "http://b996f398.ngrok.io/AndroidService/";
    private Bundle bundle = new Bundle();
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);

        LlamarFragmenteUpdate();
        LlamarFragmenteAdd();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void LlamarFragmenteUpdate() {
        p = (Producto) getIntent().getSerializableExtra(RecyclerViewAdaptador.KEY_UPDATE);

        if (p != null) {
            bundle.putSerializable(KEY_ACT, p);
            ef.setArguments(bundle);
            fm.beginTransaction().add(R.id.AdminLayout, ef).commit();
        }
    }

    public void LlamarFragmenteAdd() {
        op = getIntent().getStringExtra(ClientActivity.KEY_LAYOUT_ADD);

        if (op != null && op.equals("true")) {
            bundle.putString(KEY_ADD, KEY_ADD);
            ef.setArguments(bundle);
            fm.beginTransaction().add(R.id.AdminLayout, ef).commit();
        }
    }

    @Override
    public void onClickBtbUpdate(final Producto p) {
        url += "update.php?id=" + p.getId() + "&&nombre=" + p.getNombreProducto() + "&&precio=" + p.getPrecio() +
                "&&detalle=" + p.getDescripcion() + "&&stock=" + p.getCantidad();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SecondActivity.this, "Se actualizó el producto del ID: " + p.getId(), Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }


    @Override
    public void onClickBtbDelete(final Producto p) {
        url += "/delete.php?id=" + p.getId();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SecondActivity.this, "Se eliminó el producto con el ID: " + p.getId(), Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

    @Override
    public void onClickBtbAdd(Producto p) {
        url += "insert.php?nombre=" + p.getNombreProducto() + "&&precio=" + p.getPrecio() + "&&detalle=" + p.getDescripcion() + "&&stock=" + p.getCantidad();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SecondActivity.this, "Se ingreso el nuevo dato.", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error al ingresar", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(request);


    }
}
