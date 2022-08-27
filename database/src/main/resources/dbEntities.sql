CREATE TABLE gift_certificate(
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(200) NOT NULL,
    price DECIMAL(7, 2) NOT NULL,
    duration INT NOT NULL,
    create_date TIMESTAMP(6) NOT NULL,
    last_update_date TIMESTAMP(6)
);

CREATE TABLE tag(
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE
);


CREATE TABLE gift_certificate_tag(
    id SERIAL PRIMARY KEY,
    gift_certificate_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL
    );

ALTER TABLE
    gift_certificate_tag ADD CONSTRAINT gift_certificate_tag_gift_certificate_id_foreign FOREIGN KEY(gift_certificate_id) REFERENCES gift_certificate(id) ON DELETE CASCADE;
ALTER TABLE
    gift_certificate_tag ADD CONSTRAINT gift_certificate_tag_tag_id_foreign FOREIGN KEY(tag_id) REFERENCES tag(id) ON DELETE CASCADE;
