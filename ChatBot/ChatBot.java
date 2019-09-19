package chatbot;

import java.util.Scanner;

public class ChatBot {

    public static void main(String[] args) {
        SimpleChat bot = new SimpleChat();;
        boolean byeMes = true;
        Scanner in = new Scanner(System.in);
        String message, answerBot;
        while (byeMes) {
            System.out.print("ÂÛ: ");
            message = in.nextLine();
            answerBot=bot.sayChatBot(message);
            System.out.println("ChatBot: "+answerBot);
            if (bot.stopChat==true) {
                System.exit(0);
            }
        }
    }
}
