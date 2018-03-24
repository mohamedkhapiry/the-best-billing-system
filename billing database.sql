CREATE database billing;
\c billing;

CREATE TABLE Customer (
    CustomerID serial NOT NULL,
    Name text   NOT NULL,
    MSISDN bigint   NOT NULL,
    Rate_plan_id int   NOT NULL,
    End_date date   NOT NULL,
    CONSTRAINT pk_Customer PRIMARY KEY (
        CustomerID
     ),
    CONSTRAINT uc_Customer_MSISDN UNIQUE (
        MSISDN
    )
);

CREATE TABLE RatePlan (
    PlanID serial NOT NULL,
    name text   NOT NULL,
    Description text   NOT NULL,
    Recurring float   NOT NULL,
    sms_fu int NOT NULL,
    voice_fu int NOT NULL,
    sms_fu_other int NOT NULL,
    voice_fu_other int NOT NULL,
    sms_unit_price int NOT NULL,
    voice_unit_price int NOT NULL,
    CONSTRAINT pk_RatePlan PRIMARY KEY (
        PlanID
     )
);


CREATE TABLE OneTimeFee (
    Fee_ID serial   NOT NULL,
    name text   NOT NULL,
    price float   NOT NULL,
    CONSTRAINT pk_OneTimeFee PRIMARY KEY (
        Fee_ID
     )
);


CREATE TABLE Rated_CDR (
    CDRID serial   NOT NULL,
    Billed boolean   NOT NULL,
    External_charge float   NOT NULL,
    Destination_no bigint   ,
    Destination_url text,
    used_units int   NOT NULL,
    serviceID int   NOT NULL,
    Start_Time time   NOT NULL,
    CID int   NOT NULL,
    CONSTRAINT pk_Rated_CDR PRIMARY KEY (
        CDRID
     )
);

ALTER TABLE Customer ADD CONSTRAINT fk_Customer_Rate_plan_id FOREIGN KEY(Rate_plan_id)
REFERENCES RatePlan (PlanID);

ALTER TABLE Rated_CDR ADD CONSTRAINT fk_Rated_CDR_CID FOREIGN KEY(CID)
REFERENCES Customer (CustomerID);



