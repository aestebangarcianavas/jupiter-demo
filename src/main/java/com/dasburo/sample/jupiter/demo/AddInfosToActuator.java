package com.dasburo.sample.jupiter.demo;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a possibility to define additional Data to be seen while calling
 * .../actuator/info.
 */
@Component
public class AddInfosToActuator implements InfoContributor {

    /**
     * add additional infos details, like contact details of developers or other, redefine this
     * method to print useful information into /actuator/info.
     *
     * @param builder the {@link Info.Builder} which is used to add more details
     */
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> devs = new HashMap<>();
        devs.put("Ana Esteban Garcia-Navas", "ags@dasburo.com");
        builder.withDetail("Developers", devs);
    }
}
