CREATE TABLE main.cottages
(
    id INTEGER PRIMARY KEY NOT NULL,
    title TEXT,
    bedcount INTEGER,
    imageurl TEXT,
    version INTEGER,
	price INTEGER NOT NULL,
	availablefrom DATE,
	availableto DATE,
	description TEXT
);