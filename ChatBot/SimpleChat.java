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
        "�� ������, ��� ���������� ����� �����������.",
        "����� ����� ��������� � ���-�� ������?",
        "�� ������, ��� ��� ���������� �����.",
        "��������, � ��� ����� �� ��� �����.",
        "�� ������������� ������ ��� �����?",
        "����� ��� ����� ����������?",
        "������� �������� �������?"};
    final Map<String, String> WORD_USER = new HashMap<String, String>() {
        {
            put("���", "hello");
            put("������", "hello");
            put("����������", "hello");
            put("���\\s.*�����", "name");
            put("���\\s.*���", "name");
            put("�����\\s.*���", "name");
            put("�����", "nameUser");
            put("���\\s.*���", "nameUser");
            put("���\\s.*����", "howareyou");
            put("���\\s.*�����", "howareyou");
            put("���\\s.*�������", "whatdoyoudoing");
            put("���\\s.*�����������", "whatdoyoudoing");
            put("���\\s.*��������", "whatdoyoulike");
            put("���\\s.*������", "whatdoyoulike");
            put("������", "life");
            put("����", "life");
            put("�������\\s.*���", "whattime");
            put("�������\\s.*�����", "whattime");
            put("������", "bye");
            put("����", "bye");
            put("��������", "bye");
            put("��\\s.*��������", "bye");
            put("�����", "money");
            put("������", "money");
            put("\\d+", "whatmoney");
            put("�������\\s.*����", "givewhatmoney");
            put("������", "weather");
            put("��", "yes");
        }
    };

    final String[] HELLO_ANSWERS = {
        "������������, ��� ��� ������.",
        "������",
        "���"};
    final String[] HOWAREYOU_ANSWERS = {
        "�������, ��� �������������. � ���� �� ������.� � ����?",
        "��, ��� ����.� � ����?",
        "� ���� ���� �������. � � ���� ���?",
        "�� ��!� � ����?"};
    final String[] LIKE_ANSWERS = {
        "��� ��������� ������ ��� � �� ������ ���������.",
        "� ����� �������� ���-�� �����"};
    final String[] BYE_ANSWERS = {
        "����",
        "�� ��������. �������, ��� ��������."};
    final String[] WEATHER_ANSWERS = {
        "������� ������� ���������.",
        "��������.",
        "���� �����."};
    final Map<String, String> ANSWERS_BOT = new HashMap<String, String>() {
        {
            put("name", "� �����. � ��� ��?");
            put("nameUser", "�������� ���");
            put("whatdoyoudoing", "� ������ �������� � ������.");
            put("life", "OK");
            put("money", "������� ����� ���� ����?");
            put("whatmoney", "�� � ���� ��� ����� �����...");
            put("givewhatmoney", "� ���� ������ 150");
            put("yes", "������, ��� �� ���� ������");
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
