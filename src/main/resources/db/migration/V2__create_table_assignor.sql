CREATE TABLE assignor (
    id UUID PRIMARY KEY,
    document VARCHAR(30) NOT NULL,
    email VARCHAR(140) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    name VARCHAR(140) NOT NULL
);