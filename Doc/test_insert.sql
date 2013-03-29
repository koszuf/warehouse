--produkty
INSERT INTO PRODUCT VALUES  ('Apple','Komputery',100,'Bar1',1);
INSERT INTO PRODUCT VALUES  ('Dell','Komputery',85,'Bar2',2);
INSERT INTO PRODUCT VALUES  ('Compaq','Komputery',60,'Bar3',3);
INSERT INTO PRODUCT VALUES  ('HP','Komputery',91,'Bar4',4);

--wydania
insert into shipment values ('Komputronik','1/200',now(),'Krystian','Robert Kowalski',1);
insert into shipment values ('ABC Data','2/200',now(),'Gerard','Jan Nowak',2);
--insert into shipment values ('KK Dev','3/200',now(),'Bolek','John Doe',4);

--linie wydań
insert into shipmentline values (1,100,1,1,1);
insert into shipmentline values (2,85,1,3,2);
insert into shipmentline values (3,60,2,2,3);
insert into shipmentline values (4,91,2,2,4);
--insert into shipmentline values (1,134,3,6,5);