# ER-модель системы записи на тренировки скалодрома

## 1. Сущности и атрибуты

### Client (Клиент)
| Атрибут | Тип | Описание |
|---|---|---|
| `client_id` | int, PK | Уникальный идентификатор клиента |
| `full_name` | string | ФИО клиента |
| `phone` | string | Телефон (для авторизации и push) |
| `email` | string | Email |
| `is_regular` | boolean | Признак постоянного клиента (BR-09) |
| `created_at` | datetime | Дата регистрации |

### Instructor (Инструктор)
| Атрибут | Тип | Описание |
|---|---|---|
| `instructor_id` | int, PK | Уникальный идентификатор |
| `full_name` | string | ФИО инструктора |
| `average_rating` | decimal | Средний рейтинг (вычисляемое) |

### Zone (Зона / Формат тренировки)
| Атрибут | Тип | Описание |
|---|---|---|
| `zone_id` | int, PK | Уникальный идентификатор зоны |
| `name` | string | Название формата (например, «Болдеринг», «Трудность») |
| `level` | enum | `beginner` / `advanced` (новичок / опытный) |
| `capacity` | int | Лимит группы: 8 для новичков, 16 для опытных (BR-04) |

### TrainingSlot (Слот тренировки)
| Атрибут | Тип | Описание |
|---|---|---|
| `slot_id` | int, PK | Уникальный идентификатор слота |
| `zone_id` | int, FK | Зона / формат |
| `instructor_id` | int, FK | Инструктор |
| `start_datetime` | datetime | Дата и время начала |
| `duration_minutes` | int | Длительность |
| `status` | enum | `active`, `cancelled_by_gym`, `completed` |
| `cancellation_reason` | string | Причина отмены скалодромом (FR-13) |
| `cancelled_at` | datetime | Дата отмены |

### Booking (Бронь / Запись клиента)
| Атрибут | Тип | Описание |
|---|---|---|
| `booking_id` | int, PK | Уникальный идентификатор брони |
| `client_id` | int, FK | Клиент |
| `slot_id` | int, FK | Слот |
| `status` | enum | `active`, `cancelled_by_client`, `cancelled_by_gym`, `completed` |
| `equipment_option` | enum | `own`, `rental` (FR-05) |
| `is_late_cancellation` | boolean | Факт поздней отмены (< 10 мин, UC-03) |
| `created_at` | datetime | Дата создания брони |
| `cancelled_at` | datetime | Дата отмены |

### Equipment (Снаряжение — прокатный фонд)
| Атрибут | Тип | Описание |
|---|---|---|
| `equipment_id` | int, PK | Уникальный идентификатор |
| `type` | enum | `shoes` (скальники), `harness` (страховка) |
| `rental_price` | decimal | Стоимость проката (FR-06, R-015) |
| `total_count` | int | Общее количество единиц |
| `available_count` | int | Свободно сейчас (FR-07) |

### RentalItem (Выданное в прокат снаряжение по брони)
| Атрибут | Тип | Описание |
|---|---|---|
| `rental_id` | int, PK | Уникальный идентификатор |
| `booking_id` | int, FK | Бронь |
| `equipment_id` | int, FK | Снаряжение |

### InstructorRating (Оценка инструктора)
| Атрибут | Тип | Описание |
|---|---|---|
| `rating_id` | int, PK | Уникальный идентификатор |
| `booking_id` | int, FK, UQ | Бронь (оценка привязана к завершённой тренировке) |
| `instructor_id` | int, FK | Инструктор |
| `client_id` | int, FK | Клиент |
| `stars` | int | Оценка 1–5 |
| `comment` | text | Текстовый комментарий (опц.) |
| `created_at` | datetime | Дата оценки |

### Notification (Push-уведомление)
| Атрибут | Тип | Описание |
|---|---|---|
| `notification_id` | int, PK | Уникальный идентификатор |
| `client_id` | int, FK | Получатель |
| `booking_id` | int, FK, nullable | Связанная бронь |
| `type` | enum | `cancellation`, `reminder` |
| `title` | string | Заголовок |
| `message` | text | Текст |
| `delivery_status` | enum | `sent`, `delivered`, `failed` |
| `created_at` | datetime | Дата создания |

---

## 2. Связи между сущностями

- **Zone 1 — M TrainingSlot** — в зоне проводится много тренировок.
- **Instructor 1 — M TrainingSlot** — инструктор ведёт много слотов.
- **TrainingSlot 1 — M Booking** — на слот записывается много клиентов.
- **Client 1 — M Booking** — у клиента много броней.
- **Booking 1 — 0..M RentalItem** — по брони может быть выдано несколько единиц снаряжения.
- **Equipment 1 — M RentalItem** — единица снаряжения может выдаваться много раз.
- **Booking 1 — 0..1 InstructorRating** — по завершённой брони клиент оставляет одну оценку.
- **Instructor 1 — M InstructorRating** — у инструктора много оценок.
- **Client 1 — M Notification** — клиент получает много уведомлений.

---

## 3. ER-диаграмма (Mermaid)

```mermaid
erDiagram
    ClimbingGym ||--o{ Zone : "имеет"
    ClimbingGym ||--o{ TrainingSlot : "проводит"
    Zone ||--o{ TrainingSlot : "вмещает"
    Instructor ||--o{ TrainingSlot : "ведёт"
    TrainingSlot ||--o{ Booking : "включает"
    Client ||--o{ Booking : "создаёт"
    Booking ||--o{ RentalItem : "получает"
    Equipment ||--o{ RentalItem : "выдаётся по"
    Booking ||--o| InstructorRating : "оценивается"
    Instructor ||--o{ InstructorRating : "получает"
    Client ||--o{ Notification : "получает"

    Zone {
        int zone_id PK
        string name
        enum level "beginner|advanced"
        int capacity "8 or 16"
    }

    Instructor {
        int instructor_id PK
        string full_name
        decimal average_rating
    }

    TrainingSlot {
        int slot_id PK
        int zone_id FK
        int instructor_id FK
        datetime start_datetime
        int duration_minutes
        enum status "active|cancelled_by_gym|completed"
        string cancellation_reason
        datetime cancelled_at
    }

    Client {
        int client_id PK
        string full_name
        string phone
        string email
        boolean is_regular
        datetime created_at
    }

    Booking {
        int booking_id PK
        int client_id FK
        int slot_id FK
        enum status "active|cancelled_by_client|cancelled_by_gym|completed"
        enum equipment_option "own|rental"
        boolean is_late_cancellation
        datetime created_at
        datetime cancelled_at
    }

    Equipment {
        int equipment_id PK
        enum type "shoes|harness"
        decimal rental_price
        int total_count
        int available_count
    }

    RentalItem {
        int rental_id PK
        int booking_id FK
        int equipment_id FK
    }

    InstructorRating {
        int rating_id PK
        int booking_id FK "unique"
        int instructor_id FK
        int client_id FK
        int stars "1..5"
        text comment
        datetime created_at
    }

    Notification {
        int notification_id PK
        int client_id FK
        int booking_id FK "nullable"
        enum type "cancellation|reminder"
        string title
        text message
        enum delivery_status "sent|delivered|failed"
        datetime created_at
    }