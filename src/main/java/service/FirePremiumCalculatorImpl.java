package service;

import domen.MovableProperty;
import domen.NonMovableProperty;
import domen.Policy;
import domen.RiskType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

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

		Optional<NonMovableProperty> optNonMovProperty = policy.getNonMovableProperties().stream()
				.findFirst();

		BigDecimal result = optNonMovProperty.map(nonMovableProperty -> {
			return policy.getNonMovableProperties().stream()
					.filter(property -> property.getRiskTypes().contains(RiskType.FIRE))
					.map(NonMovableProperty::getCost)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}).orElseGet(() -> {
			return BigDecimal.ZERO;
		});
		return result;
	}

	private static BigDecimal getFireSumMovable(Policy policy) {

		return policy.getNonMovableProperties().stream()
				.flatMap(nonMovableProperty -> nonMovableProperty.getMovableProperties()
						.orElse(Collections.emptyList()).stream())
				.filter(property -> property.getRiskTypes().contains(RiskType.FIRE))
				.map(MovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

	}

}
