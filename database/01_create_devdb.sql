-- Ejecutar conectado como usuario administrador de PostgreSQL, por ejemplo postgres.
-- Si el usuario ups ya existe, solo actualiza su password.

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_roles
        WHERE rolname = 'ups'
    ) THEN
        CREATE ROLE ups LOGIN PASSWORD 'ups123';
    ELSE
        ALTER ROLE ups WITH LOGIN PASSWORD 'ups123';
    END IF;
END
$$;

-- Ejecutar solo si la base devdb todavia no existe.
CREATE DATABASE devdb OWNER ups;

GRANT ALL PRIVILEGES ON DATABASE devdb TO ups;
