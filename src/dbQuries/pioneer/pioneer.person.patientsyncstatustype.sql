
USE pharmacyportal;

truncate  `pioneer.person.patientsyncstatustype`;


INSERT INTO pharmacyportal.`pioneer.person.patientsyncstatustype`(syncStatusTypeID,syncStatusTypeText) VALUES (0,'Not Enrolled');
INSERT INTO pharmacyportal.`pioneer.person.patientsyncstatustype`(syncStatusTypeID,syncStatusTypeText) VALUES (1,'Recommended');
INSERT INTO pharmacyportal.`pioneer.person.patientsyncstatustype`(syncStatusTypeID,syncStatusTypeText) VALUES (2,'Enrolled');
INSERT INTO pharmacyportal.`pioneer.person.patientsyncstatustype`(syncStatusTypeID,syncStatusTypeText) VALUES (3,'Declined');
INSERT INTO pharmacyportal.`pioneer.person.patientsyncstatustype`(syncStatusTypeID,syncStatusTypeText) VALUES (4,'Not Recommended');
