package distribuidos.eden;

import android.content.Intent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static distribuidos.eden.ClientActivity.nombre;

;

public class Conexion {

    private ArrayList<Producto> pro = new ArrayList<>();
    private RequestQueue queue;
    private StringRequest jrq;
    private String url = "http://eden-floreria.000webhostapp.com/login.php";
    //private String url = "http://192.168.1.117/AndroidService/login.php";
    //private String url = "http://b996f398.ngrok.io/AndroidService/login.php";
    private View view;

    public Conexion(View view) {
        this.view = view;
        queue = Volley.newRequestQueue(view.getContext());
    }

    public void login(String usuario, String contrasena) {
        url += "?usuario=" + usuario + "&&contrasena=" + contrasena;
        jrq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    administrador ad = new administrador();
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonObject = null;

                    jsonObject = jsonArray.getJSONObject(0);
                    ad.setUser(jsonObject.getString("username"));
                    ad.setNombre(jsonObject.getString("nombre"));
                    ad.setApellido(jsonObject.getString("apellido"));
                    ad.setPass(jsonObject.getString("contrasena"));
                    Intent intent = new Intent(view.getContext(), ClientActivity.class);
                    nombre = ad.getNombre() + " " + ad.getApellido();
                    intent.putExtra(ClientActivity.KEY_CON, nombre);
                    view.getContext().startActivity(intent);
                } catch (Exception ex) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jrq);
    }
}
