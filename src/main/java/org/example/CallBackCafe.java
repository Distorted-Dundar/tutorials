package org.example;

import org.callback.CallbackListener;

import java.util.List;
import java.util.Random;

import static org.callback.CallbackListener.wrap;

public class CallBackCafe {
    public static void main(String[] args) {
    //        Describe the desired action
        CallbackListener<String> callbackListener = wrap((order) -> {
                    System.out.println(order);
                    System.out.println("=============[ENJOY!!!]================");
                },
                (orderException) -> {
                    System.out.println(orderException.getMessage());
                }
        );
    //    Create orders
        List<Order> orders = List.of(new Order("coffee", 3.0),
                new Order("Chocolate cake slice", 5.0),
                new Order("Alfredo pasta", 12.20));

        // dispatch the orders
        for (Order order : orders) {
            execOrder(order, callbackListener);
        }

    }

    static void execOrder(Order order, CallbackListener<String> callbackListener) {
        double probability = 0.7;
        Random random = new Random();
        double event = random.nextDouble();
        Long waitTime = 1000 * Math.round(event * 10);
        //  Simulate cooking time
        try {
            System.out.println("Cooking ... " +  order.food + " please wait " + waitTime + "s");
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 70% chance order is executed otherwise exception occurs
        if (probability >= event) {
            callbackListener.onResponse(order.serve());
        } else {
            callbackListener.onFailure(new OrderException(order));
        }

    }

}

class Order {
    String food;
    double price;

    public Order(String food, double price) {
        this.food = food;
        this.price = price;
    }

    public String serve() {
        String platter =
                "       ________       \n" +
                "     /          \\     \n" +
                "    /            \\    \n" +
                "   /              \\   \n" +
                "  |   " + food + "   |  \n" +
                "   \\              /   \n" +
                "    \\            /    \n" +
                "     \\__________/     \n";

        return platter;
    }

}


class OrderException extends Exception {
    Order order;

    public OrderException(Order order) {
        this.order = order;
    }

    @Override
    public String getMessage() {
        return "Could Not get " + order.food + " to be cooked we will refund you $" + order.price;
    }
}