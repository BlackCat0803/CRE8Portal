
USE pharmacyportal;

truncate  `pioneer.prescription.prescribeditemtype`;


INSERT INTO pharmacyportal.`pioneer.prescription.prescribeditemtype`(prescribedItemTypeID,prescribedItemTypeText) VALUES (1,'Drug Family');
INSERT INTO pharmacyportal.`pioneer.prescription.prescribeditemtype`(prescribedItemTypeID,prescribedItemTypeText) VALUES (2,'Specific Drug');
INSERT INTO pharmacyportal.`pioneer.prescription.prescribeditemtype`(prescribedItemTypeID,prescribedItemTypeText) VALUES (3,'Compound');
INSERT INTO pharmacyportal.`pioneer.prescription.prescribeditemtype`(prescribedItemTypeID,prescribedItemTypeText) VALUES (4,'IV');
