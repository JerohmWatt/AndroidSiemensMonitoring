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

    private EditText et_creacc_lastname;
    private EditText et_creacc_firstname;
    private EditText et_creacc_mail;
    private EditText et_creacc_password, getEt_creacc_passwordconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        et_creacc_lastname = findViewById(R.id.et_creacc_lastname);
        et_creacc_firstname = findViewById(R.id.et_creacc_firstname);
        et_creacc_mail = findViewById(R.id.et_creacc_mail);
        et_creacc_password = findViewById(R.id.et_creacc_password);
        getEt_creacc_passwordconfirm = findViewById(R.id.et_creacc_passwordconfirm);
    }

    public void onCreateAccClickManager(View v) {

        switch (v.getId()) {
            case R.id.bt_creacc_inscr:
                if(et_creacc_password.getText().toString().equals(getEt_creacc_passwordconfirm.getText().toString()) && et_creacc_password.getText().length() > 3) {
                    User user1 = new User(et_creacc_lastname.getText().toString(),
                            et_creacc_firstname.getText().toString(),
                            et_creacc_mail.getText().toString(),
                            et_creacc_password.getText().toString(),
                            1);
                    UserAccessDB userDB = new UserAccessDB(this);
                    userDB.openForWrite();
                    userDB.insertUser(user1);
                    userDB.Close();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Les mots de passe ne correspondent pas, ou sont inférieurs à 4 caractères", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
