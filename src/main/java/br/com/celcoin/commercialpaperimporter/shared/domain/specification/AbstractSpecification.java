package br.com.celcoin.commercialpaperimporter.shared.domain.specification;

import br.com.celcoin.commercialpaperimporter.shared.domain.exception.GenericSpecificationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpecification<T> implements Specification<T> {

  private String message;

  public abstract boolean isSatisfiedBy(T t);

  public void check(T t) throws GenericSpecificationException {
    boolean isValid = isSatisfiedBy(t);
    if (!isValid) {
      throw new GenericSpecificationException(this.getMessage());
    }
  }

  public Specification<T> and(final Specification<T> specification) {
    return new AndSpecification<>(this, specification);
  }
}
