drop table IF EXISTS requests;
drop table IF EXISTS complaints;
drop table IF EXISTS generics;
drop table IF EXISTS info;
drop table IF EXISTS foreigners;
drop table IF EXISTS applications;
drop table IF EXISTS outgoing;
drop table IF EXISTS incoming;
drop table IF EXISTS sent;
drop table IF EXISTS inbox;
drop table IF EXISTS executors;
drop table IF EXISTS contacts;
drop sequence IF EXISTS global_seq;

create sequence global_seq start with 100000;

create TABLE executors
(
  id        INTEGER                 PRIMARY KEY DEFAULT nextval('global_seq'),
  name      VARCHAR                 NOT NULL,
  enabled   BOOLEAN                 NOT NULL
);
CREATE UNIQUE INDEX executors_unique_name_idx ON executors (name);

create TABLE contacts
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  alias     VARCHAR                 NOT NULL,
  email     VARCHAR                 NOT NULL
);
CREATE UNIQUE INDEX contacts_unique_alias_email_idx ON contacts (alias, email);

create TABLE requests
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  income_date      TIMESTAMP               NOT NULL,
  income_index     VARCHAR                 NOT NULL,
  correspondent    VARCHAR                 NOT NULL,
  outer_date       TIMESTAMP               NOT NULL,
  outer_index      VARCHAR                 NOT NULL,
  description      VARCHAR                 ,
  executor_id      INTEGER   			   NOT NULL,
  done_date        TIMESTAMP,
  done_index       VARCHAR,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX requests_idx ON requests (income_date, income_index);

create TABLE complaints
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  income_date      TIMESTAMP               NOT NULL,
  income_index     VARCHAR                 NOT NULL,
  correspondent    VARCHAR                 NOT NULL,
  outer_date       TIMESTAMP               NOT NULL,
  outer_index      VARCHAR                 NOT NULL,
  description      VARCHAR                 ,
  executor_id      INTEGER   			   NOT NULL,
  done_date        TIMESTAMP,
  done_index       VARCHAR,
  done_result      VARCHAR,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX complaints_idx ON complaints (income_date, income_index);

create TABLE generics
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  income_index     VARCHAR                 NOT NULL,
  income_date      TIMESTAMP               NOT NULL,
  correspondent    VARCHAR                 NOT NULL,
  outer_date       TIMESTAMP               NOT NULL,
  outer_index      VARCHAR                 NOT NULL,
  description      VARCHAR,
  executor_id      INTEGER   			   NOT NULL,
  done_date        TIMESTAMP,
  done_index       VARCHAR,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX generics_idx ON generics (income_date, income_index);

create TABLE info
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  income_date      TIMESTAMP               NOT NULL,
  income_index     VARCHAR                 NOT NULL,
  correspondent    VARCHAR                 NOT NULL,
  outer_date       TIMESTAMP               NOT NULL,
  outer_index      VARCHAR                 NOT NULL,
  description      VARCHAR                 ,
  executor_id      INTEGER   			   NOT NULL,
  done_date        TIMESTAMP,
  done_index       VARCHAR,
  done_result      VARCHAR,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX info_idx ON info (income_date, income_index);

create TABLE foreigners
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  income_index     VARCHAR                 NOT NULL,
  income_date      TIMESTAMP               NOT NULL,
  correspondent    VARCHAR                 NOT NULL,
  outer_date       TIMESTAMP               NOT NULL,
  outer_index      VARCHAR                 NOT NULL,
  description      VARCHAR,
  executor_id      INTEGER   			   NOT NULL,
  debtor           VARCHAR                 ,
  proceeding_number VARCHAR                ,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX foreigners_idx ON foreigners (income_date, income_index);


create TABLE applications
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  income_date      TIMESTAMP               NOT NULL,
  income_index     VARCHAR                 NOT NULL,
  correspondent    VARCHAR                 NOT NULL,
  outer_date       TIMESTAMP               NOT NULL,
  outer_index      VARCHAR                 NOT NULL,
  description      VARCHAR                 ,
  executor_id      INTEGER   			   NOT NULL,
  done_date        TIMESTAMP,
  done_index       VARCHAR,
  work_date        TIMESTAMP               ,
  work_index       VARCHAR                 ,
  authority        VARCHAR                 ,
  proceeding_number VARCHAR                ,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX applications_idx ON applications (income_date, income_index);

create TABLE outgoing
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  sent_date        TIMESTAMP               NOT NULL,
  proceeding       VARCHAR                 NOT NULL,
  index            INTEGER   			   NOT NULL,
  correspondent    VARCHAR                 NOT NULL,
  description      VARCHAR                 ,
  executor_id      INTEGER   			   NOT NULL,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX outgoing_idx ON outgoing (sent_date, index);

create TABLE incoming
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  reg_date         TIMESTAMP               NOT NULL,
  index            INTEGER   			   NOT NULL,
  description      VARCHAR                 ,
  debtor           VARCHAR                 ,
  executor_id      INTEGER   			   NOT NULL,
  FOREIGN KEY (executor_id) REFERENCES executors (id)
);
CREATE UNIQUE INDEX incoming_idx ON incoming (reg_date, index);

create TABLE sent
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  sent_date         TIMESTAMP               NOT NULL,
  contact_id      INTEGER   			   NOT NULL,
  description      VARCHAR                 ,
  FOREIGN KEY (contact_id) REFERENCES contacts (id)
);

create TABLE inbox
(
  id            INTEGER     PRIMARY KEY DEFAULT nextval('global_seq'),
  index         INTEGER   	NOT NULL,
  contact_id    INTEGER   	NOT NULL,
  income_date   TIMESTAMP   NOT NULL,
  answer        BOOLEAN     NOT NULL,
  description   VARCHAR     ,
  FOREIGN KEY (contact_id) REFERENCES contacts (id)
);
CREATE UNIQUE INDEX inbox_unique_index_date ON inbox (index, income_date);

