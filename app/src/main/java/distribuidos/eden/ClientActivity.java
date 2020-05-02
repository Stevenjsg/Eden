package distribuidos.eden;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

    private List<Producto> pro;
    private RecyclerView recyclerView;
    private String url = "http://eden-floreria.000webhostapp.com/productos.php";
    //private static final String url = "http://192.168.1.117/AndroidService/productos.php";
    //private static final String url = "http://b996f398.ngrok.io/AndroidService/productos.php";
    private TextView txtNomAd;
    private EditFragment af = new EditFragment();
    public static final String KEY_CON = "user";
    static String nombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_client);
        txtNomAd = findViewById(R.id.txtNomAdmin);


        nombre = getIntent().getStringExtra(KEY_CON);
        Bundle bundle = new Bundle();
        txtNomAd.setText(" " + nombre);


        recyclerView = (RecyclerView) findViewById(R.id.idRecycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Cargando productos...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setTitle("Desea salir")
                    .setMessage("¿Está usted seguro de querer salir?")
                    .setNegativeButton(android.R.string.cancel, null)// sin listener
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
// Salir
                            finish();
                        }
                    })
                    .show();

// Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
// para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getProducto() {
        pro = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;


                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        pro.add(new Producto(
                                jsonObject.getInt("id_producto"),
                                jsonObject.getInt("stock"),
                                jsonObject.getString("nombre"),
                                jsonObject.getString("detalle"),
                                jsonObject.getDouble("precio")
                        ));
                    }

                    RecyclerViewAdaptador rca = new RecyclerViewAdaptador(pro);
                    recyclerView.setAdapter(rca);


                } catch (Exception ex) {

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

   /* public static final long PERIODO = 30000; // 60 segundos (60 * 1000 millisegundos)
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                getProducto();
                handler.postDelayed(this, PERIODO);
            }
        };
        handler.postDelayed(runnable, PERIODO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        getProducto();
    }

    public static final String KEY_LAYOUT_ADD = "true";

    public void onInstaceAdd(View view) {
        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra(KEY_LAYOUT_ADD, KEY_LAYOUT_ADD);
        startActivity(i);
    }

    public void onClickRefresch(View view) {
        getProducto();
        Toast.makeText(getApplication(), "Cargando productos...", Toast.LENGTH_LONG).show();
    }
}
