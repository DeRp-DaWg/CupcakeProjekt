INSERT INTO cupcake.user (email, password, role, balance)
VALUES ('admin','1234','ADMIN',0),
       ('user','1234','USER',0);
INSERT INTO cupcake.bottom (bottom_price, bottom_name)
VALUES (5,'Chocolate'),
       (5,'Vanilla'),
       (5,'Nutmeg'),
       (6,'Pistacio'),
       (7,'Almond');
INSERT INTO cupcake.topping (topping_price, topping_name)
VALUES (5,'Chocolate'),
       (5,'Blueberry'),
       (5,'Raspberry'),
       (6,'Crispy'),
       (6,'Strawberry'),
       (7,'Rum/Raisin'),
       (8,'Orange'),
       (8,'Lemon'),
       (9,'Blue Cheese');
INSERT INTO cupcake.orders (bottom_id, topping_id, user_id, status, date)
VALUES (1,3,2,'AWAITING_PICKUP','2022-04-04'),
       (2,1,2,'CANCELLED','2022-04-02'),
       (4,5,2,'COMPLETED','2022-04-01'),
       (5,4,2,'AWAITING_PICKUP','2022-04-03');