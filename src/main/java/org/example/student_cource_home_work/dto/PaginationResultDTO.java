package org.example.student_cource_home_work.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationResultDTO<T> {
    private Long totalSize;
    private List<?> list;
}