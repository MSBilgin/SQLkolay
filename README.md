# SQLkolay

Android library that simplifies working with SQLite database.

Usage
=====
Database Class

``` java
public class AppDB extends SQLkolay {
    private static final String databaseName = "app.db";
    private static final int version = 1;

    public TableBooks books = new TableBooks();

    public AppDB(Context context) {
        super(context, databaseName, version);
        registerTables(books);
    }
}
```
Table Class

``` java
public class TableBooks extends Table {
    private static final String tableName = "BOOKS";

    @Column(type = Column.Type.INTEGER_AUTOINC, primary = true)
    private Column id;

    private Column name;
    private Column author;

    @Column(type = Column.Type.NUMBER)
    private Column date;

    @Column(type = Column.Type.REAL)
    private Column price;

    public TableBooks() {
        super(tableName);
    }

    public void add(Book book) {
        ContentValues values = new ContentValues();
        values.put("name", book.name);
        values.put("author", book.author);
        values.put("date", book.publishDate);
        values.put("price", book.price);
        insert(null, values);
    }

    public Book getByID(int id) {
        Book book = new Book();

        String sql = String.format("select * from %s where id=%s", tableName, id);
        Cursor cursor = getDB().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            book.id = cursor.getInt(cursor.getColumnIndex("id"));
            book.name = cursor.getString(cursor.getColumnIndex("name"));
            book.author = cursor.getString(cursor.getColumnIndex("author"));
            book.price = cursor.getDouble(cursor.getColumnIndex("price"));
            book.publishDate = cursor.getString(cursor.getColumnIndex("date"));
        }
        return book;
    }

}
```
Thats all...

``` java
    AppDB appDB = new AppDB(getApplicationContext());
    Book book = new Book("Seyahatname", "Evliya Çelebi", "01.01.1600", 2000);
    appDB.books.add(book);
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
    implementation 'com.github.msbilgin:sqlkolay:0.4'
}
```

