package com.campusride.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VagasCompativelComVeiculoValidator
        implements ConstraintValidator<VagasCompativelComVeiculo, PossuiVeiculoEVagas> {

    @Override
    public boolean isValid(PossuiVeiculoEVagas valor, ConstraintValidatorContext context) {
        if (valor == null || valor.getTipoVeiculo() == null || valor.getVagasTotais() == null) {
            // ausencia de dado e responsabilidade de outras anotacoes (@NotNull)
            return true;
        }

        int capacidadeMaxima = valor.getTipoVeiculo().getCapacidadeMaximaVagas();
        boolean valido = valor.getVagasTotais() >= 1 && valor.getVagasTotais() <= capacidadeMaxima;

        if (!valido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "vagasTotais (" + valor.getVagasTotais() + ") excede a capacidade do veiculo "
                                    + valor.getTipoVeiculo() + " (maximo " + capacidadeMaxima + ")")
                    .addPropertyNode("vagasTotais")
                    .addConstraintViolation();
        }

        return valido;
    }
}
