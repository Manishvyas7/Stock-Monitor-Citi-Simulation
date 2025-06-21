package org.example;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Queue<String> stockQueue = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (true) {
            try {                                          // Note: This may throw HTTP 429 if Yahoo rate limits the IP
            	Stock dowJones = YahooFinance.get("AAPL"); // Dow Jones Industrial Average
                if (dowJones != null && dowJones.getQuote() != null) {
                    String price = dowJones.getQuote().getPrice().toString();
                    String timestamp = sdf.format(new Date());
                    String record = "[" + timestamp + "] DJIA: " + price;
                    stockQueue.add(record);
                    System.out.println(record);
                } else {
                    System.out.println("Data not available.");
                }

                Thread.sleep(5000);  // Wait for 5 seconds
            } catch (IOException e) {
                System.out.println("Error fetching stock data: " + e.getMessage());
                Thread.sleep(5000);
            }
        }
    }
}
