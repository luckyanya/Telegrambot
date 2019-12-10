package TelegramBot;

import java.sql.*;
import java.util.Date;

public class BdSql
{
    private final static String URL = "jdbc:mysql://localhost:3306/teleg";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";
    private static String SQL=null;

    public  static  Connection Connect()
    {
        Connection connection = null;
        Driver driver = null;
        try
        {
            driver = new com.mysql.cj.jdbc.Driver();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try {
            if (!connection.isClosed())
            {
                System.out.println("Соединение с БД установлено!");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    public  static  void DeleteTableA(String ids) {
        Statement statement= null;
        try {
            statement = Connect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL = "DROP TABLE id_"+ids;
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "DROP TABLE id_"+ids+"t ";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public  static  void CreateTable(String ids)
    {
        Statement   statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        String SQL = "CREATE TABLE IF NOT EXISTS id_"+ids+" " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " Catname VARCHAR(50), " +
                " CatLimit FLOAT (50), " +
                " PRIMARY KEY (id))";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "CREATE TABLE IF NOT EXISTS id_"+ids+"t " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " SUMM FLOAT (50), " +
                " TranData DATE , " +
                " Cat VARCHAR (50), " +
                " TranTo VARCHAR (50), " +
                " TranWhy VARCHAR (50), " +
                " PRIMARY KEY (id))";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "INSERT IGNORE  INTO  Userstable SET iduser="+ids+"";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL =  SQL = "UPDATE userstable SET UserState = 'Main' WHERE iduser = '"+ids+"'";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public  static void InitTable()
    {
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "CREATE TABLE IF NOT EXISTS Userstable" +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " iduser INTEGER not NULL, " +
                " alllimit FLOAT (50), " +
                "UserState VARCHAR(50), "+
                " PRIMARY KEY (id,iduser))";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public  static  void NewTran(String ids,String Cat,String TranTo,String TranWhy,Float SUM)
    {
        Date dateNow = new Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String Data=sdf.format(dateNow);
        System.out.println("Дата" + Data);
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "INSERT INTO  id_"+ids+"t   ( `SUMM`,`TranData`, `Cat`, `TranTo`, `TranWhy`) VALUES ('"+SUM+"','"+Data+"', '"+Cat+"', '"+TranTo+"', '"+TranWhy+"')";
//INSERT INTO `id_449061526t` (`id`, `TranData`, `Cat`, `TranTo`, `TranWhy`) VALUES ('1', '2019-11-11', '213', '123', '123');
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public  static  void NewCategory(String ids,String CatName,Float CatLimit)
    {
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "INSERT INTO  id_"+ids+"   ( `Catname`,`CatLimit`) VALUES ('"+CatName+"', '"+CatLimit+"')";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public  static  void ChangeStatus(String ids,String NewUserState)
    {
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "UPDATE userstable SET UserState = '"+NewUserState+"' WHERE iduser = '"+ids+"'";
        // SQL = "UPDATE id_\"+ids+\" SET Catname = '"+Catname+"', CatLimit = '"+CatLimit+"' WHERE id = '"+IdCat+"'";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public  static  void ChangeTran(String ids,String Cat,String TranTo,String TranWhy,Float SUM,String TranId) {
        Statement statement = null;
        try {
            statement = Connect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Выполнили");

        SQL = "UPDATE id_"+ids+"t SET Cat = '"+Cat+ "', TranTo = '" +TranTo+ "',TranWhy = '"+TranWhy+"',SUMM = '"+ SUM+"' WHERE id ='"+TranId+"'";
        System.out.println(SQL);
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  static void ChangeCategory(String ids,String CatName,String CatLimit,String IdCat)
    {
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "UPDATE id_"+ids+" SET Catname = '"+CatName+"', CatLimit = '"+CatLimit+"' WHERE id = '"+IdCat+"'";
        try
        {
            statement.executeUpdate(SQL);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public  static String  GetStatus(String ids)
    { String out=null;
        Statement   statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "SELECT *  FROM  userstable WHERE iduser='"+ids+"'";
        // System.out.println("Вывод с базы " + outs[rowCount-1]);
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec ;
        try {
            rec = statement.getResultSet();
            while(rec.next()) {
                out= rec.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  out;
    }
    public  static  String[] GetTableT(String ids) throws SQLException {
        int[] sqlid=null;
        float[] sqlSum=null;
        String[] sqlData=null;
        String[] sqlCat=null ;
        String[] sqlTranTo=null;
        String[] sqlTranWhy=null;
        String[] outs=null;
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        SQL = "SELECT id,SUMM,TranData,Cat,TranTo,TranWhy FROM  id_"+ids+"t";
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec;
        int rowCount;
        try {
            rowCount = 1;
            rec = statement.getResultSet();
            while(rec.next())
            {
                rowCount++;
            }
            rec.beforeFirst();
            int countStr=rowCount-1;
            //  rec.;
            rowCount=1;
            sqlid=new int[countStr];
            sqlSum=new float[countStr];
            sqlData=new String[countStr];
            sqlCat=new String[countStr] ;
            sqlTranTo=new String[countStr];
            sqlTranWhy=new String[countStr];
            outs=new String[countStr+1];
            outs[0]="Я нашел "+countStr+" транзакций";
            while(rec.next()) {
                sqlid[rowCount-1]=rec.getInt(1);
                sqlSum[rowCount-1]=rec.getFloat(2);
                sqlData[rowCount-1]=rec.getString(3);
                sqlCat[rowCount-1]=rec.getString(4);
                sqlTranTo[rowCount-1]=rec.getString(5);
                sqlTranWhy[rowCount-1]=rec.getString(6);
                outs[rowCount]="Id операции " +sqlid[rowCount-1]+"\nПотраченная сумма "+sqlSum[rowCount-1]+"\nДата операции "+sqlData[rowCount-1]+"\nКатегoрия операции "+sqlCat[rowCount-1]+"\nОперация была произведена в "+sqlTranTo[rowCount-1]+"\nОписание операции "+sqlTranWhy[rowCount-1];
                //  System.out.println("Вывод с базы " + outs[rowCount-1]);
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outs ;
    }
    public  static  String[] GetTableTCatStats(String ids,String CatNumber) throws SQLException {
        int[] sqlid=null;
        float[] sqlSum=null;
        String[] sqlData=null;
        String[] sqlCat=null ;
        String[] sqlTranTo=null;
        String[] sqlTranWhy=null;
        String[] outs=null;
        Statement   statement= null;
        String SearchCatName=null;
        float Limitcat=1;
        float Summ=1;
        System.out.println("Вывод с базы номер категории " +CatNumber);
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "SELECT id,Catname,CatLimit FROM id_"+ids+" WHERE id="+CatNumber;
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int rowCount;
        final ResultSet rec;
        try {
            rowCount = 1;
            rec = statement.getResultSet();
            while(rec.next())
            {
                rowCount++;
            }
            rec.beforeFirst();
            int countStr=rowCount-1;
            //  rec.;
            rowCount=1;
            outs=new String[countStr+1];
            outs[0]="Я нашел "+countStr+" Категорий ";
            while(rec.next()) {
                SearchCatName=rec.getString(2);
                Limitcat=rec.getFloat(3);
                System.out.println("Вывод с базы " + rec.getFloat(3));
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Поиск" + SearchCatName);
        SQL = "SELECT id,SUMM,TranData,Cat,TranTo,TranWhy FROM  id_"+ids+"t WHERE Cat='"+SearchCatName+"'";
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec1;
        try {
            rowCount = 1;
            rec1 = statement.getResultSet();
            while(rec1.next())
            {
                rowCount++;
            }
            rec1.beforeFirst();
            int countStr=rowCount-1;
            //  rec.;
            sqlid=new int[countStr];
            sqlSum=new float[countStr];
            sqlData=new String[countStr];
            sqlCat=new String[countStr] ;
            sqlTranTo=new String[countStr];
            sqlTranWhy=new String[countStr];
            outs=new String[countStr+2];
            outs[0]="Я нашел "+countStr+" транзакций";
            outs[1]="Я нашел "+countStr+" транзакций";
            rowCount=1;
            Summ=0;
            while(rec1.next()) {
                sqlid[rowCount-1]=rec1.getInt(1);
                sqlSum[rowCount-1]=rec1.getFloat(2);
                Summ=Summ+sqlSum[rowCount-1];
                sqlData[rowCount-1]=rec1.getString(3);
                sqlCat[rowCount-1]=rec1.getString(4);
                sqlTranTo[rowCount-1]=rec1.getString(5);
                sqlTranWhy[rowCount-1]=rec1.getString(6);
                outs[rowCount+1]="Id операции " +sqlid[rowCount-1]+"\nПотраченная сумма "+sqlSum[rowCount-1]+"\nДата операции "+sqlData[rowCount-1]+"\nКатегoрия операции "+sqlCat[rowCount-1]+"\nОперация была произведена в "+sqlTranTo[rowCount-1]+"\nОписание операции "+sqlTranWhy[rowCount-1];
                //  System.out.println("Вывод с базы " + outs[rowCount-1]);
                rowCount++;
            }
            if(Limitcat>=Summ)
            {
                outs[1] = "Установлен лимит категории " + Limitcat + "\nВсего потрачено " + Summ;
            }
            else {
                float raz=Summ-Limitcat;
                outs[1] = "Установлен лимит категории " + Limitcat + "\nВсего потрачено " + Summ+"\nВы прeвысили бюджет на "+raz+"\nСтоит перерспределить свои расходы или увеличить бюджет на категорию";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outs ;
    }
    public  static  String[] GetTableTDate(String ids,String Date) throws SQLException {
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
// SQL = "SELECT *  FROM  userstable WHERE iduser='"+ids+"'";
        SQL = "SELECT id,SUMM,TranData,Cat,TranTo,TranWhy FROM id_" + ids + "t WHERE TranData= '" + Date + "'";
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
            rec.beforeFirst();
            int countStr = rowCount - 1;
            //  rec.;
            rowCount = 1;
            sqlid = new int[countStr];
            sqlSum = new float[countStr];
            sqlData = new String[countStr];
            sqlCat = new String[countStr];
            sqlTranTo = new String[countStr];
            sqlTranWhy = new String[countStr];
            outs = new String[countStr + 1];
            outs[0] = "Я нашел " + countStr + " транзакций";
            while (rec.next()) {
                sqlid[rowCount - 1] = rec.getInt(1);
                sqlSum[rowCount - 1] = rec.getFloat(2);
                sqlData[rowCount - 1] = rec.getString(3);
                sqlCat[rowCount - 1] = rec.getString(4);
                sqlTranTo[rowCount - 1] = rec.getString(5);
                sqlTranWhy[rowCount - 1] = rec.getString(6);
                outs[rowCount] = "Id операции " + sqlid[rowCount - 1] + "\nПотраченная сумма " + sqlSum[rowCount - 1] + "\nДата операции " + sqlData[rowCount - 1] + "\nКатегoрия операции " + sqlCat[rowCount - 1] + "\nОперация была произведена в " + sqlTranTo[rowCount - 1] + "\nОписание операции " + sqlTranWhy[rowCount - 1];
                //  System.out.println("Вывод с базы " + outs[rowCount-1]);
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outs;
    }
    public  static  String[] GetTableTCatName(String ids) throws SQLException {
        int[] sqlid=null;
        float[] sqlLim=null;
        String[] sqlName=null;
        String[] outs=null;
        Statement   statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
// SQL = "SELECT *  FROM  userstable WHERE iduser='"+ids+"'";
        SQL = "SELECT id,Catname,CatLimit FROM id_"+ids;
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec;
        int rowCount;
        try {
            rowCount = 1;
            rec = statement.getResultSet();
            while(rec.next())
            {
                rowCount++;
            }
            rec.beforeFirst();
            int countStr=rowCount-1;
            rowCount=1;
            sqlid=new int[countStr];
            sqlLim=new float[countStr];
            sqlName=new String[countStr];
            outs=new String[countStr+1];
            outs[0]="Я нашел "+countStr+" Категорий ";
            while(rec.next()) {
                sqlid[rowCount-1]=rec.getInt(1);
                sqlName[rowCount-1]=rec.getString(2);
                sqlLim[rowCount-1]=rec.getFloat(3);
                outs[rowCount]="Id Категории " +sqlid[rowCount-1]+"\nИмя категории "+sqlName[rowCount-1]+"\nЛимит категории "+sqlLim[rowCount-1];
                //  System.out.println("Вывод с базы " + outs[rowCount-1]);
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outs ;
    }
    public  static  String[] GetTableTDateTo(String ids,String Date,String DateTo) throws SQLException {
        int[] sqlid=null;
        float[] sqlSum=null;
        String[] sqlData=null;
        String[] sqlCat=null ;
        String[] sqlTranTo=null;
        String[] sqlTranWhy=null;
        String[] outs=null;
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
// SQL = "SELECT *  FROM  userstable WHERE iduser='"+ids+"'";
        SQL = "SELECT id,SUMM,TranData,Cat,TranTo,TranWhy FROM id_"+ids+"t WHERE TranData  BETWEEN '"+Date+"' AND  '"+DateTo+"'" ;
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec;
        int rowCount;
        try {
            rowCount = 1;
            rec = statement.getResultSet();
            while(rec.next())
            {
                rowCount++;
            }
            rec.beforeFirst();
            int countStr=rowCount-1;
            rowCount=1;
            sqlid=new int[countStr];
            sqlSum=new float[countStr];
            sqlData=new String[countStr];
            sqlCat=new String[countStr] ;
            sqlTranTo=new String[countStr];
            sqlTranWhy=new String[countStr];
            outs=new String[countStr+1];
            outs[0]="Я нашел "+countStr+" транзакций";
            while(rec.next()) {
                sqlid[rowCount-1]=rec.getInt(1);
                sqlSum[rowCount-1]=rec.getFloat(2);
                sqlData[rowCount-1]=rec.getString(3);
                sqlCat[rowCount-1]=rec.getString(4);
                sqlTranTo[rowCount-1]=rec.getString(5);
                sqlTranWhy[rowCount-1]=rec.getString(6);
                outs[rowCount]="Id операции " +sqlid[rowCount-1]+"\nПотраченная сумма "+sqlSum[rowCount-1]+"\nДата операции "+sqlData[rowCount-1]+"\nКатегoрия операции "+sqlCat[rowCount-1]+"\nОперация была произведена в "+sqlTranTo[rowCount-1]+"\nОписание операции "+sqlTranWhy[rowCount-1];
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outs ;
    }
    public  static  String[] GetCatTable(String ids) throws SQLException {
        int[] sqlid=null;
        float[] sqlLimit=null;
        String[] sqlName=null;
        String[] outs=null;
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "SELECT id,Catname,CatLimit FROM  id_"+ids;
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec;
        int rowCount;
        try {
            rowCount = 1;
            rec = statement.getResultSet();
            while(rec.next()) {
                rowCount++;
            }
            rec.beforeFirst();
            int countStr=rowCount-1;
            rowCount=1;
            sqlid=new int[countStr];
            sqlLimit=new float[countStr];
            sqlName=new String[countStr];
            outs=new String[countStr+1];
            outs[0]="Я нашел "+countStr+" Категорий";
            while(rec.next()) {
                sqlid[rowCount-1]=rec.getInt(1);
                sqlLimit[rowCount-1]=rec.getFloat(3);
                sqlName[rowCount-1]=rec.getString(2);
                outs[rowCount]="Id Категории " +sqlid[rowCount-1]+"\nИмя категории "+sqlName[rowCount-1]+"\nЛимит на категорию "+sqlLimit[rowCount-1];
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outs ;
    }
    public  static  String GetCatName(String ids,int idcat) throws SQLException {
        String out=null;
        Statement statement= null;
        try
        {
            statement = Connect().createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        SQL = "SELECT *  FROM  id_"+ids+" WHERE id='"+idcat+"'";
        try {
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ResultSet rec ;
        try {
            rec = statement.getResultSet();
            while(rec.next()) {
                out= rec.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out ;
    }
}
