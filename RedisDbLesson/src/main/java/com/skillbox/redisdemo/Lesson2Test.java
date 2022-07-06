package com.skillbox.redisdemo;

           /*
      Напишите программу, которая будет эмулировать работу такого сайта,
     хранить очередь в Redis и выводить лог операций в консоль.

                Рекомендации

        Программа должна запускать бесконечный цикл, в котором:

        Выводится в консоль номер пользователя, которого нужно отобразить на главной странице.
                В одном из 10 случаев случайный пользователь оплачивает услугу, в консоль выводится его номер.
        Программа ждёт 1 секунду, и цикл начинается заново.

        — На главной странице показываем пользователя 1
— На главной странице показываем пользователя 2
— На главной странице показываем пользователя 3
> Пользователь 8 оплатил платную услугу
— На главной странице показываем пользователя 8
— На главной странице показываем пользователя 4
        */

import java.util.Random;

public class Lesson2Test {

    private static final boolean AUTO_INIT = true; // автоинициализация конфигурации
    private static final int COUNT_PEOPLE_TO_WEBSITE = 20 +1; // залогинить 20 пользователей
    private static final int SECOND_SLEEP = 1; //спать одну секунду
    private static final int SECONDS_LOG_OUT = 3; // удалять пользователя после 3х секунд

    private static void showPaidService(int idUserPaid) {
        System.out.println("> Пользователь " + idUserPaid + " оплатил услугу");
        log(idUserPaid);
    }

    private static void sleep(int sleepSeconds) {
        try {
            Thread.sleep(sleepSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void log(int userLogShow) {
        System.out.println("На главной странице показываем пользователя " + userLogShow);
    }

    public static void main(String[] args) {
        RedisStorage db = new RedisStorage(AUTO_INIT);
        Random random = new Random();


        int userCountToWebSite = 1;


        do {
            // хардкод случайности момента выбора id пользователя
            //иначе добавляем по порядку

            if (random.nextInt(100) > 90) {
                int randIdUserPaid = random.nextInt(COUNT_PEOPLE_TO_WEBSITE);
                db.loginPageVisit(randIdUserPaid);
                showPaidService(randIdUserPaid);
            } else {
                db.loginPageVisit(userCountToWebSite);
                log(userCountToWebSite);
                userCountToWebSite++;
            }

            sleep(SECOND_SLEEP);


            if (userCountToWebSite  == COUNT_PEOPLE_TO_WEBSITE) {
                db.deleteOldEntries(SECONDS_LOG_OUT);
                userCountToWebSite = 1;
            }

        } while (userCountToWebSite != COUNT_PEOPLE_TO_WEBSITE);

        db.shutdown();
    }

}
