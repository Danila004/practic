CREATE TABLE Client (
                        client_id INT PRIMARY KEY,
                        full_name VARCHAR(255) NOT NULL,
                        phone VARCHAR(20) NOT NULL UNIQUE,
                        email VARCHAR(100) UNIQUE,
                        is_regular BOOLEAN DEFAULT FALSE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Instructor (
                            instructor_id INT PRIMARY KEY,
                            full_name VARCHAR(255) NOT NULL,
                            average_rating DECIMAL(3, 2) DEFAULT 0.00
);

CREATE TABLE Zone (
                      zone_id INT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      level VARCHAR(20) NOT NULL CHECK (level IN ('beginner', 'advanced')),
                      capacity INT NOT NULL CHECK (capacity IN (8, 16))
);

CREATE TABLE Equipment (
                           equipment_id INT PRIMARY KEY,
                           type VARCHAR(20) NOT NULL CHECK (type IN ('shoes', 'harness')),
                           rental_price DECIMAL(10, 2) NOT NULL,
                           total_count INT NOT NULL,
                           available_count INT NOT NULL
);

CREATE TABLE TrainingSlot (
                              slot_id INT PRIMARY KEY,
                              zone_id INT NOT NULL,
                              instructor_id INT NOT NULL,
                              start_datetime TIMESTAMP NOT NULL,
                              duration_minutes INT NOT NULL,
                              status VARCHAR(30) NOT NULL CHECK (status IN ('active', 'cancelled_by_gym', 'completed')),
                              cancellation_reason VARCHAR(255),
                              cancelled_at TIMESTAMP,
                              FOREIGN KEY (zone_id) REFERENCES Zone(zone_id),
                              FOREIGN KEY (instructor_id) REFERENCES Instructor(instructor_id)
);

CREATE TABLE Booking (
                         booking_id INT PRIMARY KEY,
                         client_id INT NOT NULL,
                         slot_id INT NOT NULL,
                         status VARCHAR(30) NOT NULL CHECK (status IN ('active', 'cancelled_by_client', 'cancelled_by_gym', 'completed')),
                         equipment_option VARCHAR(20) NOT NULL CHECK (equipment_option IN ('own', 'rental')),
                         is_late_cancellation BOOLEAN DEFAULT FALSE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         cancelled_at TIMESTAMP,
                         FOREIGN KEY (client_id) REFERENCES Client(client_id),
                         FOREIGN KEY (slot_id) REFERENCES TrainingSlot(slot_id)
);

CREATE TABLE RentalItem (
                            rental_id INT PRIMARY KEY,
                            booking_id INT NOT NULL,
                            equipment_id INT NOT NULL,
                            FOREIGN KEY (booking_id) REFERENCES Booking(booking_id),
                            FOREIGN KEY (equipment_id) REFERENCES Equipment(equipment_id)
);

CREATE TABLE InstructorRating (
                                  rating_id INT PRIMARY KEY,
                                  booking_id INT NOT NULL UNIQUE,
                                  instructor_id INT NOT NULL,
                                  client_id INT NOT NULL,
                                  stars INT NOT NULL CHECK (stars BETWEEN 1 AND 5),
                                  comment TEXT,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (booking_id) REFERENCES Booking(booking_id),
                                  FOREIGN KEY (instructor_id) REFERENCES Instructor(instructor_id),
                                  FOREIGN KEY (client_id) REFERENCES Client(client_id)
);

CREATE TABLE Notification (
                              notification_id INT PRIMARY KEY,
                              client_id INT NOT NULL,
                              booking_id INT,
                              type VARCHAR(20) NOT NULL CHECK (type IN ('cancellation', 'reminder')),
                              title VARCHAR(255) NOT NULL,
                              message TEXT NOT NULL,
                              delivery_status VARCHAR(20) NOT NULL CHECK (delivery_status IN ('sent', 'delivered', 'failed')),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (client_id) REFERENCES Client(client_id),
                              FOREIGN KEY (booking_id) REFERENCES Booking(booking_id)
);