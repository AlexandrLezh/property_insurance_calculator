package service;

import domen.MovableProperty;
import domen.NonMovableProperty;
import domen.Policy;
import domen.RiskType;
import java.math.BigDecimal;
import java.util.Collection;

public class FirePremiumCalculatorImpl implements ConditionCalculator {

	private final Double DEFAULT_COEFFICIENT = 0.014;
	private final Double CONDITION_COEFFICIENT = 0.024;
	private final BigDecimal CONDITION_SUM = new BigDecimal("100");

	@Override
	public BigDecimal calculateByConditions(Policy policy) {

		BigDecimal fireSumMovable = getFireSumMovable(policy);
		BigDecimal fireSumNonMovable = getFireSumNonMovable(policy);
		BigDecimal fireSum = fireSumNonMovable.add(fireSumMovable);

		if (fireSum.compareTo(CONDITION_SUM) > 0) {
			return fireSum.multiply(BigDecimal.valueOf(CONDITION_COEFFICIENT));
		}
		return fireSum.multiply(BigDecimal.valueOf(DEFAULT_COEFFICIENT));
	}

	private static BigDecimal getFireSumNonMovable(Policy policy) {
		return policy.getNonMovableProperties().stream()
				.filter(nonMovableProperty -> nonMovableProperty.getRiskTypes().contains(RiskType.FIRE))
				.map(NonMovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getFireSumMovable(Policy policy) {
		return policy.getNonMovableProperties().stream()
				.map(NonMovableProperty::getMovableProperties)
				.flatMap(Collection::stream)
				.filter(movableProperty -> movableProperty.getRiskTypes().contains(RiskType.FIRE))
				.map(MovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
