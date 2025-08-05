 -- A stored Procedure for Adding patient into the room
CREATE PROCEDURE insertPatientIntoRoom
    @patientID INT, 
    @FirstName VARCHAR(50), 
    @SecondName VARCHAR(50),
    @ThirdName VARCHAR(50), 
    @Gender CHAR(1), 
    @Phone INT,
    @City VARCHAR(25),
    @RoomID INT, 
    @StayingTime INT
AS
BEGIN
    DECLARE @current_count INT;
    DECLARE @room_capacity INT;

    -- Get the current count of patients in the room
    SELECT @current_count = COUNT(*)
    FROM Patient_Room
    WHERE RoomID = @RoomID;

    -- Get the room's capacity
    SELECT @room_capacity = capacity
    FROM Room
    WHERE RoomID = @RoomID;

    -- Check if the room is full
    IF @current_count >= @room_capacity
    BEGIN
        SELECT 'Room is full, cannot insert patient.';
        RETURN; -- Exit the stored procedure without inserting
    END

	BEGIN TRANSACTION

	BEGIN TRY
		-- Insert into the Patient table
		INSERT INTO Patient (PatientID, FirstName, SecondName, ThirdName, Gender, Phone, City)
		VALUES (@patientID, @FirstName, @SecondName, @ThirdName, @Gender, @Phone, @City);

		-- Insert into the Patient_Room table
		INSERT INTO Patient_Room (PatientID, RoomID, StayingTime)
		VALUES (@patientID, @RoomID, @StayingTime);

		-- Check if room has become full after the insertion
		IF @current_count + 1 = @room_capacity
		BEGIN
			-- Update room availability to 'NOT AVAILABLE'
			UPDATE Room
			SET availability = 'NOT AVAILABLE'
			WHERE RoomID = @RoomID;
		END
        COMMIT TRANSACTION
   END TRY

   BEGIN CATCH
        ROLLBACK TRANSACTION
   END CATCH
END;
-----------------------------------------------------------------------------------------------------------------------
-- A stored procedure that display the departments data into the hospital
CREATE PROCEDURE select_Dep_info
AS
BEGIN
SELECT
    dep.depID,
    dep.depName,
    dep.MangerID,
	MangerName = doc.FirstName +' '+ doc.SecondName,
    (
        SELECT COUNT(Patient_Room.patientID)
        FROM Patient_Room
        WHERE Patient_Room.RoomID IN (
            SELECT RoomID
            FROM Room
            WHERE Room.depID = dep.depID
        )
    ) AS num_Of_Patients -- select the number of patients on each department depending on the total patient into each room in the department
FROM
    Department dep
JOIN
    Doctor doc
ON
   dep.MangerID = doc.DocID;
END;
-------------------------------------------------------------------------------------
-- A stored procedure that display the rooms data into the hospital
CREATE PROCEDURE room_info
AS
BEGIN
SELECT
    r.RoomID,
    dep.DepName,
    r.Type,
    COUNT(pr.PatientID) AS PatientCount,
    r.Capacity,
    r.Availability
FROM
    Room r
JOIN
    Department dep
ON
    r.DepID = dep.DepID
LEFT JOIN
    Patient_Room pr
ON
    r.RoomID = pr.RoomID
GROUP BY
    r.RoomID, dep.DepName, r.Type, r.Capacity, r.Availability;
END;
----------------------------------------------------------------------
-- A stored procedure that display the clinics data into the hospital
CREATE PROCEDURE clinic_info
AS
BEGIN
SELECT
    c.ClinicID,
	c.Name 'Clinic',
	dep.DepName 'Department',
	doc.FirstName+' '+doc.SecondName 'Doctor'
FROM
   Clinic c
LEFT JOIN
   Doctor doc
ON
   c.DocID = doc.DocID
LEFT JOIN
   Doc_Dep dd
ON
   doc.DocID = dd.DocID
LEFT JOIN
   Department dep
ON
   dep.DepID = dd.DepID
END;
------------------------------------------------------------------------------------
-- A stored procedure that display the nurses data into the hospital
CREATE PROCEDURE nurse_info
AS
BEGIN
SELECT
   Name = n.FirstName+' '+ n.SecondName,
   rn.RoomID,
   dep.DepName AS 'Department',
   Manager = doc.FirstName+' '+doc.SecondName
FROM
  Nurse n
RIGHT JOIN
  Room_Nurse rn
ON
  n.NurseID = rn.NurseID
JOIN
  Room r
ON
  rn.RoomID = r.RoomID
JOIN
  Department dep
ON
  dep.DepID = r.DepID
JOIN
  Doctor doc
ON
  dep.MangerID = doc.DocID
ORDER BY Department
END;
----------------------------------------------------------------
-- A stored procedure the book an appointment and add a clinic to the patient
CREATE PROCEDURE bookAppointment
    @patientID INT,
    @FirstName VARCHAR(50),
    @SecondName VARCHAR(50),
    @ThirdName VARCHAR(50),
    @Phone INT,
    @City VARCHAR(25),
	@Gender CHAR(1),
    @Clinic_name VARCHAR(50),
    @Appointment_ID INT,
	@cost INT,
	@status VARCHAR(50)
