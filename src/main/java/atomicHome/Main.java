package atomicHome;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger count1 = new AtomicInteger();
    private static AtomicInteger count2 = new AtomicInteger();
    private static AtomicInteger count3 = new AtomicInteger();
    private static String[] texts = new String[100_000];

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }


        List<Thread> list = List.of(
                new Thread(Main::isPalindrome),
                new Thread(Main::isCharEqual),
                new Thread(Main::isCharAdvance)
        );

        for (Thread thread : list) {
            thread.start();
            thread.join();
        }

        System.out.println(
                "Красивых слов с длиной 3: " + count1 + " шт.\n" +
                        "Красивых слов с длиной 4: " + count2 + " шт.\n" +
                        "Красивых слов с длиной 5: " + count3 + " шт."
        );
    }

    private static void isPalindrome() {
        for (String text : texts) {
            StringBuffer buffer = new StringBuffer(text);
            String result = buffer.reverse().toString();
            if (text.equals(result)) {
                counter(text);
            }
        }
    }

    private static void isCharEqual() {
        for (String text : texts) {
            boolean result = true;
            for (int i = 0; i < text.length() - 1; i++) {
                if (text.charAt(i) != text.charAt(i + 1)) {
                    result = false;
                }
            }
            if (result) {
                counter(text);
            }
        }
    }

    private static void isCharAdvance() {
        for (String text : texts) {
            boolean result = true;
            for (int i = 0; i < text.length() - 1; i++) {
                if (text.charAt(i) > text.charAt(i + 1)) {
                    result = false;
                }
            }
            if (result) {
                counter(text);
            }
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void counter(String text) {
        switch (text.length()) {
            case 3:
                count1.incrementAndGet();
                break;
            case 4:
                count2.incrementAndGet();
                break;
            case 5:
                count3.incrementAndGet();
                break;
        }
    }

}
