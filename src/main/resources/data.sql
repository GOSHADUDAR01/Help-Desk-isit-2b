INSERT INTO tickets (title, description, status, created_at, customer_name)
SELECT 'Не работает принтер',
       'Принтер в кабинете 204 не печатает документы. После перезагрузки ошибка повторяется.',
       'NEW',
       TIMESTAMP '2026-05-14 09:00:00',
       'Иван Иванов'
WHERE NOT EXISTS (
    SELECT 1 FROM tickets WHERE title = 'Не работает принтер' AND customer_name = 'Иван Иванов'
);

INSERT INTO tickets (title, description, status, created_at, customer_name)
SELECT 'Нет доступа к корпоративной почте',
       'После смены пароля пользователь не может войти в почту.',
       'IN_PROGRESS',
       TIMESTAMP '2026-05-14 09:30:00',
       'Анна Петрова'
WHERE NOT EXISTS (
    SELECT 1 FROM tickets WHERE title = 'Нет доступа к корпоративной почте' AND customer_name = 'Анна Петрова'
);

INSERT INTO tickets (title, description, status, created_at, customer_name)
SELECT 'Не открывается CRM',
       'При входе в CRM появляется сообщение об ошибке.',
       'NEW',
       TIMESTAMP '2026-05-14 10:15:00',
       'Сергей Смирнов'
WHERE NOT EXISTS (
    SELECT 1 FROM tickets WHERE title = 'Не открывается CRM' AND customer_name = 'Сергей Смирнов'
);

INSERT INTO tickets (title, description, status, created_at, customer_name)
SELECT 'Заменить картридж',
       'На принтере отображается низкий уровень тонера.',
       'RESOLVED',
       TIMESTAMP '2026-05-13 16:20:00',
       'Мария Орлова'
WHERE NOT EXISTS (
    SELECT 1 FROM tickets WHERE title = 'Заменить картридж' AND customer_name = 'Мария Орлова'
);

INSERT INTO tickets (title, description, status, created_at, customer_name)
SELECT 'Проблема с VPN',
       'Пользователь не может подключиться к VPN из дома.',
       'NEW',
       TIMESTAMP '2026-05-14 11:05:00',
       'Иван Соколов'
WHERE NOT EXISTS (
    SELECT 1 FROM tickets WHERE title = 'Проблема с VPN' AND customer_name = 'Иван Соколов'
);

INSERT INTO tickets (title, description, status, created_at, customer_name)
SELECT 'Обновить антивирус',
       'На рабочей станции требуется обновить антивирусные базы.',
       'RESOLVED',
       TIMESTAMP '2026-05-13 14:10:00',
       'Ольга Кузнецова'
WHERE NOT EXISTS (
    SELECT 1 FROM tickets WHERE title = 'Обновить антивирус' AND customer_name = 'Ольга Кузнецова'
);
