ALTER TABLE receivable
ADD CONSTRAINT fk_assignor_id
FOREIGN KEY (assignor)
REFERENCES assignor(id);