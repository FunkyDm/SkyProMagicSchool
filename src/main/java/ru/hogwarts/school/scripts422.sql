CREATE TABLE Persons (
    PersonID SERIAL PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Age INT NOT NULL,
    HasLicense BOOLEAN NOT NULL
);

CREATE TABLE Cars (
    CarID SERIAL PRIMARY KEY,
    Brand VARCHAR(100) NOT NULL,
    Model VARCHAR(100) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE PersonCar (
    PersonID INT NOT NULL,
    CarID INT NOT NULL,
    PRIMARY KEY (PersonID, CarID),
    FOREIGN KEY (PersonID) REFERENCES Persons(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (CarID) REFERENCES Cars(CarID) ON DELETE CASCADE
);
