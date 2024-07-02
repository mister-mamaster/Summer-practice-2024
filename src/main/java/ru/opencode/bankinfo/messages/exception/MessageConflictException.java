package ru.opencode.bankinfo.messages.exception;

import ru.opencode.bankinfo.exception.ConflictException;

public class MessageConflictException extends ConflictException {
    public MessageConflictException(String message) {
        super(message);
    }
}
