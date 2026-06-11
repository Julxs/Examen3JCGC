package tu.paquete; // ajusta a tu paquete real

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JugadorDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_JUGADOR = "jugador";

    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ULTIMA_CONEXION = "ultima_conexion";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_JUGADOR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_ULTIMA_CONEXION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUGADOR);
        onCreate(db);
    }

    // Insertar nuevo jugador
    public boolean insertarJugador(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_JUGADOR, null, values);
        db.close();
        return result != -1;
    }

    // Verificar si el usuario existe
    public boolean existeUsuario(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_JUGADOR + " WHERE " + COL_USERNAME + "=?",
                new String[]{username});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }

    // Validar credenciales
    public boolean validarCredenciales(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_JUGADOR + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password});
        boolean valido = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return valido;
    }

    // Actualizar última conexión
    public void actualizarUltimaConexion(String username, String fechaHora) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ULTIMA_CONEXION, fechaHora);
        db.update(TABLE_JUGADOR, values, COL_USERNAME + "=?", new String[]{username});
        db.close();
    }

    // Obtener última conexión
    public String obtenerUltimaConexion(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_ULTIMA_CONEXION + " FROM " + TABLE_JUGADOR + " WHERE " + COL_USERNAME + "=?",
                new String[]{username});
        String ultimaConexion = "Sin registro";
        if (cursor.moveToFirst()) {
            String valor = cursor.getString(0);
            if (valor != null) {
                ultimaConexion = valor;
            }
        }
        cursor.close();
        db.close();
        return ultimaConexion;
    }
}