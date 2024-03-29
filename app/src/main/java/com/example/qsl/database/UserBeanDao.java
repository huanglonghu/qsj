package com.example.qsl.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.qsl.database.entity.UserBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_BEAN".
*/
public class UserBeanDao extends AbstractDao<UserBean, Long> {

    public static final String TABLENAME = "USER_BEAN";

    /**
     * Properties of entity UserBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Address = new Property(0, String.class, "address", false, "ADDRESS");
        public final static Property Avatar = new Property(1, String.class, "avatar", false, "AVATAR");
        public final static Property CommanyName = new Property(2, String.class, "commanyName", false, "COMMANY_NAME");
        public final static Property Email = new Property(3, String.class, "email", false, "EMAIL");
        public final static Property Id = new Property(4, Long.class, "id", true, "_id");
        public final static Property PhoneNumber = new Property(5, String.class, "phoneNumber", false, "PHONE_NUMBER");
        public final static Property Sex = new Property(6, String.class, "sex", false, "SEX");
        public final static Property Token = new Property(7, String.class, "token", false, "TOKEN");
        public final static Property UserName = new Property(8, String.class, "userName", false, "USER_NAME");
        public final static Property UserType = new Property(9, int.class, "userType", false, "USER_TYPE");
    }


    public UserBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_BEAN\" (" + //
                "\"ADDRESS\" TEXT," + // 0: address
                "\"AVATAR\" TEXT," + // 1: avatar
                "\"COMMANY_NAME\" TEXT," + // 2: commanyName
                "\"EMAIL\" TEXT," + // 3: email
                "\"_id\" INTEGER PRIMARY KEY ," + // 4: id
                "\"PHONE_NUMBER\" TEXT," + // 5: phoneNumber
                "\"SEX\" TEXT," + // 6: sex
                "\"TOKEN\" TEXT," + // 7: token
                "\"USER_NAME\" TEXT," + // 8: userName
                "\"USER_TYPE\" INTEGER NOT NULL );"); // 9: userType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(1, address);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(2, avatar);
        }
 
        String commanyName = entity.getCommanyName();
        if (commanyName != null) {
            stmt.bindString(3, commanyName);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(4, email);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(5, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(6, phoneNumber);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(8, token);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(9, userName);
        }
        stmt.bindLong(10, entity.getUserType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(1, address);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(2, avatar);
        }
 
        String commanyName = entity.getCommanyName();
        if (commanyName != null) {
            stmt.bindString(3, commanyName);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(4, email);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(5, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(6, phoneNumber);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(8, token);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(9, userName);
        }
        stmt.bindLong(10, entity.getUserType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4);
    }    

    @Override
    public UserBean readEntity(Cursor cursor, int offset) {
        UserBean entity = new UserBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // address
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // avatar
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // commanyName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // email
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // phoneNumber
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sex
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // token
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // userName
            cursor.getInt(offset + 9) // userType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserBean entity, int offset) {
        entity.setAddress(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAvatar(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCommanyName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEmail(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setPhoneNumber(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSex(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setToken(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUserName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUserType(cursor.getInt(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
