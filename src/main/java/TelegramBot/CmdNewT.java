package TelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

final class CmdNewT {
    private Bot bot;
    private SendMessage sender;

    CmdNewT(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
    }

    public static Map<String, String> states = new HashMap<String, String>();

    public void СmdNewT(Long ids, String mes, String UserStatus) {
        String outStr = null;
        sender.setChatId(ids);
        try {
            switch (UserStatus) {
                case ("Main"): {
                    sender.setText("Так, давай начнем. \nСколько ты потратил?");
                    BdSql.ChangeStatus(ids.toString(), "Tran 1");
                    break;
                }
                case ("Tran 1"): {
                    //Записать сумму траты
                    if (isOnlyDigits(mes)) {
                        states.put(ids.toString() + "Money", mes);
                        BdSql.ChangeStatus(ids.toString(), "Tran 2");
                        sender.setText("Где потратили?");
                    } else {
                        BdSql.ChangeStatus(ids.toString(), "Tran 1");
                        sender.setText("Мне нужны цифры! A не вот это все! '" + mes + "' Что это вообще такое ? \nПопробуй еще!");
                    }
                    break;
                }
                case ("Tran 2"): {
                    states.put(ids.toString() + "TranTo", mes);
                    BdSql.ChangeStatus(ids.toString(), "Tran 3");
                    sender.setText("Какое назначение платежа?");
                    break;
                }
                case ("Tran 3"): {
                    states.put(ids.toString() + "TranWhy", mes);
                    BdSql.ChangeStatus(ids.toString(), "Tran 4");
                    int z;
                    try { //вывести категории

                        String[]   outStrBD = BdSql.GetCatTable(ids.toString());
                        outStr = outStrBD[0];
                        for (z = 1; z < outStrBD.length; z++) {
                            outStr = outStr + " \n \n " + outStrBD[z];
                        }
                        if (z == 1) {
                            sender.setText("К сожалению, я не нашел созданных тобой категорий. Ответь на пару вопросов \nКак назвать твою первую категорию? ");
                            BdSql.ChangeStatus(ids.toString(), "Cat 1 Tran 3");
                        } else {
                            states.put(ids.toString() + "Number", "" + z + "");
                            sender.setText(outStr);
                            bot.execute(sender);
                            sender.setText("Укажите номер категории, в которой была совершена покупка \n Если хотите создать категорию напишите /newcat");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case ("Tran 4"): {
                    if(mes.equals("/newcat")) {
                        BdSql.ChangeStatus(ids.toString(), "Cat 1 Tran 3");

                        sender.setText("Создаем новую категорию! Какое имя ?");
                    }
                    if (isOnlyDigits(mes)) {
                        if(Integer.parseInt(mes)<= Integer.parseInt(states.get(ids.toString()+"Number"))&&(Integer.parseInt(mes)>0))
                        {
                            String statesstring="";
                            BdSql.ChangeStatus(ids.toString(), "Tran 4");
                            try { //вывести категории
                                String[] outStrBD = BdSql.GetCatTable(ids.toString());
                                statesstring = outStrBD[1];
                            }
                            catch (SQLException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Сообщение" +mes);
                            String catId=mes;
                            System.out.println("Сообщение" +statesstring);
                            mes = BdSql.GetCatName(ids.toString(), Integer.parseInt(mes));
                            System.out.println("mes " + " " + mes);
                            states.put(ids.toString() + "Cat", mes);
                            sender.setText("Я все записал");
                            bot.execute(sender);
                            BdSql.ChangeStatus(ids.toString(), "Main");
                            BdSql.NewTran(ids.toString(), states.get(ids.toString() + "Cat"), states.get(ids.toString() + "TranTo"), states.get(ids.toString() + "TranWhy"), Float.parseFloat(states.get(ids.toString() + "Money")));
                            sender.setText("В этой категории "+ BdSql.GetTableTCatStats(ids.toString(),catId)[1]);
                        } else {
                            sender.setText("Такой категории нет, попробуй еще  ");
                            BdSql.ChangeStatus(ids.toString(), "Tran 4");
                        }
                    }
                    break;
                }
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