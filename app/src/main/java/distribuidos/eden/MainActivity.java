package distribuidos.eden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    public static final String VACIO = "Requerido 👎🏽";
    private TextInputLayout txtUser;
    private TextInputLayout txtPass;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        txtUser = (TextInputLayout) findViewById(R.id.txtUser);
        txtPass = (TextInputLayout) findViewById(R.id.txtPass);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

    }

    public void oncliker(View view) {
        if (validar()) {
            Conexion con = new Conexion(view);
            con.login(txtUser.getEditText().getText().toString(), txtPass.getEditText().getText().toString());
        }
    }

    private boolean validar() {
        if (txtUser.getEditText().getText().toString().isEmpty()) {
            txtUser.setError(VACIO);
            return false;
        }
        if (txtPass.getEditText().getText().toString().isEmpty()) {
            txtPass.setError(VACIO);
            return false;
        }
        return true;
    }


    public void omitir(View view) {
        Intent i = new Intent(this, ClientActivity.class);
        startActivity(i);
    }
}
