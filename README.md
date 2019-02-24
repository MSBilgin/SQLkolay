# SQLkolay

Android library that simplifies working with SQLite database.

Usage
=====
Database Class

``` java
public class AppDB extends SQLkolay {
    private static final String databaseName = "database";
    private static final int version = 14;

    public static TableUser user = new TableUser();

    public AppDB(Context context) {
        super(context, databaseName, version);
    }
}
```
Table Class

``` java
public class TableUser extends Table {
    private static final String tableName = "USERS";

    @Column(type = Column.Type.INTEGER)
    private Column uid;

    private Column name;
    private Column surname;

    @Column(type = Column.Type.INTEGER)
    private Column age;

    @Column(type = Column.Type.REAL)
    private Column income;

    public TableUser() {
        super(tableName);
    }

    public void add(User user) {
        ContentValues values = new ContentValues();
        values.put("uid", user.uid);
        values.put("name", user.name);
        values.put("surname", user.surname);
        values.put("age", user.age);
        values.put("income", user.income);
        insert(null, values);
    }

    public void remove(long uid) {
        delete("uid=?", new String[]{String.valueOf(uid)});
    }   
}
```
Thats all...

``` java
    AppDB appDB = new AppDB(getApplicationContext());
    User user1 = new User(111,"Mehmet Selim", "Bilgin", 29,100000);
    appDB.user.add(user1);
```

Installation
--------
on project's build.gradle
```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
on app's build.gradle
```groovy
dependencies {
    implementation 'com.github.msbilgin:sqlkolay:0.3'
}
```

