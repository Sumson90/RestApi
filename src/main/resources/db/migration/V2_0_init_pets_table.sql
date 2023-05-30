CREATE TABLE pet
(
    id               SERIAL      not null,
    pet_store_pet_id BIGINT no null,
    name             VARCHAR(64) no null,
    status           varchar(64) not null,
    category           varchar(64) not null,
    employee_id      int         not null,
    PRIMARY KEY (ID),
    CONSTRAINT fk_pet_employee
        FOREIGN KEY (employee_id)
            REFERENCES employee (employee_id)
);