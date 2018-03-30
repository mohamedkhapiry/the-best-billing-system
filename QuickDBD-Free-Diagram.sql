-- Exported from QuickDBD: https://www.quickdatatabasediagrams.com/
-- Link to schema: https://app.quickdatabasediagrams.com/#/schema/gt39LoM250yX4lupsA8HOA
-- NOTE! If you have used non-SQL datatypes in your design, you will have to change these here.

-- Modify the code to update the DB schema diagram.
-- To reset the sample schema, replace everything with
-- two dots ('..' - without quotes).

CREATE TABLE "Customer" (
    "id" serial  NOT NULL ,
    "uname" string  NOT NULL ,
    "fname" string  NOT NULL ,
    "lname" string  NOT NULL ,
    "passwd" string  NOT NULL ,
    "birthday" string  NOT NULL ,
    "e-mail" string  NOT NULL ,
    "job" string  NOT NULL ,
    "address" string  NOT NULL ,
    "interests" string  NOT NULL ,
    CONSTRAINT "pk_Customer" PRIMARY KEY (
        "id"
    )
)

GO

CREATE TABLE "Order" (
    "OrderID" serial  NOT NULL ,
    "CustomerID" int  NOT NULL ,
    "TotalAmount" money  NOT NULL ,
    CONSTRAINT "pk_Order" PRIMARY KEY (
        "OrderID"
    )
)

GO

CREATE TABLE "cart" (
    "cartID" serial  NOT NULL ,
    "OrderID" int  NOT NULL ,
    "ProductID" int  NOT NULL ,
    "Quantity" int  NOT NULL ,
    CONSTRAINT "pk_cart" PRIMARY KEY (
        "cartID"
    )
)

GO

-- Table documentation comment 1 (try the PDF/RTF export)
-- Table documentation comment 2
CREATE TABLE "Product" (
    "ProductID" serial  NOT NULL ,
    -- Field documentation comment 1
    -- Field documentation comment 2
    -- Field documentation comment 3
    "Name" varchar(200)  NOT NULL ,
    "Price" money  NOT NULL ,
    CONSTRAINT "pk_Product" PRIMARY KEY (
        "ProductID"
    )
)

GO

ALTER TABLE "Order" ADD CONSTRAINT "fk_Order_CustomerID" FOREIGN KEY("CustomerID")
REFERENCES "Customer" ("id")
GO

ALTER TABLE "cart" ADD CONSTRAINT "fk_cart_OrderID" FOREIGN KEY("OrderID")
REFERENCES "Order" ("OrderID")
GO

ALTER TABLE "cart" ADD CONSTRAINT "fk_cart_ProductID" FOREIGN KEY("ProductID")
REFERENCES "Product" ("ProductID")
GO

