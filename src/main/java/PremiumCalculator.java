import domen.Policy;
import service.FirePremiumCalculatorImpl;
import service.TheftPremiumCalculatorImpl;

import java.math.BigDecimal;

public class PremiumCalculator {

	private final FirePremiumCalculatorImpl fireCalculator = new FirePremiumCalculatorImpl();
	private final TheftPremiumCalculatorImpl theftCalculator = new TheftPremiumCalculatorImpl();

	BigDecimal calculate(Policy policy) {
		return fireCalculator.calculateByConditions(policy)
				.add(theftCalculator.calculateByConditions(policy));
	}
}
