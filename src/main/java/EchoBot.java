import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EchoBot extends TelegramLongPollingBot {

    Currency currency = new Currency();

    public void onUpdateReceived(Update update) {


        if (update.hasMessage() &&
                update.getMessage().hasText()) {

            String response = getResponse(update.getMessage().getText());

            if (!response.isEmpty()) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(response);


                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public String getResponse(String message) {
        if (message.matches("(?i)echo: .*")) {
            return message.substring(6);
        } else if (message.matches("(?i)reverse: .*")) {
            String substring = message.substring(9);
            return new StringBuilder(substring).reverse().toString();
        } else {
            return currency.convertAuto(message);
        }
        //return "";
    }


    public String getBotUsername() {
        return "systemshock_bot";
    }

    public String getBotToken() {
        return "492559714:AAG3T2bmRPYFeERbNqEhtJtzAgiBmvDQl50";
    }
}
