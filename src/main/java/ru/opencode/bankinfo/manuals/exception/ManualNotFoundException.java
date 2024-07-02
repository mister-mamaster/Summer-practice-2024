package ru.opencode.bankinfo.manuals.exception;

import ru.opencode.bankinfo.exception.NotFoundException;

public class ManualNotFoundException extends NotFoundException {

    public ManualNotFoundException(String message) {
        super(message);
    }
}
