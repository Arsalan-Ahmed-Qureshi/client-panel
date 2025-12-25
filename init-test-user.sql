-- SQL Script to add test user to the users table
-- Run this manually if needed

-- INSERT test user (password: password123)
INSERT INTO users (client_id, email, name, mobile, phone_number_id, pass, chat_prefix, role, status, created_at, updated_at)
VALUES (
    'test001',
    'admin@example.com',
    'Admin User',
    '1234567890',
    '123456789012345',
    '$2a$10$7J.7V5P5J5V5P5J5V5P5J5P5J5V5P5J5V5P5J5V5P5J5V5P5J5V5P5J5',
    'Welcome to our service!',
    'ROLE_ADMIN',
    'Active',
    NOW(),
    NOW()
);

-- If you want to add another test user:
-- INSERT INTO users (client_id, email, name, mobile, phone_number_id, pass, chat_prefix, role, status, created_at, updated_at)
-- VALUES (
--     'test002',
--     'user@example.com',
--     'Test User',
--     '9876543210',
--     '123456789012346',
--     '$2a$10$7J.7V5P5J5V5P5J5V5P5J5P5J5V5P5J5V5P5J5V5P5J5V5P5J5V5P5J5',
--     'Test chat prefix',
--     'ROLE_CLIENT',
--     'Active',
--     NOW(),
--     NOW()
-- );

-- Note: The password hash above is just a placeholder. Use the encoded password from the application.
-- Better approach: Let the application auto-create test user on first run.

-- Check users:
-- SELECT client_id, email, name, status, role FROM users;

-- Delete all users if needed:
-- DELETE FROM users;
