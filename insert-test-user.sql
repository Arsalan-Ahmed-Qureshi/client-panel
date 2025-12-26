-- First, delete all existing users (optional - only if you want to start fresh)
DELETE FROM users;

-- Insert test user with admin@gmail.com / admin
-- Password 'admin' hashed with BCrypt
INSERT INTO users (client_id, email, name, mobile, phone_number_id, pass, chat_prefix, role, status, created_at, updated_at)
VALUES (
    'test001',
    'admin@gmail.com',
    'Admin User',
    '1234567890',
    '123456789012345',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/DiO',
    'Welcome!',
    'ROLE_ADMIN',
    'Active',
    NOW(),
    NOW()
);

-- Verify the user was inserted
SELECT * FROM users;
