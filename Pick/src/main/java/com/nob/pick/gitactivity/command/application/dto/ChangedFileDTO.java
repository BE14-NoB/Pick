package com.nob.pick.gitactivity.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangedFileDTO {
    private String path;
    private String type;
    private String diff;
}