CREATE TABLE IF NOT EXISTS users
(
    id_user serial primary key,
    login varchar(50),
    email varchar(255),
    avatar_image text, -- new addded 11/10/2023
    is_terms_of_use_accepted boolean,
    role varchar(255),
    user_password varchar(255)
    );

    -- added 11/20/2023
CREATE TABLE IF NOT EXISTS categories (
    id_category serial primary key,
    category text
);

CREATE TABLE IF NOT EXISTS lot
(
    id_lot serial primary key,
    name_of_lot varchar(100),
    title_image text,
    id_category bigint, -- changed 11/20/2023
    description text,
    start_bid_amount decimal,
    expiration timestamp,
    foreign key(id_category) references Categories(id_category)
    );

create table if not exists auction_bid
(
    id_bid serial primary key,
    id_user bigint,
    id_lot bigint,
    bid numeric(6,2),
    datetime timestamp,
    foreign key(id_user) references Users(id_user),
    foreign key(id_lot) references Lot(id_lot)
    );

-- delete if not working TABLE lot_images (make new migration)
CREATE TABLE IF NOT EXISTS lot_images (
  id serial primary key,
  lot_id bigint,
  image_url text,
  FOREIGN KEY (lot_id) REFERENCES lot(id_lot)
);

-- added 11/20/2023
INSERT INTO categories (category) VALUES
    ('Clothing'),
    ('Home'),
    ('Аntiques'),
    ('Art'),
    ('Trophy');