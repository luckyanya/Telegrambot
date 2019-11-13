public class UserLogW {
    public void write(Long text) {
        BdSql mysql = new BdSql();
        mysql.CreateTable(text.toString());// проверим есть ли у нас такой пользователь. если нет, то создадим для него таблицы
    }
}
