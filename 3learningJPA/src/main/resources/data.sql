ALTER TABLE patient
ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;

INSERT INTO patient (name, birth_date, email, gender, blood_group) VALUES
('Rahul Sharma', '1992-05-14', 'rahul.sharma@example.com', 'Male', 'B_POSITIVE'),
('Priya Verma', '1988-09-20', 'priya.verma@example.com', 'Female', 'A_POSITIVE'),
('Amit Patel', '1990-12-01', 'amit.patel@example.com', 'Male', 'O_POSITIVE'),
('Sneha Iyer', '1995-03-07', 'sneha.iyer@example.com', 'Female', 'AB_POSITIVE'),
('Karan Mehta', '2000-06-30', 'karan.mehta@example.com', 'Male', 'B_NEGATIVE'),
('Anjali Gupta', '1985-08-18', 'anjali.gupta@example.com', 'Female', 'A_NEGATIVE'),
('Rohit Reddy', '1993-01-10', 'rohit.reddy@example.com', 'Male', 'O_NEGATIVE'),
('Neha Singh', '1996-11-22', 'neha.singh@example.com', 'Female', 'B_POSITIVE'),
('Vikram Joshi', '1989-04-25', 'vikram.joshi@example.com', 'Male', 'A_POSITIVE'),
('Pooja Deshmukh', '1991-07-15', 'pooja.deshmukh@example.com', 'Female', 'O_POSITIVE'),
('Arjun Nair', '1997-02-14', 'arjun.nair@example.com', 'Male', 'AB_NEGATIVE'),
('Meera Das', '1994-10-30', 'meera.das@example.com', 'Female', 'A_POSITIVE'),
('Siddharth Malhotra', '1999-03-21', 'siddharth.malhotra@example.com', 'Male', 'B_POSITIVE'),
('Isha Roy', '2001-01-19', 'isha.roy@example.com', 'Female', 'O_POSITIVE'),
('Tarun Bansal', '1986-12-11', 'tarun.bansal@example.com', 'Male', 'AB_POSITIVE')
ON CONFLICT (email) DO NOTHING;
