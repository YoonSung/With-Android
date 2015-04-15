package gaongil.safereturnhome.db;

import java.sql.SQLException;

import gaongil.safereturnhome.model.MessageData;
import gaongil.safereturnhome.model.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "Note.db";
    private static final int DB_VERSION = 1;
    private RuntimeExceptionDao<User, Integer> userDao = null;
    private RuntimeExceptionDao<MessageData, Integer> messageDataDao = null;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
                 
    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, MessageData.class);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
                 
    public RuntimeExceptionDao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null)
            userDao = getRuntimeExceptionDao(User.class);
        return userDao;
    }
 
                 
    public RuntimeExceptionDao<MessageData, Integer> getMessageDataDao() throws SQLException {
        if (messageDataDao == null)
            messageDataDao = getRuntimeExceptionDao(MessageData.class);
        return messageDataDao;
    }
 
                 
    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer,  int newVer) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, MessageData.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}