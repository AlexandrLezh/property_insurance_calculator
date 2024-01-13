package service;

import domen.Policy;
import domen.RiskType;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FirePremiumCalculatorImpl implements ConditionCalculator {

	private final Double DEFAULT_COEFFICIENT = 0.014;
	private final Double CONDITION_COEFFICIENT = 0.024;
	private final BigDecimal CONDITION_SUM = new BigDecimal("100");
	private final CalculationUtility utility = new CalculationUtility();

	@Override
	public BigDecimal calculateByConditions(Policy policy) {

		BigDecimal fireSum = utility.calculate(policy, RiskType.FIRE);

		if (fireSum.compareTo(CONDITION_SUM) > 0) {
			return fireSum.multiply(BigDecimal.valueOf(CONDITION_COEFFICIENT))
					.setScale(2, RoundingMode.HALF_UP);
		}
		return fireSum.multiply(BigDecimal.valueOf(DEFAULT_COEFFICIENT))
				.setScale(2, RoundingMode.HALF_UP);
	}

}
