package service;

import domen.Policy;
import domen.RiskType;
import java.math.BigDecimal;

public class TheftPremiumCalculatorImpl implements ConditionCalculator {

	private final Double DEFAULT_COEFFICIENT = 0.11;
	private final Double CONDITION_COEFFICIENT = 0.05;
	private final BigDecimal CONDITION_SUM = new BigDecimal("15");
	private final CalculationUtility utility = new CalculationUtility();

	@Override
	public BigDecimal calculateByConditions(Policy policy) {

		BigDecimal theftSum = utility.calculate(policy, RiskType.THEFT);

		if (theftSum.compareTo(CONDITION_SUM) > 0) {
			return theftSum.multiply(BigDecimal.valueOf(CONDITION_COEFFICIENT));
		}
		return theftSum.multiply(BigDecimal.valueOf(DEFAULT_COEFFICIENT));
	}

}
