-- ==========================================
-- 4. SEED DATA
-- Initial data required for the application to function.
-- ==========================================

-- Roles
INSERT INTO roles (name) VALUES ('ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles (name) VALUES ('LIBRARIAN') ON CONFLICT DO NOTHING;
INSERT INTO roles (name) VALUES ('MEMBER') ON CONFLICT DO NOTHING;
