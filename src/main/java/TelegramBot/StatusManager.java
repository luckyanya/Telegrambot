package TelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StatusManager {
    private final Bot bot;
    private final SendMessage sender;
    private final CmdStart commander;
    private final CmdHelp commander1;
    private final CmdDelete commander2;
    private final CmdEdit commander3;
    private final CmdNewT commander4;
    private final CmdStats commander5;
    private final CmdNewCat commander6;

    StatusManager(Bot bot) {
        this.bot = bot;
        sender = new SendMessage();
        this.commander = new CmdStart(bot);// обработчик команды /start
        this.commander1 = new CmdHelp(bot);// обработчик команды /help
        this.commander2 = new CmdDelete(bot);// обработчик команды /delete и /deleteall
        this.commander3 = new CmdEdit(bot);// упрощенная отправка сообщений
        this.commander4 = new CmdNewT(bot);// упрощенная отправка сообщений
        this.commander5 = new CmdStats(bot);// упрощенная отправка сообщений
        this.commander6 = new CmdNewCat(bot);// упрощенная отправка сообщений
    }

    public void getCmd(Long chatId, String message) {
        String textCommand = null;
        String UserStatus = null;
        textCommand = message.split(" ")[0];
        if (!textCommand.equals("/start")) {
            UserStatus = BdSql.getStatus(chatId.toString());
            System.out.println("Status " + " В цикле" + UserStatus);
            if (UserStatus.split(" ")[0].equals("Tran")) {
                textCommand = "/newt";
                System.out.println("Команда " + " " + textCommand);
            }
            if (UserStatus.split(" ")[0].equals("Cat")) {
                textCommand = "/newcat";
                System.out.println("Команда " + " " + textCommand);
                //State 1
            }
            if (UserStatus.split(" ")[0].equals("State")) {
                textCommand = "/stats";
                System.out.println("Команда " + " " + textCommand);
                //State 1
            }
            if (UserStatus.split(" ")[0].equals("Edit")) {
                textCommand = "/edit";
                System.out.println("Команда " + " " + textCommand);
                //State 1
            }
        }
        try {
            switch (textCommand) {
                case ("/start"):
                    this.commander.start(chatId);
                    break;
                case ("/newcat"):
                    this.commander6.сmdNewCat(chatId, message, UserStatus);
                    break;
                case ("/help"):
                    this.commander1.help(chatId);
                    break;
                case ("/stats"):
                    this.commander5.stats(chatId, message, UserStatus);
                    break;
                case ("/deleteAll"):
                    this.commander2.deleteAll(chatId);
                    break;
                case ("/newt"):
                    this.commander4.сmdNewT(chatId, message, UserStatus);
                    break;
                case ("/edit"):
                    this.commander3.сmdEdit(chatId, message, UserStatus);
                    break;
                default:
                    sender.setText("Я не знаю, что ответить. Попробуй посмотреть, что я могу в /help");
                    bot.execute(sender);
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
