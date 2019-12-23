package TelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

final class CmdStats {
    private Bot bot;
    private SendMessage sender;

    CmdStats(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public static Map<String, String> states = new HashMap<String, String>();

    public void stats(Long chatId, String mes, String UserStatus) {
        sender.setChatId(chatId);
        String outStr = null;
        try {
            switch (UserStatus) {
                case ("Main"): {
                    // вывести категории
                    sender.setText("Если ты хочешь посмотреть свои расходы за день, напиши /day Дата GGGG-HH-DD \n\nЕсли ты хочешь посмотреть свои расходы за определенную дату, напиши даты как в примере /date GGGG-HH-DD GGGG-HH-DD \n\nЕсли ты хочешь посмотреть свои категории, введи  /cat, а если хочешь посмотреть транзакции в категории, введи /cattran номер \n\nЧтобы вывести все транзакции, введите /tran \n\nЕсли не хочешь ничего выводить, напиши что-нибудь ");
                    bot.execute(sender);
                    BdSql.changeStatus(chatId.toString(), "State 1");
                    break;
                }
                case ("State 1"):
                    switch (mes.split(" ")[0]) {
                        case ("/day"):
                            System.out.println("CMDSTATS" + mes.split(" ")[1]);
                            sender.setText("Вы попросили вывести статистику за " + mes.split(" ")[1]);
                            bot.execute(sender);
                            BdSql.changeStatus(chatId.toString(), "Main");
                            String[] outStrBD = BdSql.getTableDate(chatId.toString(), mes.split(" ")[1]);
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];
                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            break;
                        case ("/date"):
                            sender.setText("Вы попросили вывести статистику за промежуток времени " + mes.split(" ")[1] + "По " + mes.split(" ")[2]);
                            bot.execute(sender);
                            BdSql.changeStatus(chatId.toString(), "Main");
                            outStrBD = BdSql.getTableDateTo(chatId.toString(), mes.split(" ")[1], mes.split(" ")[2]);
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];
                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            break;
                        case ("/cat"):
                            sender.setText("Ваши категории:");
                            bot.execute(sender);
                            outStrBD = BdSql.getTableCatName(chatId.toString());
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];
                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            sender.setText("Вы можете вводить ранее предложенные команды ");
                            bot.execute(sender);
                            BdSql.changeStatus(chatId.toString(), "State 1");
                            break;
                        case ("/cattran"):
                            sender.setText("Вы попросили вывести краткую статистику по категории номер " + mes.split(" ")[1]);
                            bot.execute(sender);
                            BdSql.changeStatus(chatId.toString(), "Main");
                            outStrBD = BdSql.getTableCatStats(chatId.toString(), mes.split(" ")[1]);
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];
                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            break;
                        case ("/tran"):
                            sender.setText("Вы попросили вывести все транзакции ");
                            bot.execute(sender);
                            BdSql.changeStatus(chatId.toString(), "Main");
                            outStrBD = BdSql.getTable(chatId.toString());
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];
                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            break;
                        case ("/main"):
                            sender.setText("Вы вернулись в главное меню ");
                            bot.execute(sender);
                            BdSql.changeStatus(chatId.toString(), "Main");
                            break;
                        default:
                            sender.setText("Для возврата напишите /main ");
                            bot.execute(sender);
                            break;
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}