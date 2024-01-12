package service;

import domen.MovableProperty;
import domen.NonMovableProperty;
import domen.Policy;
import domen.RiskType;
import java.math.BigDecimal;
import java.util.Collections;

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
				.filter(property -> property != null && property.getRiskTypes() != null &&
						property.getRiskTypes().contains(RiskType.THEFT) && property.getCost() != null)
				.map(NonMovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getTheftSumMovable(Policy policy) {

		return policy.getNonMovableProperties().stream()
				.flatMap(nonMovableProperty -> nonMovableProperty.getMovableProperties()
						.orElse(Collections.emptyList()).stream())
				.filter(property -> property.getRiskTypes().contains(RiskType.THEFT))
				.map(MovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
