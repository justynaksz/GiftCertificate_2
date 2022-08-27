INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES ('H&M gift card', 'Gift card to the fashion store', 100.00, 90, '2022-06-22T18:31:44.574', NULL);
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES ('Sweet coffee', 'Gift card to cafe', 50.00, 365, '2018-01-03T19:14:57.123', '2020-5-19T10:28:01.187');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES ('Ruby store', 'Gift card to jewellery store', 1000.00, 30, '2021-11-19T08:01:13.321', NULL);

INSERT INTO tag (name) VALUES ('sweets');
INSERT INTO tag (name) VALUES ('fashion');
INSERT INTO tag (name) VALUES ('jewellery');
INSERT INTO tag (name) VALUES ('beauty&spa');
INSERT INTO tag (name) VALUES ('coffee');
INSERT INTO tag (name) VALUES ('shopping');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 2);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 3);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 6);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2, 1);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2, 5);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (3, 3);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (3, 6);
