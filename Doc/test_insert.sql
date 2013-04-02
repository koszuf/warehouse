--produkty
INSERT INTO PRODUCT VALUES  ('Apple','Komputery',100,'Bar1',1);
INSERT INTO PRODUCT VALUES  ('Dell','Komputery',85,'Bar2',2);
INSERT INTO PRODUCT VALUES  ('Compaq','Komputery',60,'Bar3',3);
INSERT INTO PRODUCT VALUES  ('HP','Komputery',91,'Bar4',4);

--FIRMY

insert into company values ('Komputronik','',1);
insert into company values ('ABC Data','',2);
insert into company values ('K&K Development','',3);

--wydania
insert into shipment values ('Krystian','Robert Kowalski',1,'1/200',now(),1);
insert into shipment values ('Gerard','Jan Nowak',2,'2/200',now(),2);
insert into shipment values ('Bolek','John Doe',3,'3/200',now(),4);

--linie wydań
insert into shipmentline values (1,100,1,1,1);
insert into shipmentline values (2,85,1,3,2);
insert into shipmentline values (3,60,2,2,3);
insert into shipmentline values (4,91,2,2,4);
--insert into shipmentline values (1,134,3,6,5);