CREATE TABLE detail (
    PRIMARY KEY (id),
    id BIGINT NOT NULL IDENTITY(1,1),
    description VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE image (
    PRIMARY KEY (id),
    id BIGINT NOT NULL IDENTITY(1,1),
    detail_id BIGINT NOT NULL,
    created DATETIME NOT NULL,
    path VARCHAR(255) NOT NULL,
    FOREIGN KEY (detail_id) REFERENCES detail(id)
);