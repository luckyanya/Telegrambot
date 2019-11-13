import java.sql.*;
import java.util.Date;

public class BdSql {

    private final static String URL = "jdbc:mysql://localhost:3306/teleg";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";

    public Connection Connect() {
        Connection connection = null;
        Driver driver = null;
        try {
            driver = new com.mysql.cj.jdbc.Driver();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void DeleteTableA(String ids) {
        Statement statement = null;
        try {
            statement = Connect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String SQL = "DROP TABLE id_" + ids;
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL = "DROP TABLE id_" + ids + "t ";
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CreateTable(String ids) {

        Statement statement = null;
        try {
            statement = Connect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String SQL = "CREATE TABLE IF NOT EXISTS id_" + ids + " " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " Catname VARCHAR(50), " +
                " CatLimit FLOAT (50), " +
                " PRIMARY KEY (id))";
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL = "CREATE TABLE IF NOT EXISTS id_" + ids + "t " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " SUMM FLOAT (50), " +
                " TranData DATE , " +
                " Cat VARCHAR (50), " +
                " TranTo VARCHAR (50), " +
                " TranWhy VARCHAR (50), " +
                " PRIMARY KEY (id))";
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Initable() {
        Statement statement = null;
        try {
            statement = Connect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String SQL = "CREATE TABLE IF NOT EXISTS Userstable " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " iduser INTEGER not NULL, " +
                " alllimit FLOAT (50), " +
                " PRIMARY KEY (id))";
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void newtran(String ids, String Cat, String TranTo, String TranWhy, Float SUM) {
        Date dateNow = new Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd");
        String Data = sdf.format(dateNow);
        System.out.println("Дата" + Data);
        Statement statement = null;
        try {
            statement = Connect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String SQL = "INSERT INTO  id_" + ids + "t   ( `SUMM`,`TranData`, `Cat`, `TranTo`, `TranWhy`) VALUES ('" + SUM + "','" + Data + "', '" + Cat + "', '" + TranTo + "', '" + TranWhy + "')";
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] GettableT(String ids) throws SQLException {
        int[] sqlid = null;
        float[] sqlSum = null;
        String[] sqlData = null;
        String[] sqlCat = null;
        String[] sqlTranTo = null;
        String[] sqlTranWhy = null;
        String[] outs = null;
        Statement statement = null;
        try {
            statement = Connect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String SQL = "SELECT id,SUMM,TranData,Cat,TranTo,TranWhy FROM  id_" + ids + "t";
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec;
        int rowCount = 1;
        try {

            rowCount = 1;
            rec = statement.getResultSet();
            while (rec.next()) {
                rowCount++;
            }
            rec.beforeFirst(); //ставим на первый указатель
            int count = rowCount - 1;
            rowCount = 1;
            sqlid = new int[count];
            sqlSum = new float[count];
            sqlData = new String[count];
            sqlCat = new String[count];
            sqlTranTo = new String[count];
            sqlTranWhy = new String[count];
            outs = new String[count + 1];
            outs[0] = "Я нашел " + count + " транзакций";
            while (rec.next()) {
                sqlid[rowCount - 1] = rec.getInt(1);
                sqlSum[rowCount - 1] = rec.getFloat(2);
                sqlData[rowCount - 1] = rec.getString(3);
                sqlCat[rowCount - 1] = rec.getString(4);
                sqlTranTo[rowCount - 1] = rec.getString(5);
                sqlTranWhy[rowCount - 1] = rec.getString(6);
                outs[rowCount] = "Id операции " + sqlid[rowCount - 1] + " Потраченная сумма " + sqlSum[rowCount - 1] + " Дата операции " + sqlData[rowCount - 1] + " Категрия операции " + sqlCat[rowCount - 1] + " Операция была произведена в " + sqlTranTo[rowCount - 1] + " Описание операции " + sqlTranWhy[rowCount - 1];
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outs;
    }
}