AS
BEGIN
  DECLARE @clinicID INT
  DECLARE @docID INT

  BEGIN TRANSACTION

  BEGIN TRY
       -- Get the needed info from the clinic name
      SELECT
          @docID = DocID,
          @clinicID = ClinicID
      FROM
          Clinic
      WHERE
          Name = @Clinic_name

       -- Insert data in the tables related to the booking the appointment
      INSERT INTO Patient (PatientID, FirstName, SecondName, ThirdName, Gender, Phone, City) VALUES
       (@patientID, @FirstName, @SecondName, @ThirdName, @Gender, @Phone, @City);


      INSERT INTO Appointment VALUES
      (@Appointment_ID, @status, @clinicID)

      INSERT INTO Appointment_Patient_Doc(AppoID, patientID, DocID, Cost, Clinic_ID) VALUES
      (@Appointment_ID, @patientID, @docID, @cost, @ClinicID)
      COMMIT TRANSACTION;
  END TRY

  BEGIN CATCH
      ROLLBACK TRANSACTION
  END CATCH
END;
-------------------------------------------------------------------------------------------------------------------
-- A stored procedure decide where to search for the patient (Room or Clinic)
CREATE PROCEDURE search_patients
    @PatientID INT,
    @ResultMessage VARCHAR(100) OUTPUT,
    @PlaceFound VARCHAR(25) OUTPUT
AS
BEGIN
    -- Check if the patient exists
    IF NOT EXISTS (SELECT 1 FROM Patient WHERE PatientID = @PatientID)
    BEGIN
        SET @ResultMessage = 'The patient not found';
        RETURN;
    END

    -- Search if the patient is in any room
    IF EXISTS (SELECT 1 FROM Patient_Room WHERE PatientID = @PatientID)
    BEGIN
        SET @PlaceFound = 'Room';
    END
    ELSE
    BEGIN
        SET @PlaceFound = 'Clinic';
    END

    -- patient found successfully
    SET @ResultMessage = 'The patient is found';
END;
<------------------------------------->  <------------------------------------------>  <------------------------------------->
-- A stored procedure determines the logic of searching into rooms
CREATE PROCEDURE search_on_rooms
    @PatientID INT,
    @FirstName VARCHAR(50) OUTPUT,
    @SecondName VARCHAR(50) OUTPUT,
    @ThirdName VARCHAR(50) OUTPUT,
    @Phone INT OUTPUT,
    @City VARCHAR(25) OUTPUT,
    @Gender CHAR(1) OUTPUT,
    @Department VARCHAR(50) OUTPUT,
    @RoomID INT OUTPUT,
    @StartingDate DATE OUTPUT,
    @RemainingTime INT OUTPUT
AS
BEGIN
    DECLARE @stayingTime INT;

    BEGIN TRANSACTION;
    BEGIN TRY
        -- Select patient data
        SELECT
            @FirstName = FirstName,
            @SecondName = SecondName,
            @ThirdName = ThirdName,
            @City = City,
            @Phone = Phone,
            @Gender = Gender
        FROM Patient
        WHERE PatientID = @PatientID;

        -- Select the room data
        SELECT
            @RoomID = RoomID,
            @StartingDate = StartingDate,
            @stayingTime = StayingTime
        FROM Patient_Room
        WHERE PatientID = @PatientID;

        -- Select the department name
        SELECT
            @Department = dep.DepName
        FROM Department dep
        JOIN Room r ON dep.DepID = r.DepID
        WHERE r.RoomID = @RoomID;

        -- Calculate the remaining time
        SELECT
            @RemainingTime = @stayingTime - DATEDIFF(DAY, @StartingDate, CAST(GETDATE() AS DATE));

        -- Commit if everything found
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Rollback if any error occurs
        ROLLBACK TRANSACTION;
        RETURN;
    END CATCH;
END;
<------------------------------------->  <------------------------------------------>  <------------------------------------->
-- A stored procedure determines the logic of searching into clinics
CREATE PROCEDURE search_on_clinics
    @PatientID INT,
    @FirstName VARCHAR(50) OUTPUT,
    @SecondName VARCHAR(50) OUTPUT,
    @ThirdName VARCHAR(50) OUTPUT,
    @Phone INT OUTPUT,
    @City VARCHAR(25) OUTPUT,
    @Gender CHAR(1) OUTPUT,
    @Department VARCHAR(50) OUTPUT,
    @Clinic VARCHAR(25) OUTPUT,
    @AppointmentID INT OUTPUT,
    @StartingDate DATE OUTPUT
