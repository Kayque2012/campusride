package com.campusride.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VagasCompativelComVeiculoValidator.class)
@Documented
public @interface VagasCompativelComVeiculo {

    String message() default "numero de vagas incompativel com o tipo de veiculo informado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
