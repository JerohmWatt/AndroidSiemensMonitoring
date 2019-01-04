package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.R;

public class MainActivity extends Activity {

    EditText et_main_mail;
    EditText et_main_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //appel unique à la création de l'acti
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Mon projet","méthode oncreate");
        et_main_mail = (EditText)findViewById(R.id.et_main_mail);
        et_main_pwd = (EditText)findViewById(R.id.et_main_pwd);
    }

    public void onMainClickManager(View v) {
        //récup la vue et accès au bouton

        switch (v.getId()){
            case R.id.bt_main_inscription:
               Intent intent = new Intent(this,CreateAccActivity.class);
               startActivity(intent);
                break;

            case R.id.bt_main_login:
                //fonction qui retourne bool en cas d'exist ?
                UserAccessDB userDB = new UserAccessDB(this);
                userDB.openForWrite();
                ArrayList<User> listUsers = userDB.getAllUsers();
                String mail = et_main_mail.getText().toString();
                String pwd = et_main_pwd.getText().toString();
                for (User user : listUsers){
                    if (user.getEmail().equals(mail) && user.getPassword().equals(pwd)){
                        Toast.makeText(this,"ok il existe",Toast.LENGTH_LONG).show();}
            }

                break;

        }
    }

    @Override
    protected void onStart() {
        // appel quand acti devient visible
        super.onStart();
        Log.i("Mon projet","méthode onstart");
    }

    @Override
    protected void onRestart() {
        // appel quand acti stop et redémarrée
        super.onRestart();
        Log.i("Mon projet","méthode onrestart");
    }

    @Override
    protected void onResume(){
        //appel quand focus acquis par acti
        super.onResume();
        Log.i("Mon projet","  méthode onResume");
    }
    @Override
    protected void onPause(){
        // appel quand perte focus
        super.onPause();
        Log.i("Mon projet","  méthode OnPause");
    }
    @Override
    protected void onDestroy(){
        // appel quand détruite + libération de mémoire
        super.onDestroy();
        Log.i("Mon projet","  méthode onDestroy");
    }
    @Override
    protected void onStop(){
        // appel quand appli plus visible
        super.onStop();
        Log.i("Mon projet","  méthode onStop");
    }
}
