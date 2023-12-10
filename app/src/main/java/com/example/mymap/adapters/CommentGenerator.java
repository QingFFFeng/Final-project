package com.example.mymap.adapters;

import java.util.Random;

public class CommentGenerator {

    private final String[] DISHES = {"pasta", "steak", "sushi", "pizza", "salad"};
    private final String[] RESTAURANT_NAMES = {"Cafe Delight", "Ocean's Bounty", "The Gourmet Garden", "Urban Eats", "Heaven's Taste"};
    private final String[] CITIES = {"New York", "San Francisco", "Miami", "Austin", "Seattle"};
    private final String[] ADJECTIVES = {"amazing", "delightful", "exquisite", "mediocre", "unimpressive"};
    private final String[] TEMPLATES = {
        "Absolutely loved the [DISH] at [RESTAURANT]! A must-try.",
        "The ambiance at [RESTAURANT] was perfect for our evening. Highly recommend.",
        "Wasn't too impressed with the [DISH] at [RESTAURANT]. Could be better.",
        "Excellent service at [RESTAURANT]. Our evening was just perfect.",
        "[RESTAURANT]'s [DISH] was [ADJECTIVE]. Will be back soon!",
        "Found my new favorite spot in [CITY]. [RESTAURANT] is fantastic!",
            "The [DISH] at [RESTAURANT] was a culinary journey! Truly memorable.",
"Loved the cozy vibe at [RESTAURANT]. It made our [OCCASION] special.",
"The [DISH] at [RESTAURANT] left something to be desired. Hoping for improvements.",
"Remarkable service at [RESTAURANT]! They really made us feel at home.",
"A hidden gem in [CITY]! The [DISH] at [RESTAURANT] is top-notch.",
"Enjoyed the live music at [RESTAURANT]. Added a great touch to our meal.",
"The [DISH] at [RESTAURANT] was [ADJECTIVE]. A unique taste experience.",
"[RESTAURANT]'s dessert menu was a dream come true. The [DESSERT DISH] is a must-try!",
"The outdoor seating at [RESTAURANT] was delightful, perfect for a summer night.",
"Was really looking forward to [DISH] at [RESTAURANT], but it fell short of expectations.",
"Spectacular views at [RESTAURANT] in [CITY]. Made our dinner unforgettable.",
"The brunch at [RESTAURANT] is unbeatable. The [BRUNCH DISH] is my favorite.",
"[RESTAURANT] had a wonderful selection of wines to complement our meal.",
"A cozy spot for [OCCASION] at [RESTAURANT]. Their [DISH] is simply [ADJECTIVE].",
"The fusion dishes at [RESTAURANT] are innovative and delicious. Loved the [FUSION DISH].",
"The attentive staff at [RESTAURANT] made our celebration extra special.",
"The [DISH] at [RESTAURANT] is overhyped. Not what I expected.",
"A tranquil dining experience at [RESTAURANT]. The [DISH] was a highlight.",
"Loved the historical ambiance of [RESTAURANT] in [CITY]. It added charm to our meal.",
"The [SPECIALTY DISH] at [RESTAURANT] is a game changer. Never tasted anything like it!"
    };

    private final Random random = new Random();

    public String generateComment() {
        String template = TEMPLATES[random.nextInt(TEMPLATES.length)];

        // Replace placeholders with random values
        if (template.contains("[DISH]")) {
            template = template.replace("[DISH]", DISHES[random.nextInt(DISHES.length)]);
        }
        if (template.contains("[RESTAURANT]")) {
            template = template.replace("[RESTAURANT]", RESTAURANT_NAMES[random.nextInt(RESTAURANT_NAMES.length)]);
        }
        if (template.contains("[CITY]")) {
            template = template.replace("[CITY]", CITIES[random.nextInt(CITIES.length)]);
        }
        if (template.contains("[ADJECTIVE]")) {
            template = template.replace("[ADJECTIVE]", ADJECTIVES[random.nextInt(ADJECTIVES.length)]);
        }

        return template;
    }
}
