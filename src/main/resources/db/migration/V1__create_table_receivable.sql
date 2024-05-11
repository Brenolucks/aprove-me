CREATE TABLE receivable (
    id UUID PRIMARY KEY,
    receivable_value NUMERIC(10,2) NOT NULL,
    emission_date DATE NOT NULL,
    assignor UUID
);