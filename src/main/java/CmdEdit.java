import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CmdEdit {
    private Bot bot;
    private SendMessage sender;

    CmdEdit(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public static Map<String, String> states = new HashMap<String, String>();

    public void СmdEdit(Long ids, String mes, String UserStatus) {
        sender.setChatId(ids);

//String Cat,String TranTo,String TranWhy,Float SUM
        try {
            String[] outstringBD = new String[100];
            String outstring = null;
            sender.setChatId(ids);
            switch (UserStatus) {

                case ("Main"): {
                    sender.setText("Давай все исправлять \n Если ты хочешь что-то исправить, тебе надо знать id категории\n Чтобы ипсравить категорию, введи /cat номер категории \n Чтобы исправить транзакцию, введи /tran номер транзакции");
                    BdSql.ChangeStatus(ids.toString(), "Edit 1");
                    break;
                }
                case ("Edit 1"): {
                    if (mes.split(" ")[0].equals("/cat")) {
                        states.put(ids.toString() + "EditIdCat", mes.split(" ")[1]);
                        sender.setText("Введи новое имя этой категории");
                        BdSql.ChangeStatus(ids.toString(), "Edit Cat 1");
                    } else if (mes.split(" ")[0].equals("/tran")) {
                        states.put(ids.toString() + "EditIdTran", mes.split(" ")[1]);
                        sender.setText("Cколько ты потратил в этой категории?");
                        BdSql.ChangeStatus(ids.toString(), "Edit Tran 1");
                    }

                    break;
                }
                case ("Edit Cat 1"): {
                    states.put(ids.toString() + "EditNameCat", mes);
                    sender.setText("Введи новый лимит для этой категории");
                    BdSql.ChangeStatus(ids.toString(), "Edit Cat 2");
                    break;
                }
                case ("Edit Cat 2"): {
                    states.put(ids.toString() + "EditIdLim", mes);
                    sender.setText("С этим мы закончили");
                    BdSql.ChangeStatus(ids.toString(), "Main");
                    BdSql.ChangeCategory(ids.toString(), states.get(ids.toString() + "EditNameCat"), states.get(ids.toString() + "EditIdLim"), states.get(ids.toString() + "EditIdCat"));
                    break;
                }

                case ("Edit Tran 1"): {
                    //Записать сумму траты
                    if (isOnlyDigits(mes)) {
                        states.put(ids.toString() + "EditMoney", mes);
                        BdSql.ChangeStatus(ids.toString(), "Edit Tran 2");
                        sender.setText("Где потратили?");
                    } else {
                        BdSql.ChangeStatus(ids.toString(), "Edit Tran 1");
                        sender.setText("Мне нужны цифры! А не вот это все! '" + mes + "' Что это вообще такое ? \n Попробуй еще!");
                    }


                    break;
                }
                case ("Edit Tran 2"): {

                    states.put(ids.toString() + "EditTranTo", mes);
                    BdSql.ChangeStatus(ids.toString(), "Edit Tran 3");
                    sender.setText("Какое назначение платежа?");


                    break;
                }
                case ("Edit Tran 3"): {
                    states.put(ids.toString() + "EditTranWhy", mes);
                    BdSql.ChangeStatus(ids.toString(), "Edit Tran 4");
                    int z = 1;
                    try { //вывести категории
                        outstringBD = BdSql.GetCatTable(ids.toString());

                        outstring = outstringBD[0];
                        for (z = 1; z < outstringBD.length; z++) {
                            outstring = outstring + " \n \n " + outstringBD[z];
                        }
                        states.put(ids.toString() + "EditNumber", "" + z + "");
                        sender.setText(outstring);
                        bot.execute(sender);
                        sender.setText("Укажите номер категории, в которой была совершена покупка");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    break;
                }
                case ("Edit Tran 4"): {
                    if (isOnlyDigits(mes)) {
                        if (Integer.parseInt(mes) + 1 <= Integer.parseInt(states.get(ids.toString() + "EditNumber"))) {

                            mes = BdSql.GetCatName(ids.toString(), Integer.parseInt(mes));
                            System.out.println("mes " + " " + mes);
                            states.put(ids.toString() + "EditCat", mes);
                            sender.setText("Я все записал");
                            BdSql.ChangeStatus(ids.toString(), "Main");
                            BdSql.NewTran(ids.toString(), states.get(ids.toString() + "EditCat"), states.get(ids.toString() + "EditTranTo"), states.get(ids.toString() + "EditTranWhy"), Float.parseFloat(states.get(ids.toString() + "EditMoney")));
                        } else {
                            sender.setText("Такой нет, попробуй еще  ");
                            BdSql.ChangeStatus(ids.toString(), "Edit Tran 4");
                        }
                    }
                }
                break;
            }
            bot.execute(sender);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnlyDigits(String str) {
        return str.matches("-?[\\d]+");
    }
}




