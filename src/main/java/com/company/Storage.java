package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private ArrayList<String> quoteList;//Класс-рандомное хранилище.
    Storage() {
        quoteList = new ArrayList<>();
        quoteList.add("Жизнь нужно прожить так,чтобы не было мучительно больно за бесцельно прожитые годы\n\nВ.Маяковский");
        quoteList.add("Учиться,учиться и ещё раз учиться!\n\nВ.И.Ленин");
        quoteList.add("Если драка неизбежна,нужно бить первым\n\nВ.В.Путин");
        quoteList.add("Мы в ответе за тех,кого приручили\n\nА.Д.С.Экзюпери");
        parser("https://citatnica.ru/citaty/mudrye-tsitaty-velikih-lyudej");
    }
        String getRandQuote()
        {
            //получаем случайное значение в интервале от 0 до самого большого индекса
            int randValue = (int)(Math.random() * quoteList.size());
            //Из коллекции получаем цитату со случайным индексом и возвращаем ее
            return quoteList.get(randValue);
        }

        void parser(String strURL){
        String classNmae="su-note-inner su-u-clearfix su-u-trim";
            Document doc=null;
            try{
               doc= (Document) Jsoup.connect(strURL).maxBodySize(0).get();
            }
            catch(IOException e){
               e.printStackTrace();
            }
            Elements elQuote=doc.getElementsByClass(classNmae);

            elQuote.forEach(el -> {
                quoteList.add(el.text());
            });
        }
    }

