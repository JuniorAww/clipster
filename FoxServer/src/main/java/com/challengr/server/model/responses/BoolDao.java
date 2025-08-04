package com.challengr.server.model.responses;

import org.springframework.http.ResponseEntity;

public class BoolDao {
    private final boolean success;

    public BoolDao(boolean bool) {
        this.success = bool;
    }
    public static ResponseEntity<BoolDao> ok(boolean bool) {
        return ResponseEntity.ok(new BoolDao(bool));
    }
}
