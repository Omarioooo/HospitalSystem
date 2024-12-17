
SELECT 
    c.ClinicID,
    c.Name,
    dd.DepID AS depID,
    dep.DEPNAME AS depName
FROM 
    Clinic c
LEFT JOIN 
    Doc_Dep dd ON c.DocID = dd.DocID
LEFT JOIN 
    Department dep ON dd.DepID = dep.DepID;
