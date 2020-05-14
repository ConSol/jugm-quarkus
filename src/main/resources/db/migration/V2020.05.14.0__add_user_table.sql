CREATE SEQUENCE app_user_seq_id INCREMENT BY 1 START WITH 1;

CREATE TABLE public.app_user (
  id BIGINT CONSTRAINT app_user_pk_id PRIMARY KEY DEFAULT NEXTVAL('app_user_seq_id'),
  name VARCHAR(255) NOT NULL CONSTRAINT app_user_unique_name UNIQUE,
  email VARCHAR(255) NOT NULL CONSTRAINT app_user_unique_email UNIQUE
);

ALTER SEQUENCE app_user_seq_id
    OWNED BY public.app_user.id;