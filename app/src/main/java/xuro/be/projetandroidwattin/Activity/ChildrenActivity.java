package xuro.be.projetandroidwattin.Activity;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import xuro.be.projetandroidwattin.R;

public class ChildrenActivity extends Activity {

    //variables globales
    EditText et_children_login;
    EditText et_children_pwd;
    EditText et_children_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);

        //récupération du texte et cast
        et_children_login = (EditText)findViewById(R.id.et_children_login);
        et_children_pwd = (EditText)findViewById(R.id.et_children_pwd);
        et_children_email = (EditText)findViewById(R.id.et_children_email);
    }

    public void onChildrenClickManager(View v) {
// Récupérer la vue et accéder au bouton
        switch (v.getId()) {
            case R.id.bt_children_main:
                Toast.makeText(getApplicationContext()," Login : " + et_children_login.getText() + "\n Password: " + et_children_pwd.getText() + "\n Email : " + et_children_email.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

