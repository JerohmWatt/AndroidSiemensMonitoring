package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class LoginActivity extends Activity {

    EditText et_main_mail;
    EditText et_main_pwd;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //appel unique à la création de l'acti
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Mon projet","méthode oncreate");
        et_main_mail = (EditText)findViewById(R.id.et_main_mail);
        et_main_pwd = (EditText)findViewById(R.id.et_main_pwd);
        session = new SessionManagement(getApplicationContext());
    }

    public void onMainClickManager(View v) {
        //récup la vue et accès au bouton

        switch (v.getId()){
            case R.id.bt_main_inscription:
               Intent intent = new Intent(this,CreateAccActivity.class);
               startActivity(intent);
                break;

            case R.id.bt_main_login:
                if(isUser(et_main_mail.getText().toString(),et_main_pwd.getText().toString())) {
                    int rights = defineRights(et_main_mail.getText().toString());
                    session.createLoginSession(et_main_mail.getText().toString());
                    if (rights == 1 || rights == 2){
                    Intent intent2 = new Intent(this, UserHome.class);
                    startActivity(intent2);}
                    else if (rights == 3){
                        Intent intent3 = new Intent(this, AdminHome.class);
                        startActivity(intent3);
                    }


                    break;
                }
                else{
                    Toast.makeText(this,"Adresse email ou mot de passe incorrect",Toast.LENGTH_LONG).show();
                }


                break;
        }
    }

    public boolean isUser(String mail, String pwd){
        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForWrite();
        ArrayList<User> listUsers = userDB.getAllUsers();
        for (User user : listUsers) {
            if (user.getEmail().equals(mail) && user.getPassword().equals(pwd)) {
                return true;
            }
            }
        return false;
    }

    //cette methode appel la db et fait un select sur le mail, pour retourner un int des droits
    public int defineRights(String mail){
        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForRead();
        int i = userDB.getRights(mail);
        userDB.Close();
        return i;
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
