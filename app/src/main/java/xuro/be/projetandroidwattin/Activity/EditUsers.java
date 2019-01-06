package xuro.be.projetandroidwattin.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    ListView lv_file_liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        tv_file_datas = (TextView) findViewById(R.id.tv_file_datas);
        tv_file_datas.setText("Contenu de la table Utilisateurs:\n Droit 1 = read; Droit 2 = lecture/écriture");
        lv_file_liste = (ListView) findViewById(R.id.lv_file_liste);
        updateList();

        lv_file_liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                int pos = position;
                Log.i("poss", "position clicked is " + pos);
                dialogRights(pos);

            }
        });
    }
        public void dialogRights(final int idUser){

            String[] singleChoiceItems = getResources().getStringArray(R.array.dialog_single_choice_array);
            int itemSelected = 0;
            final int[] choice = new int[1];
            new AlertDialog.Builder(this)
                    .setTitle("Quels droits voulez-vous appliquer à l'utilisateur")
                    .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                            choice[0] = selectedIndex;
                        }
                    })
                    .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int rights;
                            if (choice[0] == 0){
                                rights = 1;
                            }
                            else {
                                rights = 2;
                            }
                            editRights(idUser+1,rights);

                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

        public void editRights (int id, int rights){

        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForWrite();
        User user1 = new User();
        user1 = userDB.getUser(id);
        user1.setRights(rights);
        userDB.updateUser(id,user1);
        userDB.Close();
        updateList();
        }

        public void updateList(){
            UserAccessDB userDB = new UserAccessDB(this);
            userDB.openForRead();
            ArrayList<User> tab_user = userDB.getAllUsers();
            userDB.Close();
            if (tab_user.isEmpty()) Toast.makeText(this, "Base de données vide !",
                    Toast.LENGTH_SHORT).show();
            else {
                ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, tab_user);
                lv_file_liste.setAdapter(adapter);
            }
        }





}
