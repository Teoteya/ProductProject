-- CREATE TYPE Status AS ENUM ('Sellable', 'Unfulfillable', 'Inbound');

-- CREATE TYPE FulfillmentCenter AS ENUM ('fc1', 'fc2', 'fc3', 'fc4', 'fc5');

create table product (
  id                    bigserial PRIMARY KEY,
  product_id             varchar not null,
  status                varchar(30) not null,
  fulfillment_center     varchar(10) not null,
  quantity              INT not null CHECK (quantity >= 0),
  "value"               INT not null CHECK ("value" >= 0)
);