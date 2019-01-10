package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.Models.SessionManagement;
import xuro.be.projetandroidwattin.R;

public class LoginActivity extends Activity {

    private EditText et_main_mail;
    private EditText et_main_pwd;
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_main_mail = findViewById(R.id.et_main_mail);
        et_main_pwd = findViewById(R.id.et_main_pwd);
        session = new SessionManagement(getApplicationContext());
        createFileConfig();
    }

    public void onMainClickManager(View v) {

        switch (v.getId()){
            case R.id.bt_main_inscription:
               Intent intent = new Intent(this,CreateAccActivity.class);
               startActivity(intent);
                break;
/**
 *
 * When login button is pressed, compare the email and password entered to all users in the DB
 * If user exists, get it's right number and start either the user activity or the super user one
 */
            case R.id.bt_main_login:
                if(isUser(et_main_mail.getText().toString(),et_main_pwd.getText().toString())) {
                    int rights = defineRights(et_main_mail.getText().toString());
                    session.createLoginSession(et_main_mail.getText().toString(),Integer.toString(rights));

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
                    Toast.makeText(this,R.string.incorrectlogin,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * Method checking if login entered is correct
     * @param mail is the user email adress
     * @param pwd is the user password
     * @return yes if credentials are corrects
     */
    private boolean isUser(String mail, String pwd){
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

    public void createFileConfig(){
        if(new File(String.valueOf(this.getFilesDir()) + "/cznfig.txt").isFile()) {
            Log.i("texte","file exist");
        }
        else {
            Log.i("texte","file doesnt exist");
            try {
                FileOutputStream confile = openFileOutput("config.txt", MODE_PRIVATE);
                for (int i = 0; i<=1; i++){
                byte[] tab;
                String a = "192.168.0.15#";
                tab = a.toString().getBytes();
                confile.write(tab);
                String r = "0#";
                tab = r.toString().getBytes();
                confile.write(tab);
                String s = "2#";
                tab = s.toString().getBytes();
                confile.write(tab);
                }
                confile.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Method returning user rights from a defined email adress
     * @param mail
     * @return rights as an int
     */
    private int defineRights(String mail){
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
