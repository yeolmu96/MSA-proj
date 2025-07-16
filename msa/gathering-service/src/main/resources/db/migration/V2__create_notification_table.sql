CREATE TABLE notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    gathering_id BIGINT NOT NULL,
    application_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notification_account_id ON notification (account_id);
CREATE INDEX idx_notification_is_read ON notification (is_read);
