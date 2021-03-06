INSERT INTO PUBLIC.MEMBER(ID, EMAIL, FIRST_NAME, LAST_NAME, USERNAME) VALUES
(888, 'first@laps.ch', 'Esimene', 'Laps', '+410000008'),
(999, 'second@laps.ch', 'Teine', 'laps', '+410000009');

INSERT INTO PUBLIC.GROUP_ACCOUNT(ID, REASON, MANAGER_ID) VALUES
(777, 'trip to Zurich', 888);

INSERT INTO PUBLIC.GROUP_MEMBER(ID, ACCOUNT_ID, MEMBER_ID) VALUES
(333, 777, 888),
(444, 777, 999);

INSERT INTO PUBLIC.TRANSACTION(ID, AMOUNT, CURRENCY_CODE, GROUP_ACCOUNT_ID, MEMBER_ID, GEOCODE, DETAILS) VALUES
(1500, 100.00, 'CHF', 777, 999, '50.0877453,14.4177806', '9'),
(1600, -50.00, 'CHF', 777, 999, '51.5007325,-0.1268141', '7'),
(1700, -50.00, 'CHF', 777, 999, '47.3890664,8.5174398', '2');
