CREATE database billing;
\c billing;

CREATE TABLE Customer (
    CustomerID serial NOT NULL,
    Name text   NOT NULL,
    MSISDN int   NOT NULL,
    Rate_plan_id int   NOT NULL,
    End_date date   NOT NULL,
    SMS_FU int   NOT NULL,
    Voice_FU int   NOT NULL,
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
    CONSTRAINT pk_RatePlan PRIMARY KEY (
        PlanID
     )
);



CREATE TABLE Services (
    ServiceID serial   NOT NULL,
    Name varchar(50)   NOT NULL,
    CONSTRAINT pk_Services PRIMARY KEY (
        ServiceID
     )
);

CREATE TABLE Service_RatePlan (
    ID serial   NOT NULL,
    SID int   NOT NULL,
    RID int   NOT NULL,
    CONSTRAINT pk_Service_RatePlan PRIMARY KEY (
        ID
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

CREATE TABLE Customer_Fee (
    CID serial NOT NULL,
    FID int   NOT NULL,
    Billed boolean   NOT NULL
);

CREATE TABLE Rated_CDR (
    CDRID serial   NOT NULL,
    Billed boolean   NOT NULL,
    External_charge float   NOT NULL,
    Destination_no int,
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

ALTER TABLE Service_RatePlan ADD CONSTRAINT fk_Service_RatePlan_SID FOREIGN KEY(SID)
REFERENCES Services (ServiceID);

ALTER TABLE Service_RatePlan ADD CONSTRAINT fk_Service_RatePlan_RID FOREIGN KEY(RID)
REFERENCES RatePlan (PlanID);

ALTER TABLE Customer_Fee ADD CONSTRAINT fk_Customer_Fee_CID FOREIGN KEY(CID)
REFERENCES Customer (CustomerID);

ALTER TABLE Customer_Fee ADD CONSTRAINT fk_Customer_Fee_FID FOREIGN KEY(FID)
REFERENCES OneTimeFee (Fee_ID);

ALTER TABLE Rated_CDR ADD CONSTRAINT fk_Rated_CDR_CID FOREIGN KEY(CID)
REFERENCES Customer (CustomerID);

