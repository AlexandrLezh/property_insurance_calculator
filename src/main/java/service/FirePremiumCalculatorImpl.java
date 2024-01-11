package service;

import domen.MovableProperty;
import domen.Policy;
import java.math.BigDecimal;

public class FirePremiumCalculatorImpl implements ConditionCalculator {

	private final Double DEFAULT_COEFFICIENT = 0.014;
	private final Double CONDITION_COEFFICIENT = 0.024;
	private final BigDecimal CONDITION_SUM = BigDecimal.valueOf(100);

	@Override
	public BigDecimal calculateByConditions(Policy policy) {
		BigDecimal fireSum = policy.getNonMovableProperties().stream()
				.flatMap(nonMovableProperty -> nonMovableProperty.getMovableProperties().stream())
				.filter(movableProperty -> movableProperty.getRiskTypes().toString().equals("FIRE"))
				.map(MovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (fireSum.compareTo(CONDITION_SUM) > 0) {
			return fireSum.multiply(BigDecimal.valueOf(CONDITION_COEFFICIENT));
		}
		return fireSum.multiply(BigDecimal.valueOf(DEFAULT_COEFFICIENT));
	}

}
