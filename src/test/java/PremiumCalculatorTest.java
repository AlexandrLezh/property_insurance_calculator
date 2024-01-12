import domen.*;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PremiumCalculatorTest {
	private final PremiumCalculator premiumCalculator = new PremiumCalculator();

	@Test
	public void oneObjectFire() {

		NonMovableProperty property1 = createProperty(1L,
				"House",
				"1000.00",
				RiskType.FIRE);

		Policy policy = createPolicy("LV-2", StatusOfPolicy.REGISTERED, property1);

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("24").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}

	@Test
	public void oneObjectOneSubObjectFireTheft() {
		MovableProperty pc = createMovableProperty(1L,
				"McBook",
				"500.00",
				RiskType.FIRE);

		NonMovableProperty property1 = createProperty(1L,
				"House",
				"500.00",
				RiskType.FIRE,
				pc);

		Policy policy = createPolicy("LV-2", StatusOfPolicy.REGISTERED, property1);

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("24").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}

	@Test
	public void twoObjectsOneSubObjectFireTheft() {
		MovableProperty pc = createMovableProperty(1L,
				"McBook",
				"500.00",
				RiskType.FIRE);

		NonMovableProperty property1 = createProperty(1L,
				"House",
				"500.00",
				RiskType.FIRE,
				pc);

		MovableProperty fridge = createMovableProperty(2L,
				"Fridge",
				"500.00",
				RiskType.FIRE);

		NonMovableProperty property2 = createProperty(2L,
				"House 2",
				"500.00",
				RiskType.FIRE,
				fridge);

		Policy policy = createPolicy("LV-1", StatusOfPolicy.REGISTERED, property1, property2);

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("48").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}

	public static MovableProperty createMovableProperty(Long id, String name, String value, RiskType riskType) {
		MovableProperty movableProperty = new MovableProperty();
		movableProperty.setId(id);
		movableProperty.setName(name);
		movableProperty.setCost(new BigDecimal(value));
		movableProperty.setRiskTypes(List.of(riskType));
		return movableProperty;
	}

	public static NonMovableProperty createProperty(Long id, String name, String value, RiskType riskType, MovableProperty subObject) {
		NonMovableProperty property = new NonMovableProperty();
		property.setId(id);
		property.setName(name);
		property.setCost(new BigDecimal(value));
		property.setRiskTypes(List.of(riskType));
		property.setMovableProperties(List.of(subObject));
		return property;
	}
	public static NonMovableProperty createProperty(Long id, String name, String value, RiskType riskType) {
		NonMovableProperty property = new NonMovableProperty();
		property.setId(id);
		property.setName(name);
		property.setCost(new BigDecimal(value));
		property.setRiskTypes(List.of(riskType));
		return property;
	}

	public static Policy createPolicy(String policyNumber, StatusOfPolicy status, NonMovableProperty... properties) {
		Policy policy = new Policy();
		policy.setId(policyNumber);
		policy.setStatus(status);
		policy.setNonMovableProperties(Arrays.asList(properties));
		return policy;
	}


}