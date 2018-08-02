package com.codecool.queststore.model.server.session;

import java.io.Serializable;
import java.net.HttpCookie;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L; // necessary UID for serialization, but JVM can generate it.
    private final UUID uuid;
    private final int USER_ID;
    private LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(2);

    public Session(int userId) {
        this.uuid = UUID.randomUUID();
        this.USER_ID = userId;
    }

    void RenewExpirationDate() {
        this.expirationDate = LocalDateTime.now().plusMinutes(2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return Objects.equals(uuid, session.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }

    UUID getUuid() {
        return uuid;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public HttpCookie getCookie() {

        return new HttpCookie("ID-Session",this.uuid.toString());

    }

    LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
