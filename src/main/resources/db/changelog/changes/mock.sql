INSERT INTO Client (client_id, full_name, phone, email, is_regular, created_at) VALUES
                                                                                    (1, 'Иванов Иван Иванович', '+79001234567', 'ivanov@example.com', TRUE, '2025-11-10 10:00:00'),
                                                                                    (2, 'Петрова Мария Сергеевна', '+79007654321', 'petrova.m@example.com', FALSE, '2026-02-15 14:30:00'),
                                                                                    (3, 'Сидоров Алексей Петрович', '+79112223344', 'sidorov.a@example.com', TRUE, '2026-05-20 09:15:00'),
                                                                                    (4, 'Волкова Елена Дмитриевна', '+79223334455', 'volkova.e@example.com', FALSE, '2026-06-30 18:00:00');

-- Инструкторы
INSERT INTO Instructor (instructor_id, full_name, average_rating) VALUES
                                                                      (1, 'Смирнов А.В.', 4.85),
                                                                      (2, 'Кузнецов Д.И.', 4.92),
                                                                      (3, 'Лебедева О.С.', 4.60);

-- Зоны
INSERT INTO Zone (zone_id, name, level, capacity) VALUES
                                                      (1, 'Болдеринг (Новички)', 'beginner', 8),
                                                      (2, 'Трудность (Опытные)', 'advanced', 16),
                                                      (3, 'Болдеринг (Опытные)', 'advanced', 16);

INSERT INTO Equipment (equipment_id, type, rental_price, total_count, available_count) VALUES
                                                                                           (1, 'shoes', 350.00, 30, 25),
                                                                                           (2, 'harness', 200.00, 20, 18),
                                                                                           (3, 'shoes', 400.00, 15, 15), -- Детские скальники
                                                                                           (4, 'harness', 250.00, 10, 9);

-- Слоты тренировок (Прошлые, текущие и будущие)
INSERT INTO TrainingSlot (slot_id, zone_id, instructor_id, start_datetime, duration_minutes, status, cancellation_reason, cancelled_at) VALUES
-- Завершенные (для оценок)
(1, 1, 3, '2026-07-01 18:00:00', 90, 'completed', NULL, NULL),
(2, 2, 1, '2026-07-02 19:00:00', 120, 'completed', NULL, NULL),
-- Отмененный скалодромом
(3, 1, 3, '2026-07-03 18:00:00', 90, 'cancelled_by_gym', 'Прорыв трубы в зале болдеринга', '2026-07-03 10:00:00'),
-- Активные (будущие)
(4, 2, 2, '2026-07-06 20:00:00', 120, 'active', NULL, NULL),
(5, 1, 3, '2026-07-07 18:00:00', 90, 'active', NULL, NULL);

INSERT INTO Booking (booking_id, client_id, slot_id, status, equipment_option, is_late_cancellation, created_at, cancelled_at) VALUES
-- Завершенные
(1, 1, 1, 'completed', 'rental', FALSE, '2026-06-28 10:00:00', NULL),
(2, 2, 2, 'completed', 'own', FALSE, '2026-06-29 15:00:00', NULL),
-- Отмененные
(3, 3, 3, 'cancelled_by_gym', 'rental', FALSE, '2026-07-01 12:00:00', '2026-07-03 10:00:00'),
(4, 4, 1, 'cancelled_by_client', 'own', TRUE, '2026-06-25 09:00:00', '2026-07-01 17:55:00'), -- Поздняя отмена (< 10 мин до начала)
-- Активные
(5, 1, 4, 'active', 'rental', FALSE, '2026-07-04 11:00:00', NULL),
(6, 3, 5, 'active', 'own', FALSE, '2026-07-04 14:20:00', NULL);

-- Выданное снаряжение (только для броней со статусом 'rental')
INSERT INTO RentalItem (rental_id, booking_id, equipment_id) VALUES
                                                                 (1, 1, 1), -- Иванов взял скальники на 1-ю тренировку
                                                                 (2, 1, 2), -- Иванов взял страховку на 1-ю тренировку
                                                                 (3, 3, 1), -- Сидоров должен был взять скальники (отменено gym, но факт записи был)
                                                                 (4, 5, 1); -- Иванов взял скальники на будущую тренировку

-- Оценки инструкторов (только для завершенных броней)
INSERT INTO InstructorRating (rating_id, booking_id, instructor_id, client_id, stars, comment, created_at) VALUES
                                                                                                               (1, 1, 3, 1, 5, 'Отличное введение в болдеринг, всё понятно объяснили!', '2026-07-01 20:30:00'),
                                                                                                               (2, 2, 1, 2, 5, 'Супер тренировка на трудность, трасса была интересной.', '2026-07-02 21:45:00');

INSERT INTO Notification (notification_id, client_id, booking_id, type, title, message, delivery_status, created_at) VALUES
                                                                                                                         (1, 3, 3, 'cancellation', 'Тренировка отменена', 'К сожалению, тренировка 03.07 отменена из-за прорыва трубы. Мы вернули деньги.', 'delivered', '2026-07-03 10:05:00'),
                                                                                                                         (2, 4, 1, 'cancellation', 'Поздняя отмена', 'Вы отменили запись менее чем за 10 минут. Согласно правилам, списан 1 штрафной балл.', 'delivered', '2026-07-01 17:56:00'),
                                                                                                                         (3, 1, 5, 'reminder', 'Напоминание о тренировке', 'Ждем вас завтра, 06.07 в 20:00 на зоне "Трудность". Не забудьте сменную обувь!', 'sent', '2026-07-05 09:00:00');