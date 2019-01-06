package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.R;

public class CreateAccActivity extends Activity {

    EditText et_creacc_lastname;
    EditText et_creacc_firstname;
    EditText et_creacc_mail;
    EditText et_creacc_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        et_creacc_lastname = (EditText)findViewById(R.id.et_creacc_lastname);
        et_creacc_firstname = (EditText)findViewById(R.id.et_creacc_firstname);
        et_creacc_mail = (EditText)findViewById(R.id.et_creacc_mail);
        et_creacc_password = (EditText)findViewById(R.id.et_creacc_password);
    }

    public void onCreateAccClickManager(View v) {
// Récupérer la vue et accéder au bouton
        switch (v.getId()) {
            case R.id.bt_creacc_inscr:
                String str = et_creacc_lastname.getText().toString() + "#" + et_creacc_firstname.getText().toString() + "#" + et_creacc_mail.getText().toString() + "#";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                //création de l'utilisateur avec récup des données, à faire > vérification de forme
                User user1 = new User(et_creacc_lastname.getText().toString(),
                        et_creacc_firstname.getText().toString(),
                        et_creacc_mail.getText().toString(),
                        et_creacc_password.getText().toString(),
                        1); //1 car utilisateur "lambda"
                UserAccessDB userDB = new UserAccessDB(this);
                userDB.openForWrite();
                userDB.insertUser(user1);
                userDB.Close();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


}
