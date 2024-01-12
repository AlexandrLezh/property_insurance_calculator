package service;

import domen.MovableProperty;
import domen.NonMovableProperty;
import domen.Policy;
import domen.RiskType;
import java.math.BigDecimal;
import java.util.Collection;


public class TheftPremiumCalculatorImpl implements ConditionCalculator {

	private final Double DEFAULT_COEFFICIENT = 0.11;
	private final Double CONDITION_COEFFICIENT = 0.05;
	private final BigDecimal CONDITION_SUM = new BigDecimal("15");

	@Override
	public BigDecimal calculateByConditions(Policy policy) {

		BigDecimal theftSumMovable = getTheftSumMovable(policy);
		BigDecimal theftSumNonMovable = getTheftSumNonMovable(policy);
		BigDecimal theftSum = theftSumNonMovable.add(theftSumMovable);

		if (theftSum.compareTo(CONDITION_SUM) > 0) {
			return theftSum.multiply(BigDecimal.valueOf(CONDITION_COEFFICIENT));
		}
		return theftSum.multiply(BigDecimal.valueOf(DEFAULT_COEFFICIENT));
	}

	private static BigDecimal getTheftSumNonMovable(Policy policy) {
		return policy.getNonMovableProperties().stream()
				.filter(nonMovableProperty -> nonMovableProperty.getRiskTypes().contains(RiskType.THEFT))
				.map(NonMovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getTheftSumMovable(Policy policy) {
		return policy.getNonMovableProperties().stream()
				.map(NonMovableProperty::getMovableProperties)
				.flatMap(Collection::stream)
				.filter(movableProperty -> movableProperty.getRiskTypes().contains(RiskType.THEFT))
				.map(MovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
