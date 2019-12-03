import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CmdStats {
    private final Bot bot;
    private SendMessage sender;

    CmdStats(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public static Map<String, String> states = new HashMap<String, String>();

    public void Stats(Long chatId, String mes, String UserStatus) {
        sender.setChatId(chatId);
        String[] outStrBD = new String[100];
        String outStr = null;
        try {
            switch (UserStatus) {
                case ("Main"): {
                    // вывести категории
                    sender.setText("Если ты хочешь посмотреть свои расходы за день напиши /day Дата GGGG-HH-DD \n Если ты хочешь посмотреть свои расходы за определенную дату напиши их ка в примере через пробел /date GGGG-HH-DD GGGG-HH-DD \n Если ты хочешь посмотреть свои категории введи  /cat, а есди хочешь посмотреть транзакции в категории введи /cattran номер \n Что бы вывести все транзакции введите /tran \n а если не хочешь не чего выводить напиши что угодно ");
                    bot.execute(sender);
                    BdSql.ChangeStatus(chatId.toString(), "State 1");
                    break;
                }
                case ("State 1"): {
                    switch (mes.split(" ")[0]) {
                        case ("/day"): {
                            System.out.println("CMDSTATS" + mes.split(" ")[1]);
                            sender.setText("Вы попросили вывести статистику за " + mes.split(" ")[1]);
                            bot.execute(sender);
                            BdSql.ChangeStatus(chatId.toString(), "Main");
                            outStrBD = BdSql.GetTableTDate(chatId.toString(), mes.split(" ")[1]);
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];

                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            break;

                        }
                        case ("/date"): {
                            sender.setText("Вы попросили вывести статистику за промежуток времени " + mes.split(" ")[1] + "По " + mes.split(" ")[2]);
                            bot.execute(sender);
                            BdSql.ChangeStatus(chatId.toString(), "Main");
                            outStrBD = BdSql.GetTableTDateTo(chatId.toString(), mes.split(" ")[1], mes.split(" ")[2]);
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];
                            }

                            sender.setText(outStr);
                            bot.execute(sender);
                            break;
                        }
                        case ("/cat"): {
                            sender.setText("Ваши категории:");
                            bot.execute(sender);

                            outStrBD = BdSql.GetTableTCatName(chatId.toString());
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];
                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            sender.setText("Вы можете вводить ранее предложенные команды ");
                            bot.execute(sender);
                            BdSql.ChangeStatus(chatId.toString(), "State 1");
                            break;
                        }
                        case ("/cattran"): {
                            sender.setText("Вы попросили вывести краткую статистику по категории номер " + mes.split(" ")[1]);
                            bot.execute(sender);
                            BdSql.ChangeStatus(chatId.toString(), "Main");
                            outStrBD = BdSql.GetTableTCatStats(chatId.toString(), mes.split(" ")[1]);
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];


                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            break;
                        }

                        case ("/tran"): {
                            sender.setText("Вы попросили вывести все транзакции ");
                            bot.execute(sender);
                            BdSql.ChangeStatus(chatId.toString(), "Main");
                            outStrBD = BdSql.GetTableT(chatId.toString());
                            outStr = outStrBD[0];
                            for (int z = 1; z < outStrBD.length; z++) {
                                outStr = outStr + " \n \n " + outStrBD[z];

                            }
                            sender.setText(outStr);
                            bot.execute(sender);
                            break;
                        }
                    }

                }
                default: {
                    sender.setText("Вы вернулись в главное меню ");
                    bot.execute(sender);
                    BdSql.ChangeStatus(chatId.toString(), "Main");
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