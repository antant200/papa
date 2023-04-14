package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.math.RoundingMode.HALF_UP;


public class Bot extends TelegramLongPollingBot {
    String operation;

    String nameMoney;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message message = update.getMessage(); //извлекли сообщение
                String chatId = message.getChatId().toString(); // извлекли чат айди
                String response = parseMess(message.getText());
                SendMessage outMessage = new SendMessage(); // наш ответ пользователю
                outMessage.setChatId(chatId);
                outMessage.setText(response);
                // Создаем кнопки "Лонг" и "Шорт"
                if (message.getText().equals("/start")) {
                    outMessage.setReplyMarkup(getOperationKeyboard());
                }
                execute(outMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String parseMess(String textMess) {
        String response = null;
        if (textMess.equals("/start")) {
            response = "Введите какую позицию: Лонг или Шорт?";
        } else if (textMess.equals("Лонг") || textMess.equals("Шорт")) {
            operation = textMess;
            response = "Введите название монеты:";
        }
        else if (textMess.matches("[A-Za-z]+")) {
            nameMoney = textMess;
            response = "Введите точку входа:";
        } else if (isNumeric(textMess)) {
            double entryPoint = Double.parseDouble(textMess);

            int amount1, amount2, amount3;
            double cost2, cost3, averageCost, averageCost2, exitPrice, exitPrice2, profit, profit2;
            if (operation.equals("Лонг")) {

            amount1 = (int) Math.floor(600 / entryPoint);

            cost2 = entryPoint - 0.05 * entryPoint;
            BigDecimal resultCost2 = new BigDecimal(cost2);
            resultCost2 = resultCost2.setScale(4, BigDecimal.ROUND_HALF_UP);
            averageCost = (cost2 + entryPoint) / 2;
            BigDecimal resultAverageCost = new BigDecimal(averageCost);
            resultAverageCost = resultAverageCost.setScale(4, BigDecimal.ROUND_HALF_UP);
            amount2 = (int) Math.floor(600 / cost2);
            exitPrice = 1.05 * averageCost;
            BigDecimal resultExitPrice = new BigDecimal(exitPrice);
            resultExitPrice = resultExitPrice.setScale(4, HALF_UP);
            profit = -(amount1 * entryPoint + amount2 * cost2) + ((amount2 + amount1) * exitPrice);
            BigDecimal resultProfit = new BigDecimal(profit);
            resultProfit = resultProfit.setScale(0, HALF_UP);


            cost3 = entryPoint - 0.1 * entryPoint;
            BigDecimal resultCost3 = new BigDecimal(cost3);
            resultCost3 = resultCost3.setScale(4, HALF_UP);
            averageCost2 = (cost3 + cost2 + entryPoint) / 3;
            BigDecimal resultAverageCost2 = new BigDecimal(averageCost2);
            resultAverageCost2 = resultAverageCost2.setScale(4, HALF_UP);
            amount3 = (int) floor(800 / cost3);
            exitPrice2 = 1.1 * averageCost2;
            BigDecimal resultExitPrice2 = new BigDecimal(exitPrice2);
            resultExitPrice2 = resultExitPrice2.setScale(4, HALF_UP);
            profit2 = -(amount1 * entryPoint + amount2 * cost2 + amount3 * cost3) + ((amount3 + amount1 + amount2) * exitPrice);
            BigDecimal resultProfit2 = new BigDecimal(profit2);
            resultProfit2 = resultProfit2.setScale(0, HALF_UP);
            response = ("Монета: " + nameMoney + " (Лонг) " + "\n" +
                    "1 вход. Кол-во -- " + amount1 + "; Цена покупки: " + entryPoint + "\n" + "\n"
                    + "2 вход. Кол-во -- " + amount2 + "; Цена покупки: " + resultCost2 + "; Средняя цена: " + resultAverageCost + "\n"
                    + "Цена выхода (5%): " + resultExitPrice + "; Сумма прибыли: " + resultProfit + "\n" + "\n"
                    + "3 вход. Кол-во -- " + amount3 + "; Цена покупки: " + resultCost3 + "; Средняя цена: " + resultAverageCost2 + "\n"
                    + "Цена выхода (10%): " + resultExitPrice2 + "; Сумма прибыли: " + resultProfit2);}
            else if (operation.equals("Шорт")) {
                amount1 = (int) Math.floor(480 / entryPoint);

                cost2 = entryPoint + 0.05 * entryPoint;
                BigDecimal resultCost2 = new BigDecimal(cost2);
                resultCost2 = resultCost2.setScale(4, BigDecimal.ROUND_HALF_UP);
                averageCost = (cost2 + entryPoint) / 2;
                BigDecimal resultAverageCost = new BigDecimal(averageCost);
                resultAverageCost = resultAverageCost.setScale(4, BigDecimal.ROUND_HALF_UP);

                amount2 = (int) Math.floor(480 / cost2);
                exitPrice = 0.95 * averageCost;
                BigDecimal resultExitPrice = new BigDecimal(exitPrice);
                resultExitPrice = resultExitPrice.setScale(4, HALF_UP);
                profit = (amount1 * entryPoint + amount2 * cost2) - ((amount2 + amount1) * exitPrice);
                BigDecimal resultProfit = new BigDecimal(profit);
                resultProfit = resultProfit.setScale(0, HALF_UP);


                cost3 = entryPoint + 0.1 * entryPoint;
                BigDecimal resultCost3 = new BigDecimal(cost3);
                resultCost3 = resultCost3.setScale(4, HALF_UP);
                averageCost2 = (cost3 + cost2 + entryPoint) / 3;
                BigDecimal resultAverageCost2 = new BigDecimal(averageCost2);
                resultAverageCost2 = resultAverageCost2.setScale(4, HALF_UP);
                amount3 = (int) floor(640 / cost3);
                exitPrice2 = 0.9 * averageCost2;
                BigDecimal resultExitPrice2 = new BigDecimal(exitPrice2);
                resultExitPrice2 = resultExitPrice2.setScale(4, HALF_UP);
                profit2 = (amount1 * entryPoint + amount2 * cost2 + amount3 * cost3) - ((amount3 + amount1 + amount2) * exitPrice);
                BigDecimal resultProfit2 = new BigDecimal(profit2);
                resultProfit2 = resultProfit2.setScale(0, HALF_UP);
                response = ("Монета: " + nameMoney + "\n" + " (Шорт) " +
                        "1 вход. Кол-во -- " + amount1 + "; Цена покупки: " + entryPoint + "\n" + "\n"
                        + "2 вход. Кол-во -- " + amount2 + "; Цена покупки: " + resultCost2 + "; Средняя цена: " + resultAverageCost + "\n"
                        + "Цена выхода (5%): " + resultExitPrice + "; Сумма прибыли: " + resultProfit + "\n" + "\n"
                        + "3 вход. Кол-во -- " + amount3 + "; Цена покупки: " + resultCost3 + "; Средняя цена: " + resultAverageCost2 + "\n"
                        + "Цена выхода (10%): " + resultExitPrice2 + "; Сумма прибыли: " + resultProfit2);}
            }
         else {
            response = "Введи название на английском или используй точку при вводе числа";
        }
        return response;
    }


    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // Метод создания кнопок "Лонг" и "Шорт"
    public static ReplyKeyboardMarkup getOperationKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Лонг");
        row.add("Шорт");
        keyboard.add(row);
        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        return markup;
    }


    @Override
    public String getBotUsername() {
        return "Papa_Ak_bot";
    }

    @Override
    public String getBotToken() {
        return "6270969692:AAFSdIfDGtnD8bB-xWsM2rKKGZ67-Z6nDok";
    }
}
