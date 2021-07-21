
USE pharmacyportal;

truncate  `pioneer.person.patientrxnotifytype`;


INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (0,'Ask Patient?');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (1,'Do not notify');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (2,'Email');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (3,'Text');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (4,'Email, Text');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (5,'IVR (Phone only)');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (6,'Email, IVR');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (7,'IVR, Text');
INSERT INTO pharmacyportal.`pioneer.person.patientrxnotifytype`(rxNotifyTypeID,rxNotifyTypeText) VALUES (8,'Email, IVR, Text');
