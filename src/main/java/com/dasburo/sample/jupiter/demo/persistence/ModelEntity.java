package com.dasburo.sample.jupiter.demo.persistence;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entity class to store a Model.
 * * @author <a href="mailto:ags@dasburo.com">Ana Esteban Garcia-Navas</a>
 */
@Entity
@Table(name = "model")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelEntity {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @NotEmpty
    private String code;
    @Column(name = "model_extension")
    private String modelExtension;
    @Column(name = "model_version")
    private long modelVersion;
    @NotNull
    @Column(name = "model_year")
    private int modelYear;
    @NotNull
    @Column(name = "market_id")
    private long marketId;

    /**
     * default to string impl using reflections to show every field using
     * {@link ToStringStyle#JSON_STYLE}.
     *
     * @return the string of elements
     */
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
    }

    /**
     * default equals impl using reflections to take care of each field.
     *
     * @param other the {@link Object} to compare with
     * @return either true (equals) or false
     */
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    /**
     * default hashCode generation using reflections to take care of each field.
     *
     * @return the correspoding hash
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }


}
