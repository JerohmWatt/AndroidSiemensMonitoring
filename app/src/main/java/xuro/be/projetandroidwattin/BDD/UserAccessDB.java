package xuro.be.projetandroidwattin.BDD;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserAccessDB {
    private static final int VERSION = 1;
    private static final String NAME_DB = "WattinDroid.db";
    private static final String TABLE_USER = "Table_users";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_LASTNAME = "LASTNAME";
    private static final int NUM_COL_LASTNAME = 1;
    private static final String COL_FIRSTNAME = "FIRSTNAME";
    private static final int NUM_COL_FIRSTNAME = 2;
    private static final String COL_PASSWORD = "PASSWORD";
    private static final int NUM_COL_PASSWORD = 3;
    private static final String COL_EMAIL = "EMAIL";
    private static final int NUM_COL_EMAIL = 4;
    private static final String COL_RIGHTS = "RIGHTS";
    private static final int NUM_COL_RIGHTS = 5;
    private SQLiteDatabase db;
    private UserBddSqlite userdb;

    public UserAccessDB(Context c){
        userdb = new UserBddSqlite(c, NAME_DB,null,VERSION);
    }
    public void openForWrite(){
        db = userdb.getWritableDatabase();
    }
    public void openForRead(){
        db = userdb.getReadableDatabase();
    }
    public void Close(){
        db.close();
    }


    // methode màj user slide 199

    //méthode pour add user
    public long insertUser(User u){
        ContentValues content = new ContentValues();
        content.put(COL_LASTNAME, u.getLastname());
        content.put(COL_FIRSTNAME, u.getFirstname());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_EMAIL, u.getEmail());
        content.put(COL_RIGHTS, u.getRights());
        return db.insert(TABLE_USER, null, content);
    }

    //méthode pour récupérer les infos d'un user apd de son combo mail/password
    public User getUser(String email, String password) {
        Cursor c = db.query(TABLE_USER, new String[] {
                        COL_ID, COL_LASTNAME,COL_FIRSTNAME, COL_PASSWORD,COL_EMAIL, COL_RIGHTS },
                COL_EMAIL + " LIKE \"" + email + "\"" + "AND " +COL_PASSWORD + " LIKE \"" + password,
                null, null, null, COL_LASTNAME);
        return cursorToUser(c);
    }

    //méthode pour transformer un objet cursor en objet métier user
    public User cursorToUser(Cursor c) {
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToFirst();
        User user1 = new User();
        user1.setId(c.getInt(NUM_COL_ID));
        user1.setLastname(c.getString(NUM_COL_LASTNAME));
        user1.setFirstname(c.getString(NUM_COL_FIRSTNAME));
        user1.setPassword(c.getString(NUM_COL_PASSWORD));
        user1.setEmail(c.getString(NUM_COL_EMAIL));
        user1.setRights(c.getInt(NUM_COL_RIGHTS));
        c.close();
        return user1;
    }

    public ArrayList<User> getAllUsers() {
        Cursor c = db.query(TABLE_USER, new String[] {
                        COL_ID, COL_LASTNAME, COL_FIRSTNAME, COL_PASSWORD, COL_EMAIL, COL_RIGHTS }, null, null, null, null,
                COL_ID);
        ArrayList<User> tabUser = new ArrayList<User> ();
        if (c.getCount() == 0) {
            c.close();
            return tabUser;
        }
        while (c.moveToNext()) {
            User user1 = new User();
            user1.setId(c.getInt(NUM_COL_ID));
            user1.setLastname(c.getString(NUM_COL_LASTNAME));
            user1.setFirstname(c.getString(NUM_COL_FIRSTNAME));
            user1.setPassword(c.getString(NUM_COL_PASSWORD));
            user1.setEmail(c.getString(NUM_COL_EMAIL));
            user1.setRights(c.getInt(NUM_COL_RIGHTS));
            tabUser.add(user1);
        }
        c.close();
        return tabUser;
    }

}