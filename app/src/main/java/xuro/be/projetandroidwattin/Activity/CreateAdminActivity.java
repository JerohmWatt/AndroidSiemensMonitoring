package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.R;

public class CreateAdminActivity extends Activity {

    private EditText et_createadmin_lastname,et_createadmin_firstname,et_createadmin_mail,et_createadmin_pwd,et_createadmin_pwdconfirm;
    private Button bt_createadmin_confirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_supwd);
        Toast.makeText(this, "Premier lancement : veuillez créer le compte administrateur", Toast.LENGTH_LONG).show();
        et_createadmin_lastname = findViewById(R.id.et_createadmin_lastname);
        et_createadmin_firstname = findViewById(R.id.et_createadmin_firstname);
        et_createadmin_mail = findViewById(R.id.et_createadmin_mail);
        et_createadmin_pwd = findViewById(R.id.et_createadmin_pwd);
        et_createadmin_pwdconfirm = findViewById(R.id.et_createadmin_pwdconfirm);
        bt_createadmin_confirm = findViewById(R.id.bt_createadmin_confirm);
    }
        public void onCreateAdminClickManager(View v) {
            switch (v.getId()) {
                case R.id.bt_createadmin_confirm:
                    addAdmin();
                    break;
            }
    }

    public void addAdmin(){
        if (et_createadmin_pwdconfirm.getText().toString().equals(
                et_createadmin_pwd.getText().toString()) &&
                et_createadmin_pwdconfirm.getText().toString().length() > 3){
        User user1 = new User(et_createadmin_lastname.getText().toString(),
                et_createadmin_firstname.getText().toString(),
                et_createadmin_mail.getText().toString(),
                et_createadmin_pwd.getText().toString(),
                3);
        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForWrite();
        userDB.insertUser(user1);
        userDB.Close();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);}
        else{
            Toast.makeText(this, "Les mots de passe ne correspondent pas, ou sont inférieurs à 4 caractères", Toast.LENGTH_SHORT).show();
        }
    }
}
