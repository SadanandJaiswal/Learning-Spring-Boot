--CREATE ROLE postgrest_user WITH LOGIN PASSWORD 'your_password';
--GRANT USAGE ON SCHEMA public TO postgrest_user;
--GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO postgrest_user;


ALTER TABLE patient
ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;

-- ========================
-- Insert Doctors
-- ========================
INSERT INTO doctor (name, specialization, email, created_at)
VALUES
('Dr. Asha Mehta', 'Cardiology', 'asha.mehta@hospital.in', CURRENT_TIMESTAMP),
('Dr. Ravi Kumar', 'Orthopedics', 'ravi.kumar@hospital.in', CURRENT_TIMESTAMP),
('Dr. Priya Sharma', 'Pediatrics', 'priya.sharma@hospital.in', CURRENT_TIMESTAMP);

-- ========================
-- Insert Departments
-- ========================
-- We'll temporarily insert departments without head_doctor_id, then update after knowing the doctor IDs
INSERT INTO department (name)
VALUES
('Cardiology'),
('Orthopedics'),
('Pediatrics');

-- ========================
-- Insert Insurance
-- ========================
INSERT INTO insurance (policy_number, provider, valid_until, created_at)
VALUES
('POL12345IN', 'LIC India', '2026-12-31', CURRENT_TIMESTAMP),
('POL54321IN', 'Star Health', '2025-11-30', CURRENT_TIMESTAMP),
('POL67890IN', 'Tata AIG', '2027-01-15', CURRENT_TIMESTAMP);

-- ========================
-- Insert Patients
-- Match order with insurance table (1st insurance to 1st patient)
INSERT INTO patient (name, birth_date, email, gender, created_at, blood_group, patient_insurance_id)
VALUES
('Amit Patel', '1990-05-20', 'amit.patel@example.com', 'Male', CURRENT_TIMESTAMP, 'B_POSITIVE', 1),
('Sneha Reddy', '1985-08-15', 'sneha.reddy@example.com', 'Female', CURRENT_TIMESTAMP, 'A_NEGATIVE', 2),
('Rajeev Menon', '1978-03-11', 'rajeev.menon@example.com', 'Male', CURRENT_TIMESTAMP, 'O_POSITIVE', 3);

-- ========================
-- Insert Patients without Insurance
-- ========================
INSERT INTO patient (name, birth_date, email, gender, created_at, blood_group)
VALUES
('Meena Joshi', '1992-04-12', 'meena.joshi@example.com', 'Female', CURRENT_TIMESTAMP, 'A_POSITIVE'),
('Karan Thakur', '1988-09-22', 'karan.thakur@example.com', 'Male', CURRENT_TIMESTAMP, 'O_NEGATIVE'),
('Divya Nair', '1995-01-30', 'divya.nair@example.com', 'Female', CURRENT_TIMESTAMP, 'AB_POSITIVE'),
('Anil Deshmukh', '1975-12-05', 'anil.deshmukh@example.com', 'Male', CURRENT_TIMESTAMP, 'B_NEGATIVE'),
('Ritika Verma', '2000-06-17', 'ritika.verma@example.com', 'Female', CURRENT_TIMESTAMP, 'O_POSITIVE');


-- ========================
-- Insert Appointments
-- Assume patients and doctors were inserted in the order as listed, so use their auto-generated IDs
INSERT INTO appointment (appointment_time, reason, status, patient_id, doctor_id)
VALUES
('2025-10-17T10:00:00', 'Routine heart checkup', 'Scheduled', 1, 1),
('2025-10-18T11:30:00', 'Knee pain consultation', 'Completed', 2, 2),
('2025-10-19T09:00:00', 'Child fever', 'Scheduled', 3, 3),
('2025-10-20T14:00:00', 'Follow-up for blood pressure', 'Cancelled', 1, 1);

-- ========================
-- Map Doctors to Departments (department_doctors)
-- Assuming departments: 1=Cardiology, 2=Orthopedics, 3=Pediatrics
INSERT INTO department_doctors (department_id, doctor_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(1, 2); -- Doctor 2 also in Cardiology

-- ========================
-- Update Departments with headDoctor
-- Again, assuming IDs were generated in same order
UPDATE department SET head_doctor_id = 1 WHERE name = 'Cardiology';
UPDATE department SET head_doctor_id = 2 WHERE name = 'Orthopedics';
UPDATE department SET head_doctor_id = 3 WHERE name = 'Pediatrics';


--INSERT INTO patient (name, birth_date, email, gender, blood_group) VALUES
--('Rahul Sharma', '1992-05-14', 'rahul.sharma@example.com', 'Male', 'B_POSITIVE'),
--('Priya Verma', '1988-09-20', 'priya.verma@example.com', 'Female', 'A_POSITIVE'),
--('Amit Patel', '1990-12-01', 'amit.patel@example.com', 'Male', 'O_POSITIVE'),
--('Sneha Iyer', '1995-03-07', 'sneha.iyer@example.com', 'Female', 'AB_POSITIVE'),
--('Karan Mehta', '2000-06-30', 'karan.mehta@example.com', 'Male', 'B_NEGATIVE'),
--('Anjali Gupta', '1985-08-18', 'anjali.gupta@example.com', 'Female', 'A_NEGATIVE'),
--('Rohit Reddy', '1993-01-10', 'rohit.reddy@example.com', 'Male', 'O_NEGATIVE'),
--('Neha Singh', '1996-11-22', 'neha.singh@example.com', 'Female', 'B_POSITIVE'),
--('Vikram Joshi', '1989-04-25', 'vikram.joshi@example.com', 'Male', 'A_POSITIVE'),
--('Pooja Deshmukh', '1991-07-15', 'pooja.deshmukh@example.com', 'Female', 'O_POSITIVE'),
--('Arjun Nair', '1997-02-14', 'arjun.nair@example.com', 'Male', 'AB_NEGATIVE'),
--('Meera Das', '1994-10-30', 'meera.das@example.com', 'Female', 'A_POSITIVE'),
--('Siddharth Malhotra', '1999-03-21', 'siddharth.malhotra@example.com', 'Male', 'B_POSITIVE'),
--('Isha Roy', '2001-01-19', 'isha.roy@example.com', 'Female', 'O_POSITIVE'),
--('Tarun Bansal', '1986-12-11', 'tarun.bansal@example.com', 'Male', 'AB_POSITIVE')
--ON CONFLICT (email) DO NOTHING;
