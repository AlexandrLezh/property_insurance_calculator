package service;

import domen.MovableProperty;
import domen.NonMovableProperty;
import domen.Policy;
import domen.RiskType;
import java.math.BigDecimal;
import java.util.Collections;

public class CalculationUtility {

	public BigDecimal calculate(Policy policy, RiskType riskType) {

		BigDecimal fireSumMovable = getSumMovable(policy, riskType);
		BigDecimal fireSumNonMovable = getSumNonMovable(policy, riskType);

		return fireSumNonMovable.add(fireSumMovable);
	}

	private static BigDecimal getSumNonMovable(Policy policy, RiskType riskType) {

		return policy.getNonMovableProperties().stream()
				.filter(property -> property != null && property.getRiskTypes() != null &&
						property.getRiskTypes().contains(riskType) && property.getCost() != null)
				.map(NonMovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getSumMovable(Policy policy, RiskType riskType) {

		return policy.getNonMovableProperties().stream()
				.flatMap(nonMovableProperty -> nonMovableProperty.getMovableProperties()
						.orElse(Collections.emptyList()).stream())
				.filter(property -> property.getRiskTypes().contains(riskType))
				.map(MovableProperty::getCost)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
