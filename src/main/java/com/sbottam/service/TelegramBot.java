package com.sbottam.service;

import com.sbottam.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//@Slf4j
@Component
public class TelegramBot  extends TelegramLongPollingBot {
    final Config config;

    public TelegramBot(Config config) {
        this.config = config;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
           String incomingMessage =  update.getMessage().getText();
           long chatId = update.getMessage().getChatId();
            switch ( incomingMessage){
                case "/start":
                    startReceived(chatId, update.getMessage().getChat().getFirstName());
//                    log.info("11111111111111111");
                    break;

                default:
                    sendMessage(chatId, "what?");
            }

        }

    }
    private void  startReceived(long chatId, String firstName){
        String textToSend ="Привет  "+ firstName+ "  рад видеть тебя!";
        sendMessage(chatId, textToSend);

    }
    private  void sendMessage(long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
//            log.error("error occurred:  " +e.getMessage());
            e.printStackTrace();
        }
    }

}
