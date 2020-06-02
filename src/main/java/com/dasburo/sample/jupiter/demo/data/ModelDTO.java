package com.dasburo.sample.jupiter.demo.data;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object that contains a ModelEntity {@link com.dasburo.sample.jupiter.demo.persistence.ModelEntity}.
 *
 * @author <a href="mailto:ags@dasburo.com">Ana Esteban Garcia-Navas</a>
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.MODULE)
@NoArgsConstructor(access = AccessLevel.MODULE)
public class ModelDTO extends Base {
    private long id;
    @NotEmpty
    private String code;
    private String modelExtension;
    private long modelVersion;
    @NotNull
    private int modelYear;
    @NotNull
    private long marketId;
}
