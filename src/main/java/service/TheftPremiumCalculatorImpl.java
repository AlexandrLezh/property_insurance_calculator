package service;

import domen.MovableProperty;
import domen.Policy;

import java.math.BigDecimal;

public class TheftPremiumCalculatorImpl implements ConditionCalculator {

	private final Double DEFAULT_COEFFICIENT = 0.11;
	private final Double CONDITION_COEFFICIENT = 0.05;
	private final BigDecimal CONDITION_SUM = BigDecimal.valueOf(15);

	@Override
	public BigDecimal calculateByConditions(Policy policy) {
		BigDecimal theftSum = policy.getNonMovableProperties().stream()
				.flatMap(nonMovableProperty -> nonMovableProperty.getMovableProperties().stream())
				.filter(movableProperty -> movableProperty.getRiskTypes().toString().equals("THEFT"))
				.map(MovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (theftSum.compareTo(CONDITION_SUM) > 0) {
			return theftSum.multiply(BigDecimal.valueOf(CONDITION_COEFFICIENT));
		}
		return theftSum.multiply(BigDecimal.valueOf(DEFAULT_COEFFICIENT));
	}
}
