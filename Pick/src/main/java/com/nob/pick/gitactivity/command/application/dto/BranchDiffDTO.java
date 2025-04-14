package com.nob.pick.gitactivity.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDiffDTO {
    private List<ChangedFileDTO> files;
    private int addedLines;
    private int removedLines;
}
