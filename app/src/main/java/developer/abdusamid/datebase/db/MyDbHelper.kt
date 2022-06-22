package developer.abdusamid.datebase.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import developer.abdusamid.datebase.models.Contacts
import developer.abdusamid.datebase.utils.Constant
import developer.abdusamid.datebase.utils.DBServiceInterface

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION),
    DBServiceInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE ${Constant.TABLE_NAME} (${Constant.ID} integer NOT NULL PRIMARY KEY  AUTOINCREMENT UNIQUE, ${Constant.NAME} text NOT NULL, ${Constant.PHONE_NUMBER} text NOT NULL)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Constant.TABLE_NAME}")
        onCreate(db)
    }

    override fun addContact(contacts: Contacts) {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(Constant.NAME, contacts.name)
        contentValue.put(Constant.PHONE_NUMBER, contacts.phoneNumber)
        database.insert(Constant.TABLE_NAME, null, contentValue)
        database.close()
    }

    override fun deleteContact(contacts: Contacts) {
        val database = this.writableDatabase
        database.delete(Constant.TABLE_NAME, "${Constant.ID} = ?", arrayOf("${contacts.id}"))
        database.close()
    }

    override fun upDataContact(contacts: Contacts): Int {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(Constant.ID, contacts.id)
        contentValue.put(Constant.NAME, contacts.name)
        contentValue.put(Constant.PHONE_NUMBER, contacts.phoneNumber)

        return database.update(
            Constant.TABLE_NAME,
            contentValue,
            "${Constant.ID} = ?",
            arrayOf(contacts.id.toString())
        )
    }

    override fun getAllContact(): List<Contacts> {
        val list = ArrayList<Contacts>()
        val query = "SELECT * FROM ${Constant.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phoneNumber = cursor.getString(1)
            } while (cursor.moveToNext())
        }
        return list
    }

}