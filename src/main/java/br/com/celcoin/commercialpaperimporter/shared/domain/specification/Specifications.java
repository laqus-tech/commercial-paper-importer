package br.com.celcoin.commercialpaperimporter.shared.domain.specification;

import java.util.Objects;
import java.util.function.BiFunction;

public interface Specifications<T> {

    static <T> AbstractSpecification<T> basic(
            BiFunction<T, AbstractSpecification<T>, Boolean> function) {
        return basic(null, function);
    }

    static <T> AbstractSpecification<T> basic(String message,
                                              BiFunction<T, AbstractSpecification<T>, Boolean> function) {
        return new FunctionValidator<>(message, function);
    }

    class FunctionValidator<T> extends AbstractSpecification<T> {

        private final BiFunction<T, AbstractSpecification<T>, Boolean> function;

        FunctionValidator(String message, BiFunction<T, AbstractSpecification<T>, Boolean> function) {
            Objects.requireNonNull(function, "A função da especificação não pode ser nula");
            this.setMessage(message);
            this.function = function;
        }

        @Override
        public boolean isSatisfiedBy(T candidate) {
            return this.function.apply(candidate, this);
        }

    }
}