package ru.opencode.bankinfo.util;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PaginatedResponseDTO {

    private Object items;
    private Object config;
}
