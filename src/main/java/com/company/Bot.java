package com.company;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    private String BOT_NAME="Telebot";
    private String BOT_TOKEN="5907762286:AAGBnokK8RigMYcReOeyI1pG8T1wR_XTPmw";
    Storage storage;

    Bot(){
        //создаём в конструкторе экхемпляр класса
        storage=new Storage();

    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try{if(update.hasMessage()&&update.getMessage().hasText()){
            //Извлекаем из объекта апдейт наше сообщение;
            Message inMess=update.getMessage();
            //Извлекаем из сообщения АйДи чата пользователя
            String chatId=inMess.getChatId().toString();
            //Создаём строку респонс,в которую помещаем то,что возвращает нам наш метод,в который мы поместили ответы на входящее сообщение пользователя
            String response=parseMessage(inMess.getText());
            //Создаем отправляемое сообщение outMess с помощью объекта SendMessage;
            SendMessage outMess=new SendMessage();
            //Обозначаем АйДи пользователя(чтобы бот понимал,кому отвечаем)
            outMess.setChatId(chatId);

            //Помещаем в объект SendMessage текст ,который получили из написанного нами метода
            outMess.setText(response);

            //Создаём специальный контейнер под клавиатуру
            ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
            //Подгоняем размер
            replyKeyboardMarkup.setResizeKeyboard(true);
            //Скрываем после использования
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            //Создаем массив объектов линия кнопок,которые по сути сами являются массивом кнопок.
            ArrayList<KeyboardRow>MyKeyBoard=new ArrayList<>();
            //Создаем тот самый объект линия кнопок
            KeyboardRow keyLine=new KeyboardRow();
            //Добавляем кнопку
            keyLine.add(new KeyboardButton("Получить напутствие"));
            //Добавляем массив для кнопок к нашей клавиатуре(по сути клавиатура это массив массивов)
            MyKeyBoard.add(keyLine);
            //Помещаем в наш контейнер нашу клавиатуру
            replyKeyboardMarkup.setKeyboard(MyKeyBoard);
            //подключаем контейнер с клавиатурой к сообщению
            outMess.setReplyMarkup(replyKeyboardMarkup);

            //отправляем сообщение
            execute(outMess);
        }
        }
        catch(TelegramApiException e){
            e.printStackTrace();
        }

    }
    public String parseMessage(String textMsg){
        String response;
        if (textMsg.equals("/start")) response="Привет,я знаю несколько хороших напутствий на день,если хочешь попытать счастья,жми /get";
        else if (textMsg.equals("/get")||textMsg.equals("Получить напутствие")) response=storage.getRandQuote();
        else response="Извините,но я вас не понимаю";
        return response;
    }

}
