CREATE TABLE category (
  id          BIGINT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;
CREATE TABLE ingredient (
  id          BIGINT NOT NULL AUTO_INCREMENT,
  amount      DECIMAL(19, 2),
  description VARCHAR(255),
  recipe_id   BIGINT,
  uom_id      BIGINT,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;
CREATE TABLE notes (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  recipe_notes LONGTEXT,
  recipe_id    BIGINT,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;
CREATE TABLE recipe (
  id          BIGINT NOT NULL AUTO_INCREMENT,
  cook_time   INTEGER,
  description VARCHAR(255),
  difficulty  VARCHAR(255),
  directions  LONGTEXT,
  image       LONGBLOB,
  prep_time   INTEGER,
  servings    INTEGER,
  source      VARCHAR(255),
  url         VARCHAR(255),
  notes_id    BIGINT,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;
CREATE TABLE recipe_category (
  recipe_id   BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  PRIMARY KEY (recipe_id, category_id)
)
  ENGINE = InnoDB;
CREATE TABLE unit_of_measure (
  id          BIGINT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;
ALTER TABLE ingredient
  ADD CONSTRAINT FKj0s4ywmqqqw4h5iommigh5yja FOREIGN KEY (recipe_id) REFERENCES recipe (id);
ALTER TABLE ingredient
  ADD CONSTRAINT FK6iv5l89qmitedn5m2a71kta2t FOREIGN KEY (uom_id) REFERENCES unit_of_measure (id);
ALTER TABLE notes
  ADD CONSTRAINT FKdbfsiv21ocsbt63sd6fg0t3c8 FOREIGN KEY (recipe_id) REFERENCES recipe (id);
ALTER TABLE recipe
  ADD CONSTRAINT FK37al6kcbdasgfnut9xokktie9 FOREIGN KEY (notes_id) REFERENCES notes (id);
ALTER TABLE recipe_category
  ADD CONSTRAINT FKqsi87i8d4qqdehlv2eiwvpwb FOREIGN KEY (category_id) REFERENCES category (id);
ALTER TABLE recipe_category
  ADD CONSTRAINT FKcqlqnvfyarhieewfeayk3v25v FOREIGN KEY (recipe_id) REFERENCES recipe (id);