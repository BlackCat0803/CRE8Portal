
USE pharmacyportal;

truncate  `pioneer.prescription.origintype`;


INSERT INTO pharmacyportal.`pioneer.prescription.origintype`(origintypeid,origintypetext,ncpdpcode) VALUES (1,'Walk-In',1);
INSERT INTO pharmacyportal.`pioneer.prescription.origintype`(origintypeid,origintypetext,ncpdpcode) VALUES (2,'Phone-In',2);
INSERT INTO pharmacyportal.`pioneer.prescription.origintype`(origintypeid,origintypetext,ncpdpcode) VALUES (3,'Fax',4);
INSERT INTO pharmacyportal.`pioneer.prescription.origintype`(origintypeid,origintypetext,ncpdpcode) VALUES (4,'Transfer-In',5);
INSERT INTO pharmacyportal.`pioneer.prescription.origintype`(origintypeid,origintypetext,ncpdpcode) VALUES (5,'Electronic',3);
INSERT INTO pharmacyportal.`pioneer.prescription.origintype`(origintypeid,origintypetext,ncpdpcode) VALUES (6,'Other - Pharmacy',5);
