package com.epam.rd.autotasks;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class Task {
    private String description;
    private Employee assignee, reviewer;
}
