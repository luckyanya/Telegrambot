package TelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

final class CmdEdit {
    private Bot bot;
    private SendMessage sender;

    CmdEdit(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public static Map<String, String> states = new HashMap<String, String>();

    public void сmdEdit(Long ids, String mes, String UserStatus) {
        sender.setChatId(ids);
        try {
            String outStr = null;
            sender.setChatId(ids);
            switch (UserStatus) {
                case ("Main"):
                    sender.setText("Давай все исправлять \n\nЕсли ты хочешь что-то исправить, тебе надо знать id категории\n\nЧтобы иcправить категорию, введи /cat номер категории \n\nЧтобы исправить транзакцию, введи /tran номер транзакции");
                    BdSql.changeStatus(ids.toString(), "Edit 1");
                    break;
                case ("Edit 1"):
                    if (mes.split(" ")[0].equals("/cat")) {
                        states.put(ids.toString() + "EditIdCat", mes.split(" ")[1]);
                        sender.setText("Введи новое имя этой категории");
                        BdSql.changeStatus(ids.toString(), "Edit Cat 1");
                    } else if (mes.split(" ")[0].equals("/tran")) {
                        states.put(ids.toString() + "EditIdTran", mes.split(" ")[1]);
                        sender.setText("Cколько ты потратил в этой транзакции?");
                        BdSql.changeStatus(ids.toString(), "Edit Tran 1");
                    }
                    break;
                case ("Edit Cat 1"):
                    states.put(ids.toString() + "EditNameCat", mes);
                    sender.setText("Введи новый лимит для этой категории");
                    BdSql.changeStatus(ids.toString(), "Edit Cat 2");
                    break;
                case ("Edit Cat 2"):
                    states.put(ids.toString() + "EditIdLim", mes);
                    sender.setText("С этим мы закончили");
                    BdSql.changeStatus(ids.toString(), "Main");
                    BdSql.changeCategory(ids.toString(), states.get(ids.toString() + "EditNameCat"), states.get(ids.toString() + "EditIdLim"), states.get(ids.toString() + "EditIdCat"));
                    break;
                case ("Edit Tran 1"):
                    //Записать сумму траты
                    if (isOnlyDigits(mes)) {
                        states.put(ids.toString() + "EditMoney", mes);
                        BdSql.changeStatus(ids.toString(), "Edit Tran 2");
                        sender.setText("Где потратили?");
                    } else {
                        BdSql.changeStatus(ids.toString(), "Edit Tran 1");
                        sender.setText("Мне нужны цифры! А не вот это все! '" + mes + "' Что это вообще такое? \nПопробуй еще!");
                    }
                    break;
                case ("Edit Tran 2"):
                    states.put(ids.toString() + "EditTranTo", mes);
                    BdSql.changeStatus(ids.toString(), "Edit Tran 3");
                    sender.setText("Какое назначение платежа?");
                    break;
                case ("Edit Tran 3"):
                    states.put(ids.toString() + "EditTranWhy", mes);
                    BdSql.changeStatus(ids.toString(), "Edit Tran 4");
                    int z;
                    try { //вывести категории
                        String[] outStrBD = BdSql.getCatTable(ids.toString());
                        outStr = outStrBD[0];
                        for (z = 1; z < outStrBD.length; z++) {
                            outStr = outStr + " \n \n " + outStrBD[z];
                        }
                        states.put(ids.toString() + "EditNumber", "" + z + "");
                        sender.setText(outStr);
                        bot.execute(sender);
                        sender.setText("Укажите номер категории, в которой была совершена покупка");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case ("Edit Tran 4"):
                    if (isOnlyDigits(mes)) {
                        if (Integer.parseInt(mes) + 1 <= Integer.parseInt(states.get(ids.toString() + "EditNumber"))) {
                            String id = mes;
                            mes = BdSql.getCatName(ids.toString(), Integer.parseInt(mes));
                            System.out.println("mes " + " " + mes);
                            states.put(ids.toString() + "EditCat", mes);
                            sender.setText("Мы изменили транзакцию");
                            BdSql.changeStatus(ids.toString(), "Main");
                            BdSql.changeTran(ids.toString(), states.get(ids.toString() + "EditCat"), states.get(ids.toString() + "EditTranTo"), states.get(ids.toString() + "EditTranWhy"), Float.parseFloat(states.get(ids.toString() + "EditMoney")), id);
                        } else {
                            sender.setText("Такой нет, попробуй еще  ");
                            BdSql.changeStatus(ids.toString(), "Edit Tran 4");
                        }
                    }
                    break;
                case ("/main"):
                    sender.setText("Вы вернулись в главное меню ");
                    bot.execute(sender);
                    BdSql.changeStatus(ids.toString(), "Main");
                    break;
                default:
                    sender.setText("Для возврата напишите /main или \nЕсли ты хочешь что-то исправить, тебе надо знать id категории\n\nЧтобы иcправить категорию, введи /cat номер категории \n\nЧтобы исправить транзакцию, введи /tran номер транзакции ");
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




