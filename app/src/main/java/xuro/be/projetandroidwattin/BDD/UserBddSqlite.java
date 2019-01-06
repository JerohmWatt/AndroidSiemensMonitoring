package xuro.be.projetandroidwattin.BDD;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserBddSqlite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WattinDroid.db";
    private static final int DATABASE_VERSION = 1;

    public UserBddSqlite(Context context, String name,
                         SQLiteDatabase.CursorFactory factory,
                         int version) {
        super (context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //le paramètre contient la db
        String strSql = "create table Table_users ("
                        +   "ID integer primary key autoincrement,"
                        +   "   lastname text not null,"
                        +   "   firstname text not null,"
                        +   "   password text not null,"
                        +   "   email text not null,"
                        +   "   rights integer not null"
                        +   ")";
        db.execSQL(strSql);
        ContentValues admin = new ContentValues();
        admin.put("lastname", "john");
        admin.put("firstname","doe");
        admin.put("password","android3");
        admin.put("email","android");
        admin.put("rights",3);
        db.insert("Table_users", null, admin);
        Log.i("database","appel onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //en cas de différence de version de la db, par màj de l'appli par exemple
        String strSql = "drop table Table_users";
        db.execSQL(strSql);
        this.onCreate(db);
        Log.i("database","Onupgrade appel");
    }


}
