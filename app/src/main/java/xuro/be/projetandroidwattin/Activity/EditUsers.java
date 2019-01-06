package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import xuro.be.projetandroidwattin.BDD.User;
import xuro.be.projetandroidwattin.BDD.UserAccessDB;
import xuro.be.projetandroidwattin.R;

public class EditUsers extends Activity {

    TextView tv_file_datas;
    /**
     *
     */
    ListView lv_file_liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        tv_file_datas = (TextView) findViewById(R.id.tv_file_datas);
        tv_file_datas.setText("Contenu de la table Utilisateurs:\n");
        lv_file_liste = (ListView) findViewById(R.id.lv_file_liste);
        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForRead();
        ArrayList<User> tab_user = userDB.getAllUsers();
        userDB.Close();
        if(tab_user.isEmpty())Toast.makeText(this, "Base de données vide !",
                Toast.LENGTH_SHORT).show();
        else { ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tab_user);
            lv_file_liste.setAdapter(adapter);
        }
    }
}
