ALTER TABLE receivable
DROP CONSTRAINT fk_assignor_id,
ADD CONSTRAINT fk_assignor_id
FOREIGN KEY (assignor)
REFERENCES assignor(id)
ON DELETE CASCADE;