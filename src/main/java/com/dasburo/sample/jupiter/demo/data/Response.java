package com.dasburo.sample.jupiter.demo.data;

import lombok.*;

/**
 * Response for the Model REST Service.
 *
 * @author <a href="mailto:ags@dasburo.com">Ana Esteban Garcia-Navas</a>
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.MODULE)
@NoArgsConstructor(access = AccessLevel.MODULE)
public class Response extends Base {

    private String errorMessage;
    private Object model;

}
