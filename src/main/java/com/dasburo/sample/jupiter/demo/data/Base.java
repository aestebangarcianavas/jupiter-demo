package com.dasburo.sample.jupiter.demo.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BaseClass provides generic Hash, Equals, ToString methods using org.apache.commons.lang3.builder.
 *
 * @author Ana Esteban Garcia-Navas(<a href="mailto:ramoni.esteban@gmail.com">ramoni</a>)
 */
public abstract class Base {

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
