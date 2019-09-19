package chatbot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class SimpleChat {

    Pattern pattern;
    Random random;
    Date date;
    public boolean stopChat = false;

    public SimpleChat() {
        random = new Random();
        date = new Date();
    }

    final String[] DONT_KNOW_ANSWERS = {
        "Не уверен, что располагаю такой информацией.",
        "Может лучше поговорим о чём-то другом?",
        "Не уверен, что Вам понравится ответ.",
        "Поверьте, я сам хотел бы это знать.",
        "Вы действительно хотите это знать?",
        "Зачем Вам такая информация?",
        "Давайте сохраним интригу?"};
    final Map<String, String> WORD_USER = new HashMap<String, String>() {
        {
            put("хай", "hello");
            put("привет", "hello");
            put("здравствуй", "hello");
            put("как\\s.*зовут", "name");
            put("как\\s.*имя", "name");
            put("какое\\s.*имя", "name");
            put("зовут", "nameUser");
            put("мое\\s.*имя", "nameUser");
            put("как\\s.*дела", "howareyou");
            put("как\\s.*жизнь", "howareyou");
            put("что\\s.*делаешь", "whatdoyoudoing");
            put("чем\\s.*занимаешься", "whatdoyoudoing");
            put("что\\s.*нравится", "whatdoyoulike");
            put("что\\s.*любишь", "whatdoyoulike");
            put("хорошо", "life");
            put("норм", "life");
            put("который\\s.*час", "whattime");
            put("сколько\\s.*время", "whattime");
            put("прощай", "bye");
            put("пока", "bye");
            put("увидимся", "bye");
            put("до\\s.*свидания", "bye");
            put("денег", "money");
            put("деньги", "money");
            put("\\d+", "whatmoney");
            put("сколько\\s.*есть", "givewhatmoney");
            put("погода", "weather");
            put("ок", "yes");
        }
    };

    final String[] HELLO_ANSWERS = {
        "Здравствуйте, рад Вас видеть.",
        "Привет",
        "Хай"};
    final String[] HOWAREYOU_ANSWERS = {
        "Спасибо, что интересуетесь. У меня всё хорошо.А у тебя?",
        "Да, все норм.А у тебя?",
        "У меня дела отлично. А у тебя как?",
        "Всё ок!А у тебя?"};
    final String[] LIKE_ANSWERS = {
        "Мне нравиться думать что я не просто программа.",
        "Я люблю узнавать что-то новое"};
    final String[] BYE_ANSWERS = {
        "Пока",
        "До свидания. Надеюсь, ещё увидимся."};
    final String[] WEATHER_ANSWERS = {
        "Сегодня немного прохладно.",
        "Солнечно.",
        "Дует ветер."};
    final Map<String, String> ANSWERS_BOT = new HashMap<String, String>() {
        {
            put("name", "Я Микки. А кто ты?");
            put("nameUser", "Красивое имя");
            put("whatdoyoudoing", "Я пробую общаться с людьми.");
            put("life", "OK");
            put("money", "Сколько денег тебе дать?");
            put("whatmoney", "Но у меня нет таких денег...");
            put("givewhatmoney", "У меня только 150");
            put("yes", "Прости, что не могу помочь");
        }
    };

    public String sayChatBot(String msg) {
        String say = DONT_KNOW_ANSWERS[random.nextInt(DONT_KNOW_ANSWERS.length)];
        String message = msg.toLowerCase().trim();
        for (Map.Entry<String, String> o : WORD_USER.entrySet()) {
            pattern = Pattern.compile(o.getKey());
            if (pattern.matcher(message).find()) {
                if (o.getValue().equals("whattime")) {
                    return date.toString();
                } else if (o.getValue().equals("hello")) {
                    return HELLO_ANSWERS[random.nextInt(HELLO_ANSWERS.length)];
                } else if (o.getValue().equals("howareyou")) {
                    return HOWAREYOU_ANSWERS[random.nextInt(HOWAREYOU_ANSWERS.length)];
                } else if (o.getValue().equals("whatdoyoulike")) {
                    return LIKE_ANSWERS[random.nextInt(LIKE_ANSWERS.length)];
                } else if (o.getValue().equals("bye")) {
                    stopChat = true;
                    return BYE_ANSWERS[random.nextInt(BYE_ANSWERS.length)];
                } else if (o.getValue().equals("weather")) {
                    return WEATHER_ANSWERS[random.nextInt(WEATHER_ANSWERS.length)];
                } else {
                    return ANSWERS_BOT.get(o.getValue());
                }
            }
        }
        return say;
    }
}