AS
BEGIN
   DECLARE @ClinicID INT

   BEGIN TRANSACTION;
   BEGIN TRY
		-- Select patient data
		SELECT
			@FirstName = FirstName,
			@SecondName = SecondName,
			@ThirdName = ThirdName,
			@City = City,
			@Phone = Phone,
			@Gender = Gender
		FROM Patient
		WHERE PatientID = @PatientID;

		-- Select the appointment data
		SELECT
			@AppointmentID = a.AppoID,
			@Clinic = c.Name,
			@Department = dep.DepName,
			@StartingDate = a.Date
		FROM
		    Appointment_Patient_Doc a
		JOIN
		    Clinic c
		ON
		   c.ClinicID = a.Clinic_ID
        JOIN
		   Doc_Dep dd
        ON
		   a.DocID = dd.DepID
        JOIN
		   Department dep
        ON
           dd.DepID = dep.DepID
		WHERE
		    PatientID = @PatientID;

		  -- commit if every thing successes
		COMMIT TRANSACTION;
   END TRY
   BEGIN CATCH
        ROLLBACK TRANSACTION;
		RETURN;
   END CATCH
END;
-------------------------------------------------------------------------------------------------------------------------
-- A stored procedure for updating the patient data with ignoring the place
CREATE PROCEDURE update_patient_data
    @PatientID INT,
	@FirstName VARCHAR(50),
	@SecondName VARCHAR(50),
	@ThirdName VARCHAR(50),
	@Phone INT,
	@City VARCHAR(25),
	@Gender CHAR(1),
	@ResultMessage VARCHAR(100) OUTPUT
AS
BEGIN
    -- Check if the patient exists before deleting
    IF EXISTS (SELECT 1 FROM Patient WHERE patientID = @patientID)
    BEGIN
    UPDATE
	   Patient
	SET
	   FirstName = @FirstName,
	   SecondName = @SecondName,
	   ThirdName = @ThirdName,
	   Gender = @Gender,
	   Phone = @Phone,
	   City = @City
	WHERE
	   patientID = @PatientID

        -- success message
        SET @ResultMessage = 'Patient data updated successfully.';
    END
    ELSE
    BEGIN
        -- failure message
        SET @ResultMessage = 'Patient not found.';
    END
END;
-------------------------------------------------------------------------------------------------------------------------------
-- A stored procedure to delete the patient
CREATE PROCEDURE delete_patient
    @patientID INT,
    @ResultMessage VARCHAR(100) OUTPUT
AS
BEGIN
    -- Check if the patient exists before deleting
    IF EXISTS (SELECT 1 FROM Patient WHERE patientID = @patientID)
    BEGIN
        -- Declare needed variables
        DECLARE @current_count INT;
        DECLARE @room_capacity INT;
        DECLARE @RoomID INT;

        -- Delete related records
        -- Delete from room if the patient is assigned to a room
        IF EXISTS (SELECT 1 FROM Patient_Room WHERE patientID = @patientID)
        BEGIN
            -- Get the room id
            SELECT @RoomID = roomID
            FROM Patient_Room
            WHERE patientID = @patientID;

            -- Get the current count of patients in the room
            SELECT @current_count = COUNT(*)
            FROM Patient_Room
            WHERE RoomID = @RoomID;

            -- Get the room's capacity
            SELECT @room_capacity = capacity
            FROM Room
            WHERE RoomID = @RoomID;

            -- If the current count is less than the room's capacity, make the room available
            IF @current_count < @room_capacity
            BEGIN
                -- Make the room available
                UPDATE Room
                SET Availability = 'AVAILABLE'
                WHERE RoomID = @RoomID;
            END

            -- Remove the patient from the room
            DELETE FROM Patient_Room WHERE patientID = @patientID;
        END
        ELSE
        BEGIN
            -- Delete the appointment if no room is assigned to the patient
            DELETE FROM Appointment_Patient_Doc WHERE patientID = @patientID;
        END

        -- Delete the patient
        DELETE FROM Patient WHERE patientID = @patientID;

        -- Success message
        SET @ResultMessage = 'Patient and related records deleted successfully.';
    END
    ELSE
    BEGIN
        -- Failure message
        SET @ResultMessage = 'Patient not found.';
    END
END;
-----------------------------------------------------------------------------------------------------
CREATE PROCEDURE get_all_patients
AS
BEGIN
    SELECT 
        p.PatientID, 
        p.FirstName, 
        p.SecondName,
        
        -- Department name (if found through Room → Department)
        d.DepName,
        
        -- State: Room or Clinic
        CASE 
            WHEN pr.PatientID IS NOT NULL THEN 'Room'
            ELSE 'Clinic'
        END AS [State]

    FROM Patient p
    LEFT JOIN Patient_Room pr ON p.PatientID = pr.PatientID
    LEFT JOIN Room r ON pr.RoomID = r.RoomID
    LEFT JOIN Department d ON r.DepID = d.DepID
END;
------------------------------------------------------------------------------------------------
CREATE PROCEDURE login_check
   @Name VARCHAR(50),
   @ID INT,
   @Message VARCHAR(20) OUTPUT
AS
BEGIN
    SET @Message = 'Field';

    IF EXISTS (
        SELECT 1
        FROM Employee
        WHERE empID = @ID
          AND (ISNULL(FirstName, '') + ' ' + ISNULL(SecondName, '')) = @Name
          AND Emp_Rule LIKE '%Officer'
    )
    BEGIN
        SET @Message = 'Ok';
    END
END
